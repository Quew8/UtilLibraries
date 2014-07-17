package com.quew8.gutils.collections;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class ExpandingStack<T> extends Stack<T> {

    public ExpandingStack(T[] stack, int usable) {
        super(stack, usable);
    }

    public ExpandingStack(Class<T> clazz, int capacity) {
        super(clazz, capacity);
    }

    @Override
    public void push(T t) {
        if(remaining() <= 0) {
            expand();
        }
        super.push(t);
    }
}
