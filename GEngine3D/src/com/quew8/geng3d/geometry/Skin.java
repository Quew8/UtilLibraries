package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.GeometricObject;

/**
 *
 * @author Quew8
 */
public class Skin extends GeometricObject<Skin, WeightedVertex> {
    private final Skeleton skeleton;
    
    public Skin(WeightedVertex[] vertices, int[] indices, int mode, Skeleton skeleton) {
        super(vertices, indices, mode);
        this.skeleton = skeleton;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    @Override
    protected Skin construct(Skin old, WeightedVertex[] vertices, int[] indices, int mode) {
        return new Skin(vertices, indices, mode, old.skeleton);
    }
}
