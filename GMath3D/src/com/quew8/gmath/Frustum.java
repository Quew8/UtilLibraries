package com.quew8.gmath;

import com.quew8.gmath.constructs.HyperPlane;

/**
 * #TODO Remove Debugging Code. And fix in general.
 * @author Quew8
 */
public class Frustum {
    public HyperPlane near, far, left, right, top, bottom;
    public Vector nrt, frt, nrb, frb, nlt, flt, nlb, flb;
    
    public Frustum(Vector p, Vector fowardV, Vector upV, Vector rightV, 
            float leftDist, float rightDist, float bottomDist, float topDist, 
            float nearDist, float farDist) {
        
        float f = farDist / nearDist;
        float topFarDist = f * topDist;
        float bottomFarDist = f * bottomDist;
        float rightFarDist = f * rightDist;
        float leftFarDist = f * leftDist;
        
        Vector nc = p.add(new Vector(), fowardV.scale(new Vector(), nearDist));
        Vector fc = p.add(new Vector(), fowardV.scale(new Vector(), farDist));
        
        nrt = getVector(rightV, rightDist, upV, topDist);
        nrt.add(nrt, nc);
        frt = getVector(rightV, rightFarDist, upV, topFarDist);
        frt.add(frt, fc);
        
        nrb = getVector(rightV, rightDist, upV, bottomDist);
        nrb.add(nrb, nc);
        frb = getVector(rightV, rightFarDist, upV, bottomFarDist);
        frb.add(frb, fc);
        
        nlt = getVector(rightV, leftDist, upV, topDist);
        nlt.add(nlt, nc);
        flt = getVector(rightV, leftFarDist, upV, topFarDist);
        flt.add(flt, fc);
        
        nlb = getVector(rightV, leftDist, upV, bottomDist);
        nlb.add(nlb, nc);
        flb = getVector(rightV, leftFarDist, upV, bottomFarDist);
        flb.add(flb, fc);
        
        this.near = new HyperPlane(nc, fowardV);
        this.far = new HyperPlane(fc, fowardV.negate(new Vector()));
        
        Vector leftNormal = GMath.getNormal(nlb, nlt, flt);
        leftNormal.scale(leftNormal, GMath.dot(new Vector(nlt, nrt), leftNormal));
        leftNormal.normalize(leftNormal);
        this.left = new HyperPlane(nlt, leftNormal);
        Vector rightNormal = GMath.getNormal(nrb, nrt, frt);
        rightNormal.scale(rightNormal, GMath.dot(new Vector(nrt, nlt), rightNormal));
        rightNormal.normalize(rightNormal);
        this.right = new HyperPlane(frt, rightNormal);
        Vector bottomNormal = GMath.getNormal(nlb, nrb, frb);
        bottomNormal.scale(bottomNormal, GMath.dot(new Vector(nrb, nrt), bottomNormal));
        bottomNormal.normalize(bottomNormal);
        this.bottom = new HyperPlane(nrb, bottomNormal);
        Vector topNormal = GMath.getNormal(nlt, nrt, frt);
        topNormal.scale(topNormal, GMath.dot(new Vector(nrt, nrb), topNormal));
        topNormal.normalize(topNormal);
        this.top = new HyperPlane(nrt, topNormal);
    }
    
    public Frustum(Vector p, Matrix rotation, float leftDist, float rightDist, 
            float bottomDist, float topDist, float nearDist, float farDist) {
        
        this(p, rotation.getForwardDirection(new Vector()), rotation.getUpDirection(new Vector()), 
                rotation.getRightDirection(new Vector()), leftDist, rightDist, bottomDist,
                topDist, nearDist, farDist);
    }
    
    public boolean isInside(Vector p) {
        /*System.out.println("Near " + near.p.toString() + " " + near.n.toString() + " " + near.getDistanceToPoint(p));
        System.out.println("Far " + far.p.toString() + " " + far.n.toString() + " " + far.getDistanceToPoint(p));
        System.out.println("Left " + left.p.toString() + " " + left.n.toString() + " " + left.getDistanceToPoint(p));
        System.out.println("Right " + right.p.toString() + " " + right.n.toString() + " " + right.getDistanceToPoint(p));
        System.out.println("Bottom " + bottom.p.toString() + " " + bottom.n.toString() + " " + bottom.getDistanceToPoint(p));
        System.out.println("Top " + top.p.toString() + " " + top.n.toString() + " " + top.getDistanceToPoint(p));*/
        if(near.getDistanceToPoint(p) < 0) { return false; }
        if(far.getDistanceToPoint(p) < 0) { return false; }
        if(left.getDistanceToPoint(p) < 0) { return false; }
        if(right.getDistanceToPoint(p) < 0) { return false; }
        if(bottom.getDistanceToPoint(p) < 0) { return false; }
        return top.getDistanceToPoint(p) >= 0;
    }
    
    public void translate(Vector dv) {
        near.translate(dv);
        far.translate(dv);
        left.translate(dv);
        right.translate(dv);
        bottom.translate(dv);
        top.translate(dv);
    }
    
    private static Vector getVector(Vector dir1, float dist1, Vector dir2, float dist2) {
        Vector a = new Vector().setXYZ(dir1);
        a.scale(a, dist1);
        Vector b = new Vector().setXYZ(dir2);
        b.scale(b, dist2);
        return a.add(a, b);
    }
}
