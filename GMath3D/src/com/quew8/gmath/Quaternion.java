package com.quew8.gmath;

/**
 *
 * @author Quew8
 */
public class Quaternion {
    public float w, x, y, z;
    
    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Quaternion() {
        this(1, 0, 0, 0);
    }
    
    public float magnitudeSquared() {
        return (w * w) + (x * x) + (y * y) + (z * z);
    }
    
    public float magnitude() {
        return GMath.sqrt(magnitudeSquared());
    }
    
    public float normalizeIfNot() {
        float magSq = magnitudeSquared();
        if(magSq != 1) {
            float mag = GMath.sqrt(magSq);
            this.w = w / mag;
            this.x = x / mag;
            this.y = y / mag;
            this.z = z / mag;
            return mag;
        } else {
            return 1;
        }
    }
    
    public float normalize() {
        float mag = magnitude();
        this.w = w / mag;
        this.x = x / mag;
        this.y = y / mag;
        this.z = z / mag;
        return mag;
    }
    
    public Matrix makeMatrix(Matrix result) {
        float xx2 = 2 * ( x * x ), yy2 = 2 * ( y * y ), zz2 = 2 * ( z * z );
        float wx2 = 2 * ( w * x ), wy2 = 2 * ( w * y ), wz2 = 2 * ( w * z ),
                xy2 = 2 * ( x * y ), xz2 = 2 * ( x * z ), yz2 = 2 * ( y * z );
        result.setData(
                1 - yy2 - zz2, xy2 - wz2,     xz2 + wy2,    0, 
                xy2 + wz2,     1 - xx2 - zz2, yz2 + wx2,    0, 
                xz2 - wy2,     yz2 - wx2,     1 - xx2- yy2, 0, 
                0,             0,             0,            1
        );
        return result;
    }
    
    public Matrix makeMatrix() {
        return makeMatrix(new Matrix());
    }

    @Override
    public String toString() {
        return "Quaternion: w = " + w + ", x = " + x + ", y = " + y + ", z = " + z;
    }
    
    
    
    public static Quaternion makeRotation(Quaternion result, float theta, Vector axis) {
        float halfTheta = theta / 2;
        float sinHalfTheta = GMath.sin(halfTheta);
        result.w = GMath.cos(halfTheta);
        result.x = axis.getX() * sinHalfTheta;
        result.y = axis.getY() * sinHalfTheta;
        result.z = axis.getZ() * sinHalfTheta;
        return result;
    }
    
    public static Quaternion makeRotation(float theta, Vector axis) {
        return makeRotation(new Quaternion(), theta, axis);
    }
    
    public static Quaternion times(Quaternion result, Quaternion a, Quaternion b) {
        result.w = ( a.w * b.w ) - ( a.x * b.x ) - ( a.y * b.y ) - ( a.z * b.z );
        result.x = ( a.w * b.x ) + ( a.x * b.w ) + ( a.y * b.z ) - ( a.z * b.y );
        result.y = ( a.w * b.y ) - ( a.x * b.z ) + ( a.y * b.w ) + ( a.z * b.x );
        result.z = ( a.w * b.z ) + ( a.x * b.y ) - ( a.y * b.x ) + ( a.z * b.w );
        return result;
    }
    
    public static Quaternion times(Quaternion a, Quaternion b) {
        return times(new Quaternion(), a, b);
    }
}
