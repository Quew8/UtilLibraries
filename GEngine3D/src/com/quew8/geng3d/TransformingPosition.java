package com.quew8.geng3d;

import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import java.util.ArrayList;

/**
 * 
 * @author Quew8
 */
public class TransformingPosition extends Position {
    private final ArrayList<VectorSet> vectorSets = new ArrayList<VectorSet>();

    public TransformingPosition(Vector translation, Vector orientation) {
        super(translation, orientation);
    }
    
    public VectorSet newVectorSet(Vector... vertices) {
        VectorSet vs = new VectorSet(vertices);
        vectorSets.add(vs);
        return vs;
    }
    
    public VectorSet newNormalSet(Vector... vertices) {
        VectorSet vs = new NormalSet(vertices);
        vectorSets.add(vs);
        return vs;
    }
    
    @Override
    public void setNeedsNewModelMatrix() {
        super.setNeedsNewModelMatrix();
        for(int i = 0; i < vectorSets.size(); i++) {
            vectorSets.get(i).needsNewTransformedVertices = true;
        }
    }
    
    /**
     * 
     */
    public class VectorSet {
        private final Vector[] originalVertices;
        private final Vector[] transformedVertices;
        private boolean needsNewTransformedVertices = true;

        private VectorSet(Vector[] vertices) {
            this.originalVertices = vertices;
            this.transformedVertices = new Vector[vertices.length];
            for(int i = 0; i < transformedVertices.length; i++) {
                transformedVertices[i] = new Vector();
            }
        }
        
        public Vector[] getOriginalVertices() {
            return originalVertices;
        }
        
        public Vector[] getTransformedVertices() {
            createTransformedVertices();
            return transformedVertices;
        }

        private void createTransformedVertices() {
            if(needsNewTransformedVertices) {
                for(int i = 0; i < originalVertices.length; i++) {
                    transform(originalVertices[i], transformedVertices[i]);
                }
                needsNewTransformedVertices = false;
            }
        }
        
        public void transform(Vector in, Vector dest) {
            Matrix.times(dest, getModelMatrix(), in);
        }
        
    }
    
    /**
     * 
     */
    public class NormalSet extends VectorSet {
        
        public NormalSet(Vector[] vertices) {
            super(vertices);
        }
        
        @Override
        public void transform(Vector in, Vector dest) {
            Matrix.timesRotation(dest, getRotationMatrix(), in);
        }
    }
}
