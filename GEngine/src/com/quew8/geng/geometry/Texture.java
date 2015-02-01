package com.quew8.geng.geometry;

import com.quew8.geng.rendering.modes.TextureFetchable;

/**
 * 
 * @author Quew8
 */
public interface Texture extends TextureFetchable {
    public void bind();
    public Image getWholeArea();
    public void dispose();
}
