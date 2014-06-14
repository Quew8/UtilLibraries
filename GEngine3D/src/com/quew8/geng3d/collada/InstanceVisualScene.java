package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class InstanceVisualScene<T, S> extends NodeParent<T, S> {
    private final String name;

    public InstanceVisualScene(String name, Node<T, S>[] nodes) {
        super(nodes, true);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
