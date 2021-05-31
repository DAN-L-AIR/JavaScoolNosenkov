package hw12;

import java.util.concurrent.Callable;

class hw12Exception extends RuntimeException{
    public String toString(){
        return("hw12Exception");
    }
}

public class Task<T> {
    final long startTime = System.currentTimeMillis();
    private Callable<? extends T> callable;
    /*volatile*/ T result = null;
    private Object s = new Object();
    private String details;


    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws hw12Exception {
        if(result != null){
            details = " return result immediatly_" + (System.currentTimeMillis() - startTime);
        }
        else {
            synchronized (s) {
                if (result == null) {
                    try {
                        Thread.sleep(500);
                        result = callable.call();
                        details =" return after call callable_"  + (System.currentTimeMillis() - startTime);
                    } catch (Exception e) {
                        throw new hw12Exception();
                    }
                }
                else{
                    details = " return after synchronized_" + (System.currentTimeMillis() - startTime);
                }
            }
        }
        return (T) (result + details);
    }
}
