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
    public void add(T el) {
        list.add(el);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
