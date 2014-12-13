package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;
import com.quew8.gutils.Colour;
import static com.quew8.gutils.opengl.OpenGL.GL_POINTS;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PointSpriteDataFactory extends SpriteDataFactory1D {
    public static final PointSpriteDataFactory BARE_INSTANCE = new PointSpriteDataFactory(PointDataFactory.INSTANCE, Mode.BARE);
    public static final PointSpriteDataFactory COLOURED_INSTANCE = new PointSpriteDataFactory(PointDataFactory.INSTANCE, Mode.COLOURED);
    
    private final PointDataFactory colouredDataFactory;
    private final PointDataFactory bareDataFactory;
    
    public PointSpriteDataFactory(PointDataFactory dataFactory, Mode mode) {
        super(GL_POINTS, dataFactory.getBytesPerSprite(mode), dataFactory.getVerticesPerSprite(), 1);
        switch(mode) {
            case BARE: {
                bareDataFactory = dataFactory;
                colouredDataFactory = null;
                break;
            }
            case COLOURED: {
                colouredDataFactory = dataFactory;
                bareDataFactory = null;
                break;
            }
            default: throw new UnsupportedOperationException("");
        }
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float width, float height) {
        bareDataFactory.addData(to, x, y, width, height);
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height) {
        colouredDataFactory.addData(to, colour, x, y, width, height);
    }

    @Override
    public int[] getIndices() {
        return new int[]{0};
    }
    
}
