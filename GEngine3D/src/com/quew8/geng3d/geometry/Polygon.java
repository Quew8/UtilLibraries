package com.quew8.geng3d.geometry;

import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class Polygon {
    public final Vertex3D[] vertices;

    public Polygon(Vertex3D[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return Arrays.toString(vertices);
    }
    
}
