package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class ByteBufferReader extends BufferReader {
    public static ByteBufferReader INSTANCE = new ByteBufferReader();

    private ByteBufferReader() {
    }

    @Override
    public String read(ByteBuffer bb) {
        return Byte.toString(bb.get());
    }

    @Override
    public int getRequiredBytes() {
        return 1;
    }
    
}
