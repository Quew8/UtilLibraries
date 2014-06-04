package com.quew8.gutils.threads;

/**
 * 
 * @param <T> 
 * @author Quew8
 */
public class ImmediateMessageListener<T extends Message> extends MessageQueue<T> {
    private boolean isTaskComplete = false;
    
    public ImmediateMessageListener(IMessageHandler<? super T> messageHandler) {
        super(messageHandler);
    }
    
    @Override
    public void postMessage(T msg) {
        synchronized(this) {
            super.postMessage(msg);
            notifyAll();
        }
    }
    
    @Override
    public void parseMessages() {
        try {
            do {
                synchronized(this) {
                    super.parseMessages();
                    if(isTaskComplete) {
                        break;
                    }
                    wait();
                }
            } while(!isTaskComplete);
        } catch (InterruptedException e) {
            throw new TaskExecutionException(e);
        }
    }

    @Override
    public void parseMessage(T msg) {
        super.parseMessage(msg);
        isTaskComplete = ( msg.getID() == TaskMessage.ENDING_MSG_ID );
    }
    
    public static <T extends SelfHandlingMessage> ImmediateMessageListener<T> createImmediateMessageListener() {
        return new ImmediateMessageListener<T>(new SelfMessageHandler());
    }
}
