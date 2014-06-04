package com.quew8.gutils.bitmapio;

/**
 * 
 * @author Quew8
 * @param <E> 
 */
public class MappedBitmapStruct<E> extends AbstractBitmapStruct<byte[][]> {
    public E[] mapping;
    
    public MappedBitmapStruct(byte[][] data, E[] mapping, int width, int height) {
        super(data, width, height);
        this.mapping = mapping;
    }
    
}
