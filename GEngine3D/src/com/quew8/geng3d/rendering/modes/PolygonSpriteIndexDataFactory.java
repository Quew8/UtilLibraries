package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;

/**
 *
 * @author Quew8
 */
public class PolygonSpriteIndexDataFactory extends SpriteIndexDataFactory {
    public static final PolygonSpriteIndexDataFactory QUAD_INSTANCE = new PolygonSpriteIndexDataFactory(4);

    private final int nVertices;
    
    public PolygonSpriteIndexDataFactory(int nVertices) {
        super(GL_TRIANGLES, nVertices, (nVertices - 2) * 3);
        this.nVertices = nVertices;
    }

    @Override
    public int[] getIndices() {
        int[] indices = new int[getIndicesPerSprite()];
        for(int i = 0, j = 1; j < nVertices - 1; j++) {
            indices[i++] = 0;
            indices[i++] = j;
            indices[i++] = j + 1;
        }
        return indices;
    }
}
