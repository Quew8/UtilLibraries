package com.quew8.gutils.threads;

import com.quew8.gutils.GeneralUtils;

/**
 * 
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class TaskMessage<T, S extends WorkerTask<S, ?, ?, ?>> extends SelfHandlingMessage {
    public static final int BEGINNING_MSG_ID = GeneralUtils.getIntEnum();
    public static final int PROGRESS_MSG_ID = GeneralUtils.getIntEnum();
    public static final int EXCEPTION_MSG_ID = GeneralUtils.getIntEnum();
    public static final int COMPLETE_MSG_ID = GeneralUtils.getIntEnum();
    public static final int ENDING_MSG_ID = GeneralUtils.getIntEnum();
    
    protected S source;
    protected T param;
    
    public TaskMessage(S source, int id, T param) {
        super(id);
        this.source = source;
        this.param = param;
    }
    
    @Override
    protected void handleSelf() {
        handle(getID(), source, param);
    }

    public abstract void handle(int id, S source, T param);

    public static class TaskBeginningMessage<T extends WorkerTask<T, ?, ?, ?>> extends TaskMessage<Object, T> {
        
        public TaskBeginningMessage(T source) {
            super(source, BEGINNING_MSG_ID, null);
        }
        
        @Override
        public void handle(int id, T source, Object param) {
            ThreadUtils.newTask();
        }
    }
    
    public static class TaskEndingMessage<T extends WorkerTask<T, ?, ?, ?>> extends TaskMessage<Object, T> {
        
        public TaskEndingMessage(T source) {
            super(source, ENDING_MSG_ID, null);
        }
        
        @Override
        public void handle(int id, T source, Object param) {
            ThreadUtils.endTask();
        }
    }
    
    public static class TaskCompleteMessage<T extends WorkerTask<T, ?, ?, U>, U> extends TaskMessage<U, T> {
        
        public TaskCompleteMessage(T source, U param) {
            super(source, COMPLETE_MSG_ID, param);
        }
        
        @Override
        public void handle(int id, T source, U param) {
            source.onPostWork(param);
        }
    }
    
    public static class TaskExceptionMessage<T extends WorkerTask<T, ?, ?, ?>> extends TaskMessage<TaskExecutionException, T> {
        
        public TaskExceptionMessage(T source, TaskExecutionException param) {
            super(source, EXCEPTION_MSG_ID, param);
        }
        
        @Override
        public void handle(int id, T source, TaskExecutionException param) {
            source.onException(param);
        }
    }
    
    public static class TaskProgressMessage<T extends WorkerTask<T, ?, S, ?>, S> extends TaskMessage<S, T> {
        
        public TaskProgressMessage(T source, S param) {
            super(source, PROGRESS_MSG_ID, param);
        }
        
        @Override
        public void handle(int id, T source, S param) {
            source.onProgress(param);
        }
    }
}

