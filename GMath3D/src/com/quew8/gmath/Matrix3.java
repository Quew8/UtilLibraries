package com.quew8.gmath;

/**
 *
 * @author Quew8
 */
public class Matrix3 {
    /**
     * Stores in Column-Major format, like OpenGL.
     */
    private final float[] data;

    /**
     * Initialise the data from a column-major array.
     * 
     * @param data The matrix data in column-major format.
     */
    private Matrix3(float[] data) {
        this.data = data;
    }
    /**
     * Initialise the data from a row-major source.
     * @param f00 row 0, column 0.
     * @param f10 row 0, column 1.
     * @param f20 row 0, column 2.
     * @param f01 row 1, column 0.
     * @param f11 row 1, column 1.
     * @param f21 row 1, column 2.
     * @param f02 row 2, column 0.
     * @param f12 row 2, column 1.
     * @param f22 row 2, column 2.
     */
    public Matrix3(float f00, float f10, float f20, 
            float f01, float f11, float f21, 
            float f02, float f12, float f22) {
        
        this(new float[] {
            f00, f01, f02,
            f10, f11, f12,
            f20, f21, f22
        });
    }
    
    /**
     * Initialise to the identity matrix.
     */
    public Matrix3() {
        this(new float[]{
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
        });
    }
    
    /**
     * Set the data from a row-major source.
     * @param f00 row 0, column 0.
     * @param f10 row 0, column 1.
     * @param f20 row 0, column 2.
     * @param f01 row 1, column 0.
     * @param f11 row 1, column 1.
     * @param f21 row 1, column 2.
     * @param f02 row 2, column 0.
     * @param f12 row 2, column 1.
     * @param f22 row 2, column 2.
     */
    public void setData(float f00, float f10, float f20, 
            float f01, float f11, float f21, 
            float f02, float f12, float f22) {
        
        data[0] = f00;
        data[1] = f01;
        data[2] = f02;
        data[3] = f10;
        data[4] = f11;
        data[5] = f12;
        data[6] = f20;
        data[7] = f21;
        data[8] = f22;
    }
    
    /**
     * Set the data from a row-major source.
     * @param src the row-major source array.
     * @param offset the offset into the source array.
     */
    public void setData(float[] src, int offset) {
        data[0] = src[offset];
        data[1] = src[offset + 3];
        data[2] = src[offset + 6];
        data[3] = src[offset + 1];
        data[4] = src[offset + 4];
        data[5] = src[offset + 7];
        data[6] = src[offset + 2];
        data[7] = src[offset + 5];
        data[8] = src[offset + 8];
    }
    
    /**
     * Set the data from a column-major source.
     * @param src the column-major source.
     * @param offset the offset into the source array
     */
    public void setDataFromCM(float[] src, int offset) {
        System.arraycopy(src, offset, data, 0, 9);
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
        data[4] = 1;
        data[5] = 0;
        data[6] = 0;
        data[7] = 0;
        data[8] = 1;
    }
    
    public Vector getForwardDirection(Vector out) {
        out.setXYZ(data[2], data[5], data[8]);
        return out;
    }
    
    public Vector getForwardDirectionXZ(Vector out) {
        out.setXYZ(data[2], 0, data[8]);
        out.normalize(out);
        return out;
    }
    
    public Vector getUpDirection(Vector out) {
        out.setXYZ(data[1], data[4], data[7]);
        return out;
    }
    
    public Vector getRightDirection(Vector out) {
        out.setXYZ(data[0], data[3], data[6]);
        return out;
    }
    
    public Vector getRightDirectionXZ(Vector out) {
        out.setXYZ(data[0], 0, data[6]);
        out.normalize(out);
        return out;
    }
    
    @Override
    public String toString() {
        //l<row><column>
        int l00 = String.valueOf(data[0]).length();
        int l10 = String.valueOf(data[1]).length();
        int l20 = String.valueOf(data[2]).length();
        int l01 = String.valueOf(data[3]).length();
        int l11 = String.valueOf(data[4]).length();
        int l21 = String.valueOf(data[5]).length();
        int clnLnt = Math.max(
                Math.max(l00, Math.max(l10, l20)),
                Math.max(l01, Math.max(l11, l21))
        );
        return "Matrix:\n"
                + data[0] + nBlanc(clnLnt - l00 + 2) + 
                    data[3] + nBlanc(clnLnt - l01 + 2) + 
                    data[6] + "\n"
                + data[1] + nBlanc(clnLnt - l10 + 2) + 
                    data[4] + nBlanc(clnLnt - l11 + 2) + 
                    data[7] + "\n"
                + data[2] + nBlanc(clnLnt - l20 + 2) + 
                    data[5] + nBlanc(clnLnt - l21 + 2) + 
                    data[8];
    }
    
    private static String nBlanc(int n) {
        String s = " ";
        for(int i = 1; i < n; i++) {
            s += " ";
        }
        return s;
    }
    
    public int getIndex(int column, int row) {
        return (column * 3) + row;
    }
    
    public static Matrix3 makeXRotation(Matrix3 result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                1, 0,         0,
                0, costheta,  sintheta,
                0, -sintheta, costheta
                );
        return result;
    }
    
    public static Matrix3 makeYRotation(Matrix3 result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                costheta, 0, -sintheta,
                0,        1, 0,
                sintheta, 0, costheta
                );
        return result;
    }
    
    public static Matrix3 makeZRotation(Matrix3 result, float theta) {
        float costheta = GMath.cos(theta);
        float sintheta = GMath.sin(theta);
        result.setData(
                costheta,  sintheta, 0,
                -sintheta, costheta, 0,
                0,         0,        1
                );
        return result;
    }
    
    public static Vector times(Vector result, Matrix3 m, Vector v) {
        result.setX((v.getX() * m.data[0]) + (v.getY() * m.data[3]) + (v.getZ() * m.data[6]));
        result.setY((v.getX() * m.data[1]) + (v.getY() * m.data[4]) + (v.getZ() * m.data[7]));
        result.setZ((v.getX() * m.data[2]) + (v.getY() * m.data[5]) + (v.getZ() * m.data[8]));
        return result;
    }
    
    public static Matrix3 times(Matrix3 result, Matrix3 m1, Matrix3 m2) {
    	result.data[0] = ((m1.data[0] * m2.data[0]) + (m1.data[3] * m2.data[1]) + (m1.data[6] * m2.data[2]));
    	result.data[1] = ((m1.data[1] * m2.data[0]) + (m1.data[4] * m2.data[1]) + (m1.data[7] * m2.data[2]));
    	result.data[2] = ((m1.data[2] * m2.data[0]) + (m1.data[5] * m2.data[1]) + (m1.data[8] * m2.data[2]));
    	result.data[3] = ((m1.data[0] * m2.data[3]) + (m1.data[3] * m2.data[4]) + (m1.data[6] * m2.data[5]));
    	result.data[4] = ((m1.data[1] * m2.data[3]) + (m1.data[4] * m2.data[4]) + (m1.data[7] * m2.data[5]));
    	result.data[5] = ((m1.data[2] * m2.data[3]) + (m1.data[5] * m2.data[4]) + (m1.data[8] * m2.data[5]));
    	result.data[6] = ((m1.data[0] * m2.data[6]) + (m1.data[3] * m2.data[7]) + (m1.data[6] * m2.data[8]));
    	result.data[7] = ((m1.data[1] * m2.data[6]) + (m1.data[4] * m2.data[7]) + (m1.data[7] * m2.data[8]));
    	result.data[8] = ((m1.data[2] * m2.data[6]) + (m1.data[5] * m2.data[7]) + (m1.data[8] * m2.data[8]));
        return result;
    }
}
