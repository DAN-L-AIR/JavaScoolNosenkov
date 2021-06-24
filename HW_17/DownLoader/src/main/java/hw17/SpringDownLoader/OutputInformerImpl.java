package hw17.SpringDownLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OutputInformerImpl implements OutputInformer {
    final Configurator configurator;
    final Logger logger;
    private List<String> outputFiles;
    private boolean isSkipAndLog;
    private String outputDirName;

    @Autowired
    public OutputInformerImpl(Configurator configurator, Logger logger) {
        this.configurator = configurator;
        this.logger = logger;
        Update();
    }

    @Override
    public String GeOutputLinkFileName(String link) {
        String toFindLink;
        int index = -1;
        do {
            index++;
            toFindLink = link.replaceAll("[\r/]","").replace(":", "_") + (index == 0 ? "" : ("(" + index + ")")) + ".txt";
        } while (outputFiles.contains(toFindLink));
        if (index != 0 && isSkipAndLog) {
            throw new RuntimeException(String.format("Предупреждение:Файл <%s> существует. Загрузка отменена", link));
        }
        return toFindLink;
    }

    @Override
    public void Update() {
        try {
            isSkipAndLog = configurator.Value(Configurator.FILE_REWITE_POLICY).equals(Configurator.SKIP_AND_LOG);
            outputDirName = configurator.Value(Configurator.OUTPUT_PATH);
        } catch (NullPointerException exc) {
            throw new RuntimeException("Ошибка: В конфигурации нет параметра");
        }
        outputFiles = FilesOnly(GetOutputFiles());
    }

    private String[] GetOutputFiles() {
        String[] filesAndDirs = null;
        try {
            File outputDir = new File(outputDirName);
            if (outputDir.isDirectory()) {
                filesAndDirs = outputDir.list();
            }
        } catch (NullPointerException exc) {
            throw new RuntimeException("Ошибка: В конфигурации нет параметра 'Имя каталога сохранённых ссылок'");
        } catch (SecurityException exc) {
            throw new RuntimeException("Ошибка: Доступ на чтения каталога сохранёных ссылок закрыт");
        }
        return filesAndDirs;
    }

    private List<String> FilesOnly(String[] filesAndDirs) {
        List<String> filesList = new ArrayList<>();
        for (String fileName : filesAndDirs) {
            if (new File(outputDirName + "\\" + fileName).isFile()) {
                filesList.add(fileName);
            }
        }
        return filesList;
    }
}
