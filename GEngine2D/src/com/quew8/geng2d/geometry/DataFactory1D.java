package com.quew8.geng2d.geometry;

import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface DataFactory1D<T> {
    public T construct(Colour colour, float x, float y, float width, float height);
    public T construct(float x, float y, float width, float height);
}
