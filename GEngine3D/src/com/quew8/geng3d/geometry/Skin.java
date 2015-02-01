package com.quew8.geng.geometry;

/**
 *
 * @author Quew8
 */
public class Skin extends GeometricObject<Skin, WeightedVertex> {
    private final Skeleton skeleton;
    
    public Skin(WeightedVertex[] vertices, int[] indices, Skeleton skeleton, Image texture) {
        super(vertices, indices, texture);
        this.skeleton = skeleton;
    }
    
    private Skin(WeightedVertex[] vertices, int[] indices, Skeleton skeleton) {
        super(vertices, indices);
        this.skeleton = skeleton;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }
    
    @Override
    protected Skin construct(Skin old, WeightedVertex[] vertices, int[] indices) {
        return new Skin(vertices, indices, old.skeleton);
    }
}
