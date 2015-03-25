package com.quew8.geng2d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class QuadDataFactory implements DataFactory2D<Polygon> {
    public static final QuadDataFactory INSTANCE = new QuadDataFactory();
    
    private final Polygon polyInstance = new Polygon(new Vertex2D[4]);
    
    private QuadDataFactory() {
        
    }
    
    @Override
    public Polygon construct(Image image, float x, float y, float width, float height) {
        Vector2 a = new Vector2(x, y);
        Vector2 texA = new Vector2(0, 0);
        polyInstance.vertices[0] = new Vertex2D(a, image.transformCoords(texA, texA));
        Vector2 b = new Vector2(x + width, y);
        Vector2 texB = new Vector2(1, 0);
        polyInstance.vertices[1] = new Vertex2D(b, image.transformCoords(texB, texB));
        Vector2 c = new Vector2(x + width, y + height);
        Vector2 texC = new Vector2(1, 1);
        polyInstance.vertices[2] = new Vertex2D(c, image.transformCoords(texC, texC));
        Vector2 d = new Vector2(x, y + height);
        Vector2 texD = new Vector2(0, 1);
        polyInstance.vertices[3] = new Vertex2D(d, image.transformCoords(texD, texD));
        return polyInstance;
    }

    @Override
    public Polygon construct(Colour colour, float x, float y, float width, float height) {
        Vector2 a = new Vector2(x, y);
        polyInstance.vertices[0] = new Vertex2D(a, colour);
        Vector2 b = new Vector2(x + width, y);
        polyInstance.vertices[1] = new Vertex2D(b, colour);
        Vector2 c = new Vector2(x + width, y + height);
        polyInstance.vertices[2] = new Vertex2D(c, colour);
        Vector2 d = new Vector2(x, y + height);
        polyInstance.vertices[3] = new Vertex2D(d, colour);
        return polyInstance;
    }

    @Override
    public Polygon construct(float x, float y, float width, float height) {
        Vector2 a = new Vector2(x, y);
        polyInstance.vertices[0] = new Vertex2D(a);
        Vector2 b = new Vector2(x + width, y);
        polyInstance.vertices[1] = new Vertex2D(b);
        Vector2 c = new Vector2(x + width, y + height);
        polyInstance.vertices[2] = new Vertex2D(c);
        Vector2 d = new Vector2(x, y + height);
        polyInstance.vertices[3] = new Vertex2D(d);
        return polyInstance;
    }
    
}
