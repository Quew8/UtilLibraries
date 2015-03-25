package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;
import com.quew8.gutils.debug.DebugException;
import com.quew8.gutils.debug.DebugInterface;
import com.quew8.gutils.debug.DebugObjectNotFoundException;
import com.quew8.gutils.debug.DebugParamNotFoundException;
import com.quew8.gutils.debug.DebugSettingFinalParamException;

/**
 * 
 * @author Quew8
 */
public abstract class GameObject implements Identifiable, DebugInterface {
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

    @Override
    public String debugGetName() {
        return "game_object";
    }

    @Override
    public String debugGetValue(String param) {
        switch(param) {
            case "id": return Integer.toString(id);
        }
        return null;
    }

    @Override
    public DebugInterface debugGetObj(String param) throws DebugObjectNotFoundException {
        throw new DebugObjectNotFoundException(this, param);
    }

    @Override
    public void debugSetValue(String param, String... value) throws DebugException {
        switch(param) {
            case "id": throw new DebugSettingFinalParamException(param);
            default: throw new DebugParamNotFoundException(this, param);
        }
    }

    @Override
    public void debugOnChangeObj(String in) {
        
    }

    @Override
    public String[] debugGetParams() {
        return new String[]{"id"};
    }

    @Override
    public String[] debugGetObjects() {
        return new String[]{};
    }
    
    public static class Creator {
        private int maxId = Integer.MIN_VALUE;
        
        public int makeId() {
            return maxId++;
        }
    }
}
