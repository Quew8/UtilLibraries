package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.SpriteBatcherData;
import com.quew8.geng.rendering.modes.CharsetRenderMode;
import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import com.quew8.geng3d.rendering.modes.PolygonSpriteIndexDataFactory;
import com.quew8.geng3d.rendering.modes.QuadSpriteDataFactory;
import com.quew8.geng3d.rendering.modes.SpriteDataFactory2D;
import com.quew8.geng3d.rendering.modes.UVQuadSpriteDataFactory;
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

    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, SpriteBatcherData<SpriteDataFactory2D> data, SpriteIndexDataFactory indexFactory) {
        super(tex, renderMode, data, indexFactory);
        this.mapping = tex.getMapping();
        this.renderMode = renderMode;
    }

    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, SpriteBatcherData<SpriteDataFactory2D> data) {
        this(tex, renderMode, data, PolygonSpriteIndexDataFactory.QUAD_INSTANCE);
    }

    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, int n) {
        this(tex, renderMode, new SpriteBatcherData<SpriteDataFactory2D>(
                n, QuadSpriteDataFactory.INSTANCE, UVQuadSpriteDataFactory.INSTANCE
        ));
    }
    
    public void setCharColour(Colour colour) {
        renderMode.setCharColour(colour);
    }
    
    public void drawNoColour(char c, float x, float y, float z, float width, float height, Plane plane) {
        predraw();
        for(int i = 0; i < getAllAttribs().length; i++) {
            getAllAttribs()[i].getFactory().addData(getAllAttribs()[i].getBuffer(), mapping.get(c), x, y, z, width, height, plane);
        }
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
