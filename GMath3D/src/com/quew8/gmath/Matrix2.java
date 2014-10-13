package com.quew8.gmath;

import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class Matrix2 {
    /**
     * Stores data in row major format.
     */
    private final float[] data;
    
    public Matrix2(float[] data) {
        this.data = data;
    }
    
    public Matrix2() {
        this(new float[]{
            1, 0,
            0, 1
        });
    }
    
    public Matrix2(Matrix2 m) {
        this();
        setData(m.data);
    }
    
    public void setIdentity() {
        setData(
                1, 0,
                0, 1
                );
    }
    
    public void setData(float[] data) {
        System.arraycopy(data, 0, this.data, 0, 4);
    }
    
    public void setData(
            float f1, float f2, 
            float f3, float f4
            ) {
        
        data[0] = f1;
        data[1] = f3;
        data[2] = f2;
        data[3] = f4;
    }
    
    public void setDataTranspose(
            float f1, float f2, 
            float f3, float f4
            ) {
        
        data[0] = f1;
        data[1] = f2;
        data[2] = f3;
        data[3] = f4;
    }
    
    public Vector2 times(Vector2 v) {
        return Matrix2.times(new Vector2(), this, v);
    }
    
    public Matrix2 times(Matrix2 m) {
    	return Matrix2.times(new Matrix2(), this, m);
    }
    
    public void putIn(FloatBuffer fb) {
        fb.put(data);
        fb.rewind();
    }
    
    public void set(int i, float f) {
        data[i] = f;
    }
    
    public void set(int column, int row, float f) {
        data[indexOf(column, row)] = f;
    }
    
    public float get(int i) {
        return data[i];
    }
    
    public float get(int column, int row) {
        return data[indexOf(column, row)];
    }
    
    @Override
    public String toString() {
        int l00 = String.valueOf(data[0]).length();
        int l10 = String.valueOf(data[1]).length();
        int l01 = String.valueOf(data[2]).length();
        int l11 = String.valueOf(data[3]).length();
        int clnLnt = Math.max(l00, Math.max(l10, Math.max(l01, l11)));
        return "Matrix:\n"
                + data[0] + nBlanc(clnLnt - l00 + 2) + 
                    data[2] + nBlanc(clnLnt - l01 + 2) + "\n"
                + data[1] + nBlanc(clnLnt - l10 + 2) + 
                    data[3] + nBlanc(clnLnt - l11 + 2);
    }
    
    private static String nBlanc(int n) {
        String s = " ";
        for(int i = 1; i < n; i++) {
            s += " ";
        }
        return s;
    }
    
    public static int indexOf(int column, int row) {
        return column + ( row * 2 );
    }
    
    public static Matrix2 makeRotation(Matrix2 result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                costheta, sintheta,
                -sintheta, costheta
        );
        return result;
    }
    
    public static Matrix2 makeRotation(float theta) {
        return makeRotation(new Matrix2(), theta);
    }
    
    public static Matrix2 scale(Matrix2 result, Matrix2 m, float xScale, float yScale) {
        result.setData(
                ( m.get(0) * xScale ), 
                ( m.get(2) * xScale ),
                ( m.get(1) * yScale ), 
                ( m.get(3) * yScale )
                );
        return result;
    }
    
    public static Matrix2 scale(Matrix2 m, float xScale, float yScale) {
        return scale(new Matrix2(), m, xScale, yScale);
    }
    
    public static Matrix2 makeScaling(Matrix2 result, float xScale, float yScale) {
        result.setData(
                xScale, 0,
                0,      yScale
                );
        return result;
    }
    
    public static Matrix2 makeScaling(float xScale, float yScale) {
        return makeScaling(new Matrix2(), xScale, yScale);
    }
    
    public static Matrix2 makeScaling(Matrix2 result, Vector2 scale) {
        return makeScaling(result, scale.getX(), scale.getY());
    }
    
    public static Matrix2 makeScaling(Vector2 scale) {
        return makeScaling(new Matrix2(), scale);
    }
    
    public static Vector2 times(Vector2 result, Matrix2 m, Vector2 v) {
        return times(result, m.data, v);
    }
    
    public static Vector2 times(Vector2 result, float[] m, Vector2 v) {
        result.setX(( v.getX() * m[0] ) + ( v.getY() * m[2] ));
        result.setY(( v.getX() * m[1] ) + ( v.getY() * m[3] ));
        return result;
    }
    
    public static Matrix2 times(Matrix2 result, Matrix2 m1, Matrix2 m2) {
    	times(result.data, m1.data, m2.data);
    	return result;
    }
    
    public static float[] times(float[] result, float[] m1, float[] m2) {
    	result[0] = ( ( m1[0] * m2[0] ) + ( m1[1] * m2[2] ) );
    	result[1] = ( ( m1[0] * m2[1] ) + ( m1[1] * m2[3] ) );
    	result[4] = ( ( m1[2] * m2[0] ) + ( m1[3] * m2[4] ) );
    	result[5] = ( ( m1[2] * m2[1] ) + ( m1[3] * m2[5] ) );
        return result;
    }
}
