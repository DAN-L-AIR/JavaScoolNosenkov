package hw3;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BeanUtils {
     /**
      * Scans object "from" for all getters. If object "to"
      * contains correspondent setter, it will invoke it
      * to set property value for "to" which equals to the property
      * of "from".
      *
      * The type in setter should be compatible to the value returned
      * by getter (if not, no invocation performed).
      * Compatible means that parameter type in setter should
      * be the same or be superclass of the return type of the getter.
      *
      * The method takes care only about public methods.
      *
      * @param to  Object which properties will be set.
      * @param from Object which properties will be used to get values.
      */
    public static void assign(Object to, Object from){
        Method[] fromMethod = from.getClass().getDeclaredMethods();
        for(Method m1 : fromMethod){
            if(Modifier.isPublic(m1.getModifiers()) && m1.getName().startsWith("get") && m1.getParameterCount() == 0){
                Class<?> m1rt = m1.getReturnType();
                if(m1rt.getName().equals("void")){
                    continue;
                }
                String setterName = "set" + m1.getName().substring(3);
                Method m2;
                try {
                     m2 = to.getClass().getMethod(setterName, m1rt);
                } catch (NoSuchMethodException e) {
                    try {
                        m2 = to.getClass().getMethod(setterName, m1rt.getSuperclass());
                    } catch (NoSuchMethodException noSuchMethodException) {
                        continue;
                    }
                }
                Class[] m2pt = m2.getParameterTypes();
                if(m2pt.length != 1){
                    continue;
                }
                if(m2pt[0].getName().equals(m1rt.getName()) || m2pt[0].getName().equals(m1rt.getSuperclass().getName())){
                    try {
                        m2.invoke(to, m1.invoke(from, null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
