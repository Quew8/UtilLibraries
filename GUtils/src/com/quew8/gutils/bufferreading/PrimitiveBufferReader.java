package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PrimitiveBufferReader extends BufferReader {
    public static final PrimitiveBufferReader 
            BYTE = new PrimitiveBufferReader(PrimitiveType.BYTE),
            CHAR = new PrimitiveBufferReader(PrimitiveType.CHAR),
            SHORT = new PrimitiveBufferReader(PrimitiveType.SHORT),
            INT = new PrimitiveBufferReader(PrimitiveType.INT),
            FLOAT = new PrimitiveBufferReader(PrimitiveType.FLOAT),
            LONG = new PrimitiveBufferReader(PrimitiveType.LONG),
            DOUBLE = new PrimitiveBufferReader(PrimitiveType.DOUBLE);
    
    private final PrimitiveType primitive;
    
    private PrimitiveBufferReader(PrimitiveType primitive) {
        this.primitive = primitive;
    }

    @Override
    public String read(ByteBuffer bb) {
        switch(primitive) {
            case BYTE: return Byte.toString(bb.get());
            case CHAR: return Character.toString(bb.getChar());
            case SHORT: return Short.toString(bb.getShort());
            case INT: return Integer.toString(bb.getInt());
            case FLOAT: return Float.toString(bb.getFloat());
            case LONG: return Long.toString(bb.getLong());
            case DOUBLE: return Double.toString(bb.getDouble());
            default: throw new RuntimeException("IMPOSSIBLE?");
        }
    }

    @Override
    public int getRequiredBytes() {
        return primitive.getSize();
    }
    
}
