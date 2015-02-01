package com.quew8.geng3d.collada;

import com.quew8.gmath.Matrix;
import com.quew8.gutils.ArrayUtils;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class Node<T, S> extends AbstractNode<T, S> {
    private final String name;
    private final String sid;
    private final Type type;
    private final Matrix transform;
    
    public Node(String name, String sid, Type type, Matrix transform, AbstractNode<T, S>[] leaves) {
        super(leaves);
        this.name = name;
        this.sid = sid;
        this.type = type;
        this.transform = transform;
    }
    
    public Node(String name, String sid, Type type, Matrix transform, AbstractNode<T, S>[] leaves, 
            InstanceGeometry<T>[] geometry, InstanceController<S>[] controllers) {
        
        this(
                name, 
                sid, 
                type, 
                transform, 
                ArrayUtils.concatVariableLengthArrays(
                        new AbstractNode[][]{
                            geometry != null ? geometry : Node.<InstanceGeometry<T>>getArray(0), 
                            controllers != null ? controllers : Node.<InstanceController<S>>getArray(0)
                        }
                )
        );
    }
    
    public Node<T, S> asAbsoluteNode() {
        return new Node<T, S>(name, sid, type, getAbsoluteTransform(), getChildren());
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
