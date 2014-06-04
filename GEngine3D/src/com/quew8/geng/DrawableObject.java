package com.quew8.geng;

import com.quew8.geng.rendering.StaticHandleList;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public abstract class DrawableObject<T extends StaticHandleList> extends GameObject {
    private final T drawable;

    public DrawableObject(T drawable) {
        this.drawable = drawable;
    }

    public T getDrawable() {
        return drawable;
    }
}
