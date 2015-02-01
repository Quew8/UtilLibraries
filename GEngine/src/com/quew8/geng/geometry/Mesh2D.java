package com.quew8.geng.geometry;

/**
 *
 * @author Quew8
 */
public class Mesh2D extends GeometricObject<Mesh2D, Vertex2D> {

    public Mesh2D(Vertex2D[] vertices, int[] indices, int mode) {
        super(vertices, indices, mode);
    }

    @Override
    protected Mesh2D construct(Mesh2D old, Vertex2D[] vertices, int[] indices, int mode) {
        return new Mesh2D(vertices, indices, mode);
    }
    
}
