package com.quew8.geng.interfaces;

import com.quew8.gmath.Frustum;

/**
 *
 * @author Quew8
 */
public interface VisibilityTestable extends Identifiable {
    public void testVisibility(Frustum viewingFrustum);
}
