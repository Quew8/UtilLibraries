package com.quew8.gmath;

/**
 * #TODO Remove Debugging Code. And fix in general.
 * @author Quew8
 */
public class Frustum {
    public Plane near, far, left, right, top, bottom;
    public Vector nrt, frt, nrb, frb, nlt, flt, nlb, flb;
    
    public Frustum(Vector p, Vector fowardV, Vector upV, Vector rightV, 
            float leftDist, float rightDist, float bottomDist, float topDist, 
            float nearDist, float farDist) {
        
        float f = farDist / nearDist;
        float topFarDist = f * topDist;
        float bottomFarDist = f * bottomDist;
        float rightFarDist = f * rightDist;
        float leftFarDist = f * leftDist;
        
        Vector nc = p.add(fowardV.times(nearDist));
        Vector fc = p.add(fowardV.times(farDist));
        
        /*Vector*/ nrt = nc.add(rightV.times(rightDist)).add(upV.times(topDist));
        /*Vector*/ frt = fc.add(rightV.times(rightFarDist)).add(upV.times(topFarDist));
        
        /*Vector*/ nrb = nc.add(rightV.times(rightDist)).add(upV.times(bottomDist));
        /*Vector*/ frb = fc.add(rightV.times(rightFarDist)).add(upV.times(bottomFarDist));
        
        /*Vector*/ nlt = nc.add(rightV.times(leftDist)).add(upV.times(topDist));
        /*Vector*/ flt = fc.add(rightV.times(leftFarDist)).add(upV.times(topFarDist));
        
        /*Vector*/ nlb = nc.add(rightV.times(leftDist)).add(upV.times(bottomDist));
        /*//Vector*/ flb = fc.add(rightV.times(leftFarDist)).add(upV.times(bottomFarDist));
        
        this.near = new Plane(nc, fowardV);
        this.far = new Plane(fc, new Vector(fowardV, Vector.NEGATE_BIT));
        Vector leftNormal = GMath.getNormal(nlb, nlt, flt);
        leftNormal.times(GMath.dot(new Vector(nlt, nrt), leftNormal)).normalize();
        this.left = new Plane(nlt, leftNormal);
        Vector rightNormal = GMath.getNormal(nrb, nrt, frt);
        rightNormal.times(GMath.dot(new Vector(nrt, nlt), rightNormal)).normalize();
        this.right = new Plane(frt, rightNormal);
        Vector bottomNormal = GMath.getNormal(nlb, nrb, frb);
        bottomNormal.times(GMath.dot(new Vector(nrb, nrt), bottomNormal)).normalize();
        this.bottom = new Plane(nrb, bottomNormal);
        Vector topNormal = GMath.getNormal(nlt, nrt, frt);
        topNormal.times(GMath.dot(new Vector(nrt, nrb), topNormal)).normalize();
        this.top = new Plane(nrt, topNormal);
    }
    public Frustum(Vector p, Matrix rotation, float leftDist, float rightDist, 
            float bottomDist, float topDist, float nearDist, float farDist) {
        this(p, rotation.getFowardDirection(), rotation.getUpDirection(), 
                rotation.getRightDirection(), leftDist, rightDist, bottomDist,
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
}
