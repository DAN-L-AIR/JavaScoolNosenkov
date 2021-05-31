package hw12;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class JMM_prog1 {

    public static void main(String[] args) {
        Task<String> task = new Task<String>(()-> "Callable:" + LocalTime.now().toString());
        for(int i = 0; i < 5; i++){
            new Thread(()->{
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1, 1000));
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":task.get() = " + task.get());
            }).start();
        }
    }
}
