package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng2d.rendering.modes.interfaces.PolygonDataFactory;
import com.quew8.gutils.Colour;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PolygonSpriteDataFactory extends SpriteDataFactory2D {
    public static final PolygonSpriteDataFactory TEXTURED_QUAD_INSTANCE = new PolygonSpriteDataFactory(QuadDataFactory.INSTANCE, Mode.TEXTURED);
    public static final PolygonSpriteDataFactory COLOURED_QUAD_INSTANCE = new PolygonSpriteDataFactory(QuadDataFactory.INSTANCE, Mode.COLOURED);
    public static final PolygonSpriteDataFactory BARE_QUAD_INSTANCE = new PolygonSpriteDataFactory(QuadDataFactory.INSTANCE, Mode.BARE);
    
    private final PolygonDataFactory texturedPolyDataFactory;
    private final PolygonDataFactory colouredPolyDataFactory;
    private final PolygonDataFactory barePolyDataFactory;
    
    public PolygonSpriteDataFactory(PolygonDataFactory polyDataFactory, Mode mode) {
        super(GL_TRIANGLES, polyDataFactory.getBytesPerSprite(mode), polyDataFactory.getVerticesPerSprite(), ( polyDataFactory.getVerticesPerSprite() - 2 ) * 3);
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
        int[] indices = new int[getIndicesPerSprite()];
        for(int i = 0, j = 1; j < getVerticesPerSprite() - 1; j++) {
            indices[i++] = 0;
            indices[i++] = j;
            indices[i++] = j + 1;
        }
        return indices;
    }
    
    public static SpriteDataFactory2D getTexEllipse(int lod) {
        return new PolygonSpriteDataFactory(new EllipseDataFactory(lod), Mode.TEXTURED);
    }
    
    public static SpriteDataFactory2D getColouredEllipse(int lod) {
        return new PolygonSpriteDataFactory(new EllipseDataFactory(lod), Mode.COLOURED);
    }
    
    public static SpriteDataFactory2D getBareEllipse(int lod) {
        return new PolygonSpriteDataFactory(new EllipseDataFactory(lod), Mode.BARE);
    }
}
