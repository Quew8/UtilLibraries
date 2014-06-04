package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class AbstractVertex<T extends AbstractVertex<T>> {
    protected static final int VERTEX_SIZE = 8;
    
    protected final float[] data;
    
    public AbstractVertex(float[] data) {
        this.data = data;
    }
    
    public int getDataSize() {
        return data.length;
    }
    
    public Vector getPosition() {
        return new Vector(data[0], data[1], data[2]);
    }

    public Vector getNormal() {
        return new Vector(data[3], data[4], data[5]);
    }
    
    public float[] getTexCoords() {
        return new float[]{data[6], data[7]};
    }

    public void transformTextureCoords(TextureArea texture) {
        texture.transformCoords(data, 6);
    }

    public void appendData(ByteBuffer bb) {
        for(int i = 0; i < data.length; i++) {
            bb.putFloat(data[i]);
        }
    }
    
    public float[] getData() {
        return data;
    }
    
    public float[] transformData(Matrix transform, Matrix normalMatrix) {
        Vector pos = getPosition();
        Matrix.times(pos, transform, new Vector(pos));
        Vector normal = getNormal();
        Matrix.times(normal, normalMatrix, new Vector(normal));
        float[] texCoords = getTexCoords();
        return new float[]{
            pos.getX(), pos.getY(), pos.getZ(), 
            normal.getX(), normal.getY(), normal.getZ(), 
            texCoords[0], texCoords[1]
        };
    }
    
    public abstract T transform(Matrix transform, Matrix normalMatrix);
}
