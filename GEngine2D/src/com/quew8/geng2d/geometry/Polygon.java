package com.quew8.geng2d.geometry;

import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class Polygon {
    public final Vertex2D[] vertices;

    public Polygon(Vertex2D[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return Arrays.toString(vertices);
    }
    
}
