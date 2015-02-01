package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class InstanceController<T> extends AbstractNode<Void, T> {
    private final String name;
    private final T skin;
    
    public InstanceController(String name, T skin) {
        this.name = name;
        this.skin = skin;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public T getSkin() {
        return skin;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    
    @Override
    public String getDesc(String prefix) {
        return prefix + "InstanceController\n" +
                prefix + "Name: " + name + "\n";
    }
}
