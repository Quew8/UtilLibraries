package com.quew8.geng3d.geometry;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;

/**
 *
 * @author Quew8
 */
public class Plane {
    private static final Vector UP_DIR = new Vector(0, 1, 0);
    public static final Plane 
            NEG_X = Plane.getPlane(new Vector(-1, 0, 0)),
            POS_X = Plane.getPlane(new Vector(1, 0, 0)),
            POS_Y = new Plane(new Vector(-1, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0)),
            NEG_Y = new Plane(new Vector(-1, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0)),
            NEG_Z = Plane.getPlane(new Vector(0, 0, -1)),
            POS_Z = Plane.getPlane(new Vector(0, 0, 1));
    public static Plane BILLBOARD_PLANE = NEG_Z;
    private final Vector tempA = new Vector(), tempB = new Vector();
    private final Vector right, up, forward;

    public Plane(Vector right, Vector up, Vector forward) {
        this.up = new Vector().setXYZ(up);
        this.right = new Vector().setXYZ(right);
        this.forward = new Vector().setXYZ(forward);
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
        Vector a = up.scale(new Vector(), dy);
        right.scale(tempA, dx);
        return a.add(a, tempA);
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
        float dotUp = GMath.dot(forward, new Vector(0, 1, 0));
        if(dotUp == 1) {
            return Plane.POS_Y;
        } else if(dotUp == -1) {
            return Plane.NEG_Y;
        }
        Vector right = GMath.cross(forward, UP_DIR);
        right.negate(right);
        Vector up = GMath.cross(forward, right);
        return new Plane(right, up, forward);
    }
}
