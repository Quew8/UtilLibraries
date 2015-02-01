package com.quew8.geng3d.collada;

import com.quew8.gmath.Matrix;

/**
 *
 * @author Quew8
 */
public interface DataInput {
    public int[] getVCount();
    public void putData(SemanticSet source, float[] dest, int offset, int initialIndex, int n);
    public void putData(SemanticSet source, String[] dest, int offset, int initialIndex, int n);
    public void putData(SemanticSet source, Matrix[] dest, int offset, int initialIndex, int n);
}
