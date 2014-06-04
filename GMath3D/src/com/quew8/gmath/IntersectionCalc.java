package com.quew8.gmath;

import static com.quew8.gmath.GMath.*;

/**
 *
 * @author Quew8
 */
public class IntersectionCalc {
    private final SupportCalc supportCalc;
    protected Vector direction;
    protected Vector a, b, c, d;
    protected int size = 1;

    public IntersectionCalc(SupportCalc supportCalc) {
        this.supportCalc = supportCalc;
        this.direction = new Vector(1, 1, 1);
    }

    public boolean intersects() {
        a = supportCalc.getSupporting(direction);
        direction = new Vector(a, Vector.NEGATE_BIT); //Towards the origin
        int loopCount = 0;
        while(true) {
            loopCount++;
            if(loopCount > 200) {
                return false;
            } 
            //System.out.println();
            //System.out.println("Process Loop " + loopCount);
            //System.out.println("Direction = " + direction);
            switch(size) { //Add a new point
                case 3: { d = c; } //A must always be most recently added.
                case 2: { c = b; }
                case 1: { b = a; }
            }
            a = supportCalc.getSupporting(direction); size++;
            //System.out.println("Simplex: Size: " + size + " ---------");
            switch(size) {
                case 4: //System.out.println("    d = " + d.toString());
                case 3: //System.out.println("    c = " + c.toString());
                case 2: //System.out.println("    b = " + b.toString());
                case 1: //System.out.println("    a = " + a.toString());
            }
            //System.out.println();
            if(!isInDirection(direction, a)) { //Same as is AO(-A) pointing away from direction.
                //System.out.println("Unreachable");
                return false; //Since A and B are on the same side of O.
            }
            if(process()) {
                return true;
            } /*else if(direction.hasMagnitudeZero()) {
                System.out.println("Just Touching");
                return false;
            }*/
        }
    }

    private boolean process() {
        Vector ao = new Vector(a, Vector.NEGATE_BIT);
        Vector ab = new Vector(a, b);
        Vector ac, ad;
        switch(size) {
            case 2: {
                return lineCase(ao, ab);
            }
            case 3: {
                ac = new Vector(a, c);
                Vector abc = cross(ab, ac);
                abc = abc.times(dot(abc, ao));
                return triangleCase(ao, ab, ac, abc);
            }
            case 4: {
                ac = new Vector(a, c);
                ad = new Vector(a, d);
                return tetrahedronCase(ao, ab, ac, ad);
            }
        }
        return false;
    }

    private boolean pointCase(Vector ao) {
        //System.out.println("Point Case");
        if(ao.hasMagnitudeZero()) {
            return onIntersection0Simplex(a);
        }
        direction = ao;
        return false;
    }

    private boolean lineCase(Vector ao, Vector ab) {
        //System.out.println("Line Case");
        if(isInDirection(ab, ao)) {
            direction = vectorTripleProduct(ab, ao, ab);
            if(direction.hasMagnitudeZero()) {
                return onIntersection1Simplex(a, b);
            }
        } else {
            size = 1;
            pointCase(ao); 
        }
        return false;
    }

    private boolean triangleCase(Vector ao, Vector ab, Vector ac, Vector abc) {
        //System.out.println("Triangle Case");
        if(abc.hasMagnitudeZero()) {
            return onIntersection2Simplex(a, b, c);
        }
        //System.out.println("abc = " + abc);
        Vector abcac = cross(abc, ac); abcac.times(-dot(abcac, ab));
        Vector ababc = cross(ab, abc); ababc.times(-dot(ababc, ac));
        if(isInDirection(abcac, ao)) {
            //System.out.println("abc x ac");
            b = c;
            size = 2;
            lineCase(ao, ac);
        } else if(isInDirection(ababc, ao)) {
            //System.out.println("ab x abc");
            size = 2;
            lineCase(ao, ab);
        } else {
            //System.out.println("Neither");
            direction = abc;
        }
        return false;
    }

    private boolean tetrahedronCase(Vector ao, Vector ab, Vector ac, Vector ad) {
        //System.out.println("Tetrahedron Case");
        Vector abc = cross(ab, ac); abc.times(-dot(abc, ad));
        Vector abd = cross(ab, ad); abd.times(-dot(abd, ac));
        Vector acd = cross(ac, ad); acd.times(-dot(acd, ab));
        if(isInDirection(abc, ao)) {
            //System.out.println("abc");
            size = 3;
            triangleCase(ao, ab, ac, abc);
        } else if(isInDirection(abd, ao)) {
            //System.out.println("abd");
            c = b;
            b = d;
            size = 3;
            triangleCase(ao, ab, ad, abd);
        } else if(isInDirection(acd, ao)) {
            //System.out.println("acd");
            b = c;
            c = d;
            size = 3;
            triangleCase(ao, ac, ad, acd);
        } else {
            //System.out.println("None Of The Above");
            return onIntersection3Simplex(a, b, c, d, abc, abd, acd);
        }
        return false;
    }
    
    public Vector getSupporting(Vector inDir) {
        return supportCalc.getSupporting(inDir);
    }
    
    public boolean onIntersection0Simplex(Vector a) {
        return true;
    }

    public boolean onIntersection1Simplex(Vector a, Vector b) {
        return true;
    }

    public boolean onIntersection2Simplex(Vector a, Vector b, Vector c) {
        return true;
    }

    public boolean onIntersection3Simplex(Vector a, Vector b, Vector c, Vector d, Vector abcN, Vector abdN, Vector acdN) {
        return true;
    }

    protected boolean isInDirection(Vector a, Vector b) {
        return dot(a, b) > 0;
    }
}
