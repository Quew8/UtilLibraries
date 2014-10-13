package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;

/**
 * 
 * @author Quew8
 */
public abstract class GameObject implements Identifiable {
    private static final Creator defaultCreator = new Creator();
    
    private final int id;
    
    private GameObject(int id) {
        this.id = id;
    }
    
    public GameObject(Creator creator) {
        this(creator.makeId());
    }
    
    public GameObject() {
        this(defaultCreator);
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public Parent getParent() {
        return NoParent.NO_PARENT;
    }
    
    public static class Creator {
        private int maxId = Integer.MIN_VALUE;
        
        public int makeId() {
            return maxId++;
        }
    }
}
