package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory1D;
import com.quew8.gutils.Colour;
import static com.quew8.gutils.opengl.OpenGL.GL_LINES;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class RaySpriteDataFactory extends SpriteDataFactory1D {
    public static final RaySpriteDataFactory COLOURED_RAY_INSTANCE = new RaySpriteDataFactory(RayDataFactory.INSTANCE, Mode.COLOURED);
    public static final RaySpriteDataFactory BARE_RAY_INSTANCE = new RaySpriteDataFactory(RayDataFactory.INSTANCE, Mode.BARE);
    
    private final RayDataFactory texturedRayDataFactory;
    private final RayDataFactory colouredRayDataFactory;
    private final RayDataFactory bareRayDataFactory;
    
    public RaySpriteDataFactory(RayDataFactory rayDataFactory, Mode mode) {
        super(GL_LINES, rayDataFactory.getBytesPerSprite(mode), rayDataFactory.getVerticesPerSprite(), 2);
        switch(mode) {
            case TEXTURED: {
                texturedRayDataFactory = rayDataFactory;
                colouredRayDataFactory = null;
                bareRayDataFactory = null;
                break;
            }
            case COLOURED: {
                texturedRayDataFactory = null;
                colouredRayDataFactory = rayDataFactory;
                bareRayDataFactory = null;
                break;
            }
            case BARE: {
                texturedRayDataFactory = null;
                colouredRayDataFactory = null;
                bareRayDataFactory = rayDataFactory;
                break;
            }
            default:{
                throw new RuntimeException("Supposedly Impossible");
            }
        }
    }

    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth) {
        colouredRayDataFactory.addData(to, colour, x, y, z, width, height, depth);
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, float depth) {
        bareRayDataFactory.addData(to, x, y, z, width, height, depth);
    }

    @Override
    public int[] getIndices() {
        return new int[]{0, 1};
    }
}
