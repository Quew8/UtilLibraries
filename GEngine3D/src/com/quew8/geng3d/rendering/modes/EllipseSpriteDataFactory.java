package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class EllipseSpriteDataFactory extends SpriteDataFactory2D {
    private final int lod;
    private final float[] trigs;

    public EllipseSpriteDataFactory(int lod) {
        super(12 * getNVertices(lod), 12, getNVertices(lod));
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
        addData(to, x, y, z, width, height, plane);
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        addData(to, x, y, z, width, height, plane);
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, Plane plane) {
        float rw = width / 2;
        float rh = height / 2;
        /*Vector c = plane.map(rw, rh);
        float cx = x + c.getX();
        float cy = x + c.getX();
        float cz = x + c.getX();*/
        for(int i = 0; i < lod; i++) {
            Vector v = plane.map(rw * (1 + trigs[lod + i]), rh * (1 + trigs[i]));
            to.putFloat(x + v.getX());
            to.putFloat(y + v.getY());
            to.putFloat(z + v.getZ());
        }
    }
    
    public static int getNVertices(int lod) {
        return lod;
    }
}
