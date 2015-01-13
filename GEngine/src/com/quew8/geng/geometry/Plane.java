package com.quew8.geng.geometry;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;

/**
 *
 * @author Quew8
 */
public class Plane {
    public static final Plane 
            POS_Y = new Plane(new Vector(1, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0)),
            NEG_Y = new Plane(new Vector(1, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0)),
            NEG_Z = Plane.getPlane(new Vector(0, 0, -1)),
            POS_Z = Plane.getPlane(new Vector(0, 0, 1));
    public static Plane BILLBOARD_PLANE = NEG_Z;
    private final Vector tempA = new Vector(), tempB = new Vector();
    private final Vector right, up, forward;

    public Plane(Vector right, Vector up, Vector forward) {
        this.up = new Vector(up);
        this.right = new Vector(right);
        this.forward = new Vector(forward);
    }

    /*public Vector getUp() {
        return up;
    }

    public Vector getRight() {
        return right;
    }

    public Vector getForward() {
        return forward;
    }*/

    public Vector map(float dx, float dy) {
        return Vector.add(new Vector(), Vector.times(tempA, up, dy), Vector.times(tempB, right, dx));
    }

    public Vector map(Vector2 v) {
        return map(v.getX(), v.getY());
    }
    
    public Vector2 unmap(Vector v) {
        return new Vector2(
                GMath.dot(v, right),
                GMath.dot(v, up)
        );
    }

    public Vector getNormal() {
        return forward;
    }
    
    @Override
    public String toString() {
        return "Plane{" + "right=" + right + ", up=" + up + ", forward=" + forward + '}';
    }
    
    public static Plane getPlane(Vector forward) {
        Vector right = GMath.cross(forward, new Vector(0, 1, 0)).negate();
        Vector up = GMath.cross(forward, right);
        return new Plane(right, up, forward);
    }
}
