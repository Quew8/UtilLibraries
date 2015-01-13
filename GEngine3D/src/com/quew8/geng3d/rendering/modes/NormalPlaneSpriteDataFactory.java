package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class NormalPlaneSpriteDataFactory extends SpriteDataFactory2D {
    private final int nVertices;
        
    public NormalPlaneSpriteDataFactory(int nVertices) {
        super(nVertices * 3 * 4, 3 * 4, nVertices);
        this.nVertices = nVertices;
    }

    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, Plane plane) {
        addData(to, x, y, z, width, height, plane);
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        addData(to, x, y, z, width, height, plane);
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float width, float z, float height, Plane plane) {
        Vector normal = plane.getNormal();
        for(int i = 0; i < nVertices; i++) {
            to.putFloat(normal.getX());
            to.putFloat(normal.getY());
            to.putFloat(normal.getZ());
        }
    }
}
