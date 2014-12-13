package com.quew8.gutils;

import com.quew8.gutils.collections.ExpandingStack;

/**
 *
 * @author Quew8
 */
public class UniqueIDGen {
    private final ExpandingStack<Integer> ids = new ExpandingStack<Integer>(Integer.class, 5);
    private int maxId;

    public int getId() {
        if(ids.isEmpty()) {
            return maxId++;
        } else {
            return ids.pop();
        }
    }

    public void recycle(int id) {
        ids.push(id);
    }
    
}
