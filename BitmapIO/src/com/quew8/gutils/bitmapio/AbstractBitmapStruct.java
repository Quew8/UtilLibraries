package com.quew8.gutils.bitmapio;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public abstract class AbstractBitmapStruct<T> {
    public T data;
    public int width, height;

    public AbstractBitmapStruct(T data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }
}
