package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;

/**
 *
 * @author Quew8
 */
public class NoParent implements Parent {
    public static Parent NO_PARENT = new NoParent();
    
    private NoParent() {
        
    }
    
    @Override
    public void remove(Identifiable t) {}

    @Override
    public Parent getParent() {
        throw new IllegalStateException("NoParent used as concrete object");
    }

    @Override
    public int getId() {
        throw new IllegalStateException("NoParent used as concrete object");
    }
    
}
