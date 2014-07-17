package com.quew8.gutils.collections;

import com.quew8.gutils.ArrayUtils;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * 
 * @param <T>
 */
public class Stack<T> extends AbstractCollection {
    private T[] stack;
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
    
    public int fillFrom(T[] from, int offset) {
        int used = 0;
        while(pos < stack.length) {
            push(from[offset + (used++)]);
        }
        return used;
    }
    
    public int fillInto(T[] into, int offset) {
        int used = 0;
        while(pos > 0) {
            into[offset + (used++)] = pop();
        }
        return used;
    }
    
    public void expand() {
        expand((( stack.length * 3 ) / 2 ) + 1);
    }
    
    public void expand(int newCap) {
        stack = Arrays.copyOf(stack, newCap);
    }
    
    public void reverse() {
        T[] copy = Arrays.copyOf(stack, pos + 1);
        for(int i = 0; i < pos; i++) {
            stack[i] = copy[pos - i - 1];
        }
    }
}
