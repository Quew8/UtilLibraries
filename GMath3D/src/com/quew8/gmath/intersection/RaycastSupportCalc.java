package com.quew8.gmath.intersection;

import com.quew8.gmath.Vector;
import static com.quew8.gmath.GMath.dot;

/**
 *
 * @author Quew8
 */
public class RaycastSupportCalc implements SupportCalc {
    private final Vector[] set;
    private final Vector a, b;
    
    public RaycastSupportCalc(Vector[] set, Vector a, Vector v) {
        this.set = set;
        this.a = a;
        this.b = v.add(new Vector(), a);
        /*for(Vector c: set) {
            System.out.println("Set " + c.toString());
        }
        System.out.println("a = " + a.toString());
        System.out.println("b = " + b.toString());
        Vector[] vs = GMath.minkowskiSubtract(set, new Vector[]{a, b});
        for(Vector c: vs) {
            System.out.println("(-)" + c.toString());
        }*/
    }
    
    @Override
    public Vector getSupporting(Vector inDir) {
        int ia = 0;
        float f = dot(inDir, set[0]);
        for(int i = 1; i < set.length; i++) {
            float g = dot(inDir, set[i]);
            if(g > f) {
                f = g;
                ia = i;
            }
        }
        Vector result = new Vector().setXYZ(set[ia]);
        Vector minusDirection = inDir.negate(new Vector());
        if(dot(minusDirection, a) > dot(minusDirection, b)) {
            result.subtract(result, a);
        } else {
            result.subtract(result, b);
        }
        return result;
    }
    
}
