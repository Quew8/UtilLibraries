package com.quew8.gmath;

/**
 *
 * @author Quew8
 */
public enum Axis {
    X(new Vector(1, 0, 0), true), 
    Y(new Vector(0, 1, 0), true), 
    Z(new Vector(0, 0, 1), true),
    NEGATIVE_X(new Vector(-1, 0, 0), false),
    NEGATIVE_Y(new Vector(0, -1, 0), false),
    NEGATIVE_Z(new Vector(0, 0, -1), false);
    
    private Axis(Vector vector, boolean positive) {
        this.vector = vector;
        this.positive = positive;
    }
    
    private final Vector vector;
    private final boolean positive;
    
    public boolean isPositive() {
        return positive;
    }
    
    public Vector getVector() {
        return vector;
    }
    
    public static Axis getNegativeOf(Axis axis) {
        switch(axis) {
            case X: return NEGATIVE_X;
            case Y: return NEGATIVE_Y;
            case Z: return NEGATIVE_Z;
            case NEGATIVE_X: return X;
            case NEGATIVE_Y: return Y;
            case NEGATIVE_Z: return Z;
            default: return null;
        }
    } 
}
