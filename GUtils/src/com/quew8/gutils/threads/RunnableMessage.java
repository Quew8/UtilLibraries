package com.quew8.gutils.threads;

import com.quew8.gutils.GeneralUtils;

/**
 *
 * @author Quew8
 */
public class RunnableMessage extends SelfHandlingMessage {
    public static final int RUNNABLE_MSG_ID = GeneralUtils.getIntEnum();
    private final Runnable r;
    
    public RunnableMessage(Runnable r) {
        super(RUNNABLE_MSG_ID);
        this.r = r;
    }
    
    @Override
    protected void handleSelf() {
        r.run();
    }
}
