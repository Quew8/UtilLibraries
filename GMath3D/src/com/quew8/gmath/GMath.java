package com.quew8.gmath;

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
    
    /**
     * 
     * @param lod
     * @return 
     */
    public static float[][][] makeSphereLatLongData(int lod) {
        int lvls = ( lod / 2 ) + 1;
        float[][] data2D = makeCircleDataLong(lod);
        float[][][] data = new float[lvls][lod][];
        float inc = (2 * PI) / lod;
        float phi = inc;
        data[0] = new float[][]{new float[]{0, 1, 0}};
        for(int i = 1; i < lvls-1; i++, phi += inc) {
            for(int j = 0; j < lod; j++) {
                data[i][j] = new float[]{phi, data2D[j][0]};
            }
        }
        data[lvls-1] = new float[][]{new float[]{0, -1, 0}};
        return data;
    }
    
    /**
     * 
     * @param lod
     * @return 
     */
    public static float[][][] makeSphereData(int lod) {
        int lvls = ( lod / 2 ) + 1;
        float[][] data2D = makeCircleData(lod);
        float[][][] data = new float[lvls][lod][];
        float inc = (2 * PI) / lod;
        float phi = inc;
        data[0] = new float[][]{new float[]{0, 1, 0}};
        for(int i = 1; i < lvls-1; i++, phi += inc) {
            float sinphi = sin(phi);
            float cosphi = cos(phi);
            for(int j = 0; j < lod; j++) {
                data[i][j] = new float[]{data2D[j][0] * sinphi, cosphi, data2D[j][1] * sinphi};
            }
        }
        data[lvls-1] = new float[][]{new float[]{0, -1, 0}};
        return data;
    }
    
    /**
     * 
     * @param lod
     * @return 
     */
    public static float[][] makeCircleDataLong(int lod) {
        float[][] data = new float[lod][];
        float inc = (2 * PI) / lod;
        float theta = 0;
        for(int i = 0; i < lod; i++, theta += inc) {
            data[i] = new float[]{theta};
        }
        return data;
    }
    
    /**
     * 
     * @param lod
     * @return 
     */
    public static float[][] makeCircleData(int lod) {
        float[][] data = new float[lod][];
        float inc = (2 * PI) / lod;
        float theta = 0;
        float sintheta, costheta;
        for(int i = 0; i < lod; i++, theta += inc) {
            sintheta = GMath.sin(theta);
            costheta = GMath.cos(theta);
            data[i] = new float[]{costheta, sintheta};
        }
        return data;
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
        Vector b2 = viewing.times(b);
        Vector b2a2 = new Vector(b2, viewing.times(a));
        Vector b2c2 = new Vector(b2, viewing.times(c));
        return wedge(new Vector2(b2a2), new Vector2(b2c2)) > 0;
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
        Vector p2p1 = new Vector(p2, p1, Vector.NORMALIZE_BIT);
        Vector p2p3 = new Vector(p2, p3, Vector.NORMALIZE_BIT);
        return GMath.cross(p2p1, p2p3);
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
        Vector p2p1 = new Vector(p2, p1, Vector.NORMALIZE_BIT);
        Vector p2p3 = new Vector(p2, p3, Vector.NORMALIZE_BIT);
        Vector normal = GMath.cross(p2p1, p2p3);
        normal.times(-dot(normal, new Vector(p1, pointBehind)));
        normal.normalize();
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
    
    /*public static boolean isPointOnSurface(Vector point, Vector[] vertices) {
        Vector pv1 = new Vector(point, vertices[0], Vector.NORMALIZE_BIT);
        Vector intermediate1 = new Vector(point, vertices[1], Vector.NORMALIZE_BIT);
        Vector intermediate2 = new Vector(point, vertices[2], Vector.NORMALIZE_BIT);
        float theta = GMath.acos(GMath.dot(pv1, intermediate1));
        theta += GMath.acos(GMath.dot(intermediate1, intermediate2));
        for(int i = 3; i < vertices.length-1; i++) {
            intermediate1 = new Vector(point, vertices[i], Vector.NORMALIZE_BIT);
            theta += GMath.acos(GMath.dot(intermediate2, intermediate1));
            i++;
            if(i == vertices.length) {break;}
            intermediate2 = new Vector(point, vertices[i], Vector.NORMALIZE_BIT);
            theta += GMath.acos(GMath.dot(intermediate1, intermediate2));
        }
        if(vertices.length % 2 == 0) {
            theta += GMath.acos(GMath.dot(intermediate1, pv1));
        } else {
            theta += GMath.acos(GMath.dot(intermediate2, pv1));
        }
        if(theta >= 350 && theta <= 370) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isPointOnSurface(Vector point, Vector v1, Vector v2, Vector v3) {
        Vector pv1 = new Vector(point, v1, Vector.NORMALIZE_BIT);
        Vector pv2 = new Vector(point, v2, Vector.NORMALIZE_BIT);
        Vector pv3 = new Vector(point, v3, Vector.NORMALIZE_BIT);
        float theta = GMath.acos(GMath.dot(pv1, pv2));
        theta += GMath.acos(GMath.dot(pv2, pv3));
        theta += GMath.acos(GMath.dot(pv3, pv1));
        return (theta >= 350 && theta <= 370);
    }
    
    public static boolean isPointOnSurface(Vector point, Vector v1, Vector v2, Vector v3, Vector v4) {
        Vector pv1 = new Vector(point, v1, Vector.NORMALIZE_BIT);
        Vector intermediate = new Vector(point, v2, Vector.NORMALIZE_BIT);
        Vector intermediate2 = new Vector(point, v3, Vector.NORMALIZE_BIT);
        float theta = GMath.acos(GMath.dot(pv1, intermediate));
        theta += GMath.acos(GMath.dot(intermediate, intermediate2));
        intermediate = new Vector(point, v4, Vector.NORMALIZE_BIT);
        theta += GMath.acos(GMath.dot(intermediate2, intermediate));
        theta += GMath.acos(GMath.dot(intermediate, pv1));
        return (theta >= 350 && theta <= 370);
    }
    
    public static Vector getCollisionPointOfHyperplane(Vector[] as, Vector v, Vector p, Vector n) {
        float magnitudeV = v.magnitude();
        Vector nn = new Vector(n);
        nn.normalize();
        Vector nv = new Vector(v);
        nv.normalize();
        float g = GMath.dot(nn, nv);
        if(g == 0) {
            return null;
        }
        Vector intersection = null;
        checkAll:
            for(Vector a: as) {
                Vector ap = new Vector(a, p);
                float t = (GMath.dot(nn, ap)) / g;
                if(t < magnitudeV && t > 0) {
                    intersection = new Vector(a).add(new Vector(nv).times(t));
                    break checkAll;
                }
            }
        return intersection;
    }
    
    public static Vector intersectPoint(Vector p, Vector n, Vector a, Vector v) {
        float magnitudeV = v.magnitude();
        float t = intersectValue(p, n, a, v);
        if(t >= magnitudeV || t < 0) {
            return null;
        }
        return new Vector(a).add(new Vector(v).times(t));
    }
    public static float intersectValue(Vector p, Vector n, Vector a, Vector v) {
        Vector ap = new Vector(a, p);
        float g = GMath.dot(n, v);
        if(g == 0) {
            return Float.NaN;
        }
        return (GMath.dot(n, ap)) / g;
    }*/
    
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

