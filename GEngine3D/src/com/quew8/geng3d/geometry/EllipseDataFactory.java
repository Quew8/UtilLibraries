package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class EllipseDataFactory implements DataFactory2D<Polygon> {
    private final int lod;
    private final float[] trigs;
    private final Polygon polyInstance;
    
    public EllipseDataFactory(int lod) {
        this.lod = lod;
        this.trigs = new float[lod * 2];
        float theta = 0, step = ( GMath.PI * 2 ) / lod;
        for(int i = 0; i < lod; i++) {
            trigs[i] = GMath.sin(theta);
            trigs[lod + i] = GMath.cos(theta);
            theta += step;
        }
        this.polyInstance = new Polygon(new Vertex3D[lod]);
    }

    @Override
    public Polygon construct(Image texture, float x, float y, float z, float width, float height, Plane plane) {
        Vector pos = new Vector(x, y, z);
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(width * trigs[lod + i], height * trigs[i]);
            Vector2 texV = new Vector2(0.5f + ( trigs[lod + i] / 2 ), 0.5f + ( trigs[i] / 2 ));
            v = Vector.add(v, pos, v);
            texV = texture.transformCoords(texV, texV);
            polyInstance.vertices[i] = new Vertex3D(v, texV);
        }
        return polyInstance;
    }

    @Override
    public Polygon construct(Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        Vector pos = new Vector(x, y, z);
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(width * trigs[lod + i], height * trigs[i]);
            v = Vector.add(v, pos, v);
            polyInstance.vertices[i] = new Vertex3D(v, colour);
        }
        return polyInstance;
    }

    @Override
    public Polygon construct(float x, float y, float z, float width, float height, Plane plane) {
        Vector pos = new Vector(x, y, z);
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(width * trigs[lod + i], height * trigs[i]);
            v = Vector.add(v, pos, v);
            polyInstance.vertices[i] = new Vertex3D(v);
        }
        return polyInstance;
    }
    
    public int getLOD() {
        return lod;
    }
    
    public static int getNVertices(int lod) {
        return lod;
    }
}
