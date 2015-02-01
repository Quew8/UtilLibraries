package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.GeometricObject;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;

/**
 * 
 * @author Quew8
 */
public class Mesh3D extends GeometricObject<Mesh3D, Vertex3D> {

    public Mesh3D(Vertex3D[] vertices, int[] indices, int mode) {
        super(vertices, indices, mode);
    }

    public Mesh3D(Vertex3D[] vertices, int[] indices) {
        this(vertices, indices, GL_TRIANGLES);
    }

    @Override
    protected Mesh3D construct(Mesh3D old, Vertex3D[] vertices, int[] indices, int mode) {
        return new Mesh3D(vertices, indices, mode);
    }
    
    /*public static Mesh3D[] createCuboid(float cx, float cy, float cz, 
            float sx, float sy, float sz, 
            Image front, Image back, 
            Image top, Image bottom, 
            Image left, Image right) {
        
        float hsx = sx / 2, hsy = sy / 2, hsz = sz / 2;
        return new Mesh3D[] {
            createQuad(cx + hsx, cy, cz, sz, sy, Axis.X, left),
            createQuad(cx - hsx, cy, cz, sz, sy, Axis.NEGATIVE_X, right),
            createQuad(cx, cy + hsy, cz, sx, sz, Axis.Y, top),
            createQuad(cx, cy - hsy, cz, sx, sz, Axis.NEGATIVE_Y, bottom),
            createQuad(cx, cy, cz + hsz, sx, sy, Axis.Z, front),
            createQuad(cx, cy, cz - hsz, sx, sy, Axis.NEGATIVE_Z, front),
        };
    }
    
    public static Mesh3D[] createCuboid(float cx, float cy, float cz, float sx, float sy, float sz, Image texture) {
        return createCuboid(cx, cy, cz, sx, sy, sz, texture, texture, texture, texture, texture, texture);
    }
    
    public static Mesh3D[] createCube(float cx, float cy, float cz, float size, Image texture) {
        return createCuboid(cx, cy, cz, size, size, size, texture);
    }*/
    
    public static Mesh3D createPolygon(Vertex3D[] vertices) {
        return new Mesh3D(vertices, createPolygonIndices(vertices.length), GL_TRIANGLES);
    }

    public static Mesh3D createTriangleColoured(Vector a, Vector b, Vector c, Vector above, Colour col) {
        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        Vector up = new Vector(a, above);
        Vector normal = GMath.cross(ab, ac);
        if(GMath.dot(normal, up) < 0) {
            normal.negate();
            Vector v = c;
            c = b;
            b = v;
        }
        normal.normalizeIfNot();
        return new Mesh3D(
                new Vertex3D[] {
                    new Vertex3D(a, normal, col),
                    new Vertex3D(b, normal, col),
                    new Vertex3D(c, normal, col)
                },
                new int[] {
                    0, 1, 2
                },
                GL_TRIANGLES
        );
    }
    
    public static Mesh3D createQuadTextured(float cx, float cy, float cz, float s1, float s2, Plane plane) {
        float hs1 = s1 / 2;
        float hs2 = s2 / 2;
        Vector normal = plane.getNormal();
        Vector centre = new Vector(cx, cy, cz);
        return new Mesh3D(
                new Vertex3D[]{
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(-hs1, -hs2)), normal, new Vector2(0, 0)),
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(hs1, -hs2)), normal, new Vector2(1, 0)),
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(hs1, hs2)), normal, new Vector2(1, 1)),
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(-hs1, hs2)), normal, new Vector2(0, 1)),
                },
                new int[]{
                    0, 1, 2,
                    0, 2, 3
                }
                );
    }

    public static Mesh3D createCircleColoured(float cx, float cy, float cz, float r, 
            Plane plane, int lod, boolean normalsOut, Colour col) {
        
        float[][] data = GMath.makeCircleData(lod);
        Vertex3D[] vertices = new Vertex3D[lod];
        Vector centre = new Vector(cx, cy, cz);
        Vector normal = plane.getNormal();
        for(int i = 0; i < lod; i++) {
            float ar = data[i][0] * r, br = data[i][1] * r;
            Vector out = plane.map(ar, br);
            vertices[i] = new Vertex3D(
                    Vector.add(new Vector(), centre, out),
                    normalsOut ? new Vector(out, Vector.NORMALIZE_BIT) : normal,
                    col
                    );
        }
        return new Mesh3D(vertices, createPolygonIndices(vertices.length));
    }

    public static Mesh3D createCircleTextured(float cx, float cy, float cz, float r, 
            Plane plane, int lod, boolean normalsOut) {
        
        float[][] data = GMath.makeCircleData(lod);
        Vertex3D[] vertices = new Vertex3D[lod];
        Vector centre = new Vector(cx, cy, cz);
        Vector normal = plane.getNormal();
        for(int i = 0; i < lod; i++) {
            float ar = data[i][0] * r, br = data[i][1] * r;
            Vector out = plane.map(ar, br);
            vertices[i] = new Vertex3D(
                    Vector.add(new Vector(), centre, out),
                    normalsOut ? new Vector(out, Vector.NORMALIZE_BIT) : normal,
                    new Vector2(
                            ( data[i][0] / 2 ) + 0.5f,
                            ( data[i][1] / 2 ) + 0.5f)
                    );
        }
        return new Mesh3D(vertices, createPolygonIndices(vertices.length));
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
