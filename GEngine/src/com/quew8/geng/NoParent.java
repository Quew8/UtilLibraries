package com.quew8.geng;

import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Parent;
import com.quew8.gutils.debug.DebugInterface;
import java.util.List;

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
    public String debugGetValue(String param) {
        return null;
    }

    @Override
    public String debugSetValue(String param, String... value) {
        return "No Such Param";
    }

    @Override
    public DebugInterface debugGetObj(String param) {
        return null;
    }

    @Override
    public void debugOnChangeObj(String in) {
        
    }

    @Override
    public void debugAddAllParams(List<String> objs, List<String> vals) {
        
    }
    
}
