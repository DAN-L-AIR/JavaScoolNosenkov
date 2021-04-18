package hw3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> int indexOf(List<T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<? super T> limit(List<? extends T> source, int size) {
        List<? super T> resList = new ArrayList<>();
        for (T el : source) {
            if (size-- == 0) {
                break;
            }
            resList.add(el);
        }
        return resList;
    }

    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static <T> void removeAll(List<? extends T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    public static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    public static <T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        for (T el : c2) {
            if (c1.contains(el)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable> List<? super T> range(List<? extends T> list, T min, T max) {
        List<? super T> resList = new ArrayList<>();
        for (T el : list) {
            if (el.compareTo(min) >= 0 && el.compareTo(max) <= 0) {
                resList.add(el);
            }
        }
        // как в задании - отсортированный
        resList.sort(null);
        return resList;
    }

    public static <T> List<? super T> range(List<? extends T> list, T min, T max, Comparator<T> comparator) {
        List<? super T> resList = new ArrayList<>();
        for (T el : list) {
            if (comparator.compare(el, min) >= 0 && comparator.compare(el, max) <= 0) {
                resList.add(el);
            }
        }
        // как в задании - отсортированный
        resList.sort(null);
        return resList;
    }
}
