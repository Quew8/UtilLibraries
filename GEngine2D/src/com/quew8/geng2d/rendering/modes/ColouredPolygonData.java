package com.quew8.geng2d.rendering.modes;

import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public interface ColouredPolygonData {

    public Colour getColour();

    public Vector2 getVertex(int i);
    
}
