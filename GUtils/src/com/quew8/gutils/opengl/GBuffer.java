package com.quew8.gutils.opengl;

import com.quew8.gutils.ArrayUtils;
import com.quew8.gutils.BufferUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class GBuffer extends GObject {
    private final int target;
    
    public GBuffer(ByteBuffer data, int target, int usage) {
        super(genId());
        this.target = target;
        glBindBuffer(target, getId());
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
    }
    
    public void bind() {
        glBindBuffer(target, getId());
    }
    
    public void unbind() {
        glBindBuffer(target, 0);
    }
    
    @Override
    public void delete() {
        glDeleteBuffers(getIdBuffer());
    }
    
    public void setData(ByteBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    
    public void updateData(int offset, ByteBuffer data) {
        glBufferSubData(target, offset, data);
    }
    
    public int getTarget() {
        return target;
    }
    
    public static void unbind(int target) {
        glBindBuffer(target, 0);
    }
    
    /**
     * 
     */
    public static class GroupedGBuffer extends GBuffer {
        private final int elementsPerData;
        protected final byte bytesPerElement;
        
        public GroupedGBuffer(ByteBuffer data, int elementsPerData, byte bytesPerElement, int target, int usage) {
            super(data, target, usage);
            this.elementsPerData = elementsPerData;
            this.bytesPerElement = bytesPerElement;
        }
        
        public GroupedGBuffer(byte[][] data, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), data[0].length, (byte)1, target, usage);
        }
        
        public int getOffsetOf(int index) {
            return index * elementsPerData;
        }
        
        public int getByteOffsetOf(int index) {
            return getOffsetOf(index) * bytesPerElement;
        }
        
        public void updateDataFrom(int index, ByteBuffer data) {
            updateData(getByteOffsetOf(index), data);
        }
    }
    
    /**
     * 
     */
    public static class VariableLengthGroupedGBuffer extends GBuffer {
        private final int[] sumElementsToIndices;
        private final byte bytesPerElement;
        
        public VariableLengthGroupedGBuffer(ByteBuffer data, int[] elementsPerDatas, byte bytesPerElement, int target, int usage) {
            super(data, target, usage);
            this.sumElementsToIndices = ArrayUtils.arrayOfOffsetsFromLengths(elementsPerDatas);
            this.bytesPerElement = bytesPerElement;
        }
        
        public VariableLengthGroupedGBuffer(byte[][] data, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), ArrayUtils.arrayOfLengths(data), (byte) 1, target, usage);
        }
        
        public int getOffsetOf(int index) {
            return sumElementsToIndices[index];
        }
        
        public int getByteOffsetOf(int index) {
            return getOffsetOf(index) * bytesPerElement;
        }
        
        public void updateDataFrom(int index, ByteBuffer data) {
            updateData(getByteOffsetOf(index), data);
        }
    }
    
    /**
     * 
     */
    public static class InterleavedGBuffer extends GroupedGBuffer {
        private final int[] componentOffsets;
        
        public InterleavedGBuffer(ByteBuffer data, int[] componentDataSizes, byte bytesPerElement, int target, int usage) {
            super(data, ArrayUtils.sumArray(componentDataSizes), bytesPerElement, target, usage);
            this.componentOffsets = ArrayUtils.arrayOfOffsetsFromLengths(componentDataSizes);
        }
        
        public InterleavedGBuffer(byte[][] data, int[] componentDataSizes, int target, int usage) {
            super(data, target, usage);
            this.componentOffsets = ArrayUtils.arrayOfOffsetsFromLengths(componentDataSizes);
        }
        
        public int getOffsetOf(int index, int component) {
            return getOffsetOf(index) + componentOffsets[component];
        }
        
        public int getByteOffsetOf(int index, int component) {
            return getOffsetOf(index, component) * bytesPerElement;
        }
        
        public void updateDataFrom(int index, int component, ByteBuffer data) {
            updateData(getByteOffsetOf(index, component), data);
        }
    }
    
    /**
     * 
     * @param <E> 
     */
    public static class MappedGroupedGBuffer<E> extends GroupedGBuffer {
        private final ArrayList<E> mapping;
        
        public MappedGroupedGBuffer(ByteBuffer data, int dataSize, byte bytesPerElement, E[] mapping, int target, int usage) {
            super(data, dataSize, bytesPerElement, target, usage);
            this.mapping = new ArrayList<>(Arrays.asList(mapping));
        }
        
        public MappedGroupedGBuffer(byte[][] data, E[] mapping, int target, int usage) {
            this(BufferUtils.createByteBuffer(data), data[0].length, (byte) 1, mapping, target, usage);
        }
        
        public int getOffsetOfElement(E e) {
            return getOffsetOf(mapping.indexOf(e));
        }
        
        public void updateDataFrom(E e, ByteBuffer data) {
            updateData(getOffsetOfElement(e), data);
        }
    }
    
    private static int genId() {
        glGenBuffers(idBuff);
        return idBuff.get(0);
    }
}
