package com.quew8.gutils.collections;

import com.quew8.gutils.ArrayUtils;

/**
 *
 * @author Quew8
 * 
 * @param <T>
 */
public class Stack<T> extends AbstractCollection {
    private final T[] stack;
    private int pos = 0;
    
    public Stack(T[] stack, int usable) {
        this.stack = stack;
        this.pos = usable;
    }
    
    public Stack(Class<T> clazz, int capacity) {
        this(ArrayUtils.createArray(clazz, capacity), 0);
    }
    
    public void push(final T t) {
        stack[pos++] = t;
    }
    
    public T pop() {
        return stack[--pos];
    }
    
    public void reset() {
        while(pos >= 0) {
            stack[--pos] = null;
        }
    }
    
    @Override
    public int capacity() {
        return stack.length;
    }
    
    @Override
    public int used() {
        return pos;
    }
    
    public int fill(T[] from, int offset) {
        int used = 0;
        while(pos < stack.length) {
            push(from[offset + (used++)]);
        }
        return used;
    }
}
