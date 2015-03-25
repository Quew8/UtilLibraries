package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface DataFactory3D<T> {
    public T construct(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, float depth, Plane m);
    public T construct(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth, Plane m);
    public T construct(ByteBuffer to, float x, float y, float z, float width, float height, float depth, Plane m);
}
