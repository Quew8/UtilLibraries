package com.quew8.geng.geometry;

import com.quew8.gutils.opengl.texture.TextureDetails;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class CharsetTexture extends BasicTexture {
    private final HashMap<Character, Image> mapping;
    
    public CharsetTexture(TextureDetails details, HashMap<Character, Image> mapping) {
        super(details);
        this.mapping = mapping;
    }
    
    public HashMap<Character, Image> getMapping() {
        return mapping;
    }
    
    public Image getChar(char c) {
        return mapping.get(c);
    }
    
    /*public static HashMap<Character, Image> getBasicMapping() {
        HashMap<Character, Image> map = new HashMap<Character, Image>();
        map.put('A', new Image(0, 0, (float) 5 / 64, (float) 8 / 64));
        map.put('B', new Image((float) 5 / 64, 0, (float) 10 / 64, (float) 8 / 64));
        map.put('C', new Image((float) 10 / 64, 0, (float) 15 / 64, (float) 8 / 64));
        map.put('D', new Image((float) 15 / 64, 0, (float) 20 / 64, (float) 8 / 64));
        map.put('E', new Image((float) 20 / 64, 0, (float) 25 / 64, (float) 8 / 64));
        map.put('F', new Image((float) 25 / 64, 0, (float) 30 / 64, (float) 8 / 64));
        map.put('G', new Image((float) 30 / 64, 0, (float) 35 / 64, (float) 8 / 64));
        map.put('H', new Image((float) 35 / 64, 0, (float) 40 / 64, (float) 8 / 64));
        map.put('I', new Image((float) 40 / 64, 0, (float) 45 / 64, (float) 8 / 64));
        map.put('J', new Image((float) 45 / 64, 0, (float) 50 / 64, (float) 8 / 64));
        map.put('K', new Image((float) 50 / 64, 0, (float) 55 / 64, (float) 8 / 64));
        map.put('L', new Image((float) 55 / 64, 0, (float) 60 / 64, (float) 8 / 64));
        return map;
    }*/
    
    public static HashMap<Character, Image> getMapping(String chars, float charWidth, float charHeight, float texWidth, float texHeight) {
        final HashMap<Character, Image> map = new HashMap<Character, Image>();
        float xPos = 0, yPos = 0;
        final float texCharWidth = charWidth / texWidth, texCharHeight = charHeight / texHeight;
        for(int i = 0; i < chars.length(); i++) {
            map.put(chars.charAt(i), new Image(xPos, yPos, xPos + texCharWidth, yPos + texCharHeight));
            xPos += texCharWidth;
            if(xPos >= 1) {
                yPos += texCharHeight;
                xPos = 0;
                if(yPos >= 1) {
                    throw new RuntimeException("Insufficient texture size for given chars");
                }
            }
        }
        return map;
    }
}