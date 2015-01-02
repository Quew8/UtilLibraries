package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import static com.quew8.gutils.opengl.OpenGL.GL_LINES;

/**
 *
 * @author Quew8
 */
public class RaySpriteIndexDataFactory extends SpriteIndexDataFactory {
    public static final RaySpriteIndexDataFactory INSTANCE = new RaySpriteIndexDataFactory();

    public RaySpriteIndexDataFactory() {
        super(GL_LINES, 2, 2);
    }

    @Override
    public int[] getIndices() {
        return new int[]{0, 1};
    }
}
