package hw17.SpringDownLoader;

public interface Logger {
    /**
     *
     * @param mes - выводимое строковое сообщение
     */
    void Log(String mes);
    /**
     *
     * @param mes - выводимое строковое сообщение
     * @param minTime - если разница времени между
     *                предыдущим сообщением, выведенным этой функцией из этого потока
     *                минус
     *                текущее время
     *                < minTime
     *                сообщение НЕ выводится
     */
    void Log(String mes, Long minTime);
}
