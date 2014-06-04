package com.quew8.gutils.opengl;

import com.quew8.gutils.ArrayUtils;
import com.quew8.gutils.BufferUtils;
import java.nio.ByteBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class VertexBuffer extends GBuffer {
    
    public VertexBuffer(ByteBuffer data, int target, int usage) {
        super(data, target, usage);
        System.out.println("Creating VB length = " + data.limit() + " pos = " + data.position());
    }
    
    public VertexBuffer(float[] data, int target, int usage) {
        this(BufferUtils.createByteBuffer(data), target, usage);
    }
    
    public VertexBuffer(int[] data, int target, int usage) {
        this(BufferUtils.createByteBuffer(data), target, usage);
    }
    
    public VertexBuffer(float[] data, int target) {
        this(data, target, GL_STATIC_DRAW);
    }
    
    public VertexBuffer(int[] data, int target) {
        this(data, target, GL_STATIC_DRAW);
    }
    
    /**
     * 
     */
    public static class GroupedVertexBuffer extends GroupedGBuffer {
        
        public GroupedVertexBuffer(ByteBuffer data, int dataSize, byte bytesPerElement, int target, int usage) {
            super(data, dataSize, bytesPerElement, target, usage);
        }
        
        public GroupedVertexBuffer(byte[][] data, int target, int usage) {
            super(data, target, usage);
        }
        
        public GroupedVertexBuffer(float[][] data, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), data[0].length, (byte)4, target, usage);
        }
        
        public GroupedVertexBuffer(int[][] data, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), data[0].length, (byte)4, target, usage);
        }
        
        public GroupedVertexBuffer(float[][] data, int target) {
            this(data, target, GL_STATIC_DRAW);
        }
        
        public GroupedVertexBuffer(int[][] data, int target) {
            this(data, target, GL_STATIC_DRAW);
        }
    }
    
    /**
     * 
     */
    public static class VariableLengthGroupedVertexBuffer extends VariableLengthGroupedGBuffer {
        
        public VariableLengthGroupedVertexBuffer(ByteBuffer data, int[] dataSizes, byte bytesPerElement, int target, int usage) {
            super(data, dataSizes, bytesPerElement, target, usage);
        }
        
        public VariableLengthGroupedVertexBuffer(byte[][] data, int target, int usage) {
            super(data, target, usage);
        }
        
        public VariableLengthGroupedVertexBuffer(float[][] data, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), ArrayUtils.arrayOfLengths(data), (byte)4, target, usage);
        }
        
        public VariableLengthGroupedVertexBuffer(int[][] data, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), ArrayUtils.arrayOfLengths(data), (byte)4, target, usage);
        }
        
        public VariableLengthGroupedVertexBuffer(float[][] data, int target) {
            this(data, target, GL_STATIC_DRAW);
        }
        
        public VariableLengthGroupedVertexBuffer(int[][] data, int target) {
            this(data, target, GL_STATIC_DRAW);
        }
    }
    
    /**
     * 
     */
    public static class InterleavedVertexBuffer extends InterleavedGBuffer {
        
        public InterleavedVertexBuffer(ByteBuffer data, int[] componentDataSizes, byte bytesPerElement, int target, int usage) {
            super(data, componentDataSizes, bytesPerElement, target, usage);
        }
        
        public InterleavedVertexBuffer(float[] data, int[] componentDataSizes, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), componentDataSizes, (byte)4, target, usage);
        }
        
        public InterleavedVertexBuffer(int[] data, int[] componentDataSizes, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), componentDataSizes, (byte)4, target, usage);
        }
        
        public InterleavedVertexBuffer(float[] data, int[] componentDataSizes, int target) {
            this(data, componentDataSizes, target, GL_STATIC_DRAW);
        }
        
        public InterleavedVertexBuffer(int[] data, int[] componentDataSizes, int target) {
            this(data, componentDataSizes, target, GL_STATIC_DRAW);
        }
    }
}
