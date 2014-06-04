package com.quew8.geng.rendering;

/**
 * 
 * @author Quew8
 *
 */
public class StaticHandleList {
    private int nHandles = 0;
    private StaticHandleInstance instance = null;
    
    public void drawn() {
        instance.drawn();
    }
    
    public boolean shouldDraw() {
        return instance.shouldDraw();
    }
    
    public StaticHandleInstance getInstance() {
        if(instance != null) {
            throw new IllegalStateException("An Instance of This List Already Exists");
        }
        instance = new StaticHandleInstance(nHandles);
        return instance;
    }
    
    public class StaticHandle extends Handle {
        
        public StaticHandle(int start, int end) {
            super(start, end);
            nHandles++;
        }
        
        public void drawn() {
            StaticHandleList.this.drawn();
        }
        
        public boolean shouldDraw() {
            return StaticHandleList.this.shouldDraw();
        }
        
    }
}
