package com.quew8.gmath.constructs;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector;

public class HyperPlane {
    public Vector p;
    public Vector n;
    
    public HyperPlane(Vector p, Vector n) {
        this.p = p;
        this.n = n;
    }
    
    public void translate(Vector dv) {
        p.add(p, dv);
    }
    
    public float getDistanceToPoint(Vector a) {
        return GMath.dot(n, new Vector(p, a));
    }
    
    public float getDistanceAlongLine(Vector a, Vector v) {
        return GMath.dot(n, new Vector(p, a)) / GMath.dot(n, v);
    }
}
