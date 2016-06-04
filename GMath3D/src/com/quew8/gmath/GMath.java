package com.quew8.gmath;

import com.quew8.gmath.constructs.HyperPlane;
import com.quew8.gmath.constructs.Line;
import com.quew8.gmath.constructs.LineHullIntersection;
import com.quew8.gmath.constructs.LineIntersection;
import com.quew8.gmath.constructs.LineIntersection2D;
import com.quew8.gmath.intersection.PenetrationIntersectionCalc;
import com.quew8.gmath.intersection.RaycastSupportCalc;
import com.quew8.gmath.intersection.SupportCalc;
import com.quew8.gmath.intersection.IntersectionCalc;
import com.quew8.gmath.intersection.MotionSupportCalc;
import com.quew8.gmath.intersection.DefSupportCalc;

/**
 * 
 * @author Quew8
 */
public class GMath {
    public static final float PI = (float) Math.PI;
    
    private GMath() {
        
    }
    
    public static float toRadians(float theta) {
        return ( ( theta * PI ) / 180 );
    }
    
    public static float toDegrees(float theta) {
        return ( ( theta * 180 ) / PI );
    }
    
    /**
     * 
     * @param theta
     * @return 
     */
    public static float sin(float theta) {
        return (float)Math.sin(theta);
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static float asin(float f) {
        return (float) Math.asin(f);
    }
    
    /**
     * 
     * @param theta
     * @return 
     */
    public static float cos(float theta) {
        return (float) Math.cos(theta);
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static float acos(float f) {
        return (float) Math.acos(f);
    }
    
    /**
     * 
     * @param theta
     * @return 
     */
    public static float tan(float theta) {
        return (float) Math.tan(theta);
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static float atan(float f) {
        return (float) Math.atan(f);
    }
    
    public static float exp(float t) {
        return (float) Math.exp(t);
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static float square(float f) {
        return f * f;
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static float sqrt(float f) {
        return (float) Math.sqrt(f);
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static float pow(float a, float b) {
        return (float) Math.pow(a, b);
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static float rec(float f) {
        return 1 / f;
    }
    
    public static int sign(float f) {
        return f > 0 ? 1 : f < 0 ? -1 : 0;
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static int makeEvenUp(int i) {
        return i + ( i % 2 );
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static int makeEvenDown(int i) {
        return i - ( i % 2 );
    }

    /**
     * 
     * @param x
     * @return 
     */
    public static int floor(float x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    /**
     * 
     * @param x
     * @return 
     */
    public static int ceil(float x) {
        return x > 0 ? (int) x + 1 : (int) x;
    }
    
    /**
     * 
     * @param x
     * @param min
     * @param max
     * @return 
     */
    public static float clamp(float x, float min, float max) {
        return x <= min ? min : x >= max ? max : x;
    }
    
    /**
     * 
     * @param x
     * @return 
     */
    public static int round(float x) {
        return Math.round(x);
    }
    
    public static float abs(float x) {
        return x < 0 ? -x : x;
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return
     */
    public static float dot(Vector2 a, Vector2 b) {
        return (a.getX()*b.getX())+(a.getY()*b.getY());
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return
     */
    public static float wedge(Vector2 a, Vector2 b) {
        return (a.getX()*b.getY())-(a.getY()*b.getX());
    }
    
    /**
     * 
     * @param a
     * @return
     */
    public static Vector2 perpendicular(Vector2 a) {
        return new Vector2(-a.getY(), a.getX());
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static float dot(Vector a, Vector b) {
        return ((a.getX()*b.getX())+(a.getY()*b.getY())+(a.getZ()*b.getZ()));
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static Vector cross(Vector a, Vector b) {
        return new Vector((a.getY()*b.getZ())-(a.getZ()*b.getY()), (a.getZ()*b.getX())-(a.getX()*b.getZ()), (a.getX()*b.getY())-(a.getY()*b.getX()));
    }
    
    /**
     * 
     * @param a
     * @param b
     * @param c
     * @return 
     */
    public static Vector vectorTripleProduct(Vector a, Vector b, Vector c) {
        //Ax(BxC) = B(A.C) - C(A.B)
        return cross(cross(a, b), c);
    }
    
    /**
     * 
     * @param viewing
     * @param a
     * @param b
     * @param c
     * @return 
     */
    public static boolean isFowardFacing(Matrix viewing, Vector a, Vector b, Vector c) {
        Vector aDash = Matrix.times(new Vector(), viewing, a);
        Vector bDash = Matrix.times(new Vector(), viewing, b);
        Vector cDash = Matrix.times(new Vector(), viewing, c);
        Vector b2a = new Vector(bDash, aDash);
        Vector b2c = new Vector(bDash, cDash);
        return wedge(b2a.getVector2(new Vector2()), b2c.getVector2(new Vector2())) > 0;
    }
    
    /**
     * 
     * @param dx
     * @param dy
     * @param dz
     * @param dw
     * @return 
     */
    public static float lengthSquared(float dx, float dy, float dz, float dw) {
        return square(dx) + square(dy) + square(dz) + square(dw);
    }
    
    /**
     * 
     * @param dx
     * @param dy
     * @param dz
     * @param dw
     * @return 
     */
    public static float length(float dx, float dy, float dz, float dw) {
        return sqrt(lengthSquared(dx, dy, dz, dw));
    }
    
    /**
     * 
     * @param dx
     * @param dy
     * @param dz
     * @return 
     */
    public static float lengthSquared(float dx, float dy, float dz) {
        return square(dx)+square(dy)+square(dz);
    }
    
    /**
     * 
     * @param dx
     * @param dy
     * @param dz
     * @return 
     */
    public static float length(float dx, float dy, float dz) {
        return sqrt(lengthSquared(dx, dy, dz));
    }
    
    /**
     * 
     * @param dx
     * @param dy
     * @return
     */
    public static float lengthSquared(float dx, float dy) {
        return square(dx)+square(dy);
    }
    
    /**
     * 
     * @param dx
     * @param dy
     * @return 
     */
    public static float length(float dx, float dy) {
        return sqrt(lengthSquared(dx, dy));
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return 
     */
    public static Vector getNormal(Vector p1, Vector p2, Vector p3) {
        Vector p2p1 = new Vector(p2, p1);
        Vector p2p3 = new Vector(p2, p3);
        Vector normal = GMath.cross(p2p1, p2p3);
        normal.normalize(normal);
        return normal;
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @param p3
     * @param pointBehind
     * @return 
     */
    public static Vector getNormal(Vector p1, Vector p2, Vector p3, Vector pointBehind) {
        Vector p2p1 = new Vector(p2, p1);
        Vector p2p3 = new Vector(p2, p3);
        Vector normal = GMath.cross(p2p1, p2p3);
        normal.scale(normal, -dot(normal, new Vector(p1, pointBehind)));
        normal.normalize(normal);
        return normal;
    }
    
    /**
     * 
     * @param supportCalc
     * @return 
     */
    public static boolean intersects(SupportCalc supportCalc) {
        IntersectionCalc calc = new IntersectionCalc(supportCalc);
        return calc.intersects();
    }
    
    /**
     * 
     * @param supportCalc
     * @return 
     */
    public static Vector intersectsGetPenetration(SupportCalc supportCalc) {
        PenetrationIntersectionCalc calc = new PenetrationIntersectionCalc(supportCalc);
        return calc.getPenetration();
    }
    
    public static SupportCalc getSupportCalc(Vector[] setA, Vector a, Vector v) {
        return new RaycastSupportCalc(setA, a, v);
    }
    
    
    public static SupportCalc getSupportCalc(Vector[] setA, Vector[] setB) {
        return new DefSupportCalc(setA, setB);
    }
    
    public static SupportCalc getSupportCalc(Vector[] setA, Vector[] setB, Vector v) {
        return new MotionSupportCalc(setA, setB, v);
    }
    
    /**
     * 
     * @param setA
     * @param setB
     * @return 
     */
    public static Vector[] minkowskiAdd(Vector[] setA, Vector[] setB) {
        Vector[] result = new Vector[setA.length * setB.length];
        for(int i = 0, k = 0; i < setA.length; i++) {
            for(int j = 0; j < setB.length; j++, k++) {
                result[k] = Vector.add(new Vector(), setA[i], setB[j]);
            }
        }
        return result;
    }
    
    /**
     * 
     * @param setA
     * @param setB
     * @return 
     */
    public static Vector[] minkowskiSubtract(Vector[] setA, Vector[] setB) {
        Vector[] result = new Vector[setA.length * setB.length];
        for(int i = 0, k = 0; i < setA.length; i++) {
            for(int j = 0; j < setB.length; j++, k++) {
                result[k] = Vector.subtract(new Vector(), setA[i], setB[j]);
            }
        }
        return result;
    }
    
    /*public static Vector[] rotateAbout(Vector da, Vector about, Vector position) {
        Vector relativeAbout = new Vector(position, about);
        Matrix m = Matrix.makeTranslation(relativeAbout);
        m = Matrix.rotateY(new Matrix(), m, da.getY());
        m = Matrix.rotateX(new Matrix(), m, da.getX());
        m = Matrix.rotateZ(new Matrix(), m, da.getZ());
        m = Matrix.translate(new Matrix(), m, new Vector(relativeAbout, Vector.NEGATE_BIT));
        return new Vector[]{
            m.getTranslation(),
            
        }
    }*/
    
    public static LineHullIntersection intersectionLineAABB(Line line, Vector centre, float width, float height, float depth) {
        float fraction0 = Float.MAX_VALUE, fraction1;
        Vector point0 = null, point1;
        if(line.v.getX() != 0) {
            float tx0 = (centre.getX() - (width / 2) - line.a.getX()) / line.v.getX();
            Vector vx0 = Vector.scale(new Vector(), line.v, tx0).add(line.a);
            if(GMath.abs(vx0.getY() - centre.getY()) <= (height / 2)) {
                if(GMath.abs(vx0.getZ() - centre.getZ()) <= (depth / 2)) {
                    point0 = vx0;
                    fraction0 = tx0;
                }
            }
            float tx1 = (centre.getX() + (width / 2) - line.a.getX()) / line.v.getX();
            Vector vx1 = Vector.scale(new Vector(), line.v, tx1).add(line.a);
            if(GMath.abs(vx1.getY() - centre.getY()) <= (height / 2)) {
                if(GMath.abs(vx1.getZ() - centre.getZ()) <= (depth / 2)) {
                    if(point0 != null) {
                        point1 = vx1;
                        fraction1 = tx1;
                        return sort(fraction0, point0, fraction1, point1);
                    } else {
                        point0 = vx1;
                        fraction0 = tx1;
                    }
                }
            }
        }
        if(line.v.getY() != 0) {
            float ty0 = (centre.getY() - (height / 2) - line.a.getY()) / line.v.getY();
            Vector vy0 = Vector.scale(new Vector(), line.v, ty0).add(line.a);
            if(GMath.abs(vy0.getX() - centre.getX()) <= (width / 2)) {
                if(GMath.abs(vy0.getZ() - centre.getZ()) <= (depth / 2)) {
                    if(point0 != null) {
                        point1 = vy0;
                        fraction1 = ty0;
                        return sort(fraction0, point0, fraction1, point1);
                    } else {
                        point0 = vy0;
                        fraction0 = ty0;
                    }
                }
            }
            float ty1 = (centre.getY() + (height / 2) - line.a.getY()) / line.v.getY();
            Vector vy1 = Vector.scale(new Vector(), line.v, ty1).add(line.a);
            if(GMath.abs(vy1.getX() - centre.getX()) <= (width / 2)) {
                if(GMath.abs(vy1.getZ() - centre.getZ()) <= (depth / 2)) {
                    if(point0 != null) {
                        point1 = vy1;
                        fraction1 = ty1;
                        return sort(fraction0, point0, fraction1, point1);
                    } else {
                        point0 = vy1;
                        fraction0 = ty1;
                    }
                }
            }
        }
        if(line.v.getZ() != 0) {
            float tz0 = (centre.getZ() - (depth / 2) - line.a.getZ()) / line.v.getZ();
            Vector vz0 = Vector.scale(new Vector(), line.v, tz0).add(line.a);
            if(GMath.abs(vz0.getY() - centre.getY()) <= (height / 2)) {
                if(GMath.abs(vz0.getX() - centre.getX()) <= (width / 2)) {
                    if(point0 != null) {
                        point1 = vz0;
                        fraction1 = tz0;
                        return sort(fraction0, point0, fraction1, point1);
                    } else {
                        point0 = vz0;
                        fraction0 = tz0;
                    }
                }
            }
            float tz1 = (centre.getZ() + (depth / 2) - line.a.getZ()) / line.v.getZ();
            Vector vz1 = Vector.scale(new Vector(), line.v, tz1).add(line.a);
            if(GMath.abs(vz1.getY() - centre.getY()) <= (height / 2)) {
                if(GMath.abs(vz1.getX() - centre.getX()) <= (width / 2)) {
                    if(point0 != null) {
                        point1 = vz1;
                        fraction1 = tz1;
                        return sort(fraction0, point0, fraction1, point1);
                    } else {
                        point0 = vz1;
                        fraction0 = tz1;
                        return sort(fraction0, point0, fraction0, point0);
                    }
                }
            }
        }
        return null;
    }
    
    private static LineHullIntersection sort(float fraction0, Vector point0, float fraction1, Vector point1) {
        return fraction0 < fraction1 ?
                new LineHullIntersection(point0, point1, fraction0, fraction1) :
                new LineHullIntersection(point1, point0, fraction1, fraction0);
    }
    
    public static LineIntersection intersectionLineSegments(Vector a1, Vector v1, Vector a2, Vector v2) {
        LineIntersection intersect = intersectionLines(a1, v1, a2, v2);
        if(intersect != null) {
            if(intersect.fraction1 >= 0 && intersect.fraction2 <= 1) {
                if(intersect.fraction2 >= 0 && intersect.fraction2 <= 1) {
                    return intersect;
                }
            }
        }
        return null;
    }
    
    public static LineIntersection intersectionLines(Vector a1, Vector v1, Vector a2, Vector v2) {
        if(v1.getX() == 0 && v2.getX() == 0) {
            LineIntersection2D intersection = intersectionLines2D(
                    new Vector2(a1.getY(), a1.getZ()),
                    new Vector2(v1.getY(), v1.getZ()),
                    new Vector2(a2.getY(), a2.getZ()),
                    new Vector2(v2.getY(), v2.getZ())
            );
            if(intersection != null) {
                Vector p = Vector.add(new Vector(), a1, Vector.scale(new Vector(), v1, intersection.fraction1));
                float x = a2.getX() + (v2.getX() * intersection.fraction2);
                if(p.getX() == x) {
                    return new LineIntersection(p, intersection.fraction1, intersection.fraction2);
                }
            }
        } else if(v1.getY() == 0 && v2.getY() == 0) {
            LineIntersection2D intersection = intersectionLines2D(
                    new Vector2(a1.getX(), a1.getZ()),
                    new Vector2(v1.getX(), v1.getZ()),
                    new Vector2(a2.getX(), a2.getZ()),
                    new Vector2(v2.getX(), v2.getZ())
            );
            if(intersection != null) {
                Vector p = Vector.add(new Vector(), a1, Vector.scale(new Vector(), v1, intersection.fraction1));
                float y = a2.getY() + (v2.getY() * intersection.fraction2);
                if(p.getY() == y) {
                    return new LineIntersection(p, intersection.fraction1, intersection.fraction2);
                }
            }
        } else {
            LineIntersection2D intersection = intersectionLines2D(
                    new Vector2(a1.getX(), a1.getY()),
                    new Vector2(v1.getX(), v1.getY()),
                    new Vector2(a2.getX(), a2.getY()),
                    new Vector2(v2.getX(), v2.getY())
            );
            if(intersection != null) {
                Vector p = Vector.add(new Vector(), a1, Vector.scale(new Vector(), v1, intersection.fraction1));
                float z = a2.getZ() + (v2.getZ() * intersection.fraction2);
                if(p.getZ() == z) {
                    return new LineIntersection(p, intersection.fraction1, intersection.fraction2);
                }
            }
        }
        return null;
    }
    
    public static LineIntersection2D intersectionLineSegments2D(Vector2 a1, Vector2 v1, Vector2 a2, Vector2 v2) {
        LineIntersection2D intersection = intersectionLines2D(a1, v1, a2, v2);
        if(intersection.fraction1 >= 0 && intersection.fraction1 <= 1 && 
                intersection.fraction2 >= 0 && intersection.fraction2 <= 1) {
            
            return intersection;
        }
        return null;
    }
    
    public static LineIntersection2D intersectionLines2D(Vector2 a1, Vector2 v1, Vector2 a2, Vector2 v2) {
        if(GMath.abs(GMath.dot(v1, v2)) == 1) {
            return null;
        }
        float denom = (v2.getY() * v1.getX()) - (v2.getX() * v1.getY());
        float t1 = ( (v2.getX() * (a1.getY() - a2.getY()) ) - (v2.getY() * (a1.getX() - a2.getX())) ) / denom;
        float t2 = ( (v1.getX() * (a1.getY() - a2.getY()) ) - (v1.getY() * (a1.getX() - a2.getX())) ) / denom;
        Vector2 intersection = Vector2.add(new Vector2(), a1, Vector2.scale(new Vector2(), v1, t1));
        return new LineIntersection2D(intersection, t1, t2);
    }
    
    public static Line intersectionPlanes(HyperPlane plane1, HyperPlane plane2) {
        return intersectionPlanes(plane1.n, plane1.p, plane2.n, plane2.p);
    }
    
    public static Line intersectionPlanes(Vector n1, Vector a1, Vector n2, Vector a2) {
        Vector v = GMath.cross(n1, n2);
        if(v.isMagnitudeZero()) { //Parallel Planes
            return null;
        }
        float d1 = GMath.dot(n1, a1);
        float d2 = GMath.dot(n2, a2);
        float a = GMath.dot(n1, n1);
        float b = GMath.dot(n2, n2);
        float c = GMath.dot(n1, n2);
        float d = (c * c) - (a * b);
        float c1 = ((d2 * c) - (d1 * b)) / d;
        float c2 = ((d1 * c) - (d2 * a)) / d;
        return new Line(Vector.add(
                new Vector(),
                Vector.scale(new Vector(), n1, c1), 
                Vector.scale(new Vector(), n2, c2)
                ), v);
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static float getMinX(Vector[] va) {
        float f = va[0].getX();
        for(int i = 1; i < va.length; i++) {
            f = Math.min(f, va[i].getX());
        }
        return f;
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static float getMinY(Vector[] va) {
        float f = va[0].getY();
        for(int i = 1; i < va.length; i++) {
            f = Math.min(f, va[i].getY());
        }
        return f;
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static float getMinZ(Vector[] va) {
        float f = va[0].getZ();
        for(int i = 1; i < va.length; i++) {
            f = Math.min(f, va[i].getZ());
        }
        return f;
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static float getMaxX(Vector[] va) {
        float f = va[0].getX();
        for(int i = 1; i < va.length; i++) {
            f = Math.max(f, va[i].getX());
        }
        return f;
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static float getMaxY(Vector[] va) {
        float f = va[0].getY();
        for(int i = 1; i < va.length; i++) {
            f = Math.max(f, va[i].getY());
        }
        return f;
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static float getMaxZ(Vector[] va) {
        float f = va[0].getZ();
        for(int i = 1; i < va.length; i++) {
            f = Math.max(f, va[i].getZ());
        }
        return f;
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static Vector getMinPoint(Vector[] va) {
        float x = va[0].getX();
        float y = va[0].getY();
        float z = va[0].getZ();
        for(int i = 1; i < va.length; i++) {
            x = Math.min(x, va[i].getX());
            y = Math.min(y, va[i].getY());
            z = Math.min(z, va[i].getZ());
        }
        return new Vector(x, y, z);
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static Vector getMaxPoint(Vector[] va) {
        float x = va[0].getX();
        float y = va[0].getY();
        float z = va[0].getZ();
        for(int i = 1; i < va.length; i++) {
            x = Math.max(x, va[i].getX());
            y = Math.max(y, va[i].getY());
            z = Math.max(z, va[i].getZ());
        }
        return new Vector(x, y, z);
    }
    
    /**
     * 
     * @param va
     * @return 
     */
    public static Vector getCentrePoint(Vector[] va) {
        Vector min = getMinPoint(va);
        Vector max = getMaxPoint(va);
        return new Vector(((min.getX()+max.getX())/2), ((min.getY()+max.getY())/2), ((min.getZ()+max.getZ())/2));
    }
}

