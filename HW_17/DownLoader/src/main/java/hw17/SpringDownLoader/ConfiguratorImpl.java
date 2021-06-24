package hw17.SpringDownLoader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConfiguratorImpl implements Configurator {
    final private Map<String, String> settingsMap = new HashMap<>();
    @Autowired
    public ConfiguratorImpl() {
        this.settingsMap.put(LINK_LIST_PATH, System.getProperty("user.dir") + "\\data\\linkList.txt");
        this.settingsMap.put(OUTPUT_PATH, System.getProperty("user.dir") + "\\data\\downloads");
        this.settingsMap.put(DOWNLOAD_MAX_SPEED, "30");
        this.settingsMap.put(THREAD_POOL_SIZE, "2");

        this.settingsMap.put(FILE_REWITE_POLICY, WRITE_MODIFYED_NAME);
        //this.settingsMap.put(FILE_REWITE_POLICY, REWRITE);
        //this.settingsMap.put(FILE_REWITE_POLICY, SKIP_AND_LOG);

        //this.settingsMap.put(LOG_POLICY, CONSOLE_LOG);
        this.settingsMap.put(LOG_POLICY, CONSOLE_AND_FILE_LOG);
        //this.settingsMap.put( LOG_POLICY, NO_LOG);
        this.settingsMap.put( LOG_FILE_PATH, String.format("%s\\data\\logs\\log.txt", System.getProperty("user.dir")));
    }

    @Override
    public String Value(String settingName) {
        String result = settingsMap.get(settingName);
        if(result != null){
            return result;
        }
        throw new RuntimeException(String.format("Параметр конфигурации <%s> не найден", settingName));
    }

    @Override
    public void UpdateFromXML(String xmlName) {
//TODO:: реализовать чтение из XML в Map
        settingsMap.clear();
    }
}
