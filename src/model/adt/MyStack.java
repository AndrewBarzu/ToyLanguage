package model.adt;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This class represents a generic stack
 *
 * @param <T> The type of the elements stored
 */
public class MyStack<T> implements MyIStack<T> {
    Deque<T> stack;

    public MyStack() {
        this.stack = new LinkedList<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T el) {
        stack.push(el);
    }

    @Override
    public boolean empty() {
        return stack.isEmpty();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
