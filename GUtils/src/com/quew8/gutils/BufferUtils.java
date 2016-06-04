package com.quew8.gutils;

import com.quew8.gutils.bufferreading.BufferReader;
import com.quew8.gutils.bufferreading.NoReadBufferReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * @author Quew8
 */
public class BufferUtils {
    public static final BufferReader 
            BB_PREAMBLE = BufferReader.parse("'BB{pos = '?bpos', lim = '?blim', cap = '?bcap'}'"),
            IB_PREAMBLE = BufferReader.parse("'IB{pos = '?ipos', lim = '?ilim', cap = '?icap'}'"),
            FB_PREAMBLE = BufferReader.parse("'FB{pos = '?fpos', lim = '?flim', cap = '?fcap'}'"),
            ELLIPSE_IF_REMAINING = new NoReadBufferReader() {
                @Override
                protected String read(ByteBuffer bb) {
                    return bb.remaining() > 0 ? "..." : "";
                }   
            };
    
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
    
    public static FloatBuffer createFloatBuffer(int length) {
        return createByteBuffer(length * 4).asFloatBuffer();
    }
    
    public static String toString(FloatBuffer fb, int maxViewableLength) {
        return BufferReader.readWith(fb, BufferReader.parse(
                "?0'['{F}[{,}-1;" + maxViewableLength + "]?1']'", 
                FB_PREAMBLE,
                ELLIPSE_IF_REMAINING
        ));
    }
    
    public static String toString(IntBuffer ib, int maxViewableLength) {
        return BufferReader.readWith(ib, BufferReader.parse(
                "?0'['{I}[{,}-1;" + maxViewableLength + "]?1']'", 
                IB_PREAMBLE,
                ELLIPSE_IF_REMAINING
        ));
    }
    
    public static String toString(ByteBuffer bb, int maxViewableLength) {
        return BufferReader.readWith(bb, BufferReader.parse(
                "?0'['{B}[{,}-1;" + maxViewableLength + "]?1']'", 
                BB_PREAMBLE,
                ELLIPSE_IF_REMAINING
        ));
    }
    
    public static String readWithPreamble(FloatBuffer fb, BufferReader with) {
        return BufferReader.readWith(fb, BufferReader.parse(
                "?0?1", FB_PREAMBLE, with
        ));
    }
    
    public static String readWithPreamble(IntBuffer ib, BufferReader with) {
        return BufferReader.readWith(ib, BufferReader.parse(
                "?0?1", IB_PREAMBLE, with
        ));
    }
    
    public static String readWithPreamble(ByteBuffer bb, BufferReader with) {
        return BufferReader.readWith(bb, BufferReader.parse(
                "?0?1", BB_PREAMBLE, with
        ));
    }
}
