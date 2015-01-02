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
public class QuadSpriteDataFactory extends SpriteDataFactory2D {
    public static final QuadSpriteDataFactory INSTANCE = new QuadSpriteDataFactory();

    private QuadSpriteDataFactory() {
        super(48, 12, 4);
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
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, Plane plane) {
        Vector b = plane.map(width, 0);
        Vector c = plane.map(width, height);
        Vector d = plane.map(0, height);
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(z);
        to.putFloat(x + b.getX());
        to.putFloat(y + b.getY());
        to.putFloat(z + b.getZ());
        to.putFloat(x + c.getX());
        to.putFloat(y + c.getY());
        to.putFloat(z + c.getZ());
        to.putFloat(x + d.getX());
        to.putFloat(y + d.getY());
        to.putFloat(z + d.getZ());
    }
}
