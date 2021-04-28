package hw3;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrfLogHandler implements InvocationHandler {
    private  Object delegate;

    public PrfLogHandler(Object delegate) {
        this.delegate = delegate;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[]args) throws Throwable {
        Object result = null;
        if(!method.isAnnotationPresent(Metric.class)){
            result = invoke(method, args);
        } else {
            long ts = System.nanoTime();
            result = invoke(method, args);
            System.out.println("Время работы метода:" + (System.nanoTime() - ts) + " (в наносек)");
        }
        return result;
    }
    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate,args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Impossible",e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}

