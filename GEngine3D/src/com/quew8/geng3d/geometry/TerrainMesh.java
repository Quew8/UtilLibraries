package com.quew8.geng3d.geometry;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import com.quew8.gutils.collections.Bag;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;

/**
 *
 * @author Quew8
 */
public class TerrainMesh {
    private static final Vector UP_DIR = new Vector(0, 1, 0);
    private final float[] heights;
    private final float initX, initZ, stepX, stepZ;
    private final int nx, nz;
    private final TerrainQuad[] rawPolys;
    private final VertexConstructor vertConstructor;
    
    public TerrainMesh(VertexConstructor vertConstructor, float[] heights, 
            float initX, float initZ, float stepX, float stepZ, int nx, int nz) {
        
        if(heights.length != (nx * nz)) {
            throw new IllegalArgumentException("");
        }
        this.heights = heights;
        this.initX = initX;
        this.initZ = initZ;
        this.stepX = stepX;
        this.stepZ = stepZ;
        this.nx = nx;
        this.nz = nz;
        this.rawPolys = new TerrainQuad[(nx - 1) * (nz - 1)];
        this.vertConstructor = vertConstructor;
    }
    
    public TerrainMesh(float[] heights, float initX, float initZ, float stepX, 
            float stepZ, int nx, int nz) {
        
        this(new VertexConstructor(), heights, initX, initZ, stepX, stepZ, nx, nz);
    }
    
    public Mesh3D constructMesh() {
        for(int xi = 0; xi < nx - 1; xi++) {
            for(int zi = 0; zi < nz - 1; zi++) {
                setPolyAtIndex(xi, zi, new TerrainQuad(
                        xi, zi,
                        xi + 1, zi + 1
                ));
            }
        }
        int nNonNull = 0;
        for(int xi = 0; xi < nx - 1; xi++) {
            for(int zi = 0; zi < nz - 1; zi++) {
                TerrainQuad base = getPolyAtIndex(xi, zi);
                if(base != null) {
                    nNonNull++;
                    if(base.isUniform()) {
                        float refHeight = base.getHeight00();
                        int maxArea = 1;
                        int maxAreaWidth = 1, maxAreaDepth = 1;
                        int maxWidth = Integer.MAX_VALUE;
                        int width = 1, depth = 0;
                        TerrainQuad next;
                        do {
                            depth++;
                            next = getPolyAtIndexClamp(xi + width, zi + (depth-1));
                            while(next != null && width + 1 <= maxWidth && next.isUniform() && next.getHeight00() == refHeight) {
                                width++;
                                if(width * depth > maxArea) {
                                    maxAreaWidth = width;
                                    maxAreaDepth = depth;
                                    maxArea = width * depth;
                                }
                                next = getPolyAtIndexClamp(xi + width, zi + (depth-1));
                            }
                            maxWidth = width;
                            width = 1;
                            next = getPolyAtIndexClamp(xi, zi + depth);
                        } while(next != null && next.isUniform() && next.getHeight00() == refHeight);
                        if(maxAreaWidth > 1 || maxAreaDepth > 1) {
                            TerrainQuad base11 = getPolyAtIndex(xi + (maxAreaWidth-1), zi + (maxAreaDepth-1));
                            base.xi1 = base11.xi1;
                            base.zi1 = base11.zi1;
                            for(int j = 1; j < maxAreaWidth; j++) {
                                for(int k = 0; k < maxAreaDepth; k++) {
                                    setPolyAtIndex(xi + j, zi + k, null);
                                }
                            }
                            for(int k = 1; k < maxAreaDepth; k++) {
                                setPolyAtIndex(xi, zi + k, null);
                            }
                        }
                    }
                }
            }
        }
        //898236
        Bag<Vertex3D> vertices = new Bag<Vertex3D>(new Vertex3D[1]);
        int[] indices = new int[nNonNull * 6];
        int indicesIndex = 0;
        for(int xi = 0; xi < nx - 1; xi++) {
            for(int zi = 0; zi < nz - 1; zi++) {
                TerrainQuad base00 = getPolyAtIndex(xi, zi);
                if(base00 != null) {
                    float x0 = initX + (stepX * base00.xi0);
                    float z0 = initZ + (stepZ * base00.zi0);
                    float x1 = initX + (stepX * base00.xi1);
                    float z1 = initZ + (stepZ * base00.zi1);
                    Vector p00 = new Vector(x0, base00.getHeight00(), z0);
                    Vector p10 = new Vector(x1, base00.getHeight10(), z0);
                    Vector p01 = new Vector(x0, base00.getHeight01(), z1);
                    Vector p11 = new Vector(x1, base00.getHeight11(), z1);
                        
                    if(base00.i00 == -1) {
                        base00.i00 = vertices.size();
                        vertices.add(new Vertex3D(p00, getNormal(x0, z0), Colour.GREEN));
                    }
                    
                    if(base00.i10 == -1) {
                        base00.i10 = vertices.size();
                        vertices.add(new Vertex3D(p10, getNormal(x1, z0), Colour.GREEN));
                    }
                    TerrainQuad base10 = getPolyAtIndexClamp(base00.xi1, base00.zi0);
                    if(base10 != null && !(base00.isUniform() ^ base10.isUniform())) {
                        base10.i00 = base00.i10;
                    }
                    
                    if(base00.i01 == -1) {
                        base00.i01 = vertices.size();
                        vertices.add(new Vertex3D(p01, getNormal(x0, z1), Colour.GREEN));
                    }
                    TerrainQuad base01 = getPolyAtIndexClamp(base00.xi0, base00.zi1);
                    if(base01 != null && !(base00.isUniform() ^ base01.isUniform())) {
                        base01.i00 = base00.i01;
                    }
                    
                    if(base00.i11 == -1) {
                        base00.i11 = vertices.size();
                        vertices.add(new Vertex3D(p11, getNormal(x1, z1), Colour.GREEN));
                    }
                    TerrainQuad base11 = getPolyAtIndexClamp(base00.xi1, base00.zi1);
                    if(base11 != null && !(base00.isUniform() ^ base11.isUniform())) {
                        base11.i00 = base00.i11;
                    }
                    
                    indices[indicesIndex++] = base00.i00;
                    indices[indicesIndex++] = base00.i01;
                    indices[indicesIndex++] = base00.i11;
                    indices[indicesIndex++] = base00.i00;
                    indices[indicesIndex++] = base00.i11;
                    indices[indicesIndex++] = base00.i10;
                }
            }
        }
        vertices.trim();
        return new Mesh3D(vertices.getBackingArray(), indices, GL_TRIANGLES);
    }

    private void setPolyAtIndex(int xi, int zi, TerrainQuad quad) {
        rawPolys[getPolyIndex(xi, zi)] = quad;
    }
    
    private TerrainQuad getPolyAtIndexClamp(int xi, int zi) {
        if(!isPolyIndexInBounds(xi, zi)) {
            return null;
        }
        return getPolyAtIndex(xi, zi);
    }

    private TerrainQuad getPolyAtIndex(int xi, int zi) {
        return rawPolys[getPolyIndex(xi, zi)];
    }
    
    private boolean isPolyIndexInBounds(int xi, int zi) {
        return xi >= 0 && zi >= 0 && xi < nx - 1  && zi < nz - 1;
    }
    
    private int getPolyIndex(int xi, int zi) {
        int index = ((zi) * (nx - 1)) + (xi);
        if(!isPolyIndexInBounds(xi, zi)) {
            throw new IndexOutOfBoundsException("Poly " + xi + ", " + zi + " -> " + index);
        }
        return index;
    }
    
    private Vector getNormal(float x, float z) {
        float x0 = x - stepX;
        float z0 = z - stepZ;
        float x1 = x + stepX;
        float z1 = z + stepZ;
        int xi = getXI(x);
        int zi = getZI(z);
        Vector p00 = new Vector(x0, getHeightAtIndexClamp(xi - 1, zi - 1), z0);
        Vector p10 = new Vector(x1, getHeightAtIndexClamp(xi + 1, zi - 1), z0);
        Vector p01 = new Vector(x0, getHeightAtIndexClamp(xi - 1, zi + 1), z1);
        Vector normal = GMath.cross(new Vector(p00, p10), new Vector(p00, p01));
        normal.scale(normal, GMath.dot(normal, UP_DIR));
        normal.normalize(normal);
        return normal;
    }
    
    private float getHeightZero(float x, float z) {
        int xi = getXI(x);
        int zi = getZI(z);
        if(!isIndexInBounds(xi, zi)) {
            return 0;
        }
        return getHeightAtIndex(xi, zi);
    }

    private float getHeightAtIndexClamp(int xi, int zi) {
        if(!isIndexInBounds(xi, zi)) {
            while(xi < 0) {
                xi += nx;
            }
            while(xi >= nx) {
                xi -= nx;
            }
            while(zi < 0) {
                zi += nz;
            }
            while(zi >= nz) {
                zi -= nz;
            }
        }
        return heights[getIndex(xi, zi)];
    }

    private float getHeightAtIndex(int xi, int zi) {
        if(!isIndexInBounds(xi, zi)) {
            throw new IndexOutOfBoundsException("");
        }
        return heights[getIndex(xi, zi)];
    }
    
    private boolean isIndexInBounds(int xi, int zi) {
        return xi >= 0 && zi >= 0 && xi < nx  && zi < nz;
    }
    
    private int getIndex(int xi, int zi) {
        return (zi * nx) + xi;
    }
    
    private int getXI(float x) {
        return GMath.round((x - initX) / stepX);
    }
    
    private int getZI(float z) {
        return GMath.round((z - initZ) / stepZ);
    }

    public float getInitX() {
        return initX;
    }

    public float getInitZ() {
        return initZ;
    }

    public float getStepX() {
        return stepX;
    }

    public float getStepZ() {
        return stepZ;
    }

    public int getNX() {
        return nx;
    }

    public int getNZ() {
        return nz;
    }
    
    private class TerrainQuad {
        int xi0, zi0, xi1, zi1;
        int i00 = -1, i10 = -1, i11 = -1, i01 = -1;
        
        TerrainQuad(int xi0, int zi0, int xi1, int zi1) {
            this.xi0 = xi0;
            this.zi0 = zi0;
            this.xi1 = xi1;
            this.zi1 = zi1;
        }
        
        public float getHeight00() {
            return getHeightAtIndex(xi0, zi0);
        }
        
        public float getHeight10() {
            return getHeightAtIndex(xi1, zi0);
        }
        
        public float getHeight01() {
            return getHeightAtIndex(xi0, zi1);
        }
        
        public float getHeight11() {
            return getHeightAtIndex(xi1, zi1);
        }
        
        public boolean isUniform() {
            return getHeight00() == getHeight01() && 
                    getHeight00() == getHeight10() && 
                    getHeight00() == getHeight11();
        }
    }
    
    public static class VertexConstructor {
        public Vertex3D construct(Vector pos, Vector normal, TerrainMesh terrain) {
            return new Vertex3D(pos, normal, null, null);
        }
    }
}
