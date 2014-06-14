package com.quew8.geng3d.collada;

import com.quew8.gmath.Matrix;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class Node<T, S> extends NodeParent<T, S> {
    private final String name;
    private final String sid;
    private final Type type;
    private final Matrix transform;
    private NodeParent<T, S> parent = null;
    private final InstanceGeometry<T>[] geometry;
    private final InstanceController<S>[] controllers;
    
    @SuppressWarnings("unchecked")
    public Node(String name, String sid, Type type, Matrix transform, Node<T, S>[] leafs, 
            InstanceGeometry<T>[] geometry, InstanceController<S>[] controllers) {
        
        super(leafs);
        this.name = name;
        this.sid = sid;
        this.type = type;
        this.transform = transform;
        this.geometry = geometry != null ? geometry : Node.<InstanceGeometry<T>>getArray(0);
        this.controllers = controllers != null ? controllers : Node.<InstanceController<S>>getArray(0);
    }
    
    public Node<T, S> asAbsoluteNode() {
        return new Node<T, S>(name, sid, type, getAbsoluteTransform(), getLeafs(), geometry, controllers);
    }
    
    void setParent(NodeParent<T, S> parent) {
        this.parent = parent;
    }
    
    @Override
    protected boolean containsControllers() {
        if(controllers.length != 0) {
            return true;
        }
        return super.containsControllers();
    }
    
    @Override
    protected boolean containsGeometry() {
        if(controllers.length != 0) {
            return true;
        }
        return super.containsGeometry();
    }
    
    @Override
    protected void addControllers(ArrayList<InstanceController<S>> to) {
        to.addAll(Arrays.asList(controllers));
        super.addControllers(to);
    }
    
    @Override
    protected void addGeometry(ArrayList<InstanceGeometry<T>> to) {
        to.addAll(Arrays.asList(geometry));
        super.addGeometry(to);
    }
    
    @Override
    protected InstanceController<S> findController(String name) {
        for(InstanceController<S> c: controllers) {
            if(c.getName().matches(name)) {
                return c;
            } 
        }
        return super.findController(name);
    }
    
    @Override
    protected InstanceGeometry<T> findGeometry(String name) {
        for(InstanceGeometry<T> g: geometry) {
            if(g.getName().matches(name)) {
                return g;
            } 
        }
        return super.findGeometry(name);
    }
    
    public String getName() {
        return name;
    }
    
    public String getSID() {
        return sid;
    }
    
    @Override
    public Matrix getAbsoluteTransform() {
        if(parent != null && parent.isNode()) {
            return Matrix.times(new Matrix(), parent.getAbsoluteTransform(), getRelativeTransform());
        } else {
            return getRelativeTransform();
        }
    }
    
    public enum Type {
        NODE, JOINT;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(int length, T... array) {
        return Arrays.copyOf(array, length);
    }
}
