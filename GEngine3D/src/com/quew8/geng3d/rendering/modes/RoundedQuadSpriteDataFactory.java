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
public class RoundedQuadSpriteDataFactory extends SpriteDataFactory2D {
    private final float topLeftR, topRightR, bottomRightR, bottomLeftR;
    private final int lod;
    
    public RoundedQuadSpriteDataFactory(float topLeftR, float topRightR, float bottomRightR, float bottomLeftR, int lod) {
        super(12 * getNVertices(lod), 12, getNVertices(lod));
        this.topLeftR = topLeftR;
        this.topRightR = topRightR;
        this.bottomRightR = bottomRightR;
        this.bottomLeftR = bottomLeftR;
        this.lod = lod;
    }

    public RoundedQuadSpriteDataFactory(float r, int lod) {
        this(r, r, r, r, lod);
    }
    
    @Override
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, Plane plane) {
        float step = (GMath.PI / 2) / (lod+1);
        Vector v = plane.map(width * bottomLeftR, 0);
        putVector(x, y, z, v, to);
        v = plane.map(width * (1 - bottomRightR), 0);
        putVector(x, y, z, v, to);
        float theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * (1 + (bottomRightR * (GMath.sin(theta) - 1))), 
                    height * bottomRightR * (1 - GMath.cos(theta))
            );
            putVector(x, y, z, v, to);
            theta += step;
        }
        v = plane.map(width, height * bottomRightR);
        putVector(x, y, z, v, to);
        v = plane.map(width, height * (1 - topRightR));
        putVector(x, y, z, v, to);
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * (1 + (bottomRightR * (GMath.cos(theta) - 1))), 
                    height * (1 + (bottomRightR * (GMath.sin(theta) - 1)))
            );
            putVector(x, y, z, v, to);
            theta += step;
        }
        v = plane.map(width * (1 - topRightR), height);
        putVector(x, y, z, v, to);
        v = plane.map(width * topLeftR, height);
        putVector(x, y, z, v, to);
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * topLeftR * (1 - GMath.sin(theta)), 
                    height * (1 + (topLeftR * (GMath.cos(theta) - 1)))
                    
            );
            putVector(x, y, z, v, to);
            theta += step;
        }
        v = plane.map(0, height * (1 - topLeftR));
        putVector(x, y, z, v, to);
        v = plane.map(0, height * bottomLeftR);
        putVector(x, y, z, v, to);
        theta = step;
        for(int i = 0; i < lod; i++) {
            v = plane.map(
                    width * bottomLeftR * (1 - GMath.cos(theta)), 
                    height * bottomLeftR * (1 - GMath.sin(theta))
            );
            putVector(x, y, z, v, to);
            theta += step;
        }
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        addData(to, x, y, z, width, height, plane);
    }

    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, Plane plane) {
        addData(to, x, y, z, width, height, plane);
    }
    
    private static void putVector(float x, float y, float z, Vector v, ByteBuffer in) {
        in.putFloat(x + v.getX());
        in.putFloat(y + v.getY());
        in.putFloat(z + v.getZ());
    }
    
    public static int getNVertices(int lod) {
        return 8 + (4 * lod);
    }
}
