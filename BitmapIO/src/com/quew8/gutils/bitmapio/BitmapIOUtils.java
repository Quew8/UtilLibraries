package com.quew8.gutils.bitmapio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public abstract class BitmapIOUtils {
    
    public static final MappingTypeIO<Character> CHAR_IO = new MappingTypeIO<Character>(2) {
        
        @Override
        public Character readFrom(ByteBuffer from) {
            return from.getChar();
        }
        
        @Override
        public void writeTo(Character t, ByteBuffer to) {
            to.putChar(t);
        }
    };
    public static final MappingTypeIO<Integer> INT_IO = new MappingTypeIO<Integer>(4) {
        
        @Override
        public Integer readFrom(ByteBuffer from) {
            return from.getInt();
        }
        
        @Override
        public void writeTo(Integer t, ByteBuffer to) {
            to.putInt(t);
        }
    };
    
    private BitmapIOUtils() {
        
    }
    
    /**
     * 
     * @param width
     * @param height
     * @return 
     */
    private static int getSizeOfBitmap(int width, int height) {
        return (int) (( Math.ceil((double)width / 8.0) ) * height);
    }
    
    /**
     * 
     * @param output
     * @param data
     * @param width
     * @param height
     * @throws IOException 
     */
    public static void writeBitmap(OutputStream output, byte[] data, 
            int width, int height) throws IOException {
        
        ByteBuffer bb = ByteBuffer.allocate(data.length + 8);
        bb.putInt(width);
        bb.putInt(height);
        bb.put(data);
        output.write(bb.array(), bb.arrayOffset(), bb.position());
    }
    
    /**
     * 
     * @param output
     * @param bitmap
     * @throws IOException 
     */
    public static void writeBitmap(OutputStream output, BitmapStruct bitmap) throws IOException {
        writeBitmap(output, bitmap.data, bitmap.width, bitmap.height);
    }
    
    /**
     * 
     * @param input
     * @return
     * @throws IOException 
     */
    public static BitmapStruct readBitmap(InputStream input) throws IOException {
        ByteBuffer dimsBB = ByteBuffer.allocate(8);
        input.read(dimsBB.array(), dimsBB.arrayOffset(), 8);
        int width = dimsBB.getInt();
        int height = dimsBB.getInt();
        int size = getSizeOfBitmap(width, height);
        byte[] data = new byte[size];
        input.read(data, 0, size);
        return new BitmapStruct(data, width, height);
    }
    
    /**
     * 
     * @param output
     * @param data
     * @param width
     * @param height
     * @throws IOException 
     */
    public static void writeGroupedBitmap(OutputStream output, byte[][] data, 
            int width, int height) throws IOException {
        
        ByteBuffer bb = ByteBuffer.allocate(12);
        bb.putInt(width);
        bb.putInt(height);
        bb.putInt(data.length);
        output.write(bb.array(), bb.arrayOffset(), 12);
        for(int i = 0; i < data.length; i++) {
            output.write(data[i], 0, data[i].length);
        }
    }
    
    /**
     * 
     * @param output
     * @param struct
     * @throws IOException 
     */
    public static void writeGroupedBitmap(OutputStream output, GroupedBitmapStruct struct) throws IOException {
        writeGroupedBitmap(output, struct.data, struct.width, struct.height);
    }
    
    /**
     * 
     * @param input
     * @return
     * @throws IOException 
     */
    public static GroupedBitmapStruct readGroupedBitmap(InputStream input) throws IOException {
        ByteBuffer dimsBB = ByteBuffer.allocate(12);
        input.read(dimsBB.array(), dimsBB.arrayOffset(), 12);
        int width = dimsBB.getInt();
        int height = dimsBB.getInt();
        int n = dimsBB.getInt();
        int size = getSizeOfBitmap(width, height);
        byte[][] data = new byte[n][size];
        for(int i = 0; i < n; i++) {
            input.read(data[i], 0, size);
        }
        return new GroupedBitmapStruct(data, width, height);
    }
    
    /**
     * 
     * @param <E>
     * @param output
     * @param data
     * @param mapping
     * @param width
     * @param height
     * @param typeIO
     * @throws IOException 
     */
    public static <E> void writeMappedBitmap(OutputStream output, byte[][] data, 
            E[] mapping, int width, int height, MappingTypeIO<E> typeIO) throws IOException {
        
        ByteBuffer bb = ByteBuffer.allocate(12);
        bb.putInt(width);
        bb.putInt(height);
        bb.putInt(data.length);
        output.write(bb.array(), bb.arrayOffset(), 12);
        
        int size = getSizeOfBitmap(width, height);
        bb = ByteBuffer.allocate(size + typeIO.getSizeOfType());
        for(int i = 0; i < data.length; i++) {
            typeIO.writeTo(mapping[i], bb);
            bb.put(data[i]);
            output.write(bb.array(), bb.arrayOffset(), bb.position());
            bb.flip();
        }
    }
    
    public static <E> void writeMappedBitmap(OutputStream output, 
            MappedBitmapStruct<E> struct, MappingTypeIO<E> typeIO) throws IOException {
        
        writeMappedBitmap(output, struct.data, struct.mapping, struct.width, struct.height, typeIO);
    }
    
    /**
     * 
     * @param <E>
     * @param input
     * @param typeIO
     * @param mappingArray
     * @return
     * @throws IOException 
     */
    public static <E> MappedBitmapStruct<E> readMappedBitmap(InputStream input, 
            MappingTypeIO<E> typeIO, E[] mappingArray) throws IOException {
        
        ByteBuffer bb = ByteBuffer.allocate(12);
        input.read(bb.array(), bb.arrayOffset(), 12);
        
        int width = bb.getInt();
        int height = bb.getInt();
        int n = bb.getInt();
        int size = getSizeOfBitmap(width, height);
        
        byte[][] data = new byte[n][size];
        if(mappingArray.length < n) {
            mappingArray = Arrays.copyOf(mappingArray, n);
        }
        bb = ByteBuffer.allocate(size + typeIO.getSizeOfType());
        
        for(int i = 0; i < n; i++) {
            input.read(bb.array(), bb.arrayOffset(), bb.capacity());
            mappingArray[i] = typeIO.readFrom(bb);
            bb.get(data[i]);
            bb.flip();
        }
        
        return new MappedBitmapStruct<E>(data, mappingArray, width, height);
    }
}
