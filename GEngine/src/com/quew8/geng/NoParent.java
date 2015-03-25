package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;
import com.quew8.gutils.debug.DebugInterface;
import com.quew8.gutils.debug.DebugObjectNotFoundException;
import com.quew8.gutils.debug.DebugParamNotFoundException;

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

    @Override
    public String debugGetName() {
        return "no_parent";
    }

    @Override
    public String debugGetValue(String param) throws DebugParamNotFoundException {
        throw new DebugParamNotFoundException(this, param);
    }

    @Override
    public void debugSetValue(String param, String... values) throws DebugParamNotFoundException {
        throw new DebugParamNotFoundException(this, param);
    }

    @Override
    public DebugInterface debugGetObj(String param) throws DebugObjectNotFoundException {
        throw new DebugObjectNotFoundException(this, param);
    }

    @Override
    public void debugOnChangeObj(String in) {
        
    }

    @Override
    public String[] debugGetParams() {
        return new String[]{};
    }

    @Override
    public String[] debugGetObjects() {
        return new String[]{};
    }
    
}
