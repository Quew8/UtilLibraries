package com.quew8.geng2d.geometry;

import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class PointDataFactory implements DataFactory1D<Vertex2D> {
    public static final PointDataFactory INSTANCE = new PointDataFactory();
    
    private PointDataFactory() {
        
    }

    @Override
    public Vertex2D construct(Colour colour, float x, float y, float width, float height) {
        return new Vertex2D(new Vector2(x, y), colour);
    }

    @Override
    public Vertex2D construct(float x, float y, float width, float height) {
        return new Vertex2D(new Vector2(x, y));
    }
    
}
