package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng3d.rendering.modes.PolygonSpriteDataFactory;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory2D;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class SpriteBatcher2D extends SpriteBatcher<SpriteDataFactory2D> {

    public SpriteBatcher2D(Texture tex, StaticRenderMode renderMode, SpriteDataFactory2D dataFactory, int size) {
        super(tex, renderMode, dataFactory, size);
    }

    public SpriteBatcher2D(Texture tex, StaticRenderMode renderMode, int size) {
        this(tex, renderMode, PolygonSpriteDataFactory.TEXTURED_QUAD_INSTANCE, size);
    }

    public SpriteBatcher2D(StaticRenderMode renderMode, int size) {
        this(null, renderMode, PolygonSpriteDataFactory.BARE_QUAD_INSTANCE, size);
    }
    
    public void draw(Image img, float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), img, x, y, z, width, height, plane);
    }
    
    public void draw(Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), colour, x, y, z, width, height, plane);
    }
    
    public void draw(float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), x, y, z, width, height, plane);
    }
}
