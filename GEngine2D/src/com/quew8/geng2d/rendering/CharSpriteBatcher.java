package com.quew8.geng2d.rendering;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.CharsetRenderMode;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng2d.geometry.DataFactory2D;
import com.quew8.gutils.Colour;
import java.util.HashMap;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class CharSpriteBatcher<T> extends SpriteBatcher<T> {
    public static final float CHAR_SIZE_RATIO = 8f / 5f, CHAR_GAP_RATIO = 0.1f / 5f;
    private final HashMap<Character, Image> mapping;
    private final CharsetRenderMode renderMode;
    private final DataFactory2D<T> dataFactory;

    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, 
            DataFactory2D<T> dataFactory, 
            FixedSizeDataInterpreter<T, ?> dataInterpreter, int maxN) {
        
        super(tex, renderMode, dataInterpreter, maxN);
        this.mapping = tex.getMapping();
        this.renderMode = renderMode;
        this.dataFactory = dataFactory;
    }
    
    public void setCharColour(Colour colour) {
        renderMode.setCharColour(colour);
    }
    
    public void drawNoColour(char c, float x, float y, float width, float height) {
        batch(dataFactory.construct(mapping.get(c), x, y, width, height));
    }
    
    public void drawNoColour(char c, float x, float y, float width) {
        drawNoColour(c, x, y, width, width * CHAR_SIZE_RATIO);
    }
    
    public void draw(Colour colour, char c, float x, float y, float width, float height) {
        setCharColour(colour);
        drawNoColour(c, x, y, width, height);
    }
    
    public void draw(Colour colour, char c, float x, float y, float width) {
        draw(colour, c, x, y, width, width * CHAR_SIZE_RATIO);
    }
    
    public void drawLine(Colour colour, String s, float x, float y, float charWidth, float charHeight, float gap) {
        setCharColour(colour);
        drawLineNoColour(s, x, y, charWidth, charHeight, gap);
    }
    
    public void drawLine(Colour colour, String s, float x, float y, float charWidth) {
        drawLine(colour, s, x, y, charWidth, charWidth * CHAR_SIZE_RATIO, charWidth * CHAR_GAP_RATIO);
    }
    
    public void drawLineNoColour(String s, float x, float y, float charWidth, float charHeight, float gap) {
        float currX = x;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ') {
                drawNoColour(s.charAt(i), currX, y, charWidth, charHeight);
            }
            currX += charWidth + gap;
        }
    }
    
    public void drawLineNoColour(String s, float x, float y, float charWidth) {
        drawLineNoColour(s, x, y, charWidth, charWidth * CHAR_SIZE_RATIO, charWidth * CHAR_GAP_RATIO);
    }
}
