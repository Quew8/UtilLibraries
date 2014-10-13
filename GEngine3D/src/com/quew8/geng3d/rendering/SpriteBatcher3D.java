package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory3D;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class SpriteBatcher3D extends SpriteBatcher<SpriteDataFactory3D> {

    public SpriteBatcher3D(Texture tex, StaticRenderMode renderMode, SpriteDataFactory3D dataFactory, int size) {
        super(tex, renderMode, dataFactory, size);
    }
    
    public void draw(Image img, float x, float y, float z, float width, float height, float depth, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), img, x, y, z, width, height, depth, plane);
    }
    
    public void draw(Colour colour, float x, float y, float z, float width, float height, float depth, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), colour, x, y, z, width, height, depth, plane);
    }
    
    public void draw(float x, float y, float z, float width, float height, float depth, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), x, y, z, width, height, depth, plane);
    }
}
