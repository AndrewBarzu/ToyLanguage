package model.adt;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a generic list
 *
 * @param <T> The type of the elements stored
 */
public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public synchronized void add(T el) {
        list.add(el);
    }

    @Override
    public synchronized void clear() {
        list.clear();
    }

    @Override
    public List<T> getContent() {
        return this.list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
