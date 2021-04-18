package hw3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CountMapImpl<T> implements  CountMap<T>{
    public Map <T, Integer> storage = new HashMap<>();
    @Override
    public void add(T o) {
        addOuter(o, 1);
    }

    @Override
    public int getCount(T o) {
        Integer result = storage.get(o);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    @Override
    public int remove(T o) {
        Integer result = storage.remove(o);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void addAll(CountMap<T> source) {
        Map <T, Integer> sourceStorage = source.toMap();
        for(Map.Entry<T, Integer> entry: sourceStorage.entrySet()) {
            addOuter(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map toMap() {
        return storage;
        // может надо было скопировать?
    }

    @Override
    public void toMap(Map destination) {
        destination.clear();
        destination.putAll(storage);
    }

    private void addOuter(T key, Integer Value){
        Integer number = storage.get(key);
        if( number == null){
            storage.put(key, Value);
        }
        else{
            storage.put(key, number + Value);
        }
    }

}
