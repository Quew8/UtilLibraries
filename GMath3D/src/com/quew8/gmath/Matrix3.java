package com.quew8.gmath;

import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class Matrix3 {
    /**
     * Stores data in row major format.
     */
    private final float[] data;
    
    public Matrix3(float[] data) {
        this.data = data;
    }
    
    public Matrix3() {
        this(new float[]{
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
        });
    }
    
    public Matrix3(Matrix3 m) {
        this();
        setData(m.data);
    }
    
    public void setIdentity() {
        setData(
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
                );
    }
    
    public void setData(float[] data) {
        System.arraycopy(data, 0, this.data, 0, 9);
    }
    
    public void setData(
            float f1, float f2, float f3,
            float f4, float f5, float f6,
            float f7, float f8, float f9
            ) {
        
        data[0] = f1;
        data[1] = f4;
        data[2] = f7;
        data[3] = f2;
        data[4] = f5;
        data[5] = f8;
        data[6] = f3;
        data[7] = f6;
        data[8] = f9;
    }
    
    public void setDataTranspose(
            float f1, float f2, float f3, 
            float f4, float f5, float f6,
            float f7, float f8, float f9
            ) {
        
        data[0] = f1;
        data[1] = f2;
        data[2] = f3;
        data[3] = f4;
        data[4] = f5;
        data[5] = f6;
        data[6] = f7;
        data[7] = f8;
        data[8] = f9;
    }
    
    public Vector2 times(Vector2 v) {
        return Matrix3.times(new Vector2(), this, v);
    }
    
    public Matrix3 times(Matrix3 m) {
    	return Matrix3.times(new Matrix3(), this, m);
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
        int l20 = String.valueOf(data[2]).length();
        int l01 = String.valueOf(data[3]).length();
        int l11 = String.valueOf(data[4]).length();
        int l21 = String.valueOf(data[5]).length();
        int l02 = String.valueOf(data[6]).length();
        int l12 = String.valueOf(data[7]).length();
        int l22 = String.valueOf(data[8]).length();
        int clnLnt = Math.max(l00, Math.max(l10, Math.max(l20, Math.max(l01, 
                Math.max(l11, Math.max(l21, Math.max(l02, Math.max(l12, l22))))))));
        return "Matrix:\n"
                + data[0] + nBlanc(clnLnt - l00 + 2) + 
                    data[3] + nBlanc(clnLnt - l01 + 2) +
                    data[6] + nBlanc(clnLnt - l02 + 2) + "\n"
                + data[1] + nBlanc(clnLnt - l10 + 2) + 
                    data[4] + nBlanc(clnLnt - l11 + 2) +
                    data[7] + nBlanc(clnLnt - l12 + 2) + "\n"
                + data[2] + nBlanc(clnLnt - l20 + 2) + 
                    data[5] + nBlanc(clnLnt - l21 + 2) +
                    data[8] + nBlanc(clnLnt - l22 + 2);
    }
    
    private static String nBlanc(int n) {
        String s = " ";
        for(int i = 1; i < n; i++) {
            s += " ";
        }
        return s;
    }
    
    public static int indexOf(int column, int row) {
        return column + ( row * 3 );
    }
    
    public static Matrix3 makeOrtho(Matrix3 result, float left, float right, float bottom, float top) {
        result.setData(
                2 / (right - left), 0,                  -((right+left)/(right-left)), 
                0,                  2 / (top - bottom), -((top+bottom)/(top-bottom)), 
                0,                  0,                  1
        );
        return result;
    }
    
    public static Matrix3 translate(Matrix3 result, Matrix3 m, Vector2 v) {
        result.setData(
                m.get(0) + ( m.get(2) * v.getX() ),
                m.get(3) + ( m.get(5) * v.getX() ),
                m.get(6) + ( m.get(8) * v.getX() ),
                m.get(1) + ( m.get(2) * v.getY() ),
                m.get(4) + ( m.get(5) * v.getY() ),
                m.get(7) + ( m.get(8) * v.getY() ),
                m.get(2),
                m.get(5),
                m.get(8)
                );
        return result;
    }
    
    public static Matrix3 makeTranslation(Matrix3 result, Vector2 v) {
        result.setData(
                1, 0, v.getX(),
                0, 1, v.getY(),
                0, 0, 1
                );
        return result;
    }
    
    public static Matrix3 makeTranslation(Vector2 v) {
        return makeTranslation(new Matrix3(), v);
    }
    
    public static Matrix3 rotate(Matrix3 result, Matrix3 m, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                ( m.get(0) * costheta ) + ( m.get(1) * sintheta ),
                ( m.get(3) * costheta ) + ( m.get(4) * sintheta ),
                ( m.get(6) * costheta ) + ( m.get(7) * sintheta ),
                ( m.get(0) * -sintheta ) + ( m.get(1) * costheta ),
                ( m.get(3) * -sintheta ) + ( m.get(4) * costheta ),
                ( m.get(6) * -sintheta ) + ( m.get(7) * costheta ),
                m.get(2),
                m.get(5),
                m.get(8)
                );
        return result;
    }
    
    public static Matrix3 makeRotation(Matrix3 result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                costheta,  sintheta, 0,
                -sintheta, costheta, 0,
                0,         0,        1
        );
        return result;
    }
    
    public static Matrix3 makeRotation(float theta) {
        return makeRotation(new Matrix3(), theta);
    }
    
    public static Matrix3 scale(Matrix3 result, Matrix3 m, float xScale, float yScale) {
        result.setData(
                ( m.get(0) * xScale ), 
                ( m.get(3) * xScale ),
                ( m.get(6) * xScale ),
                ( m.get(1) * yScale ), 
                ( m.get(4) * yScale ),
                ( m.get(7) * yScale ),
                m.get(2),
                m.get(5),
                m.get(8)
                );
        return result;
    }
    
    public static Matrix3 scale(Matrix3 m, float xScale, float yScale) {
        return scale(new Matrix3(), m, xScale, yScale);
    }
    
    public static Matrix3 makeScaling(Matrix3 result, float xScale, float yScale) {
        result.setData(
                xScale, 0,      0,
                0,      yScale, 0,
                0,      0,      1
                );
        return result;
    }
    
    public static Matrix3 makeScaling(float xScale, float yScale) {
        return makeScaling(new Matrix3(), xScale, yScale);
    }
    
    public static Matrix3 makeScaling(Matrix3 result, Vector2 scale) {
        return makeScaling(result, scale.getX(), scale.getY());
    }
    
    public static Matrix3 makeScaling(Vector2 scale) {
        return makeScaling(new Matrix3(), scale);
    }
    
    public static Vector2 times(Vector2 result, Matrix3 m, Vector2 v) {
        return times(result, m.data, v);
    }
    
    public static Vector2 times(Vector2 result, float[] m, Vector2 v) {
        result.setX(( v.getX() * m[0] ) + ( v.getY() * m[3] ) + m[6] );
        result.setY(( v.getX() * m[1] ) + ( v.getY() * m[4] ) + m[7] ) ;
        return result;
    }
    
    public static Matrix3 times(Matrix3 result, Matrix3 m1, Matrix3 m2) {
    	times(result.data, m1.data, m2.data);
    	return result;
    }
    
    public static float[] times(float[] result, float[] m1, float[] m2) {
    	result[0] = ( ( m1[0] * m2[0] ) + ( m1[1] * m2[3] )  + ( m1[2] * m2[6] ));
    	result[1] = ( ( m1[0] * m2[1] ) + ( m1[1] * m2[4] )  + ( m1[2] * m2[7] ));
    	result[2] = ( ( m1[0] * m2[2] ) + ( m1[1] * m2[5] )  + ( m1[2] * m2[8] ));
    	result[3] = ( ( m1[3] * m2[0] ) + ( m1[4] * m2[3] )  + ( m1[5] * m2[6] ));
    	result[4] = ( ( m1[3] * m2[1] ) + ( m1[4] * m2[4] )  + ( m1[5] * m2[7] ));
    	result[5] = ( ( m1[3] * m2[2] ) + ( m1[4] * m2[5] )  + ( m1[5] * m2[8] ));
    	result[6] = ( ( m1[6] * m2[0] ) + ( m1[7] * m2[3] )  + ( m1[8] * m2[6] ));
    	result[7] = ( ( m1[6] * m2[1] ) + ( m1[7] * m2[4] )  + ( m1[8] * m2[7] ));
    	result[8] = ( ( m1[6] * m2[2] ) + ( m1[7] * m2[5] )  + ( m1[8] * m2[8] ));
        return result;
    }
}