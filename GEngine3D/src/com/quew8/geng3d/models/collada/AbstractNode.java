package com.quew8.geng3d.models.collada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Quew8
 */
public abstract class AbstractNode {
    private final AbstractNode[] children;
    private AbstractNode parent = null;
    
    AbstractNode(AbstractNode[] children) {
        this.children = removeEmpties(children);
    }
    
    AbstractNode() {
        this(new AbstractNode[]{});
    }
    
    public AbstractNode[] getChildren() {
        return children;
    }
    
    public Node[] getAllChildNodes() {
        ArrayList<Node> elements = new ArrayList<Node>();
        for(AbstractNode node: children) {
            if(node instanceof Node) {
                elements.add((Node)node);
            }
        }
        return elements.toArray(new Node[elements.size()]);
    }
    
    public InstanceGeometry[] getAllGeometry() {
        ArrayList<InstanceGeometry> elements = new ArrayList<InstanceGeometry>();
        for(AbstractNode node: children) {
            if(node instanceof InstanceGeometry) {
                elements.add((InstanceGeometry)node);
            }
        }
        return elements.toArray(new InstanceGeometry[elements.size()]);
    }
    
    public InstanceController[] getAllControllers() {
        ArrayList<InstanceController> elements = new ArrayList<InstanceController>();
        for(AbstractNode node: children) {
            if(node instanceof InstanceController) {
                elements.add((InstanceController)node);
            }
        }
        return elements.toArray(new InstanceController[elements.size()]);
    }
    
    public String getName() {
        return null;
    }
    
    public void setParent(AbstractNode parent) {
        if(this.parent != null) {
            throw new IllegalStateException("Cannot set parent multiple times");
        }
        this.parent = parent;
    }
    
    public AbstractNode getParent() {
        return parent;
    }
    
    public int getNLeaves() {
        int n = children.length;
        for(AbstractNode node: children) {
            if(node.isLeaf()) {
                n++;
            } else {
                n += node.getNLeaves();
            }
        }
        return n;
    }
    
    public boolean hasLeaves() {
        for(AbstractNode node: children) {
            if(node.isLeaf() || node.hasLeaves()) {
                return true;
            }
        }
        return false;
    }
    
    public AbstractNode[] findAllNodes(String name) {
        ArrayList<AbstractNode> list = new ArrayList<AbstractNode>();
        findAllNodes(name, list);
        return list.toArray(new AbstractNode[list.size()]);
    }
    
    public void findAllNodes(String name, ArrayList<AbstractNode> list) {
        for(AbstractNode node: children) {
            if(node.getName().equals(name)) {
                list.add(node);
            }
            node.findAllNodes(name, list);
        }
    }
    
    public AbstractNode findNode(String name) {
        for(AbstractNode node: children) {
            if(node.getName().equals(name)) {
                return node;
            } else {
                AbstractNode node2 = node.findNode(name);
                if(node2 != null) {
                    return node2;
                }
            }
        }
        return null;
    }
    
    public AbstractNode getNode(String path) {
        int i = path.indexOf('/');
        if(i == -1) {
            //System.out.println("Getting: " + path);
            return getChildNode(path);
        } else {
            String thisPath = path.substring(0, i);
            String restPath = path.substring(i + 1);
            //System.out.println("Split Path: " + thisPath + " " + restPath);
            return getChildNode(thisPath).getNode(restPath);
        }
    }
    
    public AbstractNode getChildNode(String name) {
        if(name.matches("\\[([\\d]+)\\]")) {
            int index = Integer.parseInt(name.substring(1, name.length() - 1));
            if(index >= children.length) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }
            return children[index];
        } else {
            for(AbstractNode node: children) {
                if(node.getName().equals(name)) {
                    return node;
                }
            }
            throw new RuntimeException("No child of name " + name);
        }
    }
    
    public String getString(String prefix) {
        String s = prefix + "Node {\n" + getDesc(prefix + "    ");
        for(AbstractNode node : children) {
            s += node.getString(prefix + "    ") + "\n";
        }
        s += prefix + "}";
        return s;
    }
    
    public abstract String getDesc(String prefix);
    public abstract boolean isLeaf();
    
    private static AbstractNode[] removeEmpties(AbstractNode[] arrays) {
        ArrayList<AbstractNode> al = new ArrayList<AbstractNode>();
        al.addAll(Arrays.asList(arrays));
        Iterator<AbstractNode> iter = al.iterator();
        while(iter.hasNext()) {
            AbstractNode n = iter.next();
            if(!(n.isLeaf() || n.hasLeaves())) {
                iter.remove();
            }
        }
        return al.toArray(Arrays.copyOf(arrays, al.size()));
    }
}
