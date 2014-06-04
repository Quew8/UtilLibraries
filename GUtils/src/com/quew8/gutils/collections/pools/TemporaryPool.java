package com.quew8.gutils.collections.pools;

/**
 *
 * @author Quew8
 * 
 * @param <T>
 */
public interface TemporaryPool<T> {
    public T get();
    public void recallAll();
    public void setHandler(PoolableHandler<T> handler);
}
