package com.quew8.gmath;

import static com.quew8.gmath.Vector.ABSOLUTE_BIT;
import static com.quew8.gmath.Vector.NEGATE_BIT;
import static com.quew8.gmath.Vector.NORMALIZE_BIT;

/**
 *
 * @author Quew8
 */
public class Vector4 {
    private float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vector4(float x, float y, float z) {
        this(x, y, z, 1);
    }

    public Vector4(Vector v, float w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }
    
    public Vector4(Vector v) {
        this(v, 1);
    }
    
    public Vector4() {
        this(0, 0, 0, 1);
    }
    
    public Vector2 getVector2() {
        return new Vector2(x, y);
    }
    
    public Vector getVector3() {
        return new Vector(x, y, z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setW(float w) {
        this.w = w;
    }
	
    public void setXYZW(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public boolean equals(Vector v) {
        return (this.x == v.getX() && this.y == v.getY() && this.z == v.getZ());
    }
	
    public final Vector4 performOp(byte op) {
        if((op & NORMALIZE_BIT) != 0) {
            float length = GMath.length(x, y, z, w);
            this.x /= length;
            this.y /= length;
            this.z /= length;
            this.w /= length;
        }
        if((op & ABSOLUTE_BIT) != 0) {
            this.x = Math.abs(this.x);
            this.y = Math.abs(this.y);
            this.z = Math.abs(this.z);
            this.w = Math.abs(this.w);
        }
        if((op & NEGATE_BIT) != 0) {
            this.x = -this.x;
            this.y = -this.y;
            this.z = -this.z;
            this.w = -this.w;
        }
        return this;
    }
    
    public Vector4 add(Vector4 b) {
        return Vector4.add(this, this, b);
    }
	
    public Vector4 subtract(Vector4 b) {
        return Vector4.subtract(this, this, b);
    }
	
    public Vector4 times(Vector4 b) {
        return Vector4.times(this, this, b);
    }
	
    public Vector4 times(float f) {
        return Vector4.times(this, this, f);
    }
	
    public Vector4 divide(float f) {
        return Vector4.divide(this, this, f);
    }
	
    public Vector4 add(float f) {
        return Vector4.add(this, this, f); 
    }
	
    public Vector4 subtract(float f) {
        return Vector4.subtract(this, this, f);
    }
	
    public boolean isNegative() {
        return (x < 0 && y < 0 && z < 0 && w < 0);
    }
	
    public boolean hasMagnitudeZero() {
        return (x == 0 && y == 0 && z == 0 && w == 0);
    }
	
    public float magnitudeSquared() {
        return GMath.lengthSquared(x, y, z, w);
    }
	
    public float magnitude() {
        return GMath.length(x, y, z, w);
    }
	
    public float normalize() {
        float magnitude = magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
        this.w /= magnitude;
        return magnitude;
    }
	
    public float normalizeIfNot() {
        float magnitudeSq = magnitudeSquared();
        if(magnitudeSq != 1) {
            float magnitude = GMath.sqrt(magnitudeSq);
            this.x /= magnitude;
            this.y /= magnitude;
            this.z /= magnitude;
            this.w /= magnitude;
            return magnitude;
        }
        return 1;
    }
	
    public float setMagnitude(float mag) {
    	float curMag = magnitude();
    	float scale = mag / curMag;
    	times(scale);
    	return curMag;
    }
    
    public Vector4 absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        this.w = Math.abs(this.w);
        return this;
    }
	
    public Vector4 negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
        return this;
    }

    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}';
    }
	
    public static Vector4 add(Vector4 result, Vector4 a, Vector4 b) {
        result.x = a.x + b.x;
        result.y = a.y + b.y;
        result.z = a.z + b.z;
        result.w = a.w + b.w;
        return result;
    }
	
    public static Vector4 subtract(Vector4 result, Vector4 a, Vector4 b) {
        result.x = a.x - b.x;
        result.y = a.y - b.y;
        result.z = a.z - b.z;
        result.w = a.w - b.w;
        return result;
    }
	
    public static Vector4 times(Vector4 result, Vector4 a, Vector4 b) {
        result.x = a.x * b.x;
        result.y = a.y * b.y;
        result.z = a.z * b.z;
        result.w = a.w * b.w;
        return result;
    }
	
    public static Vector4 divide(Vector4 result, Vector4 a, Vector4 b) {
        result.x = a.x / b.x;
        result.y = a.y / b.y;
        result.z = a.z / b.z;
        result.w = a.w / b.w;
        return result;
    }
	
    public static Vector4 times(Vector4 result, Vector4 a, float f) {
        result.x = a.x * f;
        result.y = a.y * f;
        result.z = a.z * f;
        result.w = a.w * f;
        return result;
    }
	
    /**
     * 
     * @param result
     * @param a
     * @param f
     * @return 
     */
    public static Vector4 divide(Vector4 result, Vector4 a, float f) {
        result.x = a.x / f;
        result.y = a.y / f;
        result.z = a.z / f;
        result.w = a.z / f;
        return result;
    }
	
    public static Vector4 add(Vector4 result, Vector4 a, float f) {
        result.x = a.x + f;
        result.y = a.y + f;
        result.z = a.z + f;
        result.w = a.w + f;
        return result;
    }
	
    public static Vector4 subtract(Vector4 result, Vector4 a, float f) {
        result.x = a.x - f;
        result.y = a.y - f;
        result.z = a.z - f;
        result.w = a.w - f;
        return result;
    }
    
    public Vector perpectiveDivide() {
        return new Vector(getX() / getW(), getY() / getW(), getZ() / getW());
    }
}
