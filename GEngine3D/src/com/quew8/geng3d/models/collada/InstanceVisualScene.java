package com.quew8.geng3d.models.collada;

/**
 *
 * @author Quew8
 */
public class InstanceVisualScene extends AbstractNode {
    private final String name;

    public InstanceVisualScene(String name, Node[] nodes) {
        super(nodes);
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc(String prefix) {
        return prefix + "InstanceVisualScene\n" +
                prefix + "Name: " + name + "\n";
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
    
    @Override
    public AbstractNode[] findAllNodes(String name) {
        if(name.startsWith("#")) {
            return new AbstractNode[]{getNode(name.substring(1))};
        }
        return super.findAllNodes(name);
    }
    
    public InstanceController findController(String name) {
        AbstractNode[] matched = findAllNodes(name);
        for(AbstractNode node: matched) {
            if(node instanceof InstanceController) {
                return (InstanceController) node;
            }
        }
        throw new RuntimeException("No controller found with name: " + name);
    }
    
    public InstanceGeometry findGeometry(String name) {
        AbstractNode[] matched = findAllNodes(name);
        for(AbstractNode node: matched) {
            if(node instanceof InstanceGeometry) {
                return (InstanceGeometry) node;
            }
        }
        throw new RuntimeException("No controller found with name: " + name);
    }
    
    public InstanceController getController(String path) {
        return (InstanceController) getNode(path);
    }
    
    public InstanceGeometry getGeometry(String path) {
        return (InstanceGeometry) getNode(path);
    }
}
