package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng2d.rendering.modes.interfaces.DataFactory1D;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PointDataFactory implements DataFactory1D {
    public static final PointDataFactory INSTANCE = new PointDataFactory();
    
    private PointDataFactory() {
        
    }
    
    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height) {
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(colour.getRed());
        to.putFloat(colour.getGreen());
        to.putFloat(colour.getBlue());
    }

    @Override
    public void addData(ByteBuffer to, float x, float y, float width, float height) {
        to.putFloat(x);
        to.putFloat(y);
    }

    @Override
    public int getBytesPerSprite(Mode mode) {
        switch(mode) {
            case BARE: return 8;
            case COLOURED: return 20;
            default: throw new UnsupportedOperationException("Cannot Have 1D textured sprites");
        }
    }

    @Override
    public int getVerticesPerSprite() {
        return 1;
    }
    
}
