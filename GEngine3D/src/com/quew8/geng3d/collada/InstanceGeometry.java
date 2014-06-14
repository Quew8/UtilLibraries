package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class InstanceGeometry<T> {
    private final String name;
    private final T geometry;
    
    public InstanceGeometry(String name, T geometry) {
        this.name = name;
        this.geometry = geometry;
    }
    
    public String getName() {
        return name;
    }
    
    public T getGeometry() {
        return geometry;
    }
}
