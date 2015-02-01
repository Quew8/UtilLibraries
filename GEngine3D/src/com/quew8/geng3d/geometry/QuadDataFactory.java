package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class QuadDataFactory implements DataFactory2D<Polygon> {
    public static final QuadDataFactory INSTANCE = new QuadDataFactory();
    
    private final Polygon polyInstance = new Polygon(new Vertex3D[4]);
    
    @Override
    public Polygon construct(Image image, float x, float y, float z, float width, float height, Plane plane) {
        Vector a = new Vector(x, y, z);
        Vector2 texA = new Vector2(0, 0);
        polyInstance.vertices[0] = new Vertex3D(a, image.transformCoords(texA, texA));
        Vector b = Vector.add(new Vector(), a, plane.map(width, 0));
        Vector2 texB = new Vector2(1, 0);
        polyInstance.vertices[1] = new Vertex3D(b, image.transformCoords(texB, texB));
        Vector c = Vector.add(new Vector(), a, plane.map(width, height));
        Vector2 texC = new Vector2(1, 1);
        polyInstance.vertices[2] = new Vertex3D(c, image.transformCoords(texC, texC));
        Vector d = Vector.add(new Vector(), a, plane.map(0, height));
        Vector2 texD = new Vector2(0, 1);
        polyInstance.vertices[3] = new Vertex3D(d, image.transformCoords(texD, texD));
        return polyInstance;
    }

    @Override
    public Polygon construct(Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        Vector a = new Vector(x, y, z);
        polyInstance.vertices[0] = new Vertex3D(a, colour);
        Vector b = Vector.add(new Vector(), a, plane.map(width, 0));
        polyInstance.vertices[1] = new Vertex3D(b, colour);
        Vector c = Vector.add(new Vector(), a, plane.map(width, height));
        polyInstance.vertices[2] = new Vertex3D(c, colour);
        Vector d = Vector.add(new Vector(), a, plane.map(0, height));
        polyInstance.vertices[3] = new Vertex3D(d, colour);
        return polyInstance;
    }

    @Override
    public Polygon construct(float x, float y, float z, float width, float height, Plane plane) {
        Vector a = new Vector(x, y, z);
        polyInstance.vertices[0] = new Vertex3D(a);
        Vector b = Vector.add(new Vector(), a, plane.map(width, 0));
        polyInstance.vertices[1] = new Vertex3D(b);
        Vector c = Vector.add(new Vector(), a, plane.map(width, height));
        polyInstance.vertices[2] = new Vertex3D(c);
        Vector d = Vector.add(new Vector(), a, plane.map(0, height));
        polyInstance.vertices[3] = new Vertex3D(d);
        return polyInstance;
    }
    
}
