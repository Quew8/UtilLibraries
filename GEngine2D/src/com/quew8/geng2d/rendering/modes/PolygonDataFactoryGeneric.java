package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;
import com.quew8.geng2d.rendering.modes.ColouredPolygonData;
import com.quew8.geng2d.rendering.modes.interfaces.DataFactoryGeneric;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PolygonDataFactoryGeneric implements DataFactoryGeneric<ColouredPolygonData> {
    private final int nVertices;

    public PolygonDataFactoryGeneric(int nVertices) {
        this.nVertices = nVertices;
    }
    
    @Override
    public void addData(ByteBuffer to, ColouredPolygonData data) {
        for(int i = 0; i < nVertices; i++) {
            to.putFloat(data.getVertex(i).getX());
            to.putFloat(data.getVertex(i).getY());
            to.putFloat(data.getColour().getRed());
            to.putFloat(data.getColour().getGreen());
            to.putFloat(data.getColour().getBlue());
        }
    }

    @Override
    public int getBytesPerSprite(Mode mode) {
        switch(mode) {
            case BARE: return nVertices * 8;
            case COLOURED: return nVertices * 20;
            default: throw new UnsupportedOperationException();
        }
    }

    @Override
    public int getVerticesPerSprite() {
        return nVertices;
    }
    
}
