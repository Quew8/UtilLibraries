package com.quew8.geng3d;

import com.quew8.geng3d.TransformingPosition.VectorSet;
import com.quew8.geng.interfaces.Drawable;
import com.quew8.geng.rendering.DynamicHandleInstance;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;


/**
 * 
 * @author Quew8
 *
 */
public class DynamicSceneObject extends SceneObject<DynamicHandleInstance<?>> implements Drawable, Bindable {
    private final TransformingPosition position;
    private final VectorSet vertices;
    
    public DynamicSceneObject(DynamicHandleInstance<?> drawable, Vector[] vertices, Vector position, Vector orientation) {
        super(drawable);
        this.position = new TransformingPosition(position, orientation);
        this.vertices = this.position.newVectorSet(vertices);
    }

    public DynamicSceneObject(DynamicHandleInstance<?> drawable, Vector[] vertices, Vector position) {
        this(drawable, vertices, position, new Vector());
    }

    public DynamicSceneObject(DynamicHandleInstance<?> drawable, Vector[] vertices) {
        this(drawable, vertices, new Vector());
    }

    @Override
    public void translate(Vector dv) {
        position.translate(dv);
    }

    @Override
    public void rotate(Vector da) {
        position.rotate(da);
    }

    @Override
    public void draw() {
        getDrawable().draw(getModelMatrix());
    }

    public TransformingPosition getPosition() {
        return this.position;
    }
    
    public Matrix getModelMatrix() {
        return position.getModelMatrix();
    }

    @Override
    public Vector[] getVertices() {
        return vertices.getTransformedVertices();
    }

    public Vector getTranslation() {
        return position.getTranslationMatrix().getTranslation();
    }
}
