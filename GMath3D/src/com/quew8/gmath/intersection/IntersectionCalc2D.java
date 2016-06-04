package com.quew8.gmath.intersection;

import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import static com.quew8.gmath.GMath.*;

/**
 *
 * @author Quew8
 */
public final class IntersectionCalc2D {
    public Vector2[] shape1, shape2;
    public Vector2 direction;
    public Vector2 a, b, c;
    public int size = 1;
    public int loopCount = 0;
    
    public IntersectionCalc2D(Vector2[] shape1, Vector2[] shape2) {
        this.shape1 = shape1;
        this.shape2 = shape2;
        this.direction = new Vector2(1, 1);
    }

    public boolean intersects() {
        a = getSupporting(direction);
        direction = a.negate(new Vector2());
        loopCount = 0;
        while(true) {
            loopCount++;
            if(loopCount > 200) {
                return false;
            } 
            //System.out.println();
            //System.out.println("Process Loop " + loopCount);
            //System.out.println("Direction = " + direction);
            switch(size) { //Add a new point
                case 2: c = b;//A must always be most recently added.
                case 1: b = a;
            }
            a = getSupporting(direction); size++;
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

    /*public int intersectLoop() {
        loopCount++;
        if(loopCount > 200) {
            return 0;
        }
        switch(size) { //Add a new point
            case 2: { c = b; } //A must always be most recently added.
            case 1: { b = a; }
        }
        a = getSupporting(direction); size++;
        if(!isInDirection(direction, a)) { //Same as is AO(-A) pointing away from direction.
            return 0; //Since A and B are on the same side of O.
        }
        if(process()) {
            return 1;
        }
        return -1;
    }*/

    private boolean process() {
        Vector2 ao = a.negate(new Vector2());
        Vector2 ab = new Vector2(a, b);
        Vector2 ac;
        switch(size) {
            case 2: {
                return lineCase(ao, ab);
            }
            case 3: {
                ac = new Vector2(a, c);
                return triangleCase(ao, ab, ac);
            }
        }
        return false;
    }

    private boolean pointCase(Vector2 ao) {
        if(ao.hasMagnitudeZero()) {
            return onIntersection0Simplex(a);
        }
        direction = ao;
        return false;
    }

    private boolean lineCase(Vector2 ao, Vector2 ab) {
        if(isInDirection(ab, ao)) {
            direction = new Vector2(-ab.getY(), ab.getX());
            if(direction.hasMagnitudeZero()) {
                return onIntersection1Simplex(a, b);
            }
            direction.scale(direction, dot(ao, direction));
        } else {
            size = 1;
            pointCase(ao); 
        }
        return false;
    }

    private boolean triangleCase(Vector2 ao, Vector2 ab, Vector2 ac) {
        Vector2 axb = new Vector2(-ab.getY(), ab.getX());
        axb.scale(axb, -dot(axb, ac));
        Vector2 axc = new Vector2(-ac.getY(), ac.getX());
        axc.scale(axb, -dot(axc, ab));
        if(isInDirection(axc, ao)) {
            b = c;
            size = 2;
            lineCase(ao, ac);
        } else if(isInDirection(axb, ao)) {
            size = 2;
            lineCase(ao, ab);
        } else {
            return true;
        }
        return false;
    }
    
    public Vector2 getSupporting(Vector2 inDir) {
        int ia = 0, ib = 0;
        float f = dot(inDir, shape1[0]);
        for(int i = 1; i < shape1.length; i++) {
            float g = dot(inDir, shape1[i]);
            if(g > f) {
                f = g;
                ia = i;
            }
        }
        Vector2 result = new Vector2().setXY(shape1[ia]);
        Vector2 minusDirection = inDir.negate(new Vector2());
        f = dot(minusDirection, shape2[0]);
        for(int i = 1; i < shape2.length; i++) {
            float g = dot(minusDirection, shape2[i]);
            if(g > f) {
                f = g;
                ib = i;
            }
        }
        result.subtract(result, shape2[ib]);
        return result;
    }
    
    public boolean onIntersection0Simplex(Vector2 a) {
        return true;
    }

    public boolean onIntersection1Simplex(Vector2 a, Vector2 b) {
        return true;
    }

    protected boolean isInDirection(Vector2 a, Vector2 b) {
        return dot(a, b) > 0;
    }
}
