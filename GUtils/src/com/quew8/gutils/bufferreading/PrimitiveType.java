package com.quew8.gutils.bufferreading;

/**
 *
 * @author Quew8
 */
public enum PrimitiveType {
    BYTE(1), CHAR(2), SHORT(2), INT(4), FLOAT(4), LONG(8), DOUBLE(8);
    
    private PrimitiveType(int size) {
        this.size = size;
    }
    
    private final int size;

    public int getSize() {
        return size;
    }
}
