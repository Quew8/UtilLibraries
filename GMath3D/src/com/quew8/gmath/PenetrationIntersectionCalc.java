package com.quew8.gmath;

import static com.quew8.gmath.GMath.cross;
import static com.quew8.gmath.GMath.dot;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class PenetrationIntersectionCalc extends IntersectionCalc {
    public static final float TOLERANCE = 1f;
    private final ArrayList<Face> faces = new ArrayList<Face>();
    Vector penetrationVector = null;

    public PenetrationIntersectionCalc(SupportCalc supportCalc) {
        super(supportCalc);
    }

    public Vector getPenetration() {
        intersects();
        return penetrationVector;
    }

    @Override
    public boolean onIntersection0Simplex(Vector a) {
        //System.out.println("Intersection: Simplex0");
        return false;
    }

    @Override
    public boolean onIntersection1Simplex(Vector a, Vector b) {
        //System.out.println("Intersection: Simplex1");
        Vector p = getSupporting(new Vector(0, 1, 0));
        if(a.equals(p) || b.equals(p)) {
            p = getSupporting(new Vector(0, -1, 0));
        }
        return onIntersection2Simplex(a, b, p);
    }

    @Override
    public boolean onIntersection2Simplex(Vector a, Vector b, Vector c) {
        //System.out.println("Intersection: Simplex2");
        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        Vector abc = cross(ab, ac);
        d = getSupporting(abc);
        if(d.equals(a) || d.equals(b) || d.equals(c)) {
            d = getSupporting(new Vector(abc, Vector.NEGATE_BIT));
        } else {
            abc.times(-1);
        }
        //System.out.println("D = " + d);
        Vector ad = new Vector(a, d);
        Vector abd = cross(ab, ad);
        //System.out.println("ABD = " + abd);
        abd.times(GMath.dot(abd, ac));
        //System.out.println("ABD = " + abd);
        Vector acd = cross(ac, ad);
        //System.out.println("ACD = " + acd);
        abd.times(GMath.dot(acd, ab));
        //System.out.println("ACD = " + acd);
        
        return onIntersection3Simplex(a, b, c, d, abc, abd, acd);
    }

    @Override
    public boolean onIntersection3Simplex(Vector a, Vector b, Vector c, Vector d, Vector abcN, Vector abdN, Vector acdN) {
        //System.out.println("Intersection: Simplex3");
        abcN.normalize();
        //System.out.println("ABD = " + abdN);
        //System.out.println("|ABD| = " + abdN.magnitude());
        abdN.normalize();
        //System.out.println("ABD = " + abdN);
        acdN.normalize();
        iterateFaces(new Face[]{
            new Face(abcN, a, b, c),
            new Face(abdN, a, b, d),
            new Face(acdN, a, c, d),
            new Face(b, c, d)
        });
        return true;
    }

    protected void iterateFaces(Face[] inFaces) {
        //System.out.println("\nIn Faces");
        for(int i = 0; i < inFaces.length; i++) {
            //System.out.println(inFaces[i].toString());
        }
        //System.out.println("\n");
        this.faces.addAll(Arrays.asList(inFaces));
        int loopCounter = 0;
        int prevNearIndex = -1;
        float prevNearDistance = -Float.MAX_VALUE;
        while(true) {
            if(loopCounter++ > 15) { 
                //System.out.println("Too Many Loops"); return;
            }
            //System.out.println("--------------------");
            int nearIndex = getNearestFaceIndex();
            Face nearFace = faces.get(getNearestFaceIndex());
            float nearDistance = nearFace.distance;
            //System.out.println("Index " + nearIndex + " "+ nearFace.toString());
            if(nearDistance <= prevNearDistance + TOLERANCE) {
                penetrationVector = Vector.times(new Vector(), nearFace.normal, nearFace.distance);
                return;
            } else {
                Vector p = getSupporting(nearFace.normal);
                //System.out.println("p: " + p.toString());
                faces.set(nearIndex, new Face(nearFace.vertex1, nearFace.vertex2, p));
                faces.add(new Face(nearFace.vertex2, nearFace.vertex3, p));
                faces.add(new Face(nearFace.vertex3, nearFace.vertex1, p));
                prevNearIndex = nearIndex;
                prevNearDistance = nearDistance;
            }
        }
    }

    protected int getNearestFaceIndex() {
        float nearDist = faces.get(0).distance;
        int nearIndex = 0;
        for(int i = 1; i < faces.size(); i++) {
            //System.out.println("Nearest: " + nearDist + " " + i + " " + faces.get(i).distance);
            if(faces.get(i).distance < nearDist) {
                nearDist = faces.get(i).distance;
                nearIndex = i;
            }
        }
        return nearIndex;
    }
    
    /**
     * 
     */
    public class Face {
        private final Vector normal;
        private final float distance;
        private final Vector vertex1;
        private final Vector vertex2;
        private final Vector vertex3;
        
        private Face(Vector normal, Vector vertex1, Vector vertex2, Vector vertex3) {
            this.normal = new Vector(normal, Vector.NORMALIZE_BIT);
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.vertex3 = vertex3;
            this.distance = dot(normal, vertex1);
        }
        
        private Face(Vector vertex1, Vector vertex2, Vector vertex3) {
            this.normal = cross(new Vector(vertex1, vertex2), new Vector(vertex1, vertex3));
            normal.times(dot(normal, vertex1));
            this.normal.normalize();
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.vertex3 = vertex3;
            this.distance = dot(normal, vertex1);
        }
        
        @Override
        public String toString() {
            return 
                    "Face: \n"
                    + "Normal: " + normal.toString() + "\n"
                    + "A: " + vertex1.toString() + "\n"
                    + "B: " + vertex2.toString() + "\n"
                    + "C: " + vertex3.toString() + "\n"
                    + "Dist: " + distance;
        }
    }
}
