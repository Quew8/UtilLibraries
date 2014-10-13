package com.quew8.geng2d.rendering;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.CharsetRenderMode;
import com.quew8.geng2d.rendering.modes.PolygonSpriteDataFactory;
import com.quew8.geng2d.rendering.modes.SpriteDataFactory2D;
import com.quew8.gutils.Colour;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class CharSpriteBatcher extends SpriteBatcher<SpriteDataFactory2D> {
    private final HashMap<Character, Image> mapping;
    private final CharsetRenderMode renderMode;
    
    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, SpriteDataFactory2D dataFactory, int size) {
        super(tex, renderMode, dataFactory, size);
        this.renderMode = renderMode;
        this.mapping = tex.getMapping();
    }

    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, int size) {
        this(tex, renderMode, PolygonSpriteDataFactory.TEXTURED_QUAD_INSTANCE, size);
    }
    
    public void drawNoColour(char c, float x, float y, float width, float height) {
        predraw();
        getFactory().addData(getBuffer(), mapping.get(c), x, y, width, height);
    }
    
    public void draw(Colour colour, char c, float x, float y, float width, float height) {
        renderMode.setCharColour(colour);
        drawNoColour(c, x, y, width, height);
    }
    
    public void draw(char c, float x, float y, float width, float height) {
        draw(Colour.WHITE, c, x, y, width, height);
    }
    
    public void drawLine(Colour colour, String s, float x, float y, float charWidth, float charHeight, float gap) {
        float xPos = x;
        renderMode.setCharColour(colour);
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ') {
                drawNoColour(s.charAt(i), xPos, y, charWidth, charHeight);
            }
            xPos += charWidth + gap;
        }
    }
    
    public void drawLine(String s, float x, float y, float charWidth, float charHeight, float gap) {
        drawLine(Colour.WHITE, s, x, y, charWidth, charHeight, gap);
    }
}
