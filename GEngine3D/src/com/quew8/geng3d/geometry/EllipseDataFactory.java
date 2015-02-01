package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng3d.rendering.modes.interfaces.PolygonDataFactory;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class EllipseDataFactory implements PolygonDataFactory {
    private final int lod;
    private final float[] trigs;
    
    public EllipseDataFactory(int lod) {
        this.lod = lod;
        this.trigs = new float[lod * 2];
        float theta = 0, step = ( GMath.PI * 2 ) / lod;
        for(int i = 0; i < lod; i++) {
            trigs[i] = GMath.sin(theta);
            trigs[lod + i] = GMath.cos(theta);
            theta += step;
        }
    }

    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, Plane plane) {
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(width * trigs[lod + i], height * trigs[i]);
            float[] tc = texture.transformCoords(0.5f + ( trigs[lod + i] / 2 ), 0.5f + ( trigs[i] / 2 ));
            to.putFloat(x + v.getX());
            to.putFloat(y + v.getY());
            to.putFloat(z + v.getZ());
            to.putFloat(tc[0]);
            to.putFloat(tc[1]);
        }
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(width * trigs[lod + i], height * trigs[i]);
            to.putFloat(x + v.getX());
            to.putFloat(y + v.getY());
            to.putFloat(z + v.getZ());
            to.putFloat(colour.getRed());
            to.putFloat(colour.getGreen());
            to.putFloat(colour.getBlue());
        }
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, Plane plane) {
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(width * trigs[lod + i], height * trigs[i]);
            to.putFloat(x + v.getX());
            to.putFloat(y + v.getY());
            to.putFloat(z + v.getZ());
        }
    }
    
    public int getLOD() {
        return lod;
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
        return lod * 20;
    }

    public int getBytesPerColouredSprite() {
        return lod * 24;
    }

    public int getBytesPerBareSprite() {
        return lod * 12;
    }

    @Override
    public int getVerticesPerSprite() {
        return lod;
    }
}
