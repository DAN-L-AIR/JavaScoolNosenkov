package hw3;
import java.util.List;
import java.util.Map;

public interface CountMap <T> {
    void add(T o);

    int getCount(T o);

    //current count
    int remove(T o);

    int size();

    void addAll(CountMap<T> source);

    Map toMap();

    void toMap(Map destination);
}
