package hw12;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MyExecutionManager implements ExecutionManager {
    private final int threadCount;
    private final int POLL_PERIOD_MS = 100;
    final private BlockingQueue<Runnable> taskQueue;
    volatile boolean cancelled = false;
    private Runnable callback;
    private Integer CompletedTaskCount = 0;
    private Integer FailedTaskCount = 0;
    private Integer threadFinished = 0;

    public MyExecutionManager(int threadCount) {
        this.threadCount = threadCount;
        taskQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        this.callback = callback;
        taskQueue.addAll(Arrays.asList(tasks));

        Thread callbackThread = new Thread(() -> {
            int lThreadFinished;
            do {
                try {
                    Thread.sleep(POLL_PERIOD_MS);
                } catch (InterruptedException e) {
                }
                synchronized (threadFinished) {
                    lThreadFinished = threadFinished;
                }

            } while (lThreadFinished != threadCount);
            if (CompletedTaskCount == tasks.length) {
                callback.run();
            }
        });
        callbackThread.start();
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                while (!cancelled) {
                    try {
                        Runnable task = taskQueue.poll();
                        if (task != null) {
                            task.run();
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        synchronized (FailedTaskCount) {
                            FailedTaskCount++;
                            continue;
                        }
                    }
                    synchronized (CompletedTaskCount) {
                        CompletedTaskCount++;
                    }
                }
                synchronized (threadFinished) {
                    threadFinished++;
                }
            }).start();
        }
        return new Context() {
            @Override
            public int getCompletedTaskCount() {
                synchronized (CompletedTaskCount) {
                    return CompletedTaskCount;
                }
            }

            @Override
            public int getFailedTaskCount() {
                synchronized (FailedTaskCount) {
                    return FailedTaskCount;
                }
            }

            @Override
            public int getInterruptedTaskCount() {
                if (cancelled && isFinished()) {
                    return taskQueue.size();
                } else {
                    return 0;
                }
            }

            @Override
            public void interrupt() {
                cancelled = true;
            }

            @Override
            public boolean isFinished() {
                int lThreadFinished;
                synchronized (threadFinished) {
                    lThreadFinished = threadFinished;
                }
                return lThreadFinished == threadCount;
            }
        };
    }
}
