package com.quew8.geng3d;

import com.quew8.geng.debug.DebugDegreesInterpreter;
import com.quew8.geng.debug.DebugVectorWrapper;
import com.quew8.geng3d.Camera.DebugCameraNotifier;
import com.quew8.geng3d.geometry.Plane;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gutils.debug.DebugChangeNotifier;
import com.quew8.gutils.debug.DebugFloatInterpreter;
import com.quew8.gutils.debug.DebugFloatSliderField;
import com.quew8.gutils.debug.DebugObject;
import com.quew8.gutils.debug.DebugObjectParam;
import com.quew8.gutils.debug.DebugParam;

@DebugObject(name = "camera", changeNotifier = DebugCameraNotifier.class)
public abstract class Camera {
    private static final float MIN_PITCH_D = -70;
    private static final float MAX_PITCH_D = 70;
    private static final float MIN_PITCH = GMath.toRadians(MIN_PITCH_D);
    private static final float MAX_PITCH = GMath.toRadians(MAX_PITCH_D);
    @DebugParam(name = "left", interpreter = DebugFloatInterpreter.class)
    private float left;
    @DebugParam(name = "right", interpreter = DebugFloatInterpreter.class)
    private float right;
    @DebugParam(name = "bottom", interpreter = DebugFloatInterpreter.class)
    private float bottom;
    @DebugParam(name = "top", interpreter = DebugFloatInterpreter.class)
    private float top;
    @DebugParam(name = "near", interpreter = DebugFloatInterpreter.class)
    private float near;
    @DebugParam(name = "far", interpreter = DebugFloatInterpreter.class)
    private float far;
    private float aspect;
    @DebugObjectParam(name = "pos", wrapper = DebugVectorWrapper.class)
    private final Vector position;
    @DebugParam(name = "pitch", interpreter = DebugFloatInterpreter.class)
    @DebugFloatSliderField(target = "pitch", min = GMath.PI * MIN_PITCH_D / 180f, max = GMath.PI * MAX_PITCH_D / 180f, step = 0.6f)
    @DebugParam(name = "pitch_d", interpreter = DebugDegreesInterpreter.class)
    @DebugFloatSliderField(target = "pitch_d", min = MIN_PITCH_D, max = MAX_PITCH_D, step = 10)
    private float pitch;
    @DebugFloatSliderField(target = "yaw", min = -GMath.PI, max = GMath.PI, step = 0.6f)
    @DebugParam(name = "yaw", interpreter = DebugFloatInterpreter.class)
    @DebugFloatSliderField(target = "yaw_d", min = -180, max = 180, step = 10)
    @DebugParam(name = "yaw_d", interpreter = DebugDegreesInterpreter.class)
    private float yaw;

    private final Matrix projectionViewingMatrix = new Matrix(); 
    private final Matrix aspectMatrix = new Matrix();
    private final Matrix projectionMatrix = new Matrix();
    private final Matrix viewingMatrix = new Matrix();
    private boolean needsNewProjectionViewingMatrix = true;
    private boolean needsNewProjectionMatrix = true;
    private boolean needsNewViewingMatrix = true;

    public Camera(Vector position, float pitch, float yaw, float width, float height, float near, float far, float aspect) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.left = -( width / 2f );
        this.right = ( width / 2f );
        this.bottom = -( height / 2f );
        this.top = ( height / 2f );
        this.near = near;
        this.far = far;
        this.aspect = aspect;
    }

    public Camera(Vector position, float width, float height, float near, float far, float aspect) {
        this(position, 0, 0, width, height, near, far, aspect);
    }

    public Camera(Vector position, float width, float height, float aspect) {
        this(position, 0, 0, width, height, 0.5f, 100f, aspect);
    }

    public void rotate(float pitch, float yaw) {
        if(pitch != 0 || yaw != 0) {
            this.pitch += pitch;
            this.yaw += yaw;
            needsNewViewingMatrix = true;
        }
    }

    public void translate(Vector v) {
        if(!v.isMagnitudeZero()) {
            position.add(position, v);
            needsNewViewingMatrix = true;
        }
    }

    public void setPosition(Vector position) {
        this.position.setXYZ(position.getX(), position.getY(), position.getZ());
        needsNewViewingMatrix = true;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        needsNewViewingMatrix = true;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        needsNewViewingMatrix = true;
    }
    
    public float getWidth() {
        return right - left;
    }
    
    public void setWidth(float width) {
        setLeft(-width / 2);
        setRight(width / 2);
    }
    
    public float getHeight() {
        return top - bottom;
    }
    
    public void setHeight(float height) {
        setBottom(-height / 2);
        setTop(height / 2);
    }
    
    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
        needsNewProjectionMatrix = true;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
        needsNewProjectionMatrix = true;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
        needsNewProjectionMatrix = true;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
        needsNewProjectionMatrix = true;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
        needsNewProjectionMatrix = true;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
        needsNewProjectionMatrix = true;
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
        needsNewProjectionMatrix = true;
    }

    public Vector getPosition() {
        return position;
    }
    
    private void createProjectionViewingMatrix() {
        createProjectionMatrix();
        createViewingMatrix();
        if(needsNewProjectionViewingMatrix) {
            Matrix.times(projectionViewingMatrix, projectionMatrix, viewingMatrix);
            needsNewProjectionViewingMatrix = false;
        }
    }

    public Matrix getProjectionViewingMatrix() {
        createProjectionViewingMatrix();
        return projectionViewingMatrix;
    }

    private void createProjectionMatrix() {
        if(needsNewProjectionMatrix) {
            createProjectionMatrix(aspectMatrix, projectionMatrix);
            needsNewProjectionMatrix = false;
            needsNewProjectionViewingMatrix = true;
        }
    }

    protected abstract void createProjectionMatrix(Matrix aspectMatrix, Matrix projectionMatrix);
    
    public Matrix getAspectMatrix() {
        createProjectionMatrix();
        return aspectMatrix;
    }

    public Matrix getProjectionMatrix() {
        createProjectionMatrix();
        return projectionMatrix;
    }

    private void createViewingMatrix() {
        if(needsNewViewingMatrix) {
            createViewingMatrix(viewingMatrix);
            needsNewViewingMatrix = false;
            needsNewProjectionViewingMatrix = true;
        }
    }
    
    protected void createViewingMatrix(Matrix viewingMatrix) {
        Matrix.makeXRotation(viewingMatrix, pitch);
        Matrix m1 = Matrix.makeYRotation(new Matrix(), yaw);
        Matrix m2 = Matrix.times(new Matrix(), viewingMatrix, m1);
        Matrix.makeTranslation(m1, position);
        Matrix.times(viewingMatrix, m2, m1);
    }

    /*public Matrix getPitchMatrix() {
        return Matrix.makeXRotation(pitch);
    }

    public Matrix getYawMatrix() {
        return Matrix.makeYRotation(yaw);
    }*/

    public Matrix getRotationMatrix(Matrix out) {
        Matrix m1 = Matrix.makeYRotation(new Matrix(), yaw);
        Matrix m2 = Matrix.makeXRotation(new Matrix(), pitch);
        return Matrix.times(out, m1, m2);
    }

    public Matrix getTranslationMatrix(Matrix out) {
        return Matrix.makeTranslation(out, position);
    }
    
    public Matrix getViewingMatrix() {
        createViewingMatrix();
        return viewingMatrix;
    }

    public Vector getForwardVector() {
        createViewingMatrix();
        return viewingMatrix.getForwardDirection(new Vector());
    }

    public Vector getRightVector() {
        createViewingMatrix();
        return viewingMatrix.getRightDirection(new Vector());
    }

    public Vector getUpVector() {
        createViewingMatrix();
        return viewingMatrix.getUpDirection(new Vector());
    }
    
    public Plane getBillboard() {
        return new Plane(getRightVector(), getUpVector(), getForwardVector());
    }

    public void clampPitch(float min, float max) {
        if(pitch > max) {
            pitch = max;
            this.needsNewViewingMatrix = true;
            this.needsNewProjectionViewingMatrix = true;
        } else if(pitch < min) {
            pitch = min;
            this.needsNewViewingMatrix = true;
            this.needsNewProjectionViewingMatrix = true;
        }
    }

    public void clampYaw(float min, float max) {
        if(yaw > max) {
            yaw = max;
            this.needsNewViewingMatrix = true;
            this.needsNewProjectionViewingMatrix = true;
        } else if(yaw < min) {
            yaw = min;
            this.needsNewViewingMatrix = true;
            this.needsNewProjectionViewingMatrix = true;
        }
    }
    
    public Vector calculatePickingRay(float mouseX, float mouseY) {
        Vector eye = getPosition().negate(new Vector());
        Vector look = getForwardVector();
        Vector nearCentre = Vector.add(
                new Vector(), 
                eye.negate(new Vector()), 
                Vector.scale(
                        new Vector(), 
                        look, 
                        near
                )
        );
        Vector farCentre = Vector.add(
                new Vector(), 
                eye.negate(new Vector()), 
                Vector.scale(
                        new Vector(), 
                        look, 
                        far
                )
        );
        Vector nearMouseVec = Vector.add(
                new Vector(), 
                Vector.scale(new Vector(), getRightVector(), -(mouseX - 0.5f)),
                Vector.scale(new Vector(), getUpVector(), -(mouseY - 0.5f))
        );
        Vector farMouseVec = Vector.scale(
                new Vector(),
                nearMouseVec,
                far / near
        );
        Vector nearPos = Vector.add(
                new Vector(), 
                nearCentre, 
                nearMouseVec
        );
        Vector farPos = Vector.add(
                new Vector(), 
                farCentre, 
                farMouseVec
        );
        Vector ray = new Vector(nearPos, farPos);
        return ray.normalize(ray);
    }
    
    static class DebugCameraNotifier extends DebugChangeNotifier {

        @Override
        public void notifyObjectParamChange(Object obj, String changeIn) {
            ((Camera) obj).needsNewViewingMatrix = true;
        }

        @Override
        public void notifyParamChange(Object obj, String changeIn) {
            switch(changeIn) {
                case "left":
                case "right":
                case "bottom":
                case "top": {
                    ((Camera) obj).needsNewProjectionMatrix = true;
                    break;
                }
                default: ((Camera) obj).needsNewViewingMatrix = true;
            }
        }
        
    }
}