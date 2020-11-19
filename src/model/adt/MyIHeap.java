package model.adt;

import java.util.Map;

public interface MyIHeap<K, T> {
    int add(T elem);

    T get(K key);

    boolean isDefined(K key);

    void update(K key, T newElem);

    void remove(K key);

    void clear();

    Map<K, T> getContent();

    void setContent(Map<K, T> newValues);
}
