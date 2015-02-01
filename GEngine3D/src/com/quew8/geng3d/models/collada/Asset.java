package com.quew8.geng3d.collada;

import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;

/**
 *
 * @author Quew8
 */
public class Asset {
    private final Contributor[] contributors;
    private final String created;
    private final String modified;
    private final Unit unit;
    private final UpAxis upAxis;

    public Asset(Contributor[] contributors, String created, String modified, Unit unit, UpAxis upAxis) {
        this.contributors = contributors;
        this.created = created;
        this.modified = modified;
        this.unit = unit;
        this.upAxis = upAxis;
    }

    public Contributor[] getContributors() {
        return contributors;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public Unit getUnit() {
        return unit;
    }

    public UpAxis getUpAxis() {
        return upAxis;
    }
    
    public static class Contributor {
        private final String author;
        private final String authoringTool;

        public Contributor(String author, String authoringTool) {
            this.author = author;
            this.authoringTool = authoringTool;
        }

        public String getAuthor() {
            return author;
        }

        public String getAuthoringTool() {
            return authoringTool;
        }
    }
    
    public static class Unit {
        private final String name;
        private final float meters;

        public Unit(String name, float meters) {
            this.name = name;
            this.meters = meters;
        }

        public String getName() {
            return name;
        }

        public float getMeters() {
            return meters;
        }
    }
    
    public static enum UpAxis {
        X_UP(new Vector(0, -1, 0), new Vector(1, 0, 0), new Vector(0, 0, 1), false), 
        Y_UP(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1), false), 
        Z_UP(new Vector(1, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0), true);
        
        private UpAxis(Vector left, Vector up, Vector forward, boolean flip) {
            this.left = left;
            this.up = up;
            this.forward = forward;
            this.flip = flip;
        }
        
        private final Vector left, up, forward;
        private final boolean flip;
        
        public Vector getLeft() {
            return left;
        }

        public Vector getUp() {
            return up;
        }

        public Vector getForward() {
            return forward;
        }
        
        public Matrix getMatrix() {
            return new Matrix(
                    left.getX(),    left.getY(),    left.getZ(),    0,
                    up.getX(),      up.getY(),      up.getZ(),      0,
                    forward.getX(), forward.getY(), forward.getZ(), 0,
                    0,              0,              0,              1
            );
        }

        public boolean isFlip() {
            return flip;
        }
    }
}
