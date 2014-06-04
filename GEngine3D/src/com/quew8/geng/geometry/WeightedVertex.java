package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gutils.ArrayUtils;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class WeightedVertex extends AbstractVertex<WeightedVertex> {
    public static final int WEIGHTED_VERTEX_BYTE_SIZE = 52;
    
    public WeightedVertex(float[] data, float[] weights) {
        super(ArrayUtils.concatArrays(new float[][]{data, weights}, VERTEX_SIZE + weights.length));
    }
    
    public WeightedVertex(Vertex vertex, float[] weights) {
        this(vertex.getData(), weights);
    }
    
    public WeightedVertex(Vector pos, Vector normal, float tx, float ty, float[] weights) {
        this(new float[]{
            pos.getX(), pos.getY(), pos.getZ(), 
            normal.getX(), normal.getY(), normal.getZ(),
            tx, ty
        }, weights);
    }
    
    public WeightedVertex(float[] pos, float[] normal, float[] texCoords, float[] weights) {
        super(ArrayUtils.concatArrays(new float[][]{pos, normal, texCoords, weights}, VERTEX_SIZE + weights.length));
    }
    
    public WeightedVertex(float x, float y, float z, float nx, float ny, float nz, float tx, float ty, float[] weights) {
        this(new float[]{x, y, z, nx, ny, nz, tx, ty}, weights);
    }
    
    public int getNWeights() {
        return data.length - VERTEX_SIZE;
    }
    
    public float getWeight(int i) {
        return data[VERTEX_SIZE + i];
    }
    
    public void setWeight(int i, float weight) {
        data[VERTEX_SIZE + i] = weight;
    }
    
    @Override
    public WeightedVertex transform(Matrix transform, Matrix normalMatrix) {
        float[] newWeights = new float[getNWeights()];
        System.arraycopy(data, VERTEX_SIZE, newWeights, 0, newWeights.length);
        return new WeightedVertex(transformData(transform, normalMatrix), newWeights);
    }
    
    @Override
    public void appendData(ByteBuffer bb) {
        for(int i = 0; i < VERTEX_SIZE; i++) {
            bb.putFloat(data[i]);
        }
        int nWeights = getNWeights();
        int nUsedWeights = 0;
        int[] jointIndices = new int[4];
        float[] usedWeights = new float[4];
        for(int i = 0; i < nWeights && nUsedWeights < 4; i++) {
            float w = getWeight(i);
            if(w != 0) {
                jointIndices[nUsedWeights] = i;
                usedWeights[nUsedWeights++] = w;
            }
        }
        for(int i = 0; i < 4; i++) {
            bb.put((byte) jointIndices[i]);
        }
        for(int i = 0; i < 4; i++) {
            bb.putFloat(usedWeights[i]);
        }
    }
    
}
