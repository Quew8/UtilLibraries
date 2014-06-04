package com.quew8.geng.interfaces;

import com.quew8.geng.Collision;
import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 */
public interface Collidable extends Identifiable {
    public Vector[] getVertices();
    public Collision onCollision(Vector momentum, Vector penetration);
}
