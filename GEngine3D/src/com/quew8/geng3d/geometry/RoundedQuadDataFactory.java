package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class RoundedQuadDataFactory implements DataFactory2D<Polygon> {
    private final float topLeftR, topRightR, bottomRightR, bottomLeftR;
    private final int lod;
    private final Polygon polyInstance;
    
    public RoundedQuadDataFactory(float topLeftR, float topRightR, float bottomRightR, float bottomLeftR, int lod) {
        //super(12 * getNVertices(lod), 12, getNVertices(lod));
        this.topLeftR = topLeftR;
        this.topRightR = topRightR;
        this.bottomRightR = bottomRightR;
        this.bottomLeftR = bottomLeftR;
        this.lod = lod;
        this.polyInstance = new Polygon(new Vertex3D[getNVertices(lod)]);
    }

    public RoundedQuadDataFactory(float r, int lod) {
        this(r, r, r, r, lod);
    }
    
    @Override
    public Polygon construct(float x, float y, float z, float width, float height, Plane plane) {
        Vector base = new Vector(x, y, z);
        float step = (GMath.PI / 2) / (lod+1);
        Vector v = plane.map(width * bottomLeftR, 0);
        polyInstance.vertices[0] = new Vertex3D(Vector.add(new Vector(), base, v));
        v = plane.map(width * (1 - bottomRightR), 0);
        polyInstance.vertices[1] = new Vertex3D(Vector.add(new Vector(), base, v));
        float theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * (1 + (bottomRightR * (GMath.sin(theta) - 1))), 
                    height * bottomRightR * (1 - GMath.cos(theta))
            );
            polyInstance.vertices[2 + i] = new Vertex3D(Vector.add(new Vector(), base, v));
            theta += step;
        }
        v = plane.map(width, height * bottomRightR);
        polyInstance.vertices[1 + lod] = new Vertex3D(Vector.add(new Vector(), base, v));
        v = plane.map(width, height * (1 - topRightR));
        polyInstance.vertices[2 + lod] = new Vertex3D(Vector.add(new Vector(), base, v));
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * (1 + (bottomRightR * (GMath.cos(theta) - 1))), 
                    height * (1 + (bottomRightR * (GMath.sin(theta) - 1)))
            );
            polyInstance.vertices[3 + lod + i] = new Vertex3D(Vector.add(new Vector(), base, v));
            theta += step;
        }
        v = plane.map(width * (1 - topRightR), height);
        polyInstance.vertices[3 + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v));
        v = plane.map(width * topLeftR, height);
        polyInstance.vertices[4 + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v));
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * topLeftR * (1 - GMath.sin(theta)), 
                    height * (1 + (topLeftR * (GMath.cos(theta) - 1)))
                    
            );
            polyInstance.vertices[5 + lod + lod + i] = new Vertex3D(Vector.add(new Vector(), base, v));
            theta += step;
        }
        v = plane.map(0, height * (1 - topLeftR));
        polyInstance.vertices[5 + lod + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v));
        v = plane.map(0, height * bottomLeftR);
        polyInstance.vertices[6 + lod + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v));
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * bottomLeftR * (1 - GMath.cos(theta)), 
                    height * bottomLeftR * (1 - GMath.sin(theta))
            );
            polyInstance.vertices[7 + lod + lod + lod + i] = new Vertex3D(Vector.add(new Vector(), base, v));
            theta += step;
        }
        return polyInstance;
    }

    @Override
    public Polygon construct(Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        Vector base = new Vector(x, y, z);
        float step = (GMath.PI / 2) / (lod+1);
        Vector v = plane.map(width * bottomLeftR, 0);
        polyInstance.vertices[0] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        v = plane.map(width * (1 - bottomRightR), 0);
        polyInstance.vertices[1] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        float theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * (1 + (bottomRightR * (GMath.sin(theta) - 1))), 
                    height * bottomRightR * (1 - GMath.cos(theta))
            );
            polyInstance.vertices[2 + i] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
            theta += step;
        }
        v = plane.map(width, height * bottomRightR);
        polyInstance.vertices[1 + lod] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        v = plane.map(width, height * (1 - topRightR));
        polyInstance.vertices[2 + lod] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * (1 + (bottomRightR * (GMath.cos(theta) - 1))), 
                    height * (1 + (bottomRightR * (GMath.sin(theta) - 1)))
            );
            polyInstance.vertices[3 + lod + i] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
            theta += step;
        }
        v = plane.map(width * (1 - topRightR), height);
        polyInstance.vertices[3 + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        v = plane.map(width * topLeftR, height);
        polyInstance.vertices[4 + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * topLeftR * (1 - GMath.sin(theta)), 
                    height * (1 + (topLeftR * (GMath.cos(theta) - 1)))
                    
            );
            polyInstance.vertices[5 + lod + lod + i] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
            theta += step;
        }
        v = plane.map(0, height * (1 - topLeftR));
        polyInstance.vertices[5 + lod + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        v = plane.map(0, height * bottomLeftR);
        polyInstance.vertices[6 + lod + lod + lod] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * bottomLeftR * (1 - GMath.cos(theta)), 
                    height * bottomLeftR * (1 - GMath.sin(theta))
            );
            polyInstance.vertices[7 + lod + lod + lod + i] = new Vertex3D(Vector.add(new Vector(), base, v), colour);
            theta += step;
        }
        return polyInstance;
    }

    @Override
    public Polygon construct(Image texture, float x, float y, float z, float width, float height, Plane plane) {
        throw new UnsupportedOperationException("Not yet supported");
    }
    
    public static int getNVertices(int lod) {
        return 8 + (4 * lod);
    }
}
