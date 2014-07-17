package com.quew8.geng3d;

import com.quew8.geng3d.interfaces.Collidable;
import com.quew8.gutils.collections.Bag;

/**
 *
 * @author Quew8
 */
public class Zone {
    private final Bag<Collidable> collidables;

    public Zone(Collidable... collidables) {
        this.collidables = new Bag<Collidable>(collidables);
    }
    
    public Bag<Collidable> getCollidables() {
        return collidables;
    }
}
