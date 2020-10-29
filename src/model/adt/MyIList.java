package model.adt;

import java.util.ArrayList;

public interface MyIList<T> {
    /**
     * This method adds an element to the list
     * @param el The element to be added
     */
    void add(T el);

    /**
     * This method clears the list
     */
    void empty();
}
