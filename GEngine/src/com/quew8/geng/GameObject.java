package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;
import com.quew8.gutils.debug.DebugInterface;
import java.util.List;

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
    public String debugGetValue(String param) {
        switch(param) {
            case "id": return Integer.toString(id);
        }
        return null;
    }

    @Override
    public DebugInterface debugGetObj(String param) {
        return null;
    }

    @Override
    public String debugSetValue(String param, String... value) {
        switch(param) {
            case "id": return "id value is final";
        }
        return "No Such Parameter";
    }

    @Override
    public void debugOnChangeObj(String in) {
        
    }

    @Override
    public void debugAddAllParams(List<String> objs, List<String> vals) {
        vals.add("id");
    }
    
    public static class Creator {
        private int maxId = Integer.MIN_VALUE;
        
        public int makeId() {
            return maxId++;
        }
    }
}
