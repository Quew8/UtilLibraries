package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.GeometricObject;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.ArrayUtils;
import com.quew8.gutils.Colour;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;
import java.util.ArrayList;
import java.util.Arrays;

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
            normal.negate(normal);
            Vector v = c;
            c = b;
            b = v;
        }
        normal.normalizeIfNot(normal);
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
    
    public static Mesh3D createQuadTextured(Vector centre, float s1, float s2, 
            Plane plane) {
        return createQuadTextured(centre.getX(), centre.getY(), centre.getZ(), s1, s2, plane);
    }
    
    public static Mesh3D createQuadTextured(float cx, float cy, float cz, float s1, 
            float s2, Plane plane) {
        
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
    
    public static Mesh3D createQuadColoured(Vector centre, float s1, float s2, 
            Plane plane, Colour colour) {
        
        return createQuadColoured(centre.getX(), centre.getY(), centre.getZ(), s1, s2, plane, colour);
    }
    
    public static Mesh3D createQuadColoured(float cx, float cy, float cz, float s1, 
            float s2, Plane plane, Colour colour) {
        
        float hs1 = s1 / 2;
        float hs2 = s2 / 2;
        Vector normal = plane.getNormal();
        Vector centre = new Vector(cx, cy, cz);
        return new Mesh3D(
                new Vertex3D[]{
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(-hs1, -hs2)), normal, colour),
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(hs1, -hs2)), normal, colour),
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(hs1, hs2)), normal, colour),
                    new Vertex3D(Vector.add(new Vector(), centre, plane.map(-hs1, hs2)), normal, colour),
                },
                new int[]{
                    0, 1, 2,
                    0, 2, 3
                }
                );
    }

    public static Mesh3D createCircleColoured(float cx, float cy, float cz, float r, 
            Plane plane, int lod, boolean normalsOut, Colour col) {
        
        Vertex3D[] vertices = new Vertex3D[lod];
        Vector centre = new Vector(cx, cy, cz);
        Vector normal = plane.getNormal();
        float theta = 0;
        float step = 2 * GMath.PI / lod;
        for(int i = 0; i < lod; i++) {
            float s = GMath.sin(theta), c = GMath.cos(theta);
            Vector out = plane.map(s * r, c * r);
            vertices[i] = new Vertex3D(
                    Vector.add(new Vector(), centre, out),
                    normalsOut ? Vector.scale(new Vector(), out, 1f / r)  : normal,
                    col
                    );
            theta += step;
        }
        return new Mesh3D(vertices, createPolygonIndices(vertices.length));
    }

    public static Mesh3D createCircleTextured(float cx, float cy, float cz, float r, 
            Plane plane, int lod, boolean normalsOut) {
        
        Vertex3D[] vertices = new Vertex3D[lod];
        Vector centre = new Vector(cx, cy, cz);
        Vector normal = plane.getNormal();
        float theta = 0;
        float step = 2 * GMath.PI / lod;
        for(int i = 0; i < lod; i++) {
            float arn = GMath.sin(theta), brn = GMath.cos(theta);
            float ar = arn * r, br = brn * r;
            Vector out = plane.map(ar, br);
            vertices[i] = new Vertex3D(
                    Vector.add(new Vector(), centre, out),
                    normalsOut ? out.scale(new Vector(), 1f / r) : normal,
                    new Vector2(
                            ( arn / 2 ) + 0.5f,
                            ( brn / 2 ) + 0.5f)
                    );
            theta += step;
        }
        return new Mesh3D(vertices, createPolygonIndices(vertices.length));
    }
    
    private static int getSphereIndex(int i, int j, int lod) {
        if(i == 0) {
            return 0;
        } else {
            return 1 + ((i-1) * lod) + (((j%lod)+lod) % lod);
        }
    }
    
    private static Vector getSphereNormal(Vector centre, Vector pos) {
        Vector norm = new Vector(centre, pos);
        norm.normalize(norm);
        return norm;
    }
    
    public static Mesh3D createSphereTextured(float cx, float cy, float cz, float r, int lod, SphericalProjection projection) {
        if(lod % 2 != 0) {
            lod++;
        }
        
        Vector centre = new Vector(cx, cy, cz);
        
        ArrayList<Vertex3D> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        
        int nLevels = (lod / 2) + 1;
        
        float step = GMath.PI / (nLevels - 1);
        float lStep = 2 * GMath.PI / lod;
        float theta = step;
        
        Vector pos0 = new Vector(0, r, 0);
        pos0.add(pos0, centre);
        Vector2 v = projection.getUV(0, 0);
        vertices.add(new Vertex3D(pos0, getSphereNormal(centre, pos0), v, new Colour()));
        
        float lRadius = r * GMath.sin(theta);
        Vector lCentre = new Vector(0, r * GMath.cos(theta), 0);
        lCentre.add(lCentre, centre);
        float phi = 0;
        for(int k = 0; k < lod; k++) {
            Vector pos = new Vector(lRadius * GMath.sin(phi), 0, lRadius * GMath.cos(phi));
            pos.add(pos, lCentre);
            Vector normal = getSphereNormal(centre, pos);
            v = projection.getUV(phi, theta);
            vertices.add(new Vertex3D(pos, normal, v, new Colour()));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(0, 0, lod), getSphereIndex(1, k-1, lod), getSphereIndex(1, k, lod)}));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(1, k, lod), getSphereIndex(1, k-1, lod), getSphereIndex(2, k-1, lod)}));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(1, k, lod), getSphereIndex(2, k-1, lod), getSphereIndex(2, k, lod)}));
            phi += lStep;
        }
        theta += step;
        
        for(int i = 2; i < nLevels - 2; i++) {
            lRadius = r * GMath.sin(theta);
            lCentre = new Vector(0, r * GMath.cos(theta), 0);
            lCentre.add(lCentre, centre);
            phi = 0;
            for(int k = 0; k < lod; k++) {
                Vector pos = new Vector(lRadius * GMath.sin(phi), 0, lRadius * GMath.cos(phi));
                pos.add(pos, lCentre);
                Vector normal = getSphereNormal(centre, pos);
                v = projection.getUV(phi, theta);
                vertices.add(new Vertex3D(pos, normal, v, new Colour()));
                indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(i, k, lod), getSphereIndex(i, k-1, lod), getSphereIndex(i+1, k-1, lod)}));
                indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(i, k, lod), getSphereIndex(i+1, k-1, lod), getSphereIndex(i+1, k, lod)}));
                phi += lStep;
            }
            theta += step;
        }
        
        lRadius = r * GMath.sin(theta);
        lCentre = new Vector(0, r * GMath.cos(theta), 0);
        lCentre.add(lCentre, centre);
        phi = 0;
        for(int k = 0; k < lod; k++) {
            Vector pos = new Vector(lRadius * GMath.sin(phi), 0, lRadius * GMath.cos(phi));
            pos.add(pos, lCentre);
            Vector normal = getSphereNormal(centre, pos);
            v = projection.getUV(phi, theta);
            vertices.add(new Vertex3D(pos, normal, v, new Colour()));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(nLevels-1, 0, lod), getSphereIndex(nLevels-2, k, lod), getSphereIndex(nLevels-2, k-1, lod)}));
            phi += lStep;
        }
        
        Vector posf = new Vector(0, -r, 0);
        posf.add(posf, centre);
        Vector normal = getSphereNormal(centre, posf);
        v = projection.getUV(0, theta);
        vertices.add(new Vertex3D(posf, normal, v, new Colour()));
        
        return new Mesh3D(
                vertices.toArray(new Vertex3D[vertices.size()]), 
                ArrayUtils.unbox(indices.toArray(new Integer[indices.size()]))
        );
    }
    
    public static Mesh3D createSphereColoured(float cx, float cy, float cz, float r, int lod, Colour colour) {
        if(lod % 2 != 0) {
            lod++;
        }
        
        Vector centre = new Vector(cx, cy, cz);
        
        ArrayList<Vertex3D> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        
        int nLevels = (lod / 2) + 1;
        
        float step = GMath.PI / (nLevels - 1);
        float lStep = 2 * GMath.PI / lod;
        float theta = step;
        
        Vector pos0 = new Vector(0, r, 0);
        pos0.add(pos0, centre);
        vertices.add(new Vertex3D(pos0, getSphereNormal(centre, pos0), colour));
        
        float lRadius = r * GMath.sin(theta);
        Vector lCentre = new Vector(0, r * GMath.cos(theta), 0);
        lCentre.add(lCentre, centre);
        float phi = 0;
        for(int k = 0; k < lod; k++) {
            Vector pos = new Vector(lRadius * GMath.sin(phi), 0, lRadius * GMath.cos(phi));
            pos.add(pos, lCentre);
            Vector normal = getSphereNormal(centre, pos);
            vertices.add(new Vertex3D(pos, normal, colour));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(0, 0, lod), getSphereIndex(1, k-1, lod), getSphereIndex(1, k, lod)}));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(1, k, lod), getSphereIndex(1, k-1, lod), getSphereIndex(2, k-1, lod)}));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(1, k, lod), getSphereIndex(2, k-1, lod), getSphereIndex(2, k, lod)}));
            phi += lStep;
        }
        theta += step;
        
        for(int i = 2; i < nLevels - 2; i++) {
            lRadius = r * GMath.sin(theta);
            lCentre = new Vector(0, r * GMath.cos(theta), 0);
            lCentre.add(lCentre, centre);
            phi = 0;
            for(int k = 0; k < lod; k++) {
                Vector pos = new Vector(lRadius * GMath.sin(phi), 0, lRadius * GMath.cos(phi));
                pos.add(pos, lCentre);
                Vector normal = getSphereNormal(centre, pos);
                vertices.add(new Vertex3D(pos, normal, colour));
                indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(i, k, lod), getSphereIndex(i, k-1, lod), getSphereIndex(i+1, k-1, lod)}));
                indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(i, k, lod), getSphereIndex(i+1, k-1, lod), getSphereIndex(i+1, k, lod)}));
                phi += lStep;
            }
            theta += step;
        }
        
        lRadius = r * GMath.sin(theta);
        lCentre = new Vector(0, r * GMath.cos(theta), 0);
        lCentre.add(lCentre, centre);
        phi = 0;
        for(int k = 0; k < lod; k++) {
            Vector pos = new Vector(lRadius * GMath.sin(phi), 0, lRadius * GMath.cos(phi));
            pos.add(pos, lCentre);
            Vector normal = getSphereNormal(centre, pos);
            vertices.add(new Vertex3D(pos, normal, colour));
            indices.addAll(Arrays.asList(new Integer[]{getSphereIndex(nLevels-1, 0, lod), getSphereIndex(nLevels-2, k, lod), getSphereIndex(nLevels-2, k-1, lod)}));
            phi += lStep;
        }
        
        Vector posf = new Vector(0, -r, 0);
        posf.add(posf, centre);
        Vector normal = getSphereNormal(centre, posf);
        vertices.add(new Vertex3D(posf, normal, colour));
        
        return new Mesh3D(
                vertices.toArray(new Vertex3D[vertices.size()]), 
                ArrayUtils.unbox(indices.toArray(new Integer[indices.size()]))
        );
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
