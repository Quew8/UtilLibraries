package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng2d.rendering.modes.interfaces.PolygonDataFactory;
import com.quew8.gutils.Colour;
import com.quew8.gutils.opengl.OpenGL;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PolygonOutlineSpriteDataFactory extends SpriteDataFactory2D {
    public static final PolygonOutlineSpriteDataFactory COLOURED_QUAD_INSTANCE = new PolygonOutlineSpriteDataFactory(QuadDataFactory.INSTANCE, Mode.COLOURED);
    public static final PolygonOutlineSpriteDataFactory BARE_QUAD_INSTANCE = new PolygonOutlineSpriteDataFactory(QuadDataFactory.INSTANCE, Mode.BARE);
    
    private final PolygonDataFactory texturedPolyDataFactory;
    private final PolygonDataFactory colouredPolyDataFactory;
    private final PolygonDataFactory barePolyDataFactory;
    
    private PolygonOutlineSpriteDataFactory(PolygonDataFactory polyDataFactory, Mode mode) {
        super(OpenGL.GL_LINES, polyDataFactory.getBytesPerSprite(mode), polyDataFactory.getVerticesPerSprite(), polyDataFactory.getVerticesPerSprite() * 2);
        switch(mode) {
            case TEXTURED: {
                texturedPolyDataFactory = polyDataFactory;
                colouredPolyDataFactory = null;
                barePolyDataFactory = null;
                break;
            }
            case COLOURED: {
                texturedPolyDataFactory = null;
                colouredPolyDataFactory = polyDataFactory;
                barePolyDataFactory = null;
                break;
            }
            case BARE: {
                texturedPolyDataFactory = null;
                colouredPolyDataFactory = null;
                barePolyDataFactory = polyDataFactory;
                break;
            }
            default:{
                throw new RuntimeException("Supposedly Impossible");
            }
        }
    }

    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float width, float height) {
        texturedPolyDataFactory.addData(to, texture, x, y, width, height);
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height) {
        colouredPolyDataFactory.addData(to, colour, x, y, width, height);
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float width, float height) {
        barePolyDataFactory.addData(to, x, y, width, height);
    }

    @Override
    public int[] getIndices() {
        int[] indices = new int[getVerticesPerSprite() * 2];
        for(int i = 0, j = 0; j < getVerticesPerSprite(); j++) {
            indices[i++] = j;
            indices[i++] = ( j + 1 ) % getVerticesPerSprite();
        }
        return indices;
    }
    
    public static SpriteDataFactory2D getColouredEllipse(int lod) {
        return new PolygonOutlineSpriteDataFactory(new EllipseDataFactory(lod), Mode.COLOURED);
    }
    
    public static SpriteDataFactory2D getBareEllipse(int lod) {
        return new PolygonOutlineSpriteDataFactory(new EllipseDataFactory(lod), Mode.BARE);
    }
    
}
