package hw17.SpringDownLoader;

public interface Configurator {
    String LINK_LIST_PATH = "Путь к файлу со списком загрузок";
    String OUTPUT_PATH = "Путь к каталогу загрузок";
    String DOWNLOAD_MAX_SPEED = "Ограничение скорости закачки, кб/сек";

    String FILE_REWITE_POLICY = "Политика перезаписи файлов";
    String REWRITE = "Перезаписывать";
    String SKIP_AND_LOG = "Пропускать";
    String WRITE_MODIFYED_NAME = "Записывать с изменённым именем ";  // поумолчанию

    String THREAD_POOL_SIZE = "Число потоков закачки";

    String LOG_POLICY = "Политика протоколирования";
    String NO_LOG = "Нет протоколирования";
    String CONSOLE_LOG = "Протоколирование в консоль"; // поумолчанию
    String CONSOLE_AND_FILE_LOG = "Протоколирование в консоль и в файл";
    String LOG_FILE_PATH = "Путь к файлу протокола";
    /**
     * Возвращает строковое представление запрошенного параметра или null
     * @param settingName - имя параметра
     * @return значение параметра
     */
    String Value(String settingName);

    /**
     * Обновляет конфигурацию из xml файла
     * @param xmlName - имя xml файла
     */
    void UpdateFromXML(String xmlName);
}
