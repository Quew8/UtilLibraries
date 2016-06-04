package com.quew8.geng3d.geometry;

import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface DataFactory1D<T> {
    public T construct(T out, Colour colour, float x, float y, float z, float width, float height, float depth);
    public T construct(T out, float x, float y, float z, float width, float height, float depth);
    public T getInstance();
    public T createInstance();
}
