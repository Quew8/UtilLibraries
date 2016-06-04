package com.quew8.gmath.intersection;

import com.quew8.gmath.Vector;
import static com.quew8.gmath.GMath.dot;

/**
 *
 * @author Quew8
 */
public class MotionSupportCalc extends DefSupportCalc {
    final Vector v;

    public MotionSupportCalc(Vector[] setA, Vector[] setB, Vector v) {
        super(setA, setB);
        this.v = v;
    }

    @Override
    public Vector getSupporting(Vector inDir) {
        Vector result = super.getSupporting(inDir);
        if(dot(inDir, v) > 0) {
            result.add(result, v);
        }
        return result;
    }
}
