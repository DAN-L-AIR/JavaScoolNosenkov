package hw17.SpringDownLoader;

import java.io.*;
import java.net.URL;

public class DownLoadImpl implements DownLoad {
    final URL link;
    final String outputPath;
    final Integer maxSpeed;
    final Logger logger;

    public DownLoadImpl(URL link, String outputPath, Integer maxSpeed, Logger logger) {
        this.link = link;
        this.outputPath = outputPath;
        this.maxSpeed = maxSpeed;
        this.logger = logger;
    }

    @Override
    public void run() {
        long bytesDownLoaded = 0;

        try (BufferedInputStream in = new BufferedInputStream(link.openStream());
             FileOutputStream out = new FileOutputStream(outputPath)
        ) {
            logger.Log(String.format("%s %s :СТАРТ!", link, Thread.currentThread().getName()));
            byte [] buffer = new byte[256];
            int count;
            long TS = System.currentTimeMillis();
            while ( (count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                bytesDownLoaded += count;
                long totalTime = System.currentTimeMillis() - TS;
                long ellapsedTime = bytesDownLoaded / maxSpeed;
                logger.Log(String.format("%s %.2f Кб", link, bytesDownLoaded / 1024.0), 1000L);
                if (Thread.interrupted()) {
                    logger.Log(String.format("%s ПРЕРЫВАНИЕ!", link));
                    break;
                }
                if (totalTime < ellapsedTime) {
                    try {
                        Thread.sleep(ellapsedTime - totalTime);
                    } catch (InterruptedException exc) {
                        logger.Log(String.format("%s ПРЕРЫВАНИЕ!", link));
                        break;
                    }
                }
            }
            logger.Log(String.format("%s СТОП ", link));
        } catch (IOException e) {
            logger.Log("DownLoad " + e.getMessage());
            throw new RuntimeException("IOException:");
        }
        catch (Exception e){
            logger.Log("Other exception " + e.getMessage());
        }
    }

}
