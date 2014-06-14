package com.quew8.geng.rendering.modes;

import com.quew8.gmath.Vector;
import java.nio.ByteBuffer;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public interface GeometricDataInterpreter<T> {
    
    public Vector[] toPositions(T[] t); 
    
    public ByteBuffer toVertexData(T[] t);
    
    public int[][] toIndexData(T[] t);
}
