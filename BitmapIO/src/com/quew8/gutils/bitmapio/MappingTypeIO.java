package com.quew8.gutils.bitmapio;

import java.nio.ByteBuffer;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public abstract class MappingTypeIO<T> {
    private final int size;
    
    public MappingTypeIO(int size) {
        this.size = size;
    }
    
    /**
     * 
     * @return 
     */
    public int getSizeOfType() {
        return size;
    }
    
    /**
     * 
     * @param from
     * @return 
     */
    public abstract T readFrom(ByteBuffer from);
    
    /**
     * 
     * @param t
     * @param to 
     */
    public abstract void writeTo(T t, ByteBuffer to);
}
