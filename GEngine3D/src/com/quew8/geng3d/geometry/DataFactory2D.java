package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Plane;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface DataFactory2D<T> {
    public T construct(Image image, float x, float y, float z, float width, float height, Plane plane);
    public T construct(Colour colour, float x, float y, float z, float width, float height, Plane plane);
    public T construct(float x, float y, float z, float width, float height, Plane plane);
}
