package hw12;

class myTask implements Runnable {
    final int id;
    final boolean doException;
    final int delayMs;

    public myTask(int id, boolean doException, int delay_ms) {
        this.id = id;
        this.doException = doException;
        this.delayMs = delay_ms;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task:" + id);
        if (doException) {
            int x, y, z;
            x = 1;
            y = 0;
            z = x / y;
        }
        //System.out.println("Task:" + id);
    }
}

public class JMM_prog2 {

    public static void main(String[] args) {
        System.out.println("Д/З JMM2");
        ExecutionManager executionManager = new MyExecutionManager(2);
        Runnable tasks[] = new Runnable[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new myTask(i, i % 3 == 0, 1000);
        }
        Context context = executionManager.execute(() -> System.out.println("CallBack"), tasks);
        while (!context.isFinished()){
            if( context.getCompletedTaskCount() == 4){
                context.interrupt();
            }
            System.out.println("context.getCompletedTaskCount() = " + context.getCompletedTaskCount());
            System.out.println("context.getFailedTaskCount() = " + context.getFailedTaskCount());
            System.out.println("context.getInterruptedTaskCount() = " + context.getInterruptedTaskCount());
            System.out.println("---------------------------------------------------");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("context.getCompletedTaskCount() = " + context.getCompletedTaskCount());
        System.out.println("context.getFailedTaskCount() = " + context.getFailedTaskCount());
        System.out.println("context.getInterruptedTaskCount() = " + context.getInterruptedTaskCount());
        System.out.println("---------------------------------------------------");
    }
}
