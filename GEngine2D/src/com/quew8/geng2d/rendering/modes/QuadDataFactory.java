package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng2d.rendering.modes.interfaces.PolygonDataFactory;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class QuadDataFactory implements PolygonDataFactory {
    public static final QuadDataFactory INSTANCE = new QuadDataFactory();
    
    private QuadDataFactory() {
        
    }
    
    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float width, float height) {
        float[] uv1 = texture.transformCoords(0, 0);
        float[] uv2 = texture.transformCoords(1, 0);
        float[] uv3 = texture.transformCoords(1, 1);
        float[] uv4 = texture.transformCoords(0, 1);
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(uv1[0]);
        to.putFloat(uv1[1]);
        to.putFloat(x + width);
        to.putFloat(y);
        to.putFloat(uv2[0]);
        to.putFloat(uv2[1]);
        to.putFloat(x + width);
        to.putFloat(y + height);
        to.putFloat(uv3[0]);
        to.putFloat(uv3[1]);
        to.putFloat(x);
        to.putFloat(y + height);
        to.putFloat(uv4[0]);
        to.putFloat(uv4[1]);
    }
    
    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height) {
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x + width);
        to.putFloat(y);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x + width);
        to.putFloat(y + height);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x);
        to.putFloat(y + height);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
    }
    
    @Override
    public void addData(ByteBuffer to, float x, float y, float width, float height) {
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(x + width);
        to.putFloat(y);
        to.putFloat(x + width);
        to.putFloat(y + height);
        to.putFloat(x);
        to.putFloat(y + height);
    }

    @Override
    public int getBytesPerSprite(Mode mode) {
        switch(mode){
            case TEXTURED: return getBytesPerTexturedSprite();
            case COLOURED: return getBytesPerColouredSprite();
            case BARE: return getBytesPerBareSprite();
            default: return -1;
        }
    }

    public int getBytesPerTexturedSprite() {
        return 64;
    }

    public int getBytesPerColouredSprite() {
        return 80;
    }

    public int getBytesPerBareSprite() {
        return 32;
    }

    @Override
    public int getVerticesPerSprite() {
        return 4;
    }
    
}
