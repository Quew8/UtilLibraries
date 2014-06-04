package com.quew8.gmath;

public class Plane {
    public Vector p;
    public Vector n;
    public Plane(Vector p, Vector n) {
        this.p = p;
        this.n = n;
    }
    public void translate(Vector dv) {
        p = p.add(dv);
    }
    public float getDistanceToPoint(Vector a) {
        return GMath.dot(n, new Vector(p, a));
    }
    public float getDistanceAlongLine(Vector a, Vector v) {
        return GMath.dot(n, new Vector(p, a)) / GMath.dot(n, v);
    }
}
