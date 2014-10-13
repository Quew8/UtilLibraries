package com.quew8.geng3d.collada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class AbstractNode<T, S> {
    private final AbstractNode<T, S>[] children;
    private AbstractNode<T, S> parent = null;
    
    AbstractNode(AbstractNode<T, S>[] children) {
        this.children = removeEmpties(children);
    }
    
    AbstractNode() {
        this(new AbstractNode[]{});
    }
    
    public AbstractNode<T, S>[] getChildren() {
        return children;
    }
    
    public Node<T, S>[] getAllChildNodes() {
        ArrayList<Node<T, S>> elements = new ArrayList<Node<T, S>>();
        for(AbstractNode<T, S> node: children) {
            if(node instanceof Node) {
                elements.add((Node<T, S>)node);
            }
        }
        return elements.toArray(new Node[elements.size()]);
    }
    
    public InstanceGeometry<T>[] getAllGeometry() {
        ArrayList<InstanceGeometry<T>> elements = new ArrayList<InstanceGeometry<T>>();
        for(AbstractNode<T, S> node: children) {
            if(node instanceof InstanceGeometry) {
                elements.add((InstanceGeometry<T>)node);
            }
        }
        return elements.toArray(new InstanceGeometry[elements.size()]);
    }
    
    public InstanceController<S>[] getAllControllers() {
        ArrayList<InstanceController<S>> elements = new ArrayList<InstanceController<S>>();
        for(AbstractNode<T, S> node: children) {
            if(node instanceof InstanceController) {
                elements.add((InstanceController<S>)node);
            }
        }
        return elements.toArray(new InstanceController[elements.size()]);
    }
    
    public String getName() {
        return null;
    }
    
    public void setParent(AbstractNode<T, S> parent) {
        if(this.parent != null) {
            throw new RuntimeException("Cannot set parent multiple times");
        }
        this.parent = parent;
    }
    
    public AbstractNode<T, S> getParent() {
        return parent;
    }
    
    public int getNLeaves() {
        int n = children.length;
        for(AbstractNode<T, S> node: children) {
            if(node.isLeaf()) {
                n++;
            } else {
                n += node.getNLeaves();
            }
        }
        return n;
    }
    
    public boolean hasLeaves() {
        for(AbstractNode<T, S> node: children) {
            if(node.isLeaf() || node.hasLeaves()) {
                return true;
            }
        }
        return false;
    }
    
    public AbstractNode<T, S>[] findAllNodes(String name) {
        ArrayList<AbstractNode<T, S>> list = new ArrayList<AbstractNode<T, S>>();
        findAllNodes(name, list);
        return list.toArray(new AbstractNode[list.size()]);
    }
    
    public void findAllNodes(String name, ArrayList<AbstractNode<T, S>> list) {
        for(AbstractNode<T, S> node: children) {
            if(node.getName().equals(name)) {
                list.add(node);
            }
            node.findAllNodes(name, list);
        }
    }
    
    public AbstractNode<T, S> findNode(String name) {
        for(AbstractNode<T, S> node: children) {
            if(node.getName().equals(name)) {
                return node;
            } else {
                AbstractNode<T, S> node2 = node.findNode(name);
                if(node2 != null) {
                    return node2;
                }
            }
        }
        return null;
    }
    
    public AbstractNode<T, S> getNode(String path) {
        int i = path.indexOf('/');
        if(i == -1) {
            System.out.println("Getting: " + path);
            return getChildNode(path);
        } else {
            String thisPath = path.substring(0, i);
            String restPath = path.substring(i + 1);
            System.out.println("Split Path: " + thisPath + " " + restPath);
            return getChildNode(thisPath).getNode(restPath);
        }
    }
    
    public AbstractNode<T, S> getChildNode(String name) {
        if(name.matches("\\[([\\d]+)\\]")) {
            int index = Integer.parseInt(name.substring(1, name.length() - 1));
            if(index >= children.length) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }
            return children[index];
        } else {
            for(AbstractNode<T, S> node: children) {
                if(node.getName().equals(name)) {
                    return node;
                }
            }
            throw new RuntimeException("No child of name " + name);
        }
    }
    
    public String getString(String prefix) {
        String s = prefix + "Node {\n" + getDesc(prefix + "    ");
        for(AbstractNode<T, S> node : children) {
            s += node.getString(prefix + "    ") + "\n";
        }
        s += prefix + "}";
        return s;
    }
    
    public abstract String getDesc(String prefix);
    public abstract boolean isLeaf();
    
    private static <T, S> AbstractNode<T, S>[] removeEmpties(AbstractNode<T, S>[] arrays) {
        ArrayList<AbstractNode<T, S>> al = new ArrayList<AbstractNode<T, S>>();
        al.addAll(Arrays.asList(arrays));
        Iterator<AbstractNode<T, S>> iter = al.iterator();
        while(iter.hasNext()) {
            AbstractNode<T, S> n = iter.next();
            if(!(n.isLeaf() || n.hasLeaves())) {
                iter.remove();
            }
        }
        return al.toArray(Arrays.copyOf(arrays, al.size()));
    }
}
