package com.quew8.geng.geometry;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;

/**
 *
 * @author Quew8
 */
public class Plane {
    public static final Plane NEG_Z = Plane.getPlane(new Vector(0, 0, -1));
    public static Plane BILLBOARD_PLANE = NEG_Z;
    private final Vector tempA = new Vector(), tempB = new Vector();
    private final Vector up, right;

    public Plane(Vector up, Vector right) {
        this.up = up;
        this.right = right;
    }

    public Vector map(float dx, float dy) {
        return Vector.add(new Vector(), Vector.times(tempA, up, dy), Vector.times(tempB, right, dx));
    }
    
    public static Plane getPlane(Vector forward) {
        Vector right = GMath.cross(forward, new Vector(0, 1, 0)).negate();
        Vector up = GMath.cross(forward, right);
        return new Plane(up, right);
    }
}
