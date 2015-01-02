package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.SpriteBatcherData;
import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import com.quew8.geng3d.rendering.modes.PolygonSpriteIndexDataFactory;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory2D;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
final public class SpriteBatcher2D extends SpriteBatcher<SpriteDataFactory2D> {

    public SpriteBatcher2D(Texture tex, StaticRenderMode renderMode, SpriteBatcherData<SpriteDataFactory2D> data, SpriteIndexDataFactory indexFactory) {
        super(tex, renderMode, data, indexFactory);
    }

    /*public SpriteBatcher2D(Texture tex, StaticRenderMode renderMode, int size) {
        this(tex, renderMode, PolygonSpriteDataFactory.TEXTURED_QUAD_INSTANCE, size);
    }*/

    public SpriteBatcher2D(StaticRenderMode renderMode, SpriteBatcherData<SpriteDataFactory2D> data, SpriteIndexDataFactory indexFactory) {
        this(null, renderMode, data, indexFactory);
    }

    /*public SpriteBatcher2D(StaticRenderMode renderMode, int size) {
        this(null, renderMode, PolygonSpriteDataFactory.BARE_QUAD_INSTANCE, size);
    }*/
    
    public void draw(Image img, float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        for(int i = 0; i < getAllAttribs().length; i++) {
            getAllAttribs()[i].getFactory().addData(getAllAttribs()[i].getBuffer(), img, x, y, z, width, height, plane);
        }
    }
    
    public void draw(Colour colour, float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        for(int i = 0; i < getAllAttribs().length; i++) {
            getAllAttribs()[i].getFactory().addData(getAllAttribs()[i].getBuffer(), colour, x, y, z, width, height, plane);
        }
    }
    
    public void draw(float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        for(int i = 0; i < getAllAttribs().length; i++) {
            getAllAttribs()[i].getFactory().addData(getAllAttribs()[i].getBuffer(), x, y, z, width, height, plane);
        }
    }
}
