package com.quew8.gutils.desktop;

import com.quew8.gutils.Clock;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class GProcessManager {
    private final ClosingPolicy closingPolicy;
    private final ArrayList<GProcess> processes = new ArrayList<GProcess>();
    
    public GProcessManager(ClosingPolicy closingPolicy, GProcess... processes) {
        this.closingPolicy = closingPolicy;
        this.processes.addAll(Arrays.asList(processes));
    }
    
    public GProcessManager(GProcess... processes) {
        this(ClosingPolicy.ON_ANY_CLOSE, processes);
    }
    
    public final void play() {
        for(GProcess p: processes) {
            p.getWindow().makeCurrent();
            p.init();
        }
        Clock.begin();
        loop();
        for(GProcess p: processes) {
            p.getWindow().makeCurrent();
            p.deinit();
        }
    }
    
    private void loop() {
        while(!shouldEndProcesses()) {
            Clock.makeDelta();
            for(int i = 0; i < processes.size(); i++) {
                processes.get(i).getWindow().makeCurrent();
                if(processes.get(i).getWindow().isResized()) {
                    processes.get(i).resize(processes.get(i).getViewport());
                }
                processes.get(i).update();
                processes.get(i).render();
                processes.get(i).getWindow().endOfFrame();
            }
        }
    }
    
    private boolean shouldEndProcesses() {
        switch(closingPolicy) {
            case ON_ALL_CLOSE: {
                for(GProcess p: processes) {
                    if(p.getWindow().remainOpen()) {
                        return false;
                    }
                }
                return true;
            }
            case ON_ANY_CLOSE: {
                for(GProcess p: processes) {
                    if(!p.getWindow().remainOpen()) {
                        return true;
                    }
                }
                return false;
            }
            default: throw new IllegalArgumentException("");
        }
    }
    
    public static enum ClosingPolicy {
        ON_ALL_CLOSE, ON_ANY_CLOSE;
    }
}
