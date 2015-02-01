package com.quew8.geng3d.models.collada;

import com.quew8.gmath.Matrix;
import com.quew8.gutils.ArrayUtils;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class Node extends AbstractNode {
    private final String name;
    private final String sid;
    private final Type type;
    private final Matrix transform;
    
    public Node(String name, String sid, Type type, Matrix transform, AbstractNode[] leaves) {
        super(leaves);
        this.name = name;
        this.sid = sid;
        this.type = type;
        this.transform = transform;
    }
    
    public Node(String name, String sid, Type type, Matrix transform, AbstractNode[] leaves, 
            InstanceGeometry[] geometry, InstanceController[] controllers) {
        
        this(
                name, 
                sid, 
                type, 
                transform, 
                ArrayUtils.concatVariableLengthArrays(
                        new AbstractNode[][]{
                            geometry != null ? geometry : new InstanceGeometry[0], 
                            controllers != null ? controllers : new InstanceController[0]
                        }
                )
        );
    }
    
    public Node asAbsoluteNode() {
        return new Node(name, sid, type, getAbsoluteTransform(), getChildren());
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public String getSID() {
        return sid;
    }
    
    @Override
    public boolean isLeaf() {
        return (type == Type.JOINT) && (getChildren().length == 0);
    }
    
    public Matrix getRelativeTransform() {
        return transform;
    }
    
    public Matrix getAbsoluteTransform() {
        if(getParent() != null && getParent() instanceof Node) {
            return Matrix.times(new Matrix(), ((Node)getParent()).getAbsoluteTransform(), getRelativeTransform());
        } else {
            return getRelativeTransform();
        }
    }
    
    @Override
    public String getDesc(String prefix) {
        String s = 
                prefix + "Name: " + name + "\n" +
                prefix + "sid: " + sid + "\n" +
                prefix + "type: " + type + "\n";
        return s;
    }
    
    public enum Type {
        NODE, JOINT;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(int length, T... array) {
        return Arrays.copyOf(array, length);
    }
}
