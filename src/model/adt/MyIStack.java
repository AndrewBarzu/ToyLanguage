package model.adt;

public interface MyIStack<T> {
    /**
     * This function pops the element on the top of the stack
     * @return The object on the top of the stack
     */
    T pop();

    /**
     * This function adds an element to the stack
     * @param el An element of type T
     */
    void push(T el);

    boolean empty();
    void clear();
}
