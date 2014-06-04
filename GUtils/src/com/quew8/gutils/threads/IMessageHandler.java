package com.quew8.gutils.threads;

/**
 *
 * @author Quew8
 */
public interface IMessageHandler<T extends Message> {
    public void handle(T msg);
}
