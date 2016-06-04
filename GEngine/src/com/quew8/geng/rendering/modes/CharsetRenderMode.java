package com.quew8.geng.rendering.modes;

import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public abstract class CharsetRenderMode extends StaticRenderMode {

    public CharsetRenderMode(int nAttribs, int stride) {
        super(nAttribs, stride);
    }
    
    public abstract void setCharColour(Colour colour);
}
