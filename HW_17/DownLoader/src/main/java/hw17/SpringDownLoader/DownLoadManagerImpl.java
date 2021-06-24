package hw17.SpringDownLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class DownLoadManagerImpl implements DownLoadManager {
    final private Logger logger;
    final private ExecutorService executorService;
    final private List<Future<?>> taskControl;

    @Autowired
    public DownLoadManagerImpl(Configurator configurator, Logger logger) {
        int threadNum;
        this.logger = logger;
        try {
            threadNum = Integer.parseInt(configurator.Value(Configurator.THREAD_POOL_SIZE));
        } catch (NumberFormatException e) {
            logger.Log(String.format("Ошибка: Недопустимое значение параметра %s. Число потоков принято равным 1", Configurator.THREAD_POOL_SIZE));
            threadNum = 1;
        } catch (RuntimeException e) {
            logger.Log(String.format("%s", e.getMessage()));
            threadNum = 1;
        }
        executorService = Executors.newFixedThreadPool(threadNum);
        taskControl = new ArrayList<>();
    }

    public void AddTask(DownLoad task) {
        taskControl.add(executorService.submit(task));
    }

    public void CancellAll() {
        taskControl.forEach((n) -> n.cancel(false));
    }

    public boolean isAllCompleted() {
        for(Future<?> future : taskControl){
            if(!future.isDone()) return  false;
        }
        return true;
    }

    public void Stop() {
        List<Runnable> list = executorService.shutdownNow();
        logger.Log(String.format("Ожидают выполнения %d", list.size()));
    }
}
