package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class InstanceController<T> {
    private final String name;
    private final T skin;
    
    public InstanceController(String name, T skin) {
        this.name = name;
        this.skin = skin;
    }
    
    public String getName() {
        return name;
    }
    
    public T getSkin() {
        return skin;
    }
}
