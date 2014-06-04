package com.quew8.gutils.threads;

/**
 *
 * @author Quew8
 */
public class SelfMessageHandler implements IMessageHandler<SelfHandlingMessage> {
    
    @Override
    public void handle(SelfHandlingMessage msg) {
        msg.handleSelf();
    }
    
}
