package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class InstanceVisualScene<T, S> extends AbstractNode<T, S> {
    private final String name;

    public InstanceVisualScene(String name, Node<T, S>[] nodes) {
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
    public AbstractNode<T, S>[] findAllNodes(String name) {
        if(name.startsWith("#")) {
            return new AbstractNode[]{getNode(name.substring(1))};
        }
        return super.findAllNodes(name);
    }
    
    public InstanceController<S> findController(String name) {
        AbstractNode<T, S>[] matched = findAllNodes(name);
        for(AbstractNode<T, S> node: matched) {
            if(node instanceof InstanceController) {
                return (InstanceController<S>) node;
            }
        }
        throw new RuntimeException("No controller found with name: " + name);
    }
    
    public InstanceGeometry<T> findGeometry(String name) {
        AbstractNode<T, S>[] matched = findAllNodes(name);
        for(AbstractNode<T, S> node: matched) {
            if(node instanceof InstanceGeometry) {
                return (InstanceGeometry<T>) node;
            }
        }
        throw new RuntimeException("No controller found with name: " + name);
    }
    
    public InstanceController<S> getController(String path) {
        return (InstanceController<S>) getNode(path);
    }
    
    public InstanceGeometry<T> getGeometry(String path) {
        return (InstanceGeometry<T>) getNode(path);
    }
}
