package com.quew8.geng3d;

import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 */
public interface Bindable {
    public void translate(Vector dv);
    public void rotateAbout(Vector da, Vector about);
}
