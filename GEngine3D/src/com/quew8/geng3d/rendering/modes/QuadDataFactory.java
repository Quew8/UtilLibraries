package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng3d.rendering.modes.interfaces.PolygonDataFactory;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class QuadDataFactory implements PolygonDataFactory {
    public static final QuadDataFactory INSTANCE = new QuadDataFactory();
    
    private QuadDataFactory() {
        
    }
    
    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, Plane plane) {
        float[] uv1 = texture.transformCoords(0, 0);
        float[] uv2 = texture.transformCoords(1, 0);
        float[] uv3 = texture.transformCoords(1, 1);
        float[] uv4 = texture.transformCoords(0, 1);
        Vector b = plane.map(width, 0);
        Vector c = plane.map(width, height);
        Vector d = plane.map(0, height);
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(z);
        to.putFloat(uv1[0]);
        to.putFloat(uv1[1]);
        to.putFloat(x + b.getX());
        to.putFloat(y + b.getY());
        to.putFloat(z + b.getZ());
        to.putFloat(uv2[0]);
        to.putFloat(uv2[1]);
        to.putFloat(x + c.getX());
        to.putFloat(y + c.getY());
        to.putFloat(z + c.getZ());
        to.putFloat(uv3[0]);
        to.putFloat(uv3[1]);
        to.putFloat(x + d.getX());
        to.putFloat(y + d.getY());
        to.putFloat(z + d.getZ());
        to.putFloat(uv4[0]);
        to.putFloat(uv4[1]);
    }
    
    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        Vector b = plane.map(width, 0);
        Vector c = plane.map(width, height);
        Vector d = plane.map(0, height);
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(z);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x + b.getX());
        to.putFloat(y + b.getY());
        to.putFloat(z + b.getZ());
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x + c.getX());
        to.putFloat(y + c.getY());
        to.putFloat(z + c.getZ());
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x + d.getX());
        to.putFloat(y + d.getY());
        to.putFloat(z + d.getZ());
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
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

    @Override
    public int getBytesPerSprite(Mode mode) {
        switch(mode){
            case TEXTURED: return getBytesPerTexturedSprite();
            case COLOURED: return getBytesPerColouredSprite();
            case BARE: return getBytesPerBareSprite();
            default: return -1;
        }
    }

    public int getBytesPerTexturedSprite() {
        return 80;
    }

    public int getBytesPerColouredSprite() {
        return 96;
    }

    public int getBytesPerBareSprite() {
        return 48;
    }

    @Override
    public int getVerticesPerSprite() {
        return 4;
    }
    
}
