package com.quew8.gutils.collections;

/**
 *
 * @author Quew8
 */
abstract class AbstractCollection {
    
    public abstract int capacity();
    public abstract int used();
    
    public int remaining() {
        return capacity() - used();
    }
    
    public boolean hasCapacity() {
        return capacity() > 0;
    }
    
    public boolean hasRemaining() {
        return remaining() > 0;
    }
    
    public boolean isEmpty() {
        return used() == 0;
    }
}
