package hw8;


import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

class Pair implements Serializable {
    private Object key;
    private Object value;

    public Pair(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}

class CacheDescriptor {
    private final Map<Object, Object> cache = new HashMap<>();
    private String cacheFileName;
    private int maxListElements;
    private boolean zip;
    private Boolean[] cachedArgs;

    public String getCacheFileName() {
        return cacheFileName;
    }

    public void setCacheFileName(String cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    public void setMaxListElements(int maxListElements) {
        this.maxListElements = maxListElements;
    }

    public boolean isZip() {
        return zip;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    public Map<Object, Object> getCache() {
        return cache;
    }

    public List<Object> GetKeyArgs(Object[] args) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (cachedArgs[i]) {
                result.add(args[i]);
            }
        }
        return result;
    }

    public void setCachedArgs(Boolean[] cachedArgs) {
        this.cachedArgs = cachedArgs;
    }

    public Object TrimResult(Object list) {
        List<Object> result = (List<Object>) list;
        if (Arrays.asList(list.getClass().getInterfaces()).contains(List.class)) {
            if (result.size() > maxListElements) {
                result = result.subList(0, maxListElements);
            }
        }
        return result;
    }
}

class CacheHandler implements InvocationHandler {
    private final Object delegate;
    private final Map<String, CacheDescriptor> cacheTable = new HashMap<>();

    public CacheHandler(Object delegate, String cacheFolder) {
        this.delegate = delegate;

        Method[] mtds1 = delegate.getClass().getDeclaredMethods();
        Class<?>[] cl = delegate.getClass().getInterfaces();
        Method[] mtds = cl[0].getMethods();

        String cacheFileName;
        for (Method me : mtds) {
            if (me.getParameterCount() == 0) continue;
            if (!me.isAnnotationPresent(Cache.class)) continue;
            Cache anno = me.getAnnotation(Cache.class);
            if (anno.cacheType() == _cacheType.IN_MEMORY) continue;
            Boolean[] cachedArgs = GetCachedArgs(anno.identityBy(), me.getParameterTypes());
            if (!Arrays.asList(cachedArgs).contains(true)) continue;
            CacheDescriptor cacheDescriptor = new CacheDescriptor();
            cacheDescriptor.setCachedArgs(cachedArgs);
            cacheDescriptor.setMaxListElements(anno.listMaxLength());
            if (anno.fileNamePrefix().isEmpty()) {
                cacheFileName = me.getName();
            } else {
                cacheFileName = anno.fileNamePrefix();
            }
            cacheDescriptor.setCacheFileName(cacheFileName);

            File f = new File(cacheFolder + "\\" + cacheFileName);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    System.out.println("Ошибка <" + e + "> создания файла для данных кэширования метода: " + me.getName());
                    continue;
                }
            }
            if (f.canRead() && f.length() != 0) {
                try (FileInputStream fin = new FileInputStream(f.getName());
                     ObjectInputStream in = new ObjectInputStream(fin)) {
                    while (fin.available() > 0) {
                        Pair pair = (Pair) in.readObject();
                        cacheDescriptor.getCache().put(pair.getKey(), pair.getValue());
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Ошибка <" + e + "> невозможно открыть файл для данных кэширования метода:" + me.getName());
                    continue;
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Ошибка <" + e + "> ввода/вывода при загрузке кэша метода:" + me.getName());
                    continue;
                }
            }
            cacheTable.put(me.getName(), cacheDescriptor);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        if (!cacheTable.containsKey(method.getName())) {
            result = invoke(method, args);
        } else {
            CacheDescriptor cacheDescriptor = cacheTable.get(method.getName());
            List<Object> keyArgsList = cacheDescriptor.GetKeyArgs(args);
            if (cacheDescriptor.getCache().containsKey(keyArgsList)) {
                result = cacheDescriptor.getCache().get(keyArgsList);
            } else {
                result = cacheDescriptor.TrimResult(invoke(method, args));
                WriteCache(new Pair(keyArgsList, result), cacheDescriptor.getCacheFileName());
                cacheDescriptor.getCache().put(keyArgsList, result);
            }
        }
        return result;
    }

    private void WriteCache(Pair keyValue, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(keyValue);
        } catch (NotSerializableException e) {
            System.out.println("Ошибка: объект:" + keyValue.getValue().getClass() + " не может быть сериализован для записив файл");
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода при кешировании в файл:" + fileName);
        }
    }

    public Boolean[] GetCachedArgs(Class<?>[] annoClass, Class<?>[] methodClass) {
        Boolean[] argCahed = new Boolean[methodClass.length];
        Arrays.fill(argCahed, false);
        int jStart = 0;
        for (Class<?> aClass : annoClass) {
            for (int j = jStart; j < methodClass.length; j++) {
                if (methodClass[j] == aClass) {
                    argCahed[j] = true;
                    jStart = j + 1;
                    break;
                }
            }
            if (jStart == methodClass.length) {
                break;
            }
        }
        return argCahed;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Impossible", e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}

public class CacheProxy {
    private final String rootFolder;

    public CacheProxy(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public Object cache(Object obj) {
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                obj.getClass().getInterfaces(),
                new CacheHandler(obj, rootFolder));
    }
}

