package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;

import com.quew8.gmath.Vector;

import com.quew8.gutils.ArrayUtils;
import java.nio.ByteBuffer;

/**
 * 
 * @author Quew8
 *
 */
public class Vertex extends AbstractVertex<Vertex> {
    public static final int VERTEX_BYTE_SIZE = VERTEX_SIZE * 4;
    public static final int 
            X = 0x0,
            Y = 0x1,
            Z = 0x2,
            NX = 0x3,
            NY = 0x4,
            NZ = 0x5,
            TX = 0x6,
            TY = 0x7;

    public Vertex(float[] data) {
        super(data);
    }

    public Vertex(Vector pos, Vector normal, float tx, float ty) {
        this(new float[]{
            pos.getX(), pos.getY(), pos.getZ(),
            normal.getX(), normal.getY(), normal.getZ(),
            tx, ty
        });
    }

    public Vertex(float[] pos, float[] normal, float[] texCoords) {
        this(ArrayUtils.concatArrays(new float[][]{pos, normal, texCoords}, 8));
    }

    public Vertex(float x, float y, float z, float nx, float ny, float nz, float tx, float ty) {
        this(new float[]{x, y, z, nx, ny, nz, tx, ty});
    }

    public float[] getData(int... params) {
        float[] fa = new float[params.length];
        for(int i = 0; i < params.length; i++) {
            fa[i] = data[params[i]];
        }
        return fa;
    }

    public void appendData(ByteBuffer bb, int... params) {
        for(int i = 0; i < params.length; i++) {
            bb.putFloat(data[params[i]]);
        }
    }
    
    @Override
    public Vertex transform(Matrix transform, Matrix normalMatrix) {
        return new Vertex(transformData(transform, normalMatrix));
    }
}
