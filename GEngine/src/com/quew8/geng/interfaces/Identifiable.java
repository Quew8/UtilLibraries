package com.quew8.geng.interfaces;

import com.quew8.gutils.debug.DebugInterface;

/**
 * 
 * @author Quew8
 */
public interface Identifiable extends DebugInterface {
    public Parent getParent();
    public int getId();
}
