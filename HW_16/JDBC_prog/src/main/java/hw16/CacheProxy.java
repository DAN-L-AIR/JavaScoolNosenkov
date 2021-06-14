package hw16;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

class CacheHandler implements InvocationHandler {
    private final Object delegate;
    private final CacheDao cacheDao = new CacheDao();
    public CacheHandler(Object delegate, String cacheFolder) {
        this.delegate = delegate;

        Method[] mtds = delegate.getClass().getDeclaredMethods();

        for (Method me : mtds) {
            if (me.getParameterCount() != 1 || me.getParameterTypes()[0] == Integer.class) continue;
            if (!me.isAnnotationPresent(Cacheable.class)) continue;
            Cacheable anno = me.getAnnotation(Cacheable.class);
            DataSourceHelper.SetDBPath(cacheFolder);
            DataSourceHelper.CreateDb();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Integer> cachedResult = cacheDao.GetResult((Integer)args[0]);
        if (cachedResult == null) {
            cachedResult = (List<Integer>) invoke(method, args);
            cacheDao.PutResult((Integer) args[0], cachedResult);
        }
        return cachedResult;
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
        /*return*/
        Object ret = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), obj.getClass().getInterfaces(),
                new CacheHandler(obj, this.rootFolder));
        return ret;
    }
}

