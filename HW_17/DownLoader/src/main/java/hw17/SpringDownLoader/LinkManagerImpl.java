package hw17.SpringDownLoader;

import com.ibm.jvm.dtfjview.tools.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinkManagerImpl implements LinkManager {

    private static final Integer DEFAULT_MAX_SPEED = 10;
    final private Configurator configurator;
    final private Logger logger;
    final private DownLoadManager downLoadManager;
    final private OutputInformer outputInformer;

    @Autowired
    public LinkManagerImpl(Configurator configurator,
                           Logger logger,
                           DownLoadManager downLoadManager,
                           OutputInformer outputInformer) {
        this.configurator = configurator;
        this.logger = logger;
        this.downLoadManager = downLoadManager;
        this.outputInformer = outputInformer;
    }

    @Override
    public void AddForExecution() {
        String outputPath;
        String linkListPath;
        Integer maxSpeed;

        try {
            outputPath = configurator.Value(Configurator.OUTPUT_PATH);
            linkListPath = configurator.Value(Configurator.LINK_LIST_PATH);
            try {
                maxSpeed = Integer.parseInt(configurator.Value(Configurator.DOWNLOAD_MAX_SPEED));
            } catch (NumberFormatException e) {
                maxSpeed = DEFAULT_MAX_SPEED;
                logger.Log(String.format("Ошибка: получения значения максимальной скорости.Используем значение 'по умрочанию' %d", maxSpeed));
            }
        } catch (NullPointerException exc) {
            logger.Log("LinkManager: Ошибка чтения параметров конфигурации");
            throw new RuntimeException("LinkManager:Невозможно добавить задачи");
        }

        String[] links;
        try {
            File linkListFile = new File(linkListPath);
            links = FileUtils.read(linkListFile);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("LinkManager: Ошибка: неподдерживаемая кодировка файла списка ссылок");
        } catch (IOException e) {
            throw new RuntimeException("LinkManager:Невозможно добавить задачи");
        }

        List<String[]> finalPairs = new ArrayList<>();
        for (String link : links) {
            try {
                String[] twoString = new String[2];
                twoString[1] = outputPath + "\\" + outputInformer.GeOutputLinkFileName(link.replace("\r", ""));
                twoString[0] = link;
                finalPairs.add(twoString);
            } catch (RuntimeException e) {
                logger.Log(e.getMessage());
            }
        }

        for (String[] finalPair: finalPairs) {
            try {
                downLoadManager.AddTask(new DownLoadImpl(new URL(finalPair[0]), finalPair[1], maxSpeed, logger));
            } catch (MalformedURLException e) {
                logger.Log(String.format("Предупреждение: Недопустимый url ссылки: <%s>. Загрузка отменена", finalPair[0]));
            }
        }
    }
}
