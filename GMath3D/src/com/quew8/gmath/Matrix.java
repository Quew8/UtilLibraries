package com.quew8.gmath;

import static com.quew8.gmath.Axis.NEGATIVE_X;
import static com.quew8.gmath.Axis.NEGATIVE_Y;
import static com.quew8.gmath.Axis.NEGATIVE_Z;
import static com.quew8.gmath.Axis.X;
import static com.quew8.gmath.Axis.Y;
import static com.quew8.gmath.Axis.Z;
import java.nio.FloatBuffer;

/**
 * 
 * @author Quew8
 */
public class Matrix {
    /**
     * Stores data in row major format.
     */
    private final float[] data;
    
    public Matrix(float[] data) {
        this.data = data;
    }
    
    public Matrix() {
        this(new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        });
    }
    
    public Matrix(FloatBuffer fb) {
        this(new float[]{
            fb.get(0), fb.get(1), fb.get(2), fb.get(3), 
            fb.get(4), fb.get(5), fb.get(6), fb.get(7), 
            fb.get(8), fb.get(9), fb.get(10), fb.get(11), 
            fb.get(12), fb.get(13), fb.get(14), fb.get(15)
        });
    }
    
    /**
     * Requires data in column-major format.
     * @param f1
     * @param f2
     * @param f3
     * @param f4
     * @param f5
     * @param f6
     * @param f7
     * @param f8
     * @param f9
     * @param f10
     * @param f11
     * @param f12
     * @param f13
     * @param f14
     * @param f15
     * @param f16 
     */
    public Matrix(float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7, float f8, float f9, float f10, float f11, float f12, float f13, 
            float f14, float f15, float f16) {
    	
        this(new float[]{
            f1, f5, f9, f13, 
            f2, f6, f10, f14, 
            f3, f7, f11, f15, 
            f4, f8, f12, f16
        });
    }
    
    public Matrix(Matrix m) {
        this();
        setData(m.data);
    }
    
    public void setIdentity() {
        setData(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
                );
    }
    
    public void setData(float[] data) {
        System.arraycopy(data, 0, this.data, 0, 16);
    }
    
    /**
     * Requires data in column-major ordering.
     * 
     * @param f1
     * @param f2
     * @param f3
     * @param f4
     * @param f5
     * @param f6
     * @param f7
     * @param f8
     * @param f9
     * @param f10
     * @param f11
     * @param f12
     * @param f13
     * @param f14
     * @param f15
     * @param f16 
     */
    public void setData(
            float f1, float f2, float f3, float f4,
            float f5, float f6, float f7, float f8,
            float f9, float f10, float f11, float f12,
            float f13, float f14, float f15, float f16
            ) {
        
        data[0] = f1;
        data[1] = f5;
        data[2] = f9;
        data[3] = f13;
        data[4] = f2;
        data[5] = f6;
        data[6] = f10;
        data[7] = f14;
        data[8] = f3;
        data[9] = f7;
        data[10] = f11;
        data[11] = f15;
        data[12] = f4;
        data[13] = f8;
        data[14] = f12;
        data[15] = f16;
    }
    
    /**
     * Requires data in row-major ordering.
     * @param f1
     * @param f2
     * @param f3
     * @param f4
     * @param f5
     * @param f6
     * @param f7
     * @param f8
     * @param f9
     * @param f10
     * @param f11
     * @param f12
     * @param f13
     * @param f14
     * @param f15
     * @param f16 
     */
    public void setDataTranspose(
            float f1, float f2, float f3, float f4,
            float f5, float f6, float f7, float f8,
            float f9, float f10, float f11, float f12,
            float f13, float f14, float f15, float f16
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
        data[9] = f10;
        data[10] = f11;
        data[11] = f12;
        data[12] = f13;
        data[13] = f14;
        data[14] = f15;
        data[15] = f16;
    }
    
    public Matrix getRotation() {
        return Matrix.getRotationMatrix(new Matrix(), this);
    }
    
    public Vector times(Vector v) {
        return Matrix.times(new Vector(), this, v);
    }
    
    public Vector timesRotation(Vector v) {
        return Matrix.timesRotation(new Vector(), this, v);
    }
    
    public Matrix times(Matrix m) {
    	return Matrix.times(new Matrix(), this, m);
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
    
    public Vector getTranslation() {
        return new Vector(data[12], data[13], data[14]);
    }
    
    public Vector getFowardDirection() {
        return new Vector(data[2], data[6], data[10]);
    }
    
    public Vector getFowardDirectionXZ() {
        return new Vector(data[2], 0, data[10], Vector.NORMALIZE_BIT);
    }
    
    public Vector getBackDirection() {
        return new Vector(-data[2], -data[6], -data[10]);
    }
    
    public Vector getBackDirectionXZ() {
        return new Vector(-data[2], 0, -data[10], Vector.NORMALIZE_BIT);
    }
    
    public Vector getUpDirection() {
        return new Vector(data[1], data[5], data[9]);
    }
    
    public Vector getDownDirection() {
        return new Vector(-data[1], -data[5], -data[9]);
    }
    
    public Vector getRightDirection() {
        return new Vector(-data[0], -data[4], -data[8]);
    }
    
    public Vector getRightDirectionXZ() {
        return new Vector(-data[0], 0, -data[8], Vector.NORMALIZE_BIT);
    }
    
    public Vector getLeftDirection() {
        return new Vector(data[0], data[4], data[8]);
    }
    
    public Vector getLeftDirectionXZ() {
        return new Vector(data[0], 0, data[8], Vector.NORMALIZE_BIT);
    }
    
    @Override
    public String toString() {
        int l00 = String.valueOf(data[0]).length();
        int l10 = String.valueOf(data[1]).length();
        int l20 = String.valueOf(data[2]).length();
        int l30 = String.valueOf(data[3]).length();
        int l01 = String.valueOf(data[4]).length();
        int l11 = String.valueOf(data[5]).length();
        int l21 = String.valueOf(data[6]).length();
        int l31 = String.valueOf(data[7]).length();
        int l02 = String.valueOf(data[8]).length();
        int l12 = String.valueOf(data[9]).length();
        int l22 = String.valueOf(data[10]).length();
        int l32 = String.valueOf(data[11]).length();
        int clnLnt = Math.max(Math.max(Math.max(l00, Math.max(l10, Math.max(l20, l30))), 
                Math.max(l01, Math.max(l11, Math.max(l21, l31)))), 
                Math.max(l02, Math.max(l12, Math.max(l22, l32))));
        return "Matrix:\n"
                + data[0] + nBlanc(clnLnt - l00 + 2) + 
                    data[4] + nBlanc(clnLnt - l01 + 2) + 
                    data[8] + nBlanc(clnLnt - l02 + 2) + 
                    data[12] + "\n"
                + data[1] + nBlanc(clnLnt - l10 + 2) + 
                    data[5] + nBlanc(clnLnt - l11 + 2) + 
                    data[9] + nBlanc(clnLnt - l12 + 2) + 
                    data[13] + "\n"
                + data[2] + nBlanc(clnLnt - l20 + 2) + 
                    data[6] + nBlanc(clnLnt - l21 + 2) + 
                    data[10] + nBlanc(clnLnt - l22 + 2) + 
                    data[14] + "\n"
                + data[3] + nBlanc(clnLnt - l30 + 2) + 
                    data[7] + nBlanc(clnLnt - l31 + 2) + 
                    data[11] + nBlanc(clnLnt - l32 + 2) + 
                    data[15];
    }
    
    private static String nBlanc(int n) {
        String s = " ";
        for(int i = 1; i < n; i++) {
            s += " ";
        }
        return s;
    }
    
    public static int indexOf(int column, int row) {
        return column + (row*4);
    }
    
    public static Matrix makeFrustum(Matrix result, float left, float right, float bottom, float top, float near, float far) {
        float f = 2 * near,
                g = right - left,
                h = top - bottom,
                j = far - near;
        result.setData(
                f / g, 0,     (right+left)/g, 0,
                0,     f / h, (top+bottom)/h, 0,
                0,     0,     -(far+near)/j,  -(f * far)/j,
                0,     0,     -1,             0
                );
        return result;
    }
    
    public static Matrix makeFrustum(float left, float right, float bottom, float top, float near, float far) {
        return makeFrustum(new Matrix(), left, right, bottom, top, near, far);
    }
    
    public static Matrix makePerspective(Matrix result, float fovy, float aspect, float near, float far) {
        float top = ( near * GMath.tan(fovy) ) / 2;
        float bottom = -top;
        float right = top * aspect;
        float left = -right;
        return makeFrustum(result, left, right, bottom, top, near, far);
    }
    
    public static Matrix makePerspective(float fovy, float aspect, float near, float far) {
        return makePerspective(new Matrix(), fovy, aspect, near, far);
    }
    
    public static Matrix makeOrtho(Matrix result, float left, float right, float bottom, float top, float near, float far) {
        result.setData(
                2 / (right - left), 0,                  0,                -((right+left)/(right-left)),
                0,                  2 / (top - bottom), 0,                -((top+bottom)/(top-bottom)),
                0,                  0,                  -2 / (far - near), -((far+near)/(far-near)),
                0,                  0,                  0,                1
                );
        return result;
    }
    
    public static Matrix makeOrtho(float left, float right, float bottom, float top, float near, float far) {
        return makeOrtho(new Matrix(), left, right, bottom, top, near, far);
    }
    
    public static Matrix makeOrtho2D(Matrix result, float left, float right, float bottom, float top) {
        result.setData(
                2 / (right - left), 0,                  0, -((right+left)/(right-left)),
                0,                  2 / (top - bottom), 0, -((top+bottom)/(top-bottom)),
                0,                  0,                  1, 0,
                0,                  0,                  0, 1
                );
        return result;
    }
    
    public static Matrix makeOrtho2D(float left, float right, float bottom, float top) {
        return makeOrtho2D(new Matrix(), left, right, bottom, top);
    }
    
    public static Matrix translate(Matrix result, Matrix m, Vector v) {
        result.setData(
                m.get(0) + ( m.get(3) * v.getX() ), 
                m.get(4) + ( m.get(7) * v.getX() ), 
                m.get(8) + ( m.get(11) * v.getX() ), 
                m.get(12) + ( m.get(15) * v.getX() ), 
                m.get(1) + ( m.get(3) * v.getY() ), 
                m.get(5) + ( m.get(7) * v.getY() ), 
                m.get(9) + ( m.get(11) * v.getY() ), 
                m.get(13) + ( m.get(15) * v.getY() ), 
                m.get(2) + ( m.get(3) * v.getZ() ), 
                m.get(6) + ( m.get(7) * v.getZ() ), 
                m.get(10) + ( m.get(11) * v.getZ() ), 
                m.get(14) + ( m.get(15) * v.getZ() ), 
                m.get(3), 
                m.get(7), 
                m.get(11), 
                m.get(15)
                );
        return result;
    }
    
    public static Matrix translate(Matrix m, Vector v) {
        return translate(new Matrix(), m, v);
    }
    
    public static Matrix makeTranslation(Matrix result, Vector v) {
        result.setData(
                1, 0, 0, v.getX(),
                0, 1, 0, v.getY(), 
                0, 0, 1, v.getZ(),
                0, 0, 0, 1
                );
        return result;
    }
    
    public static Matrix makeTranslation(Vector v) {
        return makeTranslation(new Matrix(), v);
    }
    
    public static Matrix makeRotation(Matrix result, Vector v, float theta) {
        float c = GMath.cos(theta);
        float s = GMath.sin(theta);
        float x = v.getX();
        float y = v.getY();
        float z = v.getZ();
        float f = 1 - c;
        float xs = x * s;
        float ys = y * s;
        float zs = z * s;
        result.setData(
                ( ( x * x ) * f ) + c,  ( ( x * y ) * f ) - zs, ( ( x * z ) * f ) + ys, 0, 
                ( ( y * x ) * f ) - zs, ( ( y * y ) * f ) + c,  ( ( y * z ) * f ) + xs, 0, 
                ( ( z * x ) * f ) - ys, ( ( z * y ) * f ) + xs, ( ( z * z ) * f ) + c,  0, 
                0,                      0,                      0,                      1
        );
        return result;
    }
    
    public static Matrix makeRotation(Vector v, float theta) {
        return makeRotation(new Matrix(), v, theta);
    }
    
    public static Matrix rotateX(Matrix result, Matrix m, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                m.get(0), 
                m.get(4),
                m.get(8), 
                m.get(12), 
                ( m.get(1) * costheta ) + ( m.get(2) * sintheta ), 
                ( m.get(5) * costheta ) + ( m.get(6) * sintheta ), 
                ( m.get(9) * costheta ) + ( m.get(10) * sintheta ), 
                ( m.get(13) * costheta ) + ( m.get(14) * sintheta ), 
                ( m.get(1) * -sintheta ) + ( m.get(2) * costheta ), 
                ( m.get(5) * -sintheta ) + ( m.get(6) * costheta ), 
                ( m.get(9) * -sintheta ) + ( m.get(10) * costheta ), 
                ( m.get(13) * -sintheta ) + ( m.get(14) * costheta ), 
                m.get(3), 
                m.get(7), 
                m.get(11), 
                m.get(15)
                );
        return result;
    }
    
    public static Matrix rotateX(Matrix m, float theta) {
        return rotateX(new Matrix(), m, theta);
    }
    
    public static Matrix makeXRotation(Matrix result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                1, 0,         0,        0,
                0, costheta,  sintheta, 0,
                0, -sintheta, costheta, 0,
                0, 0,         0, 1
                );
        return result;
    }
    
    public static Matrix makeXRotation(float theta) {
        return makeXRotation(new Matrix(), theta);
    }
    
    public static Matrix rotateY(Matrix result, Matrix m, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                ( m.get(0) * costheta ) +  ( m.get(2) * -sintheta ), 
                ( m.get(4) * costheta ) +  ( m.get(6) * -sintheta ),
                ( m.get(8) * costheta ) +  ( m.get(10) * -sintheta ), 
                ( m.get(12) * costheta ) +  ( m.get(14) * -sintheta ), 
                m.get(1), 
                m.get(5), 
                m.get(9), 
                m.get(13), 
                ( m.get(0) * sintheta ) +  ( m.get(2) * costheta ), 
                ( m.get(4) * sintheta ) +  ( m.get(6) * costheta ), 
                ( m.get(8) * sintheta ) +  ( m.get(10) * costheta ), 
                ( m.get(12) * sintheta ) +  ( m.get(14) * costheta ), 
                m.get(3), 
                m.get(7), 
                m.get(11), 
                m.get(15)
                );
        return result;
    }
    
    public static Matrix rotateY(Matrix m, float theta) {
        return rotateY(new Matrix(), m, theta);
    }
    
    public static Matrix makeYRotation(Matrix result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                costheta, 0, -sintheta, 0,
                0,        1, 0,         0,
                sintheta, 0, costheta,  0,
                0,        0, 0,         1
                );
        return result;
    }
    
    public static Matrix makeYRotation(float theta) {
        return makeYRotation(new Matrix(), theta);
    }
    
    public static Matrix rotateZ(Matrix result, Matrix m, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                ( m.get(0) * costheta ) + ( m.get(1) * sintheta ), 
                ( m.get(4) * costheta ) + ( m.get(5) * sintheta ),
                ( m.get(8) * costheta ) + ( m.get(9) * sintheta ), 
                ( m.get(12) * costheta ) + ( m.get(13) * sintheta), 
                ( m.get(0) * -sintheta ) + ( m.get(1) * costheta ), 
                ( m.get(4) * -sintheta ) + ( m.get(5) * costheta ), 
                ( m.get(8) * -sintheta ) + ( m.get(9) * costheta ), 
                ( m.get(12) * -sintheta ) + ( m.get(13) * costheta ), 
                m.get(2), 
                m.get(6), 
                m.get(10), 
                m.get(14), 
                m.get(3), 
                m.get(7), 
                m.get(11), 
                m.get(15)
                );
        return result;
    }
    
    public static Matrix rotateZ(Matrix m, float theta) {
        return rotateZ(new Matrix(), m, theta);
    }
    
    public static Matrix makeZRotation(Matrix result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                costheta,  sintheta, 0, 0,
                -sintheta, costheta, 0, 0,
                0,         0,        1, 0,
                0,         0,        0, 1
                );
        return result;
    }
    
    public static Matrix makeZRotation(float theta) {
        return makeZRotation(new Matrix(), theta);
    }
    
    public static Matrix rotate(Matrix result, Matrix m, float theta, Axis axis) {
        switch(axis) {
            case X:
            case NEGATIVE_X:{
                return rotateX(result, m, theta);
            }
            case Y:
            case NEGATIVE_Y: {
                return rotateY(result, m, theta);
            }
            case Z:
            case NEGATIVE_Z: {
                return rotateZ(result, m, theta);
            }
        }
        return null;
    }
    
    public static Matrix rotate(Matrix m, float theta, Axis axis) {
        return rotate(new Matrix(), m, theta, axis);
    }
    
    public static Matrix makeRotation(Matrix result, float theta, Axis axis) {
        switch(axis) {
            case X:
            case NEGATIVE_X:{
                return makeXRotation(result, theta);
            }
            case Y:
            case NEGATIVE_Y: {
                return makeYRotation(result, theta);
            }
            case Z:
            case NEGATIVE_Z: {
                return makeZRotation(result, theta);
            }
        }
        return null;
    }
    
    public static Matrix makeRotation(float theta, Axis axis) {
        return makeRotation(new Matrix(), theta, axis);
    }
    
    public static Matrix scale(Matrix result, Matrix m, float xScale, float yScale, float zScale) {
        result.setData(
                ( m.get(0) * xScale ), 
                ( m.get(4) * xScale ),
                ( m.get(8) * xScale ), 
                ( m.get(12) * xScale ), 
                ( m.get(1) * yScale ), 
                ( m.get(5) * yScale ), 
                ( m.get(9) * yScale ), 
                ( m.get(13) * yScale ), 
                ( m.get(2) * zScale ), 
                ( m.get(6) * zScale ), 
                ( m.get(10) * zScale ), 
                ( m.get(14) * zScale ), 
                m.get(3), 
                m.get(7), 
                m.get(11), 
                m.get(15)
                );
        return result;
    }
    
    public static Matrix scale(Matrix m, float xScale, float yScale, float zScale) {
        return scale(new Matrix(), m, xScale, yScale, zScale);
    }
    
    public static Matrix makeScaling(Matrix result, float xScale, float yScale, float zScale) {
        result.setData(
                xScale, 0,      0,      0,
                0,      yScale, 0,      0,
                0,      0,      zScale, 0,
                0,      0,      0,      1
                );
        return result;
    }
    
    public static Matrix makeScaling(float xScale, float yScale, float zScale) {
        return makeScaling(new Matrix(), xScale, yScale, zScale);
    }
    
    public static Matrix makeScaling(Matrix result, Vector scale) {
        return makeScaling(result, scale.getX(), scale.getY(), scale.getZ());
    }
    
    public static Matrix makeScaling(Vector scale) {
        return makeScaling(new Matrix(), scale);
    }
    
    public static Matrix makeViewportTransform(Matrix result, float x, float y, float width, float height) {
        float f = width / 2;
        float g = height / 2;
        result.setData(
                f, 0, 0, f + x,
                0, g, 0, g + y,
                0, 0, 0, 0, 
                0, 0, 0, 1
                );
        return result;
    }
    
    public static Matrix makeViewportTransform(float x, float y, float width, float height) {
        return makeViewportTransform(new Matrix(), x, y, width, height);
    }
    
    public static Matrix getRotationMatrix(Matrix result, Matrix m) {
        result.setData(
                m.data[0], m.data[1], m.data[2],  0, 
                m.data[4], m.data[5], m.data[6],  0, 
                m.data[8], m.data[9], m.data[10], 0, 
                0,         0,         0,          1
                );
        return result;
    }
    
    public static Vector times(Vector result, Matrix m, Vector v) {
        return times(result, m.data, v);
    }
    
    public static Vector times(Vector result, float[] m, Vector v) {
        result.setX(( v.getX() * m[0] ) + ( v.getY() * m[4] ) + ( v.getZ() * m[8] ) + m[12]);
        result.setY(( v.getX() * m[1] ) + ( v.getY() * m[5] ) + ( v.getZ() * m[9] ) + m[13]);
        result.setZ(( v.getX() * m[2] ) + ( v.getY() * m[6] ) + ( v.getZ() * m[10] ) + m[14]);
        return result;
    }
    
    public static Vector timesRotation(Vector result, Matrix m, Vector v) {
        return timesRotation(result, m.data, v);
    }
    
    public static Vector timesRotation(Vector result, float[] m, Vector v) {
        result.setX(( v.getX() * m[0] ) + ( v.getY() * m[4] ) + ( v.getZ() * m[8] ));
        result.setY(( v.getX() * m[1] ) + ( v.getY() * m[5] ) + ( v.getZ() * m[9] ));
        result.setZ(( v.getX() * m[2] ) + ( v.getY() * m[6] ) + ( v.getZ() * m[10] ));
        return result;
    }
    
    public static Matrix times(Matrix result, Matrix m1, Matrix m2) {
    	times(result.data, m1.data, m2.data);
    	return result;
    }
    
    public static float[] times(float[] result, float[] m1, float[] m2) {
    	result[0] = ( ( m1[0] * m2[0] ) + ( m1[1] * m2[4] ) + ( m1[2] * m2[8] ) + ( m1[3] * m2[12] ) );
    	result[1] = ( ( m1[0] * m2[1] ) + ( m1[1] * m2[5] ) + ( m1[2] * m2[9] ) + ( m1[3] * m2[13] ) );
    	result[2] = ( ( m1[0] * m2[2] ) + ( m1[1] * m2[6] ) + ( m1[2] * m2[10] ) + ( m1[3] * m2[14] ) );
    	result[3] = ( ( m1[0] * m2[3] ) + ( m1[1] * m2[7] ) + ( m1[2] * m2[11] ) + ( m1[3] * m2[15] ) );
    	result[4] = ( ( m1[4] * m2[0] ) + ( m1[5] * m2[4] ) + ( m1[6] * m2[8] ) + ( m1[7] * m2[12] ) );
    	result[5] = ( ( m1[4] * m2[1] ) + ( m1[5] * m2[5] ) + ( m1[6] * m2[9] ) + ( m1[7] * m2[13] ) );
    	result[6] = ( ( m1[4] * m2[2] ) + ( m1[5] * m2[6] ) + ( m1[6] * m2[10] )  + ( m1[7] * m2[14] ) );
    	result[7] = ( ( m1[4] * m2[3] ) + ( m1[5] * m2[7] ) + ( m1[6] * m2[11] ) + ( m1[7] * m2[15] ) );
    	result[8] = ( ( m1[8] * m2[0] ) + ( m1[9] * m2[4] ) + ( m1[10] * m2[8] ) + ( m1[11] * m2[12] ) );
    	result[9] = ( ( m1[8] * m2[1] ) + ( m1[9] * m2[5] ) + ( m1[10] * m2[9] ) + ( m1[11] * m2[13] ) );
    	result[10] = ( ( m1[8] * m2[2] ) + ( m1[9] * m2[6] ) + ( m1[10] * m2[10] ) + ( m1[11] * m2[14] ) );
    	result[11] = ( ( m1[8] * m2[3] ) + ( m1[9] * m2[7] ) + ( m1[10] * m2[11] ) + ( m1[11] * m2[15] ) );
    	result[12] = ( ( m1[12] * m2[0] ) + ( m1[13] * m2[4] ) + ( m1[14] * m2[8] ) + ( m1[15] * m2[12] ) );
    	result[13] = ( ( m1[12] * m2[1] ) + ( m1[13] * m2[5] ) + ( m1[14] * m2[9] ) + ( m1[15] * m2[13] ) );
    	result[14] = ( ( m1[12] * m2[2] ) + ( m1[13] * m2[6] ) + ( m1[14] * m2[10] ) + ( m1[15] * m2[14] ) );
    	result[15] = ( ( m1[12] * m2[3] ) + ( m1[13] * m2[7] ) + ( m1[14] * m2[11] ) + ( m1[15] * m2[15] ) );
        return result;
    }
}