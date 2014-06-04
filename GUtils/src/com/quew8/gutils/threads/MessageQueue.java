package com.quew8.gutils.threads;

import java.util.ArrayList;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public class MessageQueue<T extends Message> implements IMessageListener<T> {
    private final ArrayList<T> messageQueue = new ArrayList<>();
    private final IMessageHandler<? super T> messageHandler;
    
    public MessageQueue(IMessageHandler<? super T> messageHandler) {
        this.messageHandler = messageHandler;
    }
    
    @Override
    public void parseMessages() {
        synchronized(messageQueue) {
            for(int i = 0; i < messageQueue.size(); i++) {
                parseMessage(messageQueue.get(i));
            }
            messageQueue.clear();
        }
    }

    public void parseMessage(T msg) {
        messageHandler.handle(msg);
    }

    @Override
    public void postMessage(T msg) {
        synchronized(messageQueue) {
            messageQueue.add(msg);
        }
    }
    
    public static <T extends SelfHandlingMessage> MessageQueue<T> createMessageQueue() {
        return new MessageQueue<T>(new SelfMessageHandler());
    } 
}
