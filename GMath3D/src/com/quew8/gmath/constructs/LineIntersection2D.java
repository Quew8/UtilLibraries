package com.quew8.gmath.constructs;

import com.quew8.gmath.Vector2;

/**
 *
 * @author Quew8
 */
public class LineIntersection2D {
    public Vector2 point;
    public float fraction1;
    public float fraction2;

    public LineIntersection2D(Vector2 point, float fraction1, float fraction2) {
        this.point = point;
        this.fraction1 = fraction1;
        this.fraction2 = fraction2;
    }
}
