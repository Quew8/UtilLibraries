package com.quew8.gutils;

import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class Timer {
    private static final ArrayList<Timer> timers = new ArrayList<Timer>();
    
    private final Runnable run;
    private long remaining;

    public Timer(Runnable run, long remaining) {
        this.run = run;
        this.remaining = remaining;
    }
    
    public Timer(long remaining) {
        this(null, remaining);
    }
    
    private boolean update() {
        remaining -= Clock.getDelta();
        if(remaining <= 0) {
            onTimeUp();
            return true;
        } else {
            return false;
        }
    }
    
    public void onTimeUp() {
        run.run();
    }
    
    public static void updateTimers() {
        for(int i = 0; i < timers.size(); i++) {
            if(timers.get(i).update()) {
                timers.remove(i);
                i--;
            }
        }
    }
    
    public static void registerTimer(Timer timer) {
        timers.add(timer);
    }
    
    public static void registerTimer(Runnable r, long time) {
        registerTimer(new Timer(r, time));
    }
    
    public static void waitFor(long time) {
        new WaitTimer(time).waitOnTimer();
    }
    
    public static class WaitTimer extends Timer {
        private boolean done = false;
        
        public WaitTimer(long remaining) {
            super(remaining);
        }
        
        @Override
        public void onTimeUp() {
            done = true;
            synchronized(this) {
                notifyAll();
            }
        }
        
        public void waitOnTimer() {
            registerTimer(this);
            synchronized(this) {
                while(!done) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        
    }
}
