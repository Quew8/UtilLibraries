package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.IVertex;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Param;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Matrix3;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Quew8
 */
public class WeightedVertex implements IVertex<WeightedVertex> {
    private final Vertex3D vertex;
    private final float[] weights;

    public WeightedVertex(Vertex3D vertex, float[] weights) {
        this.vertex = vertex;
        this.weights = weights;
    }
    
    public int getNWeights() {
        return weights.length;
    }
    
    public float getWeight(int i) {
        return weights[i];
    }
    
    public void setWeight(int i, float weight) {
        weights[i] = weight;
    }
    
    @Override
    public WeightedVertex transform(Matrix transform, Matrix3 normalMatrix) {
        return new WeightedVertex(
                vertex.transform(transform, normalMatrix),
                Arrays.copyOf(weights, weights.length)
        );
    }

    @Override
    public void addData(ByteBuffer to, Param param) {
        switch(param) {
            case WEIGHT_0: to.putFloat(getWeight(0)); return;
            case WEIGHT_1: to.putFloat(getWeight(1)); return; 
            case WEIGHT_2: to.putFloat(getWeight(2)); return; 
            case WEIGHT_3: to.putFloat(getWeight(3)); return; 
            case WEIGHT_4: to.putFloat(getWeight(4)); return; 
            case WEIGHT_5: to.putFloat(getWeight(5)); return; 
            case WEIGHT_6: to.putFloat(getWeight(6)); return; 
            default: vertex.addData(to, param);
        }
    }

    @Override
    public WeightedVertex transformTextureCoords(Image img) {
        return new WeightedVertex(
                vertex.transformTextureCoords(img),
                Arrays.copyOf(weights, weights.length)
        );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.vertex);
        hash = 61 * hash + Arrays.hashCode(this.weights);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final WeightedVertex other = (WeightedVertex) obj;
        if(!Objects.equals(this.vertex, other.vertex)) {
            return false;
        }
        return Arrays.equals(this.weights, other.weights);
    }
    
}
