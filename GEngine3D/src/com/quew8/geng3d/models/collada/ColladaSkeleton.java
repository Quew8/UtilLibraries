package com.quew8.geng3d.models.collada;

import com.quew8.gmath.Matrix;

/**
 *
 * @author Quew8
 */
public class ColladaSkeleton {
    private final Matrix bindShapeMatrix;
    private final ColladaJoint[] children;
    
    public ColladaSkeleton(Matrix bindShapeMatrix, ColladaJoint[] children) {
        this.bindShapeMatrix = bindShapeMatrix;
        this.children = children;
        int index = -1;
        for(ColladaJoint cj: children) {
            cj.indexJoints(++index);
        }
    }
    
    public ColladaJoint findJoint(String sid) {
        for (ColladaJoint child : children) {
            if (child.getSID().matches(sid)) {
                return child;
            } else {
                ColladaJoint j = child.findJoint(sid);
                if(j != null) {
                    return j;
                }
            }
        }
        throw new IllegalArgumentException("No Such Joint");
    }
    
    public int getNJoints() {
        int n = children.length;
        for(ColladaJoint children1 : children) {
            n += children1.getNChildren();
        }
        return n;
    }
    
    public ColladaJoint[] getChildren() {
        return children;
    }
    
    public Matrix getBindShapeMatrix() {
        return bindShapeMatrix;
    }
    
    @Override
    public String toString() {
        String prefix = "Skeleton:";
        for(ColladaJoint children1 : children) {
            prefix += "\n    " + children1.toString();
        }
        return prefix;
    }
}
