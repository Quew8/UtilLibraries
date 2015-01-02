package com.quew8.gutils.opengl;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Quew8
 * 
 */
public class NonInterleavedVertexArray implements VertexData {
    private final VertexArrayWithOffset[] arrays;

    private NonInterleavedVertexArray(VertexArrayWithOffset... arrays) {
        Arrays.sort(arrays, new Comparator<VertexArrayWithOffset>() {

            @Override
            public int compare(VertexArrayWithOffset o1, VertexArrayWithOffset o2) {
                return o1.offset - o2.offset;
            }
            
        });
        for(int i = 0; i < arrays.length; i++) {
            System.out.println(i + " :: " + arrays[i].vertexArray.getClass().getSimpleName() + " " + arrays[i].offset);
        }
        this.arrays = arrays;
    }
    
    @Override
    public void vertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        for(int i = 1; i < arrays.length; i++) {
            if(offset >= arrays[i-1].offset && offset < arrays[i].offset) {
                arrays[i-1].vertexArray.vertexAttribPointer(index, size, type, normalized, arrays[i-1].stride, offset - arrays[i-1].offset);
                return;
            }
        }
        arrays[arrays.length-1].vertexArray.vertexAttribPointer(index, size, type, normalized, arrays[arrays.length-1].stride, offset - arrays[arrays.length-1].offset);
    }

    @Override
    public void bind() {
        
    }

    @Override
    public void delete() {
        
    }
    
    public static class VertexArrayWithOffset {
        private final VertexArray vertexArray;
        private final int stride;
        private final int offset;

        public VertexArrayWithOffset(VertexArray vertexArray, int stride, int offset) {
            this.vertexArray = vertexArray;
            this.stride = stride;
            this.offset = offset;
        }
    }
    
    public static VertexData interleaveVertexArrays(VertexArrayWithOffset... arrays) {
        if(arrays.length == 1) {
            return arrays[0].vertexArray;
        } else {
            return new NonInterleavedVertexArray(arrays);
        }
    }
}
