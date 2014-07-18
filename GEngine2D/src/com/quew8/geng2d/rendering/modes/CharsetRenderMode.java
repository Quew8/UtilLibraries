package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.StaticRenderMode;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public abstract class CharsetRenderMode extends StaticRenderMode {
    public abstract void setCharColour(FloatBuffer colour);
}
