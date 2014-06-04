package com.quew8.gutils.threads;

/**
 *
 * @author Quew8
 */
public interface IMessageListener<T extends Message> {
    public void postMessage(T msg);
    public void parseMessages();
}
