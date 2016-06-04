package com.quew8.geng3d;

import com.quew8.geng3d.interfaces.Collidable;
import com.quew8.gmath.Vector;
import com.quew8.geng.interfaces.Updateable;
import com.quew8.geng.rendering.DynamicHandleInstance;
import com.quew8.gmath.GMath;
import com.quew8.gmath.intersection.MotionSupportCalc;
import com.quew8.gutils.Clock;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.collections.Bag;

/**
 * 
 * @author Quew8
 */
public class Body extends DynamicSceneObject implements Updateable, Collidable {
    private static Zone globalZone = new Zone();
    
    private final Vector linearVelocity = new Vector();
    private final Vector angularVelocity = new Vector();
    private final Vector linearAcceleration = new Vector();
    private final Vector angularAcceleration = new Vector();
    private final float mass = 100;
    private Zone zone;
    
    public Body(DynamicHandleInstance<?> drawable, Vector[] vertices, Vector position, Vector orientation) {
        super(drawable, vertices, position, orientation);
        this.zone = globalZone;
    }
    
    public Body(DynamicHandleInstance<?> drawable, Vector[] vertices, Vector position) {
        super(drawable, vertices, position);
        this.zone = globalZone;
    }
    
    public Vector getVelocity() {
        return linearVelocity;
    }
    
    public float getMass() {
        return mass;
    }
    
    public Vector getMomentum() {
        return Vector.scale(new Vector(), linearVelocity, mass);
    }
    
    public void applyForce(Vector f, Vector p) {
        float r2 = p.magnitudeSquared();
        if(r2 != 0) {
            Vector np = Vector.scale(new Vector(), p, 1f / GMath.sqrt(r2));
            Vector fTra = Vector.scale(new Vector(), f, GMath.dot(f, np));
            Vector fRot = Vector.subtract(new Vector(), f, fTra);
            Vector t = GMath.cross(fRot, p);
            t.scale(t, 1 / (mass * r2));
            angularAcceleration.add(angularAcceleration, t);
            fTra.scale(fTra, 1 / mass);
            linearAcceleration.add(linearAcceleration, fTra);
        } else {
            applyForce(f);
        }
    }
    
    public void applyForce(Vector f) {
        linearAcceleration.add(linearAcceleration, f.scale(new Vector(), 1 / mass));
    }
    
    @Override
    public void update() {
        makeVelocity();
        Vector dv = makeMovement(linearVelocity);
        checkCollisionAndTranslate(dv);
        makeAngularVelocity();
        Vector da = makeRotation(angularVelocity);
        rotate(da);
    }
    
    public void makeVelocity() {
        linearVelocity.add(linearVelocity, linearAcceleration.scale(new Vector(), Clock.getDelta()));
        linearAcceleration.setXYZ(0, 0, 0);
    }
    
    public void makeAngularVelocity() {
        angularVelocity.add(angularVelocity, angularAcceleration.scale(new Vector(), Clock.getDelta()));
        angularAcceleration.setXYZ(0, 0, 0);
    }
    
    public Vector makeMovement(Vector linearVelocity) {
        return Vector.scale(new Vector(), linearVelocity, Clock.getDelta());
    }
    
    public Vector makeRotation(Vector angularVelocity) {
        return Vector.scale(new Vector(), angularVelocity, Clock.getDelta());
    }
    
    public void onFreeMovement(Vector v) {}
    
    public Body setZone(Zone zone) {
        this.zone = zone;
        return this;
    }
    
    public Vector onColliding(Collision collision, Vector dv) {
        Vector reaction = collision.getReaction();
        dv.add(dv, reaction);
        //onContact(dv, reaction);
        DebugLogger.log("COLLISIONS", "Collision: " + collision.getReaction());
        float magReaction = reaction.magnitude();
        reaction.normalize(reaction);
        reaction.scale(reaction, -GMath.dot(reaction, linearVelocity));
        DebugLogger.log("COLLISIONS", "Lin V Pre: " + linearVelocity);
        Vector.add(linearVelocity, linearVelocity, reaction);
        DebugLogger.log("COLLISIONS", "Lin V Post: " + linearVelocity);
        //Vector.times(linearVelocity, linearVelocity, 1 - (magReaction * collision.getFrictionCoefficient()));
        return dv;
    }
    
    public void checkCollisionAndTranslate(Vector v) {
        Vector originalV = new Vector().setXYZ(v);
        Bag<Collidable> collidables = zone.getCollidables();
        for(int i = 0; i < collidables.size(); i++) {
            if(getId() != collidables.get(i).getId()) {
                v = doCollisionCheck(collidables.get(i), v);
                if(v.isMagnitudeZero()) {
                    return;
                }
            }
        }
        if(v.isMagnitudeZero()) {
            return;
        }
        if(v.equals(originalV)) {
            onFreeMovement(v);
        }
        translate(v);
    }

    private Vector doCollisionCheck(Collidable c, Vector dv) {
        Vector penetration = GMath.intersectsGetPenetration(
                new MotionSupportCalc(getVertices(), c.getVertices(), new Vector().setXYZ(linearVelocity)));
        if(penetration != null) {
            dv = onColliding(c.onCollision(getMomentum(), penetration), dv);
        }
        return dv;
    }
    
    public static Zone getGlobalZone() {
        return globalZone;
    }
    
    public static void setGlobalZone(Zone zone) {
        Body.globalZone = zone;
    }
}
