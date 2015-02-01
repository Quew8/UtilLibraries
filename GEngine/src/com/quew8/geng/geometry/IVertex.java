package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface IVertex<T extends IVertex> {
    public void addData(ByteBuffer to, Param param);
    public T transform(Matrix transform, Matrix normalMatrix);
    public T transformTextureCoords(Image img);
}
