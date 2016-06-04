package com.quew8.geng3d.geometry;

import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class RayDataFactory implements DataFactory1D<Ray> {
    public static final RayDataFactory INSTANCE = new RayDataFactory();
    private final Ray rayInstance = createInstance();
    
    @Override
    public Ray construct(Ray out, Colour colour, float x, float y, float z, float width, float height, float depth) {
        out.a = new Vertex3D(new Vector(x, y, z), colour);
        out.b = new Vertex3D(new Vector(x + width, y + height, z + depth), colour);
        return out;
    }
    
    @Override
    public Ray construct(Ray out, float x, float y, float z, float width, float height, float depth) {
        out.a = new Vertex3D(new Vector(x, y, z));
        out.b = new Vertex3D(new Vector(x + width, y + height, z + depth));
        return out;
    }

    @Override
    public Ray getInstance() {
        return rayInstance;
    }
    
    @Override
    public Ray createInstance() {
        return new Ray();
    }
}
