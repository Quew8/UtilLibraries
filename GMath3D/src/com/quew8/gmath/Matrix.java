package com.quew8.gmath;

/**
 *
 * @author Quew8
 */
public class Matrix {
    /**
     * Stores in Column-Major format, like OpenGL.
     */
    private final float[] data;

    /**
     * Initialise the data from a column-major array.
     * 
     * @param data The matrix data in column-major format.
     */
    private Matrix(float[] data) {
        this.data = data;
    }
    /**
     * Initialise the data from a row-major source.
     * @param f00 row 0, column 0.
     * @param f10 row 0, column 1.
     * @param f20 row 0, column 2.
     * @param f30 row 0, column 3.
     * @param f01 row 1, column 0.
     * @param f11 row 1, column 1.
     * @param f21 row 1, column 2.
     * @param f31 row 1, column 3.
     * @param f02 row 2, column 0.
     * @param f12 row 2, column 1.
     * @param f22 row 2, column 2.
     * @param f32 row 2, column 3.
     * @param f03 row 3, column 0.
     * @param f13 row 3, column 1.
     * @param f23 row 3, column 2.
     * @param f33 row 3, column 3.
     */
    public Matrix(float f00, float f10, float f20, float f30, 
            float f01, float f11, float f21, float f31, 
            float f02, float f12, float f22, float f32, 
            float f03, float f13, float f23, float f33) {
        
        this(new float[] {
            f00, f01, f02, f03,
            f10, f11, f12, f13,
            f20, f21, f22, f23,
            f30, f31, f32, f33
        });
    }
    
    /**
     * Initialise to the identity matrix.
     */
    public Matrix() {
        this(new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        });
    }
    
    /**
     * Set the data from a row-major source.
     * @param f00 row 0, column 0.
     * @param f10 row 0, column 1.
     * @param f20 row 0, column 2.
     * @param f30 row 0, column 3.
     * @param f01 row 1, column 0.
     * @param f11 row 1, column 1.
     * @param f21 row 1, column 2.
     * @param f31 row 1, column 3.
     * @param f02 row 2, column 0.
     * @param f12 row 2, column 1.
     * @param f22 row 2, column 2.
     * @param f32 row 2, column 3.
     * @param f03 row 3, column 0.
     * @param f13 row 3, column 1.
     * @param f23 row 3, column 2.
     * @param f33 row 3, column 3.
     */
    public void setData(float f00, float f10, float f20, float f30, 
            float f01, float f11, float f21, float f31, 
            float f02, float f12, float f22, float f32, 
            float f03, float f13, float f23, float f33) {
        
        data[0] = f00;
        data[1] = f01;
        data[2] = f02;
        data[3] = f03;
        data[4] = f10;
        data[5] = f11;
        data[6] = f12;
        data[7] = f13;
        data[8] = f20;
        data[9] = f21;
        data[10] = f22;
        data[11] = f23;
        data[12] = f30;
        data[13] = f31;
        data[14] = f32;
        data[15] = f33;
    }
    
    /**
     * Set the data from a row-major source.
     * @param src the row-major source array.
     * @param offset the offset into the source array.
     */
    public void setData(float[] src, int offset) {
        data[0] = src[offset];
        data[1] = src[offset + 4];
        data[2] = src[offset + 8];
        data[3] = src[offset + 12];
        data[4] = src[offset + 1];
        data[5] = src[offset + 5];
        data[6] = src[offset + 9];
        data[7] = src[offset + 13];
        data[8] = src[offset + 2];
        data[9] = src[offset + 6];
        data[10] = src[offset + 10];
        data[11] = src[offset + 14];
        data[12] = src[offset + 3];
        data[13] = src[offset + 7];
        data[14] = src[offset + 11];
        data[15] = src[offset + 15];
    }
    
    /**
     * Set the data from a column-major source.
     * @param src the column-major source.
     * @param offset the offset into the source array
     */
    public void setDataFromCM(float[] src, int offset) {
        System.arraycopy(src, offset, data, 0, 16);
    }
    
    /**
     * 
     * @return the matrix data in column-major format.
     */
    public float[] getData() {
        return data;
    }
    
    public void setIdentity() {
        data[0] = 1;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;
        data[4] = 0;
        data[5] = 1;
        data[6] = 0;
        data[7] = 0;
        data[8] = 0;
        data[9] = 0;
        data[10] = 1;
        data[11] = 0;
        data[12] = 0;
        data[13] = 0;
        data[14] = 0;
        data[15] = 1;
    }
    
    public Vector getTranslation(Vector out) {
        out.setXYZ(data[12], data[13], data[14]);
        return out;
    }
    
    public Vector getForwardDirection(Vector out) {
        out.setXYZ(data[2], data[6], data[10]);
        return out;
    }
    
    public Vector getForwardDirectionXZ(Vector out) {
        out.setXYZ(data[2], 0, data[10]);
        out.normalize(out);
        return out;
    }
    
    public Vector getUpDirection(Vector out) {
        out.setXYZ(data[1], data[5], data[9]);
        return out;
    }
    
    public Vector getRightDirection(Vector out) {
        out.setXYZ(data[0], data[4], data[8]);
        return out;
    }
    
    public Vector getRightDirectionXZ(Vector out) {
        out.setXYZ(data[0], 0, data[8]);
        out.normalize(out);
        return out;
    }
    
    @Override
    public String toString() {
        //l<row><column>
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
        int clnLnt = Math.max(
                Math.max(
                        Math.max(l00, Math.max(l10, Math.max(l20, l30))), 
                        Math.max(l01, Math.max(l11, Math.max(l21, l31)))
                ), 
                Math.max(l02, Math.max(l12, Math.max(l22, l32)))
        );
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
    
    public int getIndex(int column, int row) {
        return (column * 4) + row;
    }
    
    public static Matrix3 getMatrix3(Matrix3 result, Matrix m) {
        //Data in column-major, method expects row-major.
        result.setData(
                m.data[0], m.data[4], m.data[8],
                m.data[1], m.data[5], m.data[9],
                m.data[2], m.data[6], m.data[10]
        );
        return result;
    }
    
    public static Matrix makeFrustum(Matrix result, float left, float right,
            float bottom, float top, float near, float far) {
        
        float f = 2 * near,
                g = right - left,
                h = top - bottom,
                j = far - near;
        result.setData(
                f / g, 0,     (right+left) / g, 0,
                0,     f / h, (top+bottom) / h, 0,
                0,     0,     -(far+near) / j,  -(f * far) / j,
                0,     0,     -1,               0
                );
        return result;
    }
    
    public static Matrix makePerspective(Matrix result, float fovy, 
            float aspect, float near, float far) {
        
        float top = ( near * GMath.tan(fovy / 2) );
        float bottom = -top;
        float right = top * aspect;
        float left = -right;
        return makeFrustum(result, left, right, bottom, top, near, far);
    }
    
    public static Matrix makeOrtho(Matrix result, float left, float right, 
            float bottom, float top, float near, float far) {
        
        float f = right - left,
                g = top - bottom,
                h = far - near;
        result.setData(
                2 / f, 0,        0,      -(right + left) / f,
                0,     2 / g,    0,      -(top + bottom) / g,
                0,     0,        -2 / h, -(far + near) / h,
                0,     0,        0,      1
                );
        return result;
    }
    
    public static Matrix makeOrtho2D(Matrix result, float left, float right, 
            float bottom, float top) {
        
        float f = right - left,
                g = top - bottom;
        result.setData(
                2 / f, 0,     0,  -(right + left) / f,
                0,     2 / g, 0,  -(top + bottom) / g,
                0,     0,     -1, 0,
                0,     0,     0,  1
                );
        return result;
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
    
    public static Matrix makeAxisRotation(Matrix result, Vector axis, float theta) {
        float c = GMath.cos(theta);
        float s = GMath.sin(theta);
        float x = axis.getX(),
                y = axis.getY(),
                z = axis.getZ();
        float xs = x * s,
                ys = y * s,
                zs = z * s;
        float f = 1 - c;
        result.setData(
                (x * x * f) + c,  (x * y * f) - zs, (x * z * f) + ys, 0,
                (y * x * f) + zs, (y * y * f) + c,  (y * z * f) - xs, 0,
                (x * z * f) - ys, (y * z * f) + xs, (z * z * f) + c,  0,
                0,                0,                0,                1
        );
        return result;
    }
    
    public static Matrix makeScaling(Matrix result, Vector scale) {
        result.setData(
                scale.getX(), 0,            0,            0,
                0,            scale.getY(), 0,            0,
                0,            0,            scale.getZ(), 0,
                0,            0,            0,            1
        );
        return result;
    }
    
    public static Matrix makeLookAt(Matrix result, Vector pos, Vector at) {
        Vector forward = new Vector(pos, at);
        Vector left = GMath.cross(forward, new Vector(0, 1, 0));
        Vector up = GMath.cross(forward, left);
        forward.normalizeIfNot(forward);
        left.normalizeIfNot(left);
        up.normalizeIfNot(up);
        Matrix t = Matrix.makeTranslation(new Matrix(), pos);
        Matrix r = new Matrix(
                left.getX(),    left.getY(),    left.getZ(),    0,
                up.getX(),      up.getY(),      up.getZ(),      0,
                forward.getX(), forward.getY(), forward.getZ(), 0,
                0,              0,              0,              1
        );
        return Matrix.times(result, t, r);
    }
    
    public static Vector4 times(Vector4 result, Matrix m, Vector4 v) {
        result.setX((v.getX() * m.data[0]) + (v.getY() * m.data[4]) + (v.getZ() * m.data[8]) + (v.getW() * m.data[12]));
        result.setY((v.getX() * m.data[1]) + (v.getY() * m.data[5]) + (v.getZ() * m.data[9]) + (v.getW() * m.data[13]));
        result.setZ((v.getX() * m.data[2]) + (v.getY() * m.data[6]) + (v.getZ() * m.data[10]) + (v.getW() * m.data[14]));
        result.setW((v.getX() * m.data[3]) + (v.getY() * m.data[7]) + (v.getZ() * m.data[11]) + (v.getW() * m.data[15]));
        return result;
    }
    
    public static Vector times(Vector result, Matrix m, Vector v) {
        result.setX((v.getX() * m.data[0]) + (v.getY() * m.data[4]) + (v.getZ() * m.data[8]) + m.data[12]);
        result.setY((v.getX() * m.data[1]) + (v.getY() * m.data[5]) + (v.getZ() * m.data[9]) + m.data[13]);
        result.setZ((v.getX() * m.data[2]) + (v.getY() * m.data[6]) + (v.getZ() * m.data[10]) + m.data[14]);
        return result;
    }
    
    public static Matrix times(Matrix result, Matrix m1, Matrix m2) {
    	result.data[0] = ((m1.data[0] * m2.data[0]) + (m1.data[4] * m2.data[1]) + (m1.data[8] * m2.data[2]) + (m1.data[12] * m2.data[3]));
    	result.data[1] = ((m1.data[1] * m2.data[0]) + (m1.data[5] * m2.data[1]) + (m1.data[9] * m2.data[2]) + (m1.data[13] * m2.data[3]));
    	result.data[2] = ((m1.data[2] * m2.data[0]) + (m1.data[6] * m2.data[1]) + (m1.data[10] * m2.data[2]) + (m1.data[14] * m2.data[3]));
    	result.data[3] = ((m1.data[3] * m2.data[0]) + (m1.data[7] * m2.data[1]) + (m1.data[11] * m2.data[2]) + (m1.data[15] * m2.data[3]));
    	result.data[4] = ((m1.data[0] * m2.data[4]) + (m1.data[4] * m2.data[5]) + (m1.data[8] * m2.data[6]) + (m1.data[12] * m2.data[7]));
    	result.data[5] = ((m1.data[1] * m2.data[4]) + (m1.data[5] * m2.data[5]) + (m1.data[9] * m2.data[6]) + (m1.data[13] * m2.data[7]));
    	result.data[6] = ((m1.data[2] * m2.data[4]) + (m1.data[6] * m2.data[5]) + (m1.data[10] * m2.data[6])  + (m1.data[14] * m2.data[7]));
    	result.data[7] = ((m1.data[3] * m2.data[4]) + (m1.data[7] * m2.data[5]) + (m1.data[11] * m2.data[6]) + (m1.data[15] * m2.data[7]));
    	result.data[8] = ((m1.data[0] * m2.data[8]) + (m1.data[4] * m2.data[9]) + (m1.data[8] * m2.data[10]) + (m1.data[12] * m2.data[11]));
    	result.data[9] = ((m1.data[1] * m2.data[8]) + (m1.data[5] * m2.data[9]) + (m1.data[9] * m2.data[10]) + (m1.data[13] * m2.data[11]));
    	result.data[10] = ((m1.data[2] * m2.data[8]) + (m1.data[6] * m2.data[9]) + (m1.data[10] * m2.data[10]) + (m1.data[14] * m2.data[11]));
    	result.data[11] = ((m1.data[3] * m2.data[8]) + (m1.data[7] * m2.data[9]) + (m1.data[11] * m2.data[10]) + (m1.data[15] * m2.data[11]));
    	result.data[12] = ((m1.data[0] * m2.data[12]) + (m1.data[4] * m2.data[13]) + (m1.data[8] * m2.data[14]) + (m1.data[12] * m2.data[15]));
    	result.data[13] = ((m1.data[1] * m2.data[12]) + (m1.data[5] * m2.data[13]) + (m1.data[9] * m2.data[14]) + (m1.data[13] * m2.data[15]));
    	result.data[14] = ((m1.data[2] * m2.data[12]) + (m1.data[6] * m2.data[13]) + (m1.data[10] * m2.data[14]) + (m1.data[14] * m2.data[15]));
    	result.data[15] = ((m1.data[3] * m2.data[12]) + (m1.data[7] * m2.data[13]) + (m1.data[11] * m2.data[14]) + (m1.data[15] * m2.data[15]));
        return result;
    }
}
