package com.quew8.geng3d.collada;

import com.quew8.gmath.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Quew8
 */
class NodeParent<T, S> {
    private final Node<T, S>[] leafs;
    
    public NodeParent(Node<T, S>[] leafs, boolean removeEmpties) {
        this.leafs = removeEmpties ? removeEmpties(leafs) : leafs;
        for(Node<T, S> child: leafs) {
            child.setParent(this);
        }
    }
    
    public NodeParent(Node<T, S>[] leafs) {
        this(leafs, false);
    }
    
    public Node<T, S>[] getLeafs() {
        return leafs;
    }
    
    public int getNLeafs() {
        int n = leafs.length;
        for(Node<T, S> l: leafs) {
            n += l.getNLeafs();
        }
        return n;
    }
    
    protected Node<T, S> findNode(String name) {
        for(Node<T, S> n: leafs) {
            if(n.getName().matches(name)) {
                return n;
            } else {
                Node<T, S> n2 = n.findNode(name);
                if(n2 != null) {
                    return n2;
                }
            }
        }
        return null;
    }
    
    protected boolean containsControllers() {
        for(Node<T, S> n: leafs) {
            if(n.containsControllers()) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean containsGeometry() {
        for(Node<T, S> n: leafs) {
            if(n.containsControllers()) {
                return true;
            }
        }
        return false;
    }
    
    protected void addControllers(ArrayList<InstanceController<S>> to) {
        for(Node<T, S> n: leafs) {
            n.addControllers(to);
        }
    }
    
    protected void addGeometry(ArrayList<InstanceGeometry<T>> to) {
        for(Node<T, S> n: leafs) {
            n.addGeometry(to);
        }
    }
    
    protected InstanceController<S> findController(String name) {
        for(Node<T, S> n: leafs) {
            InstanceController<S> c = n.findController(name);
            if(c != null) {
                return c;
            }
        }
        return null;
    }
    
    protected InstanceGeometry<T> findGeometry(String name) {
        for(Node<T, S> n: leafs) {
            InstanceGeometry<T> g = n.findGeometry(name);
            if(g != null) {
                return g;
            }
        }
        return null;
    }
    
    public Matrix getAbsoluteTransform() {
        return getRelativeTransform();
    }
    
    protected Matrix getRelativeTransform() {
        return new Matrix();
    }
    
    public boolean isNode() {
        return false;
    }
    
    private static <T, S> Node<T, S>[] removeEmpties(Node<T, S>[] arrays) {
        ArrayList<Node<T, S>> al = new ArrayList<Node<T, S>>();
        al.addAll(Arrays.asList(arrays));
        Iterator<Node<T, S>> iter = al.iterator();
        while(iter.hasNext()) {
            Node<T, S> n = iter.next();
            if(!(n.containsControllers() || n.containsGeometry())) {
                iter.remove();
            }
        }
        return al.toArray(Arrays.copyOf(arrays, al.size()));
    }
}
