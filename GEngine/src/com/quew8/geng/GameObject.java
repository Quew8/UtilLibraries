package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;
import com.quew8.gutils.debug.DebugIntInterpreter;
import com.quew8.gutils.debug.DebugObject;
import com.quew8.gutils.debug.DebugParam;

/**
 * 
 * @author Quew8
 */
@DebugObject(name = "game_object")
public abstract class GameObject implements Identifiable {
    private static final Creator defaultCreator = new Creator();
    
    @DebugParam(interpreter = DebugIntInterpreter.class)
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
