package com.quew8.gutils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * @author Quew8
 */
public class BufferUtils {
    
    private BufferUtils() {
        
    }
    
    public static ByteBuffer getSlice(ByteBuffer bb, int offset, int length) {
        int oldpos = bb.position();
        int oldlim = bb.limit();
        bb.position(oldpos + offset);
        bb.limit(oldpos + offset + length);
        ByteBuffer bb2 = bb.slice();
        bb.limit(oldlim);
        bb.position(oldpos);
        return bb2;
    }
    
    public static ByteBuffer createByteBuffer(int size) {
        ByteBuffer bb = ByteBuffer.allocateDirect(size);
        bb.order(ByteOrder.nativeOrder());
        return bb;
    }
    
    public static ByteBuffer createByteBuffer(byte[] data) {
        ByteBuffer bb = createByteBuffer(data.length);
        bb.put(data);
        bb.rewind();
        return bb;
    }
    
    public static ByteBuffer createByteBuffer(float[] data) {
        ByteBuffer bb = createByteBuffer(data.length * 4);
        bb.asFloatBuffer().put(data);
        bb.rewind();
        return bb;
    }
    
    public static ByteBuffer createByteBuffer(int[] data) {
        ByteBuffer bb = createByteBuffer(data.length * 4);
        bb.asIntBuffer().put(data);
        bb.rewind();
        return bb;
    }
    
    public static ByteBuffer createByteBuffer(byte[][] data) {
        int length = ArrayUtils.sumArray(ArrayUtils.arrayOfLengths(data));
        ByteBuffer bb = createByteBuffer(length);
        for(int i = 0, pos = 0; i < data.length; i++) {
            bb.position(pos);
            bb.put(data[i]);
            pos += data[i].length;
        }
        bb.rewind();
        return bb;
    }
    
    public static ByteBuffer createByteBuffer(float[][] data) {
        int length = ArrayUtils.sumArray(ArrayUtils.arrayOfLengths(data));
        ByteBuffer bb = createByteBuffer(length * 4);
        for(int i = 0, pos = 0; i < data.length; i++) {
            bb.position(pos);
            bb.asFloatBuffer().put(data[i]);
            pos += (data[i].length * 4);
        }
        bb.rewind();
        return bb;
    }
    
    public static ByteBuffer createByteBuffer(int[][] data) {
        int length = ArrayUtils.sumArray(ArrayUtils.arrayOfLengths(data));
        ByteBuffer bb = createByteBuffer(length * 4);
        for(int i = 0, pos = 0; i < data.length; i++) {
            bb.position(pos);
            bb.asIntBuffer().put(data[i]);
            pos += (data[i].length * 4);
        }
        bb.rewind();
        return bb;
    }
    
    public static IntBuffer createIntBuffer(int length) {
        return createByteBuffer(length * 4).asIntBuffer();
    }
    
    public static IntBuffer createIntBuffer(int[] data) {
        IntBuffer ib = createIntBuffer(data.length);
        ib.put(data);
        ib.rewind();
        return ib;
    }
    
    public static FloatBuffer createFloatBuffer(int length) {
        return createByteBuffer(length * 4).asFloatBuffer();
    }
    
    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer fb = createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();
        return fb;
    }
    
    public static String toString(FloatBuffer fb) {
        return toString(fb, 10);
    }

    public static String toString(FloatBuffer fb, int maxViewableLength) {
        String s = "FB{pos = " + fb.position() + ", lim = " + fb.limit() + ", cap = " + fb.capacity() + "}[";
        if(maxViewableLength > 0) {
            if(fb.limit() > 0) {
                s += fb.get(0);
                for (int i = 1; i < fb.limit(); i++) {
                    if(i >= maxViewableLength) {
                        s += "...";
                        break;
                    } else {
                        s += ", " + fb.get(i);
                    }
                }
            }
        } else {
            s += "...";
        }
        s += "]";
        return s;
    }

    public static String toString(IntBuffer ib) {
        return toString(ib, false, 20);
    }
    
    public static String toString(IntBuffer ib, boolean b, int maxViewableLength) {
        String s = "IB{pos = " + ib.position() + ", lim = " + ib.limit() + ", cap = " + ib.capacity() + "}[";
        if(maxViewableLength > 0) {
            if(ib.limit() > 0) {
                s += ib.get(0);
                for (int i = 1; i < ib.limit(); i++) {
                    if(i >= maxViewableLength) {
                        s += "...";
                        break;
                    } else {
                        s += ", " + ib.get(i);
                    }
                }
            }
        } else {
            s += "...";
        }
        s += "]";
        return s;
    }
    
    public static String toString(ByteBuffer bb) {
        return toString(bb, 20);
    }

    public static String toString(ByteBuffer bb, int maxViewableLength) {
        String s = "BB{pos = " + bb.position() + ", lim = " + bb.limit() + ", cap = " + bb.capacity() + "}[";
        if(maxViewableLength > 0) {
            if(bb.limit() > 0) {
                s += bb.get(0);
                for (int i = 1; i < bb.limit(); i++) {
                    if(i >= maxViewableLength) {
                        s += "...";
                        break;
                    } else {
                        s += ", " + bb.get(i);
                    }
                }
            }
        } else {
            s += "...";
        }
        s += "]";
        return s;
    }
}
