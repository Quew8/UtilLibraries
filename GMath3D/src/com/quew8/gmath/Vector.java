package com.quew8.gmath;

public class Vector {
    private float x, y, z;
			
    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
	
    public Vector(Vector a, Vector b) {
        this(b.x - a.x, b.y - a.y, b.z - a.z);
    }
	
    public Vector(Vector2 v) {
        this(v.getX(), v.getY(), 0);
    }
	
    public Vector() {
        this(0, 0, 0);
    }
    
    public Vector setXYZ(Vector src) {
        this.x = src.getX();
        this.y = src.getY();
        this.z = src.getZ();
        return this;
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
    
    public Vector2 getVector2(Vector2 out) {
        out.setX(x);
        out.setY(y);
        return out;
    }
    
    public float[] getXYZ(float[] out, int offset) {
        out[offset] = x;
        out[offset + 1] = y;
        out[offset + 2] = z;
        return out;
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
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Float.floatToIntBits(this.x);
        hash = 13 * hash + Float.floatToIntBits(this.y);
        hash = 13 * hash + Float.floatToIntBits(this.z);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if(Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if(Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        return Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
    }
    
    public Vector add(Vector b) {
        return add(this, b);
    }
    
    public Vector add(Vector out, Vector b) {
        return Vector.add(out, this, b);
    }
	
    public Vector subtract(Vector b) {
        return subtract(this, b);
    }
	
    public Vector subtract(Vector out, Vector b) {
        return Vector.subtract(out, this, b);
    }
	
    public Vector times(Vector b) {
        return times(this, b);
    }
	
    public Vector times(Vector out, Vector b) {
        return Vector.times(out, this, b);
    }
	
    public Vector scale(float f) {
        return scale(this, f);
    }
	
    public Vector scale(Vector out, float f) {
        return Vector.scale(out, this, f);
    }
	
    public Vector addBias(float f) {
        return addBias(this, f); 
    }
	
    public Vector addBias(Vector out, float f) {
        return Vector.addBias(out, this, f); 
    }
	
    public Vector normalize() {
        return normalize(this);
    }
	
    public Vector normalize(Vector out) {
        return Vector.normalize(out, this);
    }
	
    public Vector normalizeIfNot(Vector out) {
        float magnitudeSq = magnitudeSquared();
        if(magnitudeSq != 1) {
            float magnitude = GMath.sqrt(magnitudeSq);
            out.x = this.x / magnitude;
            out.y = this.y / magnitude;
            out.z = this.z / magnitude;
            return out;
        }
        return out.setXYZ(this);
    }
	
    public Vector negate() {
        return negate(this);
    }
	
    public Vector negate(Vector out) {
        return Vector.negate(out, this);
    }
	
    public boolean isNegative() {
        return (x < 0 && y < 0 && z < 0);
    }
	
    public boolean isMagnitudeZero() {
        return (x == 0 && y == 0 && z == 0);
    }
	
    public float magnitudeSquared() {
        return GMath.lengthSquared(x, y, z);
    }
	
    public float magnitude() {
        return GMath.length(x, y, z);
    }
    
    public float manhattenMagnitude() {
        return GMath.abs(x) + GMath.abs(y) + GMath.abs(z);
    }
    
    public float distanceSquared(Vector other) {
        return GMath.lengthSquared(x - other.getX(), y - other.getY(), z - other.getZ());
    }
    
    public float distance(Vector other) {
        return GMath.length(x - other.getX(), y - other.getY(), z - other.getZ());
    }
    
    public float manhattenDistance(Vector other) {
        return GMath.abs(x - other.getX()) + GMath.abs(y - other.getY()) + GMath.abs(z - other.getZ());
    }

    @Override
    public String toString() {
        return "Vector{" + x + ", " + y + ", " + z + '}';
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
	
    public static Vector scale(Vector result, Vector a, float f) {
        result.x = a.x * f;
        result.y = a.y * f;
        result.z = a.z * f;
        return result;
    }
	
    public static Vector addBias(Vector result, Vector a, float f) {
        result.x = a.x + f;
        result.y = a.y + f;
        result.z = a.z + f;
        return result;
    }
    
    public static Vector normalize(Vector result, Vector a) {
        float magnitude = a.magnitude();
        result.x = a.x / magnitude;
        result.y = a.y / magnitude;
        result.z = a.z / magnitude;
        return result;
    }
    
    public static Vector negate(Vector result, Vector a) {
        result.x = -a.x;
        result.y = -a.y;
        result.z = -a.z;
        return result;
    }
}