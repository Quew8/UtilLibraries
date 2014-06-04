package com.quew8.gutils.collections;

/**
 *
 * @author Quew8
 */
public class IntStack extends AbstractCollection {
    private final int[] stack;
    private int pos = 0;
    
    public IntStack(int[] stack, int usable) {
        this.stack = stack;
        this.pos = usable;
    }
    
    public IntStack(int capacity) {
        this(new int[capacity], 0);
    }
    
    public void push(final int i) {
        stack[pos++] = i;
    }
    
    public int pop() {
        return stack[--pos];
    }
    
    public void reset() {
        pos = 0;
    }
    
    @Override
    public int capacity() {
        return stack.length;
    }
    
    @Override
    public int used() {
        return pos;
    }
    
    public int fill(int[] from, int offset) {
        int used = 0;
        while(pos < stack.length) {
            push(from[offset + (used++)]);
        }
        return used;
    }
}
