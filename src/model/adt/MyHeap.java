package model.adt;

import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap<Integer, Value> {
    private static int currentAddress;
    Map<Integer, Value> hashMap;

    public MyHeap() {
        hashMap = new HashMap<>();
        currentAddress = 1;
    }

    @Override
    public int add(Value elem) {
        hashMap.put(currentAddress, elem);
        return currentAddress++;
    }

    @Override
    public Value get(Integer key) {
        return hashMap.get(key);
    }

    @Override
    public boolean isDefined(Integer key) {
        return hashMap.containsKey(key);
    }

    @Override
    public void update(Integer key, Value newElem) {
        hashMap.replace(key, newElem);
    }

    @Override
    public void remove(Integer key) {
        hashMap.remove(key);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    public String toString() {
        return hashMap.toString();
    }

    @Override
    public Map<Integer, Value> getContent() {
        return hashMap;
    }

    public void setContent(Map<Integer, Value> newContent) {
        this.hashMap = newContent;
    }
}
