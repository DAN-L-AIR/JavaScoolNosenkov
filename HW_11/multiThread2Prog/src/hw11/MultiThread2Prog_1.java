package hw11;

import java.util.concurrent.TimeUnit;

public class MultiThread2Prog_1 {

    public static void main(String[] args) {
        System.out.println("Д/з по теме: Многопоточное программирование 2. Пул потоков");
        MyThreadPool myThreadPool = new MyThreadPool(4, 3);
        myThreadPool.start();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            myThreadPool.execute(() -> {
                final int jobId = finalI;
                System.out.println("job start " + jobId + "(" + Thread.currentThread().getName()+ ")");
                for (int j = 0; j < 10 && !Thread.currentThread().isInterrupted(); j++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(" job " + jobId + " interrupted(" + Thread.currentThread().getName()+ ")");
                    }
                    System.out.println("job " + jobId+ " work stage = " + j);
                }
                System.out.println("job " + jobId + " finished(" + Thread.currentThread().getName()+ ")");
            });
        }
        try {
            Thread.sleep(8_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThreadPool.stop();
    }
}
