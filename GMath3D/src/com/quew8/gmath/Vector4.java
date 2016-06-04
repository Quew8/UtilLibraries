package com.quew8.gmath;

public class Vector4 {
    private float x, y, z, w;
			
    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
	
    public Vector4(Vector4 a, Vector4 b) {
        this(b.x - a.x, b.y - a.y, b.z - a.z, b.w - a.w);
    }
	
    public Vector4(Vector v) {
        this(v.getX(), v.getY(), v.getZ(), 1);
    }
	
    public Vector4() {
        this(0, 0, 0, 1);
    }
    
    public Vector4 setXYZW(Vector4 src) {
        this.x = src.getX();
        this.y = src.getY();
        this.z = src.getZ();
        this.w = src.getW();
        return this;
    }
    
    public Vector4 setXYZW(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
    
    public Vector4 setX(float x) {
        this.x = x;
        return this;
    }

    public Vector4 setY(float y) {
        this.y = y;
        return this;
    }
	
    public Vector4 setZ(float z) {
        this.z = z;
        return this;
    }
	
    public Vector4 setW(float w) {
        this.w = w;
        return this;
    }
    
    public Vector getVector(Vector out) {
        out.setX(x);
        out.setY(y);
        out.setZ(z);
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
    
    public float getW() {
        return w;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Float.floatToIntBits(this.x);
        hash = 29 * hash + Float.floatToIntBits(this.y);
        hash = 29 * hash + Float.floatToIntBits(this.z);
        hash = 29 * hash + Float.floatToIntBits(this.w);
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
        final Vector4 other = (Vector4) obj;
        if(Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if(Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if(Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
            return false;
        }
        return Float.floatToIntBits(this.w) == Float.floatToIntBits(other.w);
    }
    
    public Vector4 add(Vector4 out, Vector4 b) {
        return Vector4.add(out, this, b);
    }
	
    public Vector4 subtract(Vector4 out, Vector4 b) {
        return Vector4.subtract(out, this, b);
    }
	
    public Vector4 times(Vector4 out, Vector4 b) {
        return Vector4.times(out, this, b);
    }
	
    public Vector4 scale(Vector4 out, float f) {
        return Vector4.scale(out, this, f);
    }
	
    public Vector4 addBias(Vector4 out, float f) {
        return Vector4.addBias(out, this, f); 
    }
	
    public boolean isNegative() {
        return (x < 0 && y < 0 && z < 0 && w < 0);
    }
	
    public boolean isMagnitudeZero() {
        return (x == 0 && y == 0 && z == 0 && w == 0);
    }
	
    public float magnitudeSquared() {
        return GMath.lengthSquared(x, y, z, w);
    }
	
    public float magnitude() {
        return GMath.length(x, y, z, w);
    }
    
    public float manhattenMagnitude() {
        return GMath.abs(x) + GMath.abs(y) + GMath.abs(z) + GMath.abs(w);
    }
    
    public float distanceSquared(Vector4 other) {
        return GMath.lengthSquared(x - other.x, y - other.y, z - other.z, w - other.w);
    }
    
    public float distance(Vector4 other) {
        return GMath.length(x - other.x, y - other.y, z - other.z, w - other.w);
    }
    
    public float manhattenDistance(Vector4 other) {
        return GMath.abs(x - other.x) + GMath.abs(y - other.y) + GMath.abs(z - other.z)
                + GMath.abs(w - other.w);
    }
	
    public Vector4 normalize(Vector4 out) {
        float magnitude = magnitude();
        out.x = this.x / magnitude;
        out.y = this.y / magnitude;
        out.z = this.z / magnitude;
        out.w = this.w / magnitude;
        return out;
    }
	
    public Vector4 normalizeIfNot(Vector4 out) {
        float magnitudeSq = magnitudeSquared();
        if(magnitudeSq != 1) {
            float magnitude = GMath.sqrt(magnitudeSq);
            out.x = this.x / magnitude;
            out.y = this.y / magnitude;
            out.z = this.z / magnitude;
            out.w = this.w / magnitude;
            return out;
        }
        return out.setXYZW(this);
    }
	
    public Vector4 negate(Vector4 out) {
        out.x = -this.x;
        out.y = -this.y;
        out.z = -this.z;
        out.w = -this.w;
        return out;
    }

    @Override
    public String toString() {
        return "Vector{" + x + ", " + y + ", " + z + ", " + w + '}';
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
	
    public static Vector4 scale(Vector4 result, Vector4 a, float f) {
        result.x = a.x * f;
        result.y = a.y * f;
        result.z = a.z * f;
        result.w = a.w * f;
        return result;
    }
	
    public static Vector4 addBias(Vector4 result, Vector4 a, float f) {
        result.x = a.x + f;
        result.y = a.y + f;
        result.z = a.z + f;
        result.w = a.w + f;
        return result;
    }
}