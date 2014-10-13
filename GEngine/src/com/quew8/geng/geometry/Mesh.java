package com.quew8.geng.geometry;

import com.quew8.gmath.Axis;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 */
public class Mesh extends GeometricObject<Mesh, Vertex> {
    
    public Mesh(Vertex[] vertices, int[] indices, Image texture) {
        super(vertices, indices, texture);
    }
    
    private Mesh(Vertex[] vertices, int[] indices) {
        super(vertices, indices);
    }

    public Mesh(Vertex[] vertices, Image texture) {
        this(vertices, createPolygonIndices(vertices.length), texture);
    }

    @Override
    protected Mesh construct(Mesh old, Vertex[] vertices, int[] indices) {
        return new Mesh(vertices, indices);
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
    
    public static Mesh[] createCuboid(float cx, float cy, float cz, 
            float sx, float sy, float sz, 
            Image front, Image back, 
            Image top, Image bottom, 
            Image left, Image right) {
        
        float hsx = sx / 2, hsy = sy / 2, hsz = sz / 2;
        return new Mesh[] {
            createQuad(cx + hsx, cy, cz, sz, sy, Axis.X, left),
            createQuad(cx - hsx, cy, cz, sz, sy, Axis.NEGATIVE_X, right),
            createQuad(cx, cy + hsy, cz, sx, sz, Axis.Y, top),
            createQuad(cx, cy - hsy, cz, sx, sz, Axis.NEGATIVE_Y, bottom),
            createQuad(cx, cy, cz + hsz, sx, sy, Axis.Z, front),
            createQuad(cx, cy, cz - hsz, sx, sy, Axis.NEGATIVE_Z, front),
        };
    }
    
    public static Mesh[] createCuboid(float cx, float cy, float cz, float sx, float sy, float sz, Image texture) {
        return createCuboid(cx, cy, cz, sx, sy, sz, texture, texture, texture, texture, texture, texture);
    }
    
    public static Mesh[] createCube(float cx, float cy, float cz, float size, Image texture) {
        return createCuboid(cx, cy, cz, size, size, size, texture);
    }
    
    public static Mesh createPolygon(Vertex[] vertices, Image texture) {
        return new Mesh(vertices, texture);
    }

    public static Mesh createQuad(VectorMapper mapper, float s1, float s2, Image texture) {
        float hs1 = s1 / 2;
        float hs2 = s2 / 2;
        Vector normal = mapper.getMatrix().getFowardDirection();
        return new Mesh(
                new Vertex[]{
                    new Vertex(mapper.map(-hs1, -hs2, 0), normal, 0, 0),
                    new Vertex(mapper.map(hs1, -hs2, 0), normal, 1, 0),
                    new Vertex(mapper.map(hs1, hs2, 0), normal, 1, 1),
                    new Vertex(mapper.map(-hs1, hs2, 0), normal, 0, 1),
                },
                new int[]{
                    0, 1, 2,
                    0, 2, 3
                },
                texture
                );
    }
    
    public static Mesh createQuad(float cx, float cy, float cz, float s1, float s2, Matrix matrix, Image texture) {
        return createQuad(new VectorMapper(matrix, new Vector(cx, cy, cz)), s1, s2, texture);
    }
    
    public static Mesh createQuad(float cx, float cy, float cz, float s1, float s2, Axis axis, Image texture) {
        return createQuad(new VectorMapper(axis, new Vector(cx, cy, cz)), s1, s2, texture);
    }

    public static Mesh createCircle(VectorMapper mapper, float r, int lod, boolean normalsOut, Image texture) {
        float[][] data = GMath.makeCircleData(lod);
        Vertex[] vertices = new Vertex[lod];
        Vector normal = mapper.getMatrix().getFowardDirection();
        for(int i = 0; i < lod; i++) {
            float ar = data[i][0] * r, br = data[i][1] * r;
            vertices[i] = new Vertex(
                    mapper.map(ar, br, 0),
                    normalsOut ? mapper.mapNormal(ar, br, 0) : normal,
                    ( ( data[i][0] / 2 ) + 0.5f ), 
                    ( ( data[i][1] / 2 ) + 0.5f )
                    );
        }
        return new Mesh(vertices, texture);
    }

    public static Mesh createCircle(float cx, float cy, float cz, float r, Axis axis, int lod, boolean normalsOut, Image texture) {
        return createCircle(new VectorMapper(axis, new Vector(cx, cy, cz)), r, lod, normalsOut, texture);
    }
}
