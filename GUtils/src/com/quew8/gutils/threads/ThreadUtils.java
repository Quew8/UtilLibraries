package com.quew8.gutils.threads;

import com.quew8.gutils.GeneralUtils.ExceptionHandler;

/**
 * 
 * @author Will
 *
 */
public class ThreadUtils {
    private static boolean waitForExecution = false;
    private static final ExceptionHandler<TaskExecutionException> taskExecutionHandler = new ExceptionHandler<>();
    private static int numberTasksActive = 0;
    private static final IMessageListener<SelfHandlingMessage> defaultMessageListener = MessageQueue.createMessageQueue();
    
    private ThreadUtils() {
        
    }
    
    /**
     * 
     * @return true if all threads are complete
     */
    public static boolean parseMessages() {
    	defaultMessageListener.parseMessages();
        return numberTasksActive == 0;
    }
    
    public static void postMessage(SelfHandlingMessage msg) {
        defaultMessageListener.postMessage(msg);
    }
    
    /**
     * 
     * @param <T>
     * @param <S>
     * @param task
     * @param input 
     */
    public static <T extends WorkerTask<T, S, ?, ?>, S> void executeTask(WorkerTask<T, S, ?, ?> task, S input)  {
    	if(waitForExecution) {
            IMessageListener<SelfHandlingMessage> listener = ImmediateMessageListener.createImmediateMessageListener();
            task.execute(input, listener);
            listener.parseMessages();
    	} else {
            task.execute(input, defaultMessageListener);
    	}
    }
    
    /**
     * 
     * @param waitForExecution
     */
    public static void setWaitForExecution(boolean waitForExecution) {
        ThreadUtils.waitForExecution = waitForExecution;
    }
    
    protected static void newTask() {
        numberTasksActive++;
    }
    
    protected static void endTask() {
        numberTasksActive--;
    }
    
    public static void handle(TaskExecutionException ex) {
        taskExecutionHandler.handle(ex);
    }
}
