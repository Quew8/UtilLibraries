package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import com.quew8.gutils.opengl.OpenGL;

/**
 *
 * @author Quew8
 */
public class PolygonOutlineSpriteIndexDataFactory extends SpriteIndexDataFactory {
    public static final PolygonOutlineSpriteIndexDataFactory QUAD_INSTANCE = new PolygonOutlineSpriteIndexDataFactory(4);
    
    private final int nVertices;
    
    public PolygonOutlineSpriteIndexDataFactory(int nVertices) {
        super(OpenGL.GL_LINES, nVertices, nVertices * 2);
        this.nVertices = nVertices;
    }

    @Override
    public int[] getIndices() {
        int[] indices = new int[nVertices * 2];
        for(int i = 0, j = 0; j < nVertices; j++) {
            indices[i++] = j;
            indices[i++] = ( j + 1 ) % nVertices;
        }
        return indices;
    }
    
}
