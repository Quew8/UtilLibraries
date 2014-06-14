package com.quew8.geng3d.interfaces;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng3d.Collision;
import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 */
public interface Collidable extends Identifiable {
    public Vector[] getVertices();
    public Collision onCollision(Vector momentum, Vector penetration);
}
