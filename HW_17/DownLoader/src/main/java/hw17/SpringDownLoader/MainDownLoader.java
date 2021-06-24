package hw17.SpringDownLoader;

import java.util.concurrent.TimeUnit;

public class MainDownLoader {
    public static void main(String[] args) {
        System.out.println("Д/З DownLoader");
        Configurator configurator = new ConfiguratorImpl();
        Logger logger = new LoggerImpl(configurator);
        hw17.SpringDownLoader.OutputInformer outputInformer = new OutputInformerImpl(configurator, logger);
        DownLoadManager downLoadManager = new DownLoadManagerImpl(configurator, logger);
        LinkManager linkManager = new LinkManagerImpl(configurator, logger, downLoadManager, outputInformer);
        linkManager.AddForExecution();
        int count = 1;
        while(!downLoadManager.isAllCompleted()) {
            System.out.println(String.format("Основной поток:Ожидание завершнния %d", count++));
            if(count == 13){
                downLoadManager.CancellAll();
            }
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        downLoadManager.Stop();
        System.out.println("Основной поток зввершён");
    }
}
