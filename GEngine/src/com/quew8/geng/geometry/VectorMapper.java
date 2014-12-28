package com.quew8.geng.geometry;

import com.quew8.gmath.Axis;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 */
public class VectorMapper {
    private static final Matrix 
            xMatrix = new Matrix(
            0, 0, -1, 0,
            0, -1, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 1),
            negXMatrix = new Matrix(
            0,  0, -1, 0,
            0,  -1, 0,  0,
            -1, 0, 0,  0,
            0,  0, 0,  1),
            yMatrix = new Matrix(
            -1, 0, 0,  0,
            0, 0, -1, 0,
            0, 1, 0,  0,
            0, 0, 0,  1),
            negYMatrix = new Matrix(
            -1, 0,  0,  0,
            0, 0,  1, 0,
            0, -1, 0,  0,
            0, 0,  0,  1),
            zMatrix = new Matrix(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1),
            negZMatrix = new Matrix(
            -1, 0, 0,  0,
            0,  1, 0,  0,
            0,  0, -1, 0,
            0,  0, 0,  1);
    
    private final Matrix matrix;
    private final Matrix normalMatrix;
    private final Vector centre;
    
    public VectorMapper(Matrix matrix, Vector centre, Vector offset) {
        this.matrix = matrix;
        this.normalMatrix = matrix.getRotation();
        this.centre = centre.add(matrix.times(offset));
    }
    
    public VectorMapper(Matrix matrix, Vector centre) {
        this.matrix = matrix;
        this.normalMatrix = matrix.getRotation();
        this.centre = centre;
    }
    
    public VectorMapper(Axis axis, Vector centre, Vector offset) {
        this(getMatrixForAxis(axis), centre, offset);
    }

    public VectorMapper(Axis axis, Vector centre) {
        this(getMatrixForAxis(axis), centre);
    }
    
    public Matrix getMatrix() {
        return matrix;
    }
    
    public Vector map(Vector v) {
        return Vector.add(new Vector(), centre, matrix.times(v));
    }
    
    public Vector map(float x, float y, float z) {
        return map(new Vector(x, y, z));
    }
    
    public Vector mapNormal(Vector n) {
        return normalMatrix.times(n);
    }
    
    public Vector mapNormal(float x, float y, float z) {
        return mapNormal(new Vector(x, y, z));
    }
    
    private static Matrix getMatrixForAxis(Axis axis) {
        switch(axis) {
            case X: return xMatrix;
            case NEGATIVE_X: return negXMatrix;
            case Y: return yMatrix;
            case NEGATIVE_Y: return negYMatrix;
            case Z: return zMatrix;
            case NEGATIVE_Z: return negZMatrix;
            default: return null;
        }
    }
}
