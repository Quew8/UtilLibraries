package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public abstract class CharsetRenderMode extends StaticRenderMode {
    public abstract void setCharColour(Colour colour);
}
