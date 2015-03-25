package com.quew8.geng3d;

import com.quew8.geng.debug.VectorDebugInterface;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gutils.collections.Bag;
import com.quew8.gutils.debug.DebugInterface;
import com.quew8.gutils.debug.DebugObjectNotFoundException;
import com.quew8.gutils.debug.DebugParamNotFoundException;

/**
 * 
 * @author Quew8
 *
 */
public class Position implements Bindable, DebugInterface {
    private Position superPosition = null;
    private final Bag<Position> subPositions = new Bag<Position>(Position.class, 0);
    private final Bag<Bindable> bound = new Bag<Bindable>(Bindable.class, 0);
    private final Vector translation;
    private final Matrix translationMatrix = new Matrix();
    private boolean needsNewTranslationMatrix = true;
    
    private final Vector orientation;
    private final Matrix rotationMatrix = new Matrix();
    private boolean needsNewRotationMatrix = true;
    
    private final Matrix modelMatrix = new Matrix();
    private boolean needsNewModelMatrix = true;

    public Position(Vector translation, Vector orientation) {
        this.translation = translation;
        this.orientation = orientation;
    }

    public Position(Vector translation) {
        this(translation, new Vector());
    }
	
    public Position(float x, float y, float z, float pitch, float yaw, float roll) {
    	this(new Vector(x, y, z), new Vector(pitch, yaw, roll));
    }
    
    public Position(float x, float y, float z, float pitch, float yaw) {
    	this(x, y, z, pitch, yaw, 0);
    }
    
    public Position(float x, float y, float z) {
    	this(x, y, z, 0, 0);
    }
    
    public void setTranslation(Vector p) {
        for(int i = 0; i < bound.size(); i++) {
            bound.get(i).translate(new Vector(getTranslation(), p));
        }
    	translation.setXYZ(p.getX(), p.getY(), p.getZ());
        setNeedsNewTranslationMatrix();
    }
    
    @Override
    public void translate(Vector dv) {
        for(int i = 0; i < bound.size(); i++) {
            bound.get(i).translate(dv);
        }
        translation.add(dv);
        setNeedsNewTranslationMatrix();
    }
    
    public void setRotation(Vector a) {
        for(int i = 0; i < bound.size(); i++) {
            bound.get(i).rotateAbout(new Vector(getOrientation(), a), getTranslation());
        }
    	orientation.setXYZ(a.getX(), a.getY(), a.getZ());
        setNeedsNewRotationMatrix();
    }
    
    @Override
    public void rotateAbout(Vector da, Vector about) {
        throw new UnsupportedOperationException("TODO");
        /*Vector relativeAbout = new Vector(getTranslation(), about);
        for(int i = 0; i < bound.size(); i++) {
            bound.get(i).rotateAbout(da, about);
        }
        orientation.add(da);
        setNeedsNewRotationMatrix();*/
    }
    
    public void rotate(Vector da) {
        for(int i = 0; i < bound.size(); i++) {
            bound.get(i).rotateAbout(da, getTranslation());
        }
        orientation.add(da);
        setNeedsNewRotationMatrix();
    }

    public Matrix getModelMatrix() {
        createModelMatrix();
        return modelMatrix;
    }

    private void createModelMatrix() {
        if(needsNewModelMatrix) {
            if(superPosition == null) {
                Matrix.times(modelMatrix, getRotationMatrix(), getTranslationMatrix());
            } else {
                Matrix m = new Matrix();
                Matrix.times(m, getRotationMatrix(), getTranslationMatrix());
                Matrix.times(modelMatrix, m, superPosition.getModelMatrix());
            }
            needsNewModelMatrix = false;
        }
    }

    public Matrix getTranslationMatrix() {
        createTranslationMatrix();
        return translationMatrix;
    }

    private void createTranslationMatrix() {
        if(needsNewTranslationMatrix) {
            Matrix.makeTranslation(translationMatrix, translation);
            needsNewTranslationMatrix = false;
        }
    }

    public Matrix getRotationMatrix() {
        createRotationMatrix();
        return rotationMatrix;
    }

    public void createRotationMatrix() {
        if(needsNewRotationMatrix) {
            Matrix m = new Matrix();
            Matrix.makeYRotation(rotationMatrix, orientation.getY());
            Matrix.rotateX(m, rotationMatrix, orientation.getX());
            Matrix.rotateZ(rotationMatrix, m, orientation.getZ());
            needsNewRotationMatrix = false;
        }
    }
    
    public void bind(Bindable b) {
        bound.add(b);
    }
    
    public Position addSubPosition(Position pos) {
        pos.superPosition = this;
        pos.setNeedsNewTranslationMatrix();
        pos.setNeedsNewRotationMatrix();
        subPositions.add(pos);
        return pos;
    }
    
    public void setNeedsNewTranslationMatrix() {
        needsNewTranslationMatrix = true;
        setNeedsNewModelMatrix();
    }
    
    public void setNeedsNewRotationMatrix() {
        needsNewRotationMatrix = true;
        setNeedsNewModelMatrix();
    }

    public void setNeedsNewModelMatrix() {
        needsNewModelMatrix = true;
        for(int i = 0; i < subPositions.size(); i++) {
            subPositions.get(i).setNeedsNewModelMatrix();
        }
    }
    
    public Vector getTranslation() {
        return translation;
    }
    
    public Vector getOrientation() {
        return orientation;
    }

    @Override
    public String debugGetName() {
        return "position";
    }

    @Override
    public String debugGetValue(String param) throws DebugParamNotFoundException {
        throw new DebugParamNotFoundException(this, param);
    }

    @Override
    public DebugInterface debugGetObj(String obj) throws DebugObjectNotFoundException {
        switch(obj) {
            case "translation": return new VectorDebugInterface(translation);
            case "orientation": return new VectorDebugInterface(orientation);
        }
        throw new DebugObjectNotFoundException(this, obj);
    }

    @Override
    public void debugSetValue(String param, String... values) throws DebugParamNotFoundException {
        throw new DebugParamNotFoundException(this, param);
    }

    @Override
    public void debugOnChangeObj(String in) {
        switch(in) {
            case "translation": setNeedsNewTranslationMatrix(); break;
            case "orientation": setNeedsNewRotationMatrix(); break;
        }
    }

    @Override
    public String[] debugGetParams() {
        return new String[]{};
    }

    @Override
    public String[] debugGetObjects() {
        return new String[]{
            "translation", "orientation"
        };
    }
}
