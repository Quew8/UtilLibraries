package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng2d.rendering.modes.interfaces.DataFactory1D;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class RayDataFactory implements DataFactory1D {
    public static final RayDataFactory INSTANCE = new RayDataFactory();
    
    private RayDataFactory() {
        
    }
    
    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height) {
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
        to.putFloat(x + width);
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
        to.putFloat(y + height);
    }

    @Override
    public int getBytesPerSprite(Mode mode) {
        switch(mode){
            case TEXTURED: throw new UnsupportedOperationException("Textured Rays are not supported");
            case COLOURED: return getBytesPerColouredSprite();
            case BARE: return getBytesPerBareSprite();
            default: return -1;
        }
    }

    public int getBytesPerColouredSprite() {
        return 40;
    }

    public int getBytesPerBareSprite() {
        return 16;
    }

    @Override
    public int getVerticesPerSprite() {
        return 2;
    }
    
}
