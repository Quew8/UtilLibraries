package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;
import static com.quew8.geng.rendering.modes.Mode.BARE;
import static com.quew8.geng.rendering.modes.Mode.COLOURED;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PolygonSpriteDataFactoryGeneric extends SpriteDataFactoryGeneric<ColouredPolygonData> {
    private final PolygonDataFactoryGeneric colouredPolyDataFactory;
    private final PolygonDataFactoryGeneric barePolyDataFactory;
    
    public PolygonSpriteDataFactoryGeneric(PolygonDataFactoryGeneric polyDataFactory, Mode mode) {
        super(GL_TRIANGLES, polyDataFactory.getBytesPerSprite(mode), polyDataFactory.getVerticesPerSprite(), ( polyDataFactory.getVerticesPerSprite() - 2 ) * 3);
        switch(mode) {
            case COLOURED: {
                colouredPolyDataFactory = polyDataFactory;
                barePolyDataFactory = null;
                break;
            }
            case BARE: {
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
    public int[] getIndices() {
        int[] indices = new int[getIndicesPerSprite()];
        for(int i = 0, j = 1; j < getVerticesPerSprite() - 1; j++) {
            indices[i++] = 0;
            indices[i++] = j;
            indices[i++] = j + 1;
        }
        return indices;
    }

    @Override
    public void addData(ByteBuffer to, ColouredPolygonData data) {
        colouredPolyDataFactory.addData(to, data);
    }
}
