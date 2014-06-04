package com.quew8.gutils.collections.pools;

import com.quew8.gutils.collections.Stack;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public class BasicPool<T> implements Pool<T> {
    private final Stack<T> availableStack;
    private PoolableHandler<T> handler;
    
    public BasicPool(PoolableHandler<T> handler, int n) {
        this.availableStack = new Stack<T>(handler.getPoolableClass(), n);
        this.handler = handler;
        fill();
    }
    
    @Override
    public T get() {
        return availableStack.pop();
    }
    
    @Override
    public void release(final T t) {
        handler.clean(t);
        availableStack.push(t);
    }
    
    @Override
    public void setHandler(PoolableHandler<T> handler) {
        this.handler = handler;
    }
    
    private void fill() {
        int remaining = availableStack.remaining();
        while(remaining > 0) {
            availableStack.push(handler.createNew());
        }
    }
    
}
