package com.quew8.geng.geometry;

import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;

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
    
    public static Mesh2D createPolygon(Vertex2D[] vertices) {
        return new Mesh2D(vertices, createPolygonIndices(vertices.length), GL_TRIANGLES);
    }
    
    private static int[] createPolygonIndices(int nVertices) {
        int[] indices = new int[(nVertices-2)*3];
        for(int i = 1, pos = 0; i < nVertices-1;) {
            indices[pos++] = 0;
            indices[pos++] = i;
            indices[pos++] = ++i;
        }
        return indices;
    }
    
}
