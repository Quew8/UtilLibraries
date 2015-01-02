package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Plane;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class RGBSpriteDataFactory {
    public static final RGBSpriteDataFactory2D QUAD_INSTANCE = new RGBSpriteDataFactory2D(4);
    public static final RGBSpriteDataFactory1D RAY_INSTANCE = new RGBSpriteDataFactory1D(2);
    
    private static void addColour(Colour c, ByteBuffer to, int n) {
        for(int i = 0; i < n; i++) {
            to.putFloat(c.getRed());
            to.putFloat(c.getGreen());
            to.putFloat(c.getBlue());
        }
    }
    
    public static class RGBSpriteDataFactory1D extends SpriteDataFactory1D {
        private final int nVertices;
        
        public RGBSpriteDataFactory1D(int nVertices) {
            super(nVertices * 3 * 4, 3 * 4, nVertices);
            this.nVertices = nVertices;
        }

        @Override
        public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth) {
            addColour(colour, to, nVertices);
        }
        
    }
    
    public static class RGBSpriteDataFactory2D extends SpriteDataFactory2D {
        private final int nVertices;
        
        public RGBSpriteDataFactory2D(int nVertices) {
            super(nVertices * 3 * 4, 3 * 4, nVertices);
            this.nVertices = nVertices;
        }

        @Override
        public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, Plane plane) {
            addColour(colour, to, nVertices);
        }
        
    }
    
    public static class RGBSpriteDataFactory3D extends SpriteDataFactory3D {
        private final int nVertices;
        
        public RGBSpriteDataFactory3D(int nVertices) {
            super(nVertices * 3 * 4, 3 * 4, nVertices);
            this.nVertices = nVertices;
        }

        @Override
        public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth, Plane plane) {
            addColour(colour, to, nVertices);
        }
        
    }
}
