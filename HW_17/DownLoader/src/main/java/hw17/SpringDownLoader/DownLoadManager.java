package hw17.SpringDownLoader;

public interface DownLoadManager {
    void AddTask(DownLoad task);
    void CancellAll();
    boolean isAllCompleted();
    void Stop();
}
