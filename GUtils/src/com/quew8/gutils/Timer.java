package com.quew8.gutils;

import com.quew8.gutils.collections.Bag;

/**
 *
 * @author Quew8
 */
public class Timer {
    private static final Bag<Timer> TIMERS = new Bag<Timer>(Timer.class, 20);
    
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
        for(int i = 0; i < TIMERS.size(); i++) {
            if(TIMERS.get(i).update()) {
                TIMERS.remove(i);
                i--;
            }
        }
    }
    
    public static void registerTimer(Timer timer) {
        TIMERS.add(timer);
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
