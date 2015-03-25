package com.quew8.geng2d.geometry;

import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class RayDataFactory implements DataFactory1D<Ray> {
    public static final RayDataFactory INSTANCE = new RayDataFactory();
    private final Ray rayInstance = new Ray();
    
    private RayDataFactory() {
        
    }

    @Override
    public Ray construct(Colour colour, float x, float y, float width, float height) {
        rayInstance.a = new Vertex2D(new Vector2(x, y), colour);
        rayInstance.b = new Vertex2D(new Vector2(x + width, y + height), colour);
        return rayInstance;
    }

    @Override
    public Ray construct(float x, float y, float width, float height) {
        rayInstance.a = new Vertex2D(new Vector2(x, y));
        rayInstance.b = new Vertex2D(new Vector2(x + width, y + height));
        return rayInstance;
    }
    
}
