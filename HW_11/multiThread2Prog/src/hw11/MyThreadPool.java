package hw11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyThreadPool implements ThreadPool {
    private final int fixedMaxThread;
    private final int dynamicMaxThread;
    private int dynamicThread = 0;
    private BlockingQueue<Runnable> task = new LinkedBlockingQueue<>();
    private List<Thread> trPool = new ArrayList<>();

    public MyThreadPool(int fixedMaxThread, int dynamicMaxThread) {
        this.fixedMaxThread = fixedMaxThread;
        this.dynamicMaxThread = dynamicMaxThread;
    }

    @Override
    public void start() {
        for (int i = 0; i < fixedMaxThread; i++) {
            addThread();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if(dynamicThread < dynamicMaxThread){
            if(fixedMaxThread + dynamicThread == busyThreads()) {
                dynamicThread++;
                addThread();
                System.out.println("Thread created (thread pool size= " + trPool.size() + " )");
            }
        }
        task.add(runnable);
        System.out.println("Job added to queue (queue size=" + task.size() +")");
    }

    @Override
    public void stop() {
        trPool.forEach((o)->o.interrupt());
        trPool.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void addThread(){
        Thread t = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    task.take().run();
                    System.out.println(Thread.currentThread().getName() + " job terminated(" + Thread.currentThread().getName()+ ")");
                    if(task.size() == 0 && dynamicThread > 0){
                        dynamicThread--;
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + " pool shrinked");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName() + " terminated(" + Thread.currentThread().getName()+ ")");
        });
        trPool.add(t);
        t.start();
    }

    private int busyThreads(){
        int count = 0;
        Iterator<Thread> it = trPool.iterator();
        while(it.hasNext()){
            Thread.State state = it.next().getState();
            if(state == Thread.State.RUNNABLE || state == Thread.State.TIMED_WAITING){
                count++;
            }
        }
        System.out.println("Busy threads:" + count);
        return count;
    }
}
