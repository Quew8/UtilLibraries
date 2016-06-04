package com.quew8.geng3d.geometry;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector2;

/**
 *
 * @author Quew8
 */
public interface SphericalProjection {
    public Vector2 getUV(float theta, float phi);
    
    public static class StereographicProjection implements SphericalProjection {
        private final float maxPhi;
        private final float r;
        
        public StereographicProjection(float maxPhi) {
            this.maxPhi = maxPhi;
            this.r = 1 / (4 * GMath.tan((GMath.PI - maxPhi) / 2));
        }
        
        @Override
        public Vector2 getUV(float theta, float phi) {
            float d;
            if(phi >= maxPhi) {
                d = 0.5f;
            } else {
                d = 2 * r * GMath.tan((GMath.PI - phi) / 2);
            }
            return new Vector2(0.5f + (d * GMath.sin(theta)), 0.5f + (d * GMath.cos(theta)));
        }
    }
}
