package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Plane;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.CharsetRenderMode;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng3d.geometry.DataFactory2D;
import com.quew8.gmath.Vector;
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

    public CharSpriteBatcher(CharsetTexture tex, CharsetRenderMode renderMode, DataFactory2D<T> dataFactory, FixedSizeDataInterpreter<T, ?> dataInterpreter, int maxN) {
        super(tex, renderMode, dataInterpreter, maxN);
        this.mapping = tex.getMapping();
        this.renderMode = renderMode;
        this.dataFactory = dataFactory;
    }
    
    public void setCharColour(Colour colour) {
        renderMode.setCharColour(colour);
    }
    
    public void drawNoColour(char c, float x, float y, float z, float width, float height, Plane plane) {
        batch(dataFactory.construct(mapping.get(c), x, y, z, width, height, plane));
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
