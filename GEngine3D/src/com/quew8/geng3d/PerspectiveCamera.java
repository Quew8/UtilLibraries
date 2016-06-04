package com.quew8.geng3d;

import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;

/**
 *
 * @author Quew8
 */
public class PerspectiveCamera extends Camera {

    public PerspectiveCamera(Vector position, float pitch, float yaw, float width, float height, float near, float far, float aspect) {
        super(position, pitch, yaw, width, height, near, far, aspect);
    }

    public PerspectiveCamera(Vector position, float width, float height, float near, float far, float aspect) {
        super(position, width, height, near, far, aspect);
    }

    public PerspectiveCamera(Vector position, float width, float height, float aspect) {
        super(position, width, height, aspect);
    }

    @Override
    protected void createProjectionMatrix(Matrix aspectMatrix, Matrix projectionMatrix) {
        Matrix.makeOrtho(aspectMatrix, getAspect(), -getAspect(), -1, 1, -1, 1);
        Matrix.makeFrustum(projectionMatrix, getLeft() * getAspect(), getRight() * getAspect(), getBottom(), getTop(), getNear(), getFar());
    }
}
