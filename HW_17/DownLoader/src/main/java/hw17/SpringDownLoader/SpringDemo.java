package hw17.SpringDownLoader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;
@Configuration
//@ComponentScan("hw17.SpringDownLoader")
public class SpringDemo {
    public static void main(String[] arg) {
        System.out.println("Д/з Spring demo");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");
//
        Configurator configurator = applicationContext.getBean(Configurator.class);
        //configurator.UpdateXml("download-list1.xml");
//
        LinkManager linkManager = applicationContext.getBean(LinkManagerImpl.class);
        DownLoadManager downLoadManager = applicationContext.getBean(DownLoadManagerImpl.class);

        linkManager.AddForExecution();
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(4));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(downLoadManager.isAllCompleted()){
            System.out.println("Загрузки завершены");
        }
        else{
            System.out.println("Загрузки прерваны");
            downLoadManager.CancellAll();
            downLoadManager.Stop();
        }
    }
}
