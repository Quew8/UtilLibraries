package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.CharsetRenderMode;
import com.quew8.geng3d.rendering.modes.PolygonSpriteDataFactory;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory2D;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class CharSpriteBatcher extends SpriteBatcher<SpriteDataFactory2D> {
    public static final float CHAR_SIZE_RATIO = 8f / 5f, CHAR_GAP_RATIO = 0.1f / 5f;
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
    
    public void setCharColour(Colour colour) {
        renderMode.setCharColour(colour);
    }
    
    public void drawNoColour(char c, float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        getFactory().addData(getBuffer(), mapping.get(c), x, y, z, width, height, plane);
    }
    
    public void drawNoColour(char c, float x, float y, float z, float width, Plane plane) {
        drawNoColour(c, x, y, z, width, width * CHAR_SIZE_RATIO, plane);
    }
    
    public void draw(Colour colour, char c, float x, float y, float z, float width, float height, Plane plane) {
        setCharColour(colour);
        drawNoColour(c, x, y, z, width, height, plane);
    }
    
    public void draw(Colour colour, char c, float x, float y, float z, float width, Plane plane) {
        draw(colour, c, x, y, z, width, width * CHAR_SIZE_RATIO, plane);
    }
    
    public void drawLine(Colour colour, String s, float x, float y, float z, float charWidth, float charHeight, float gap, Plane plane) {
        setCharColour(colour);
        drawLineNoColour(s, x, y, z, charWidth, charHeight, gap, plane);
    }
    
    public void drawLine(Colour colour, String s, float x, float y, float z, float charWidth, Plane plane) {
        drawLine(colour, s, x, y, z, charWidth, charWidth * CHAR_SIZE_RATIO, charWidth * CHAR_GAP_RATIO, plane);
    }
    
    public void drawLineNoColour(String s, float x, float y, float z, float charWidth, float charHeight, float gap, Plane plane) {
        Vector pos = new Vector(x, y, z);
        Vector step = plane.map(charWidth + gap, 0);
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ') {
                drawNoColour(s.charAt(i), pos.getX(), pos.getY(), pos.getZ(), charWidth, charHeight, plane);
            }
            pos.add(step);
        }
    }
    
    public void drawLineNoColour(String s, float x, float y, float z, float charWidth, Plane plane) {
        drawLineNoColour(s, x, y, z, charWidth, charWidth * CHAR_SIZE_RATIO, charWidth * CHAR_GAP_RATIO, plane);
    }
}