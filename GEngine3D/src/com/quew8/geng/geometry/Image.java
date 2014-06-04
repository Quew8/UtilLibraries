package com.quew8.geng.geometry;

import com.quew8.geng.rendering.modes.ImageFetchable;

/**
 * 
 * @author Quew8
 */
public interface Image extends ImageFetchable {
    public void bind();
    public TextureArea getWholeArea();
    public void dispose();
}
