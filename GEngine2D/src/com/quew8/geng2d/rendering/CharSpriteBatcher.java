package com.quew8.geng2d.rendering;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng2d.rendering.modes.SpriteDataFactory;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class CharSpriteBatcher extends SpriteBatcher {
    private final HashMap<Character, Image> mapping;

    public CharSpriteBatcher(int mode, CharsetTexture tex, StaticRenderMode renderMode, SpriteDataFactory dataFactory, int size) {
        super(mode, tex, renderMode, dataFactory, size);
        this.mapping = tex.getMapping();
    }

    public CharSpriteBatcher(CharsetTexture tex, StaticRenderMode renderMode, SpriteDataFactory dataFactory, int size) {
        super(tex, renderMode, dataFactory, size);
        this.mapping = tex.getMapping();
    }

    public CharSpriteBatcher(CharsetTexture tex, StaticRenderMode renderMode, int size) {
        super(tex, renderMode, size);
        this.mapping = tex.getMapping();
    }

    public void draw(char c, float x, float y, float width, float height) {
        draw(mapping.get(c), x, y, width, height);
    }
    
    public void drawLine(String s, float x, float y, float charWidth, float charHeight, float gap) {
        float xPos = x;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ') {
                draw(s.charAt(i), xPos, y, charWidth, charHeight);
            }
            xPos += charWidth + gap;
        }
    }
}
