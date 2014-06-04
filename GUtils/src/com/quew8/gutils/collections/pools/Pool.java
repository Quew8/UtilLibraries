package com.quew8.gutils.collections.pools;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public interface Pool<T> {
    public T get();
    public void release(final T t);
    public void setHandler(PoolableHandler<T> handler);
}
