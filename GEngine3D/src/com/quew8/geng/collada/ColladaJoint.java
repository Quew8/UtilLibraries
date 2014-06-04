package com.quew8.geng.collada;

import com.quew8.gmath.Matrix;

/**
 *
 * @author Quew8
 */
public class ColladaJoint {
    private final String sid;
    private int index = -1;
    private final Matrix jointMatrix;
    private final Matrix invBindMatrix;
    private ColladaJoint parent = null;
    private final ColladaJoint[] children;
    
    public ColladaJoint(String sid, Matrix jointMatrix, Matrix invBindMatrix, ColladaJoint[] children) {
        this.sid = sid;
        this.jointMatrix = jointMatrix;
        this.invBindMatrix = invBindMatrix;
        this.children = children;
        for(ColladaJoint joint: children) {
            joint.setParent(this);
        }
    }
    
    private void setParent(ColladaJoint parent) {
        this.parent = parent;
    }
    
    public String getSID() {
        return sid;
    }
    
    public int getIndex() {
        return index;
    }
    
    public Matrix getInvBindMatrix() {
        return invBindMatrix;
    }
    
    public Matrix getJointMatrix() {
        return jointMatrix;
    }
    
    public ColladaJoint getParent() {
        return parent;
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
        return null;
    }
    
    int indexJoints(int index) {
        this.index = index;
        for(ColladaJoint children1 : children) {
            index = children1.indexJoints(++index);
        }
        return index;
    }
    
    public int getNChildren() {
        int n = children.length;
        for(ColladaJoint children1 : children) {
            n += children1.getNChildren();
        }
        return n;
    }
    
    public ColladaJoint[] getChildren() {
        return children;
    }
    
    private int getDepth() {
        return parent == null ? 0 : parent.getDepth() + 1;
    }
    
    @Override
    public String toString() {
        String prefix = "";
        int depth = getDepth();
        for(int i = 0; i < depth; i++) {
            prefix += "     ";
        }
        prefix += "Joint " + index + ":";
        for(ColladaJoint children1 : children) {
            prefix += "\n" + children1.toString();
        }
        return prefix;
    }
}
