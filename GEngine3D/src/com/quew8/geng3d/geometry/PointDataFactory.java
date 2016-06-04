package com.quew8.geng3d.geometry;

import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class PointDataFactory implements DataFactory1D<Vertex3D> {
    public static final PointDataFactory INSTANCE = new PointDataFactory();
    
    private final Vertex3D pointInstance;
    
    public PointDataFactory() {
        pointInstance = createInstance();
    }

    @Override
    public Vertex3D construct(Vertex3D out, Colour colour, float x, float y, float z, float width, float height, float depth) {
        out.getPosition().setXYZ(x, y, z);
        out.getColour().setRGBA(colour);
        return out;
    }

    @Override
    public Vertex3D construct(Vertex3D out, float x, float y, float z, float width, float height, float depth) {
        out.getPosition().setXYZ(x, y, z);
        return out;
    }

    @Override
    public Vertex3D getInstance() {
        return pointInstance;
    }

    @Override
    public Vertex3D createInstance() {
        return new Vertex3D(new Vector(), new Colour());
    }
}
