package com.quew8.gmath.constructs;

import com.quew8.gmath.Vector;

/**
 *
 * @author Quew8
 */
public class LineIntersection {
    public Vector point;
    public float fraction1;
    public float fraction2;

    public LineIntersection(Vector point, float fraction1, float fraction2) {
        this.point = point;
        this.fraction1 = fraction1;
        this.fraction2 = fraction2;
    }
}
