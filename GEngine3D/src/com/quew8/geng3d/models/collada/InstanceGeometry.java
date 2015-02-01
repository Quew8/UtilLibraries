package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class InstanceGeometry<T> extends AbstractNode<T, Void> {
    private final String name;
    private final T geometry;
    
    public InstanceGeometry(String name, T geometry) {
        this.name = name;
        this.geometry = geometry;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public T getGeometry() {
        return geometry;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    
    @Override
    public String getDesc(String prefix) {
        return prefix + "InstanceGeometry\n" +
                prefix + "Name: " + name + "\n";
    }
}
