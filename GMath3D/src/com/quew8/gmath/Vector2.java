package com.quew8.gmath;

/**
 * 
 * @author Quew8
 */
public class Vector2 {
    private float x, y;
    
    public static final byte 
            NORMALIZE_BIT = 1,
            ABSOLUTE_BIT = 2,
            NEGATE_BIT = 4;
    
    public Vector2(float x, float y, byte... op) {
        this.x = x;
        this.y = y;
        if(op.length > 0) {
            if((op[0] & NORMALIZE_BIT) != 0) {
                float length = GMath.length(x, y);
                this.x /= length;
                this.y /= length;
            }
            if((op[0] & ABSOLUTE_BIT) != 0) {
                this.x = Math.abs(this.x);
                this.y = Math.abs(this.y);
            }
            if((op[0] & NEGATE_BIT) != 0) {
                this.x = -this.x;
                this.y = -this.y;
            }
        }
    }
    
    public Vector2(Vector2 a, Vector2 b, byte... op) {
        this(b.x - a.x, b.y - a.y, op);
    }
    
    public Vector2(Vector2 v, byte... op) {
        this(v.x, v.y, op);
    }
    
    public Vector2(Vector v, byte... op) {
    	this(v.getX(), v.getY(), op);
    }
    
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }
    
    public float[] getXY() {
        return new float[]{x, y};
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public boolean equals(Vector2 v) {
        return (this.x == v.getX() && this.y == v.getY());
    }
    
    public Vector2 add(Vector2 b) {
        return Vector2.add(this, this, b);
    }
	
    public Vector2 subtract(Vector2 b) {
        return Vector2.subtract(this, this, b);
    }
	
    public Vector2 times(Vector2 b) {
        return Vector2.times(this, this, b);
    }
	
    public Vector2 times(float f) {
        return Vector2.times(this, this, f);
    }
	
    public Vector2 divide(float f) {
        return Vector2.divide(this, this, f);
    }
	
    public Vector2 add(float f) {
        return Vector2.add(this, this, f); 
    }
	
    public Vector2 subtract(float f) {
        return Vector2.subtract(this, this, f);
    }
	
    public Vector2 times(Matrix m) {
        return Vector2.times(new Vector2(), this, m);
    }
	
    public Vector2 times(Vector2 result, Matrix m) {
        return Vector2.times(result, this, m);
    }
    
    public boolean isNegative() {
        return (x < 0 && y < 0);
    }
    
    public boolean hasMagnitudeZero() {
        return (x == 0 && y == 0);
    }
    
    public float magnitudeSquared() {
        return GMath.lengthSquared(x, y);
    }
    
    public float magnitude() {
        return GMath.length(x, y);
    }
    
    public float normalize() {
        float magnitude = magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        return magnitude;
    }
    
    public void absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
    }
    
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }
    
    @Override
    public String toString() {
        return "Vector: X = " + x + ", Y = " + y;
    }
    
    public static Vector2 add(Vector2 result, Vector2 a, Vector2 b) {
    	result.x = a.x + b.x;
    	result.y = a.y + b.y;
        return result;
    }
	
    public static Vector2 subtract(Vector2 result, Vector2 a, Vector2 b) {
        result.x = a.x - b.x;
        result.y = a.y - b.y;
        return result;
    }
	
    public static Vector2 times(Vector2 result, Vector2 a, Vector2 b) {
        result.x = a.x * b.x;
        result.y = a.y * b.y;
        return result;
    }
	
    public static Vector2 times(Vector2 result, Vector2 a, float f) {
        result.x = a.x * f;
        result.y = a.y * f;
        return result;
    }
	
    public static Vector2 divide(Vector2 result, Vector2 a, float f) {
        result.x = a.x / f;
        result.y = a.y / f;
        return result;
    }
	
    public static Vector2 add(Vector2 result, Vector2 a, float f) {
        result.x = a.x + f;
        result.y = a.y + f;
        return result;
    }
	
    public static Vector2 subtract(Vector2 result, Vector2 a, float f) {
        result.x = a.x - f;
        result.y = a.y - f;
        return result;
    }
	
    public static Vector2 times(Vector2 result, Vector2 a, Matrix m) {
        result.x = ( a.x * m.get(0) ) + ( a.y * m.get(4) ) + m.get(12);
        result.y = ( a.x * m.get(1) ) + ( a.y * m.get(5) ) + m.get(13);
        return result;
    }
}
