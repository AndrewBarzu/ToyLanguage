package model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, T> implements MyIDictionary<K, T> {
    Map<K, T> dict;

    public MyDictionary() {
        this.dict = new HashMap<>();
    }

    @Override
    public synchronized void add(K key, T elem) {
        dict.put(key, elem);
    }

    @Override
    public T get(K key) {
        return dict.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public synchronized void update(K key, T newElem) {
        dict.replace(key, newElem);
    }

    @Override
    public synchronized void remove(K key) {
        dict.remove(key);
    }

    @Override
    public synchronized void clear() {
        dict.clear();
    }

    @Override
    public String toString() {
        return dict.toString();
    }

    @Override
    public Map<K, T> getContent() {
        return this.dict;
    }

    @Override
    public synchronized void setContent(Map<K, T> newContent) {
        this.dict = newContent;
    }
}
