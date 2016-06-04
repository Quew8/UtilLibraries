package com.quew8.gutils.bufferreading;

/**
 *
 * @author Quew8
 */
public abstract class NoReadBufferReader extends BufferReader {
    
    @Override
    protected final int getRequiredBytes() {
        return 0;
    }
    
}
