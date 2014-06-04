package com.quew8.gutils.threads;

/**
 *
 * @author Quew8
 */
public abstract class SelfHandlingMessage extends Message {
    
    public SelfHandlingMessage(int id) {
        super(id);
    }
    
    protected abstract void handleSelf();
}
