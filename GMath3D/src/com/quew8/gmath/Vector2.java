package com.quew8.gmath;

/**
 * 
 * @author Quew8
 */
public class Vector2 {
    private float x, y;
    
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 a, Vector2 b) {
        this(b.x - a.x, b.y - a.y);
    }
    
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2 setXY(Vector2 src) {
        this.x = src.getX();
        this.y = src.getY();
        return this;
    }
    
    public Vector2 setXY(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Vector2 setX(float x) {
        this.x = x;
        return this;
    }

    public Vector2 setY(float y) {
        this.y = y;
        return this;
    }
    
    public Vector2 getXY(Vector2 out) {
        out.x = this.x;
        out.y = this.y;
        return out;
    }
    
    public float[] getXY(float[] out, int offset) {
        out[offset] = x;
        out[offset + 1] = y;
        return out;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final Vector2 other = (Vector2) obj;
        if(Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        return Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Float.floatToIntBits(this.x);
        hash = 97 * hash + Float.floatToIntBits(this.y);
        return hash;
    }
    
    public boolean equals(Vector2 v) {
        return (this.x == v.getX() && this.y == v.getY());
    }
    
    public Vector2 add(Vector2 out, Vector2 b) {
        return Vector2.add(out, this, b);
    }
	
    public Vector2 subtract(Vector2 out, Vector2 b) {
        return Vector2.subtract(out, this, b);
    }
	
    public Vector2 times(Vector2 out, Vector2 b) {
        return Vector2.times(out, this, b);
    }
	
    public Vector2 scale(Vector2 out, float f) {
        return Vector2.scale(out, this, f);
    }
	
    public Vector2 addBias(Vector2 out, float f) {
        return Vector2.addBias(out, this, f); 
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
    
    public float manhattenMagnitude() {
        return GMath.abs(x) + GMath.abs(y);
    }
    
    public float distance(Vector2 other) {
        return GMath.length(this.x - other.x, this.y - other.y);
    }
    
    public float distanceSquared(Vector2 other) {
        return GMath.lengthSquared(this.x - other.x, this.y - other.y);
    }
    
    public float manhattenDistance(Vector2 other) {
        return GMath.abs(this.x - other.x) + GMath.abs(this.y - other.y);
    }
    
    public Vector2 normalize(Vector2 out) {
        float magnitude = magnitude();
        out.x = this.x / magnitude;
        out.y = this.y / magnitude;
        return out;
    }
    
    public Vector2 negate(Vector2 out) {
        out.x = -this.x;
        out.y = -this.y;
        return out;
    }
    
    @Override
    public String toString() {
        return "Vector2{" + "x=" + x + ", y=" + y + '}';
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
	
    public static Vector2 divide(Vector2 result, Vector2 a, Vector2 b) {
        result.x = a.x / b.x;
        result.y = a.y / b.y;
        return result;
    }
	
    public static Vector2 scale(Vector2 result, Vector2 a, float f) {
        result.x = a.x * f;
        result.y = a.y * f;
        return result;
    }
	
    public static Vector2 addBias(Vector2 result, Vector2 a, float f) {
        result.x = a.x + f;
        result.y = a.y + f;
        return result;
    }
}
