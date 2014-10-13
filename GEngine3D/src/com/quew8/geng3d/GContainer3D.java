package com.quew8.geng3d;

import com.quew8.geng.GContainer;
import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng3d.interfaces.Collidable;
import com.quew8.gutils.collections.Bag;

/**
 * 
 * @author Quew8
 * @param <T>
 *
 */
public class GContainer3D<T extends Identifiable> extends GContainer<T> {
    public final Bag<Collidable> collidables = new Bag<Collidable>(Collidable.class);
    
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

    @Override
    public Identifiable get(int id) {
        Identifiable i = super.get(id);
        if(i == null) {
            return getFrom(id, collidables);
        }
        return i;
    }
    
    @Override
    public int add(T i) {
        if(i instanceof Collidable) {
            collidables.add((Collidable) i);
        }
        return super.add(i);
    }

    @Override
    public void remove(T t) {
        removeFrom(t, collidables);
        super.remove(t);
    }
}
