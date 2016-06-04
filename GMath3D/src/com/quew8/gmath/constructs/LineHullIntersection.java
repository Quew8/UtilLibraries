package com.quew8.gmath.constructs;

import com.quew8.gmath.Vector;

/**
 *
 * @author Quew8
 */
public class LineHullIntersection {
    public Vector point1;
    public Vector point2;
    public float fraction1;
    public float fraction2;

    public LineHullIntersection(Vector point1, Vector point2, float fraction1, float fraction2) {
        this.point1 = point1;
        this.point2 = point2;
        this.fraction1 = fraction1;
        this.fraction2 = fraction2;
    }

    @Override
    public String toString() {
        return "LineHullIntersection{" + "point1=" + point1 + ", point2=" + point2 + ", fraction1=" + fraction1 + ", fraction2=" + fraction2 + '}';
    }
}
