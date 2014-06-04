package com.quew8.gutils.threads;

import com.quew8.gutils.threads.TaskMessage.TaskBeginningMessage;
import com.quew8.gutils.threads.TaskMessage.TaskCompleteMessage;
import com.quew8.gutils.threads.TaskMessage.TaskEndingMessage;
import com.quew8.gutils.threads.TaskMessage.TaskExceptionMessage;
import com.quew8.gutils.threads.TaskMessage.TaskProgressMessage;

/**
 * 
 * @author Will
 *
 * @param <SELF> Self
 * @param <T> Input
 * @param <S> Progress
 * @param <U> Output
 */
public abstract class WorkerTask<SELF extends WorkerTask<SELF, T, S, U>, T, S, U> extends Thread {
    private T input;
    private IMessageListener<SelfHandlingMessage> msgListener;

    /**
     * 
     * @param input
     * @param msgListener  
     */
    public void execute(T input, IMessageListener<SelfHandlingMessage> msgListener) {
        this.input = input;
        this.msgListener = msgListener;
        onPreWork(input);
        start();
    }

    @Override
    public void run() {
        postMessage(new TaskBeginningMessage<SELF>(self()));
        try {
            U output = work(input);
            postMessage(new TaskCompleteMessage<SELF, U>(self(), output));
        } catch(TaskExecutionException ex) {
            postMessage(new TaskExceptionMessage<SELF>(self(), ex));
        }
        postMessage(new TaskEndingMessage<SELF>(self()));
    }

    /**
     * 
     * @param input
     */
    public void onPreWork(T input) { }

    /**
     * 
     * @param input
     * @return
     */
    public abstract U work(T input);

    /**
     * 
     * @param output
     */
    public abstract void onPostWork(U output);

    /**
     * 
     * @param progress
     */
    protected void postProgress(S progress) {
        postMessage(new TaskProgressMessage<SELF, S>(self(), progress));
    }

    /**
     * 
     * @param msg
     */
    protected void postMessage(SelfHandlingMessage msg) {
        msgListener.postMessage(msg);
    }

    /**
     * 
     * @param progress
     */
    public void onProgress(S progress) { }

    /**
     * 
     * @param ex
     */
    public void onException(TaskExecutionException ex) {
        ThreadUtils.handle(ex);
    }
    
    @SuppressWarnings(value = "unchecked")
    public final SELF self() {
        return (SELF) this;
    }
}
