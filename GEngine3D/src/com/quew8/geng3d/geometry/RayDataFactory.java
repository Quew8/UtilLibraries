package com.quew8.geng3d.geometry;

import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class RayDataFactory implements DataFactory1D<Ray> {
    public static final RayDataFactory INSTANCE = new RayDataFactory();
    private final Ray rayInstance = new Ray();
    
    @Override
    public Ray construct(Colour colour, float x, float y, float z, float width, float height, float depth) {
        rayInstance.a = new Vertex3D(new Vector(x, y, z), colour);
        rayInstance.b = new Vertex3D(new Vector(x + width, y + height, z + depth), colour);
        return rayInstance;
    }
    
    @Override
    public Ray construct(float x, float y, float z, float width, float height, float depth) {
        rayInstance.a = new Vertex3D(new Vector(x, y, z));
        rayInstance.b = new Vertex3D(new Vector(x + width, y + height, z + depth));
        return rayInstance;
    }
}
