package model.adt;

import java.util.HashMap;

public class MyDictionary<K, T> implements MyIDictionary<K, T> {
    HashMap<K, T> dict;

    public MyDictionary(){
        this.dict = new HashMap<>();
    }

    @Override
    public void add(K key, T elem) {
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
    public void update(K key, T newelem) {
        dict.replace(key, newelem);
    }

    @Override
    public void remove(K key) {
        dict.remove(key);
    }

    @Override
    public void clear() {
        dict.clear();
    }

    @Override
    public String toString() {
        return dict.toString();
    }
}
