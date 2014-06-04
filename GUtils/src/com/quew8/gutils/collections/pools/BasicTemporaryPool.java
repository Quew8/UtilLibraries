package com.quew8.gutils.collections.pools;

import com.quew8.gutils.ArrayUtils;
import com.quew8.gutils.collections.IntStack;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public class BasicTemporaryPool<T> implements TemporaryPool<T> {
    private final T[] objectList;
    private final IntStack availableList;
    private final IntStack usedList;
    private PoolableHandler<T> handler;

    public BasicTemporaryPool(T[] objectList, PoolableHandler<T> handler) {
        this.objectList = objectList;
        this.availableList = new IntStack(intArrayTo(objectList.length), objectList.length);
        this.usedList = new IntStack(new int[objectList.length], 0);
        this.handler = handler;
    }
    
    public BasicTemporaryPool(int n, Class<T> clazz, PoolableHandler<T> handler) {
        this(ArrayUtils.createArray(clazz, n), handler);
        fill();
    }
    
    @Override
    public T get() {
        int index = availableList.pop();
        usedList.push(index);
        return objectList[index];
    }

    @Override
    public void recallAll() {
        while(!usedList.isEmpty()) {
            int i = usedList.pop();
            handler.clean(objectList[i]);
            availableList.push(i);
        }
    }

    @Override
    public void setHandler(PoolableHandler<T> handler) {
        this.handler = handler;
    }
    
    private void fill() {
        for(int i = 0; i < objectList.length; i++) {
            objectList[i] = handler.createNew();
        }
    }
    
    private static int[] intArrayTo(int n) {
        int[] list = new int[n];
        for(int i = 0; i < n; i++) {
            list[i] = i;
        }
        return list;
    }
    
}
