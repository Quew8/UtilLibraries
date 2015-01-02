package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.SpriteBatcherData;
import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory1D;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class SpriteBatcher1D extends SpriteBatcher<SpriteDataFactory1D> {

    public SpriteBatcher1D(Texture tex, StaticRenderMode renderMode, SpriteBatcherData<SpriteDataFactory1D> data, SpriteIndexDataFactory indexFactory) {
        super(tex, renderMode, data, indexFactory);
    }
    
    public void draw(Colour colour, float x, float y, float z, float width, float height, float depth) {
        predraw();
        for(int i = 0; i < getAllAttribs().length; i++) {
            getAllAttribs()[i].getFactory().addData(getAllAttribs()[i].getBuffer(), colour, x, y, z, width, height, depth);
        }
    }
    
    public void draw(float x, float y, float z, float width, float height, float depth) {
        predraw();
        for(int i = 0; i < getAllAttribs().length; i++) {
            getAllAttribs()[i].getFactory().addData(getAllAttribs()[i].getBuffer(), x, y, z, width, height, depth);
        }
    }
    
}
