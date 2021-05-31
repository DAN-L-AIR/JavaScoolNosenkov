package hw9;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    List<T> intColl;

    private Streams(List<T> coll) {
        intColl = coll;
    }

    public static <T> Streams<T> of(List<T> coll) {
        return new Streams(coll);
    }

    public Streams<T> filter(Predicate<? super T> f) {
        List<T> result = new ArrayList<>();
        Iterator<T> it = intColl.iterator();
        while (it.hasNext()) {
            T item = it.next();
            if (f.test(item)) {
                result.add(item);
            }
        }
        return new Streams(result);
    }

    public <R> Streams<R> transform(Function<? super T, ? extends R> tr) {
        List<R> result = new ArrayList<>();
        Iterator<T> it = intColl.iterator();
        while (it.hasNext()) {
            T item = it.next();
            result.add(tr.apply(item));
        }
        return new Streams(result);
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> ob, Function<? super T, ? extends V> ob1) {
        Map<K, V> result = new HashMap<>();
        Iterator<T> it = intColl.iterator();
        while (it.hasNext()) {
            T item = it.next();
            result.put(ob.apply(item), ob1.apply(item));
        }
        return result;
    }
}
