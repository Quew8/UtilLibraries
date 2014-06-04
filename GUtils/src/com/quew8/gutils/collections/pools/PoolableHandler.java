package com.quew8.gutils.collections.pools;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public interface PoolableHandler<T> {
    public Class<T> getPoolableClass();
    public T createNew();
    public void clean(final T t);
}
