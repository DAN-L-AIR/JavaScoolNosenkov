package hw17.SpringDownLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class LoggerImpl implements Logger {
    final Configurator configurator;
    final Map<Long, Long> accessTimebyThread = new HashMap<>();
    boolean isNoLog;
    boolean LogToFile;
    String logPath;
    Long accessTime;

    @Autowired
    public LoggerImpl(Configurator configurator) {
        this.configurator = configurator;
        Update();
    }

    @Override
    public void Log(String mes) {
        if (isNoLog) return;
        System.out.println(mes);
        if(LogToFile) {
            LogtoFile(mes);
        }
    }

    @Override
    public void Log(String mes, Long minTime) {
        Long ctid = Thread.currentThread().getId();
        accessTime = accessTimebyThread.get(ctid);
        if (accessTime == null) {
            accessTimebyThread.put(ctid, System.currentTimeMillis());
            Log(mes);
            return;
        }
        long nowTime = System.currentTimeMillis();
        if (nowTime - accessTime > minTime) {
            accessTimebyThread.put(ctid, nowTime);
            Log(mes);
        }
    }

    public void Update() {
        try {
            isNoLog = configurator.Value(Configurator.LOG_POLICY).equals(Configurator.NO_LOG);
            LogToFile = configurator.Value(Configurator.LOG_POLICY).equals(Configurator.CONSOLE_AND_FILE_LOG);
            logPath = configurator.Value(Configurator.LOG_FILE_PATH);
        } catch (RuntimeException e) {
            LogToFile = false;
            isNoLog = false;
            System.out.println("Logger: %s Параметры логирования установлены по умолчанию");
        }
    }

    private void LogtoFile(String mes){
        try(FileWriter fileWriter = new FileWriter(logPath, true)) {
            fileWriter.write(String.format("[%s] %s\r", new Date(), mes));
        } catch (IOException e) {
            System.out.println("Logger: Ошибка вывода в лог файл. Вывод в файл отключён");
            LogToFile = false;
        }
    }
}
