package com.quew8.geng;

import com.quew8.geng.interfaces.Collidable;
import com.quew8.gutils.collections.Bag;

/**
 *
 * @author Quew8
 */
public class Zone {
    private final Bag<Collidable> collidables;

    public Zone(Collidable... collidables) {
        this.collidables = new Bag<Collidable>(Collidable.class, collidables);
    }
    
    public Bag<Collidable> getCollidables() {
        return collidables;
    }
}
