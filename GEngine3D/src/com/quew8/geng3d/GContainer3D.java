package com.quew8.geng3d;

import com.quew8.geng.GContainer;
import com.quew8.geng3d.interfaces.Collidable;
import com.quew8.geng.interfaces.*;
import com.quew8.gutils.collections.Bag;

/**
 * 
 * @author Quew8
 *
 */
public class GContainer3D extends GContainer {
    public final Bag<Drawable> drawables = new Bag<Drawable>(Drawable.class);
    public final Bag<FinalDrawable> finalDrawables = new Bag<FinalDrawable>(FinalDrawable.class);
    public final Bag<Updateable> updateables = new Bag<Updateable>(Updateable.class);
    public final Bag<Collidable> collidables = new Bag<Collidable>(Collidable.class);
    public final Bag<Disposable> disposables = new Bag<Disposable>(Disposable.class);
    
    /*public void checkCollision(Body e, Vector v) {
        Vector originalV = new Vector(v);
        for(int i = 0; i < collidables.size(); i++) {
            if(e.getId() != collidables.get(i).getId()) {
                v = doCollisionCheck(e, collidables.get(i), v);
                if(v.hasMagnitudeZero()) {
                    return;
                }
            }
        }
        if(v.hasMagnitudeZero()) {
            return;
        }
        if(v.equals(originalV)) {
            e.onFreeMovement(v);
        }
        e.translate(v);
    }

    private Vector doCollisionCheck(Body e, Collidable c, Vector dv) {
        Vector penetration = e.checkCollision(c, dv);
        if(penetration != null) {
            dv = e.onColliding(c.onCollision(e.getMomentum(), penetration), dv);
        }
        return dv;
    }*/

    public void updateAll() {
        for(int i = 0; i < updateables.size(); i++) {
            updateables.get(i).update();
        }
    }

    public void drawAll() {
        for (int i = 0; i < drawables.size(); i++) {
            drawables.get(i).draw();
        }
    }

    public void drawAllFinal() {
        for (int i = 0; i < finalDrawables.size(); i++) {
            finalDrawables.get(i).draw();
        }
    }
    
    public int[] add(Identifiable... identifiables) {
        int[] ids = new int[identifiables.length];
        for(int i = 0; i < identifiables.length; i++) {
            ids[i] = add(identifiables[i]);
        }
        return ids;
    }

    public int add(Identifiable i) {
        if(i instanceof Drawable) {
            drawables.add((Drawable)i);
        }
        if(i instanceof FinalDrawable) {
            finalDrawables.add((FinalDrawable)i);
        }
        if(i instanceof Updateable) {
            updateables.add((Updateable)i);
        }
        if(i instanceof Collidable) {
            collidables.add((Collidable)i);
        }
        if(i instanceof Disposable) {
            disposables.add((Disposable)i);
        }
        return i.getId();
    }

    public Identifiable get(int id) {
        Identifiable i = getFrom(id, drawables);
        if(i != null) { return i; }
        i = getFrom(id, finalDrawables);
        if(i != null) { return i; }
        i = getFrom(id, updateables);
        if(i != null) { return i; }
        i = getFrom(id, collidables);
        if(i != null) { return i; }
        i = getFrom(id, disposables);
        if(i != null) { return i; }
        return null;
    }

    public static <T extends Identifiable> T getFrom(int id, Bag<T> from) {
        for(int i = 0; i < from.size(); i++) {
            if(from.get(i).getId() == id) {
                return from.get(i);
            }
        }
        return null;
    }
}
