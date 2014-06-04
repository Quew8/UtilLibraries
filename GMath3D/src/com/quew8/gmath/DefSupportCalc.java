package com.quew8.gmath;

import static com.quew8.gmath.GMath.dot;

/**
 *
 * @author Quew8
 */
public class DefSupportCalc implements SupportCalc {
    final Vector[] setA;
    final Vector[] setB;

    public DefSupportCalc(Vector[] setA, Vector[] setB) {
        this.setA = setA;
        this.setB = setB;
        /*Vector[] minkDiff = GMath.minkowskiSubtract(setA, setB);
        for(int i = 0; i < minkDiff.length; i++) {
            System.out.println(minkDiff[i].toString());
        }*/
    }

    @Override
    public Vector getSupporting(Vector inDir) {
        int ia = 0, ib = 0;
        float f = dot(inDir, setA[0]);
        for(int i = 1; i < setA.length; i++) {
            float g = dot(inDir, setA[i]);
            if(g > f) {
                f = g;
                ia = i;
            }
        }
        Vector result = new Vector(setA[ia]);
        Vector minusDirection = new Vector(inDir, Vector.NEGATE_BIT);
        f = dot(minusDirection, setB[0]);
        for(int i = 1; i < setB.length; i++) {
            float g = dot(minusDirection, setB[i]);
            if(g > f) {
                f = g;
                ib = i;
            }
        }
        result.subtract(setB[ib]);
        return result;
    }
}
