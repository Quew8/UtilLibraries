package com.quew8.gmath;

public class Vector {
    public static final byte 
            NORMALIZE_BIT = 1,
            ABSOLUTE_BIT = 2,
            NEGATE_BIT = 4;
    
    private float x, y, z;
			
    public Vector(float x, float y, float z, byte op) {
        this.x = x;
        this.y = y;
        this.z = z;
        performOp(op);
    }
			
    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
	
    public Vector(Vector a, Vector b, byte op) {
        this(b.x - a.x, b.y - a.y, b.z - a.z, op);
    }
	
    public Vector(Vector a, Vector b) {
        this(b.x - a.x, b.y - a.y, b.z - a.z);
    }
	
    public Vector(Vector v, byte op) {
        this(v.x, v.y, v.z, op);
    }
	
    public Vector(Vector v) {
        this(v.x, v.y, v.z);
    }
	
    public Vector(Vector2 v, byte op) {
        this(v.getX(), v.getY(), 0, op);
    }
	
    public Vector(Vector2 v) {
        this(v.getX(), v.getY(), 0);
    }
	
    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public Vector setXYZ(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vector setX(float x) {
        this.x = x;
        return this;
    }

    public Vector setY(float y) {
        this.y = y;
        return this;
    }
	
    public Vector setZ(float z) {
        this.z = z;
        return this;
    }
    
    public Vector2 getVector2() {
        return new Vector2(x, y);
    }
    
    public float[] getXYZ() {
        return new float[]{x, y, z};
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
	
    public boolean equals(Vector v) {
        return (this.x == v.getX() && this.y == v.getY() && this.z == v.getZ());
    }
	
    public final Vector performOp(byte op) {
        if((op & NORMALIZE_BIT) != 0) {
            float length = GMath.length(x, y, z);
            this.x /= length;
            this.y /= length;
            this.z /= length;
        }
        if((op & ABSOLUTE_BIT) != 0) {
            this.x = Math.abs(this.x);
            this.y = Math.abs(this.y);
            this.z = Math.abs(this.z);
        }
        if((op & NEGATE_BIT) != 0) {
            this.x = -this.x;
            this.y = -this.y;
            this.z = -this.z;
        }
        return this;
    }
    
    public Vector add(Vector b) {
        return Vector.add(this, this, b);
    }
	
    public Vector subtract(Vector b) {
        return Vector.subtract(this, this, b);
    }
	
    public Vector times(Vector b) {
        return Vector.times(this, this, b);
    }
	
    public Vector times(float f) {
        return Vector.times(this, this, f);
    }
	
    public Vector divide(float f) {
        return Vector.divide(this, this, f);
    }
	
    public Vector add(float f) {
        return Vector.add(this, this, f); 
    }
	
    public Vector subtract(float f) {
        return Vector.subtract(this, this, f);
    }
	
    public boolean isNegative() {
        return (x < 0 && y < 0 && z < 0);
    }
	
    public boolean hasMagnitudeZero() {
        return (x == 0 && y == 0 && z == 0);
    }
	
    public float magnitudeSquared() {
        return GMath.lengthSquared(x, y, z);
    }
	
    public float magnitude() {
        return GMath.length(x, y, z);
    }
    
    public float manhattenMagnitude() {
        return x + y + z;
    }
    
    public float distance(Vector other) {
        return Vector.subtract(new Vector(), this, other).magnitude();
    }
    
    public float distanceSquared(Vector other) {
        return Vector.subtract(new Vector(), this, other).magnitudeSquared();
    }
    
    public float manhattenDistance(Vector other) {
        return Vector.subtract(new Vector(), this, other).manhattenMagnitude();
    }
	
    public float normalize() {
        float magnitude = magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
        return magnitude;
    }
	
    public float normalizeIfNot() {
        float magnitudeSq = magnitudeSquared();
        if(magnitudeSq != 1) {
            float magnitude = GMath.sqrt(magnitudeSq);
            this.x /= magnitude;
            this.y /= magnitude;
            this.z /= magnitude;
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
    
    public Vector absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        return this;
    }
	
    public Vector negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
	
    public static Vector add(Vector result, Vector a, Vector b) {
        result.x = a.x + b.x;
        result.y = a.y + b.y;
        result.z = a.z + b.z;
        return result;
    }
	
    public static Vector subtract(Vector result, Vector a, Vector b) {
        result.x = a.x - b.x;
        result.y = a.y - b.y;
        result.z = a.z - b.z;
        return result;
    }
	
    public static Vector times(Vector result, Vector a, Vector b) {
        result.x = a.x * b.x;
        result.y = a.y * b.y;
        result.z = a.z * b.z;
        return result;
    }
	
    public static Vector divide(Vector result, Vector a, Vector b) {
        result.x = a.x / b.x;
        result.y = a.y / b.y;
        result.z = a.z / b.z;
        return result;
    }
	
    public static Vector times(Vector result, Vector a, float f) {
        result.x = a.x * f;
        result.y = a.y * f;
        result.z = a.z * f;
        return result;
    }
	
    /**
     * 
     * @param result
     * @param a
     * @param f
     * @return 
     */
    public static Vector divide(Vector result, Vector a, float f) {
        result.x = a.x / f;
        result.y = a.y / f;
        result.z = a.z / f;
        return result;
    }
	
    public static Vector add(Vector result, Vector a, float f) {
        result.x = a.x + f;
        result.y = a.y + f;
        result.z = a.z + f;
        return result;
    }
	
    public static Vector subtract(Vector result, Vector a, float f) {
        result.x = a.x - f;
        result.y = a.y - f;
        result.z = a.z - f;
        return result;
    }
	
    /*public static Vector times(Vector result, Vector a, Matrix m) {
        result.x = ( a.x * m.get(0) ) + ( a.y * m.get(4) ) + ( a.z * m.get(8) ) + m.get(12);
        result.y = ( a.x * m.get(1) ) + ( a.y * m.get(5) ) + ( a.z * m.get(9) ) + m.get(13);
        result.z = ( a.x * m.get(2) ) + ( a.y * m.get(6) ) + ( a.z * m.get(10) ) + m.get(14);
        return result;
    }*/
}