package com.quew8.gutils.debug;

import com.quew8.gutils.debug.DebugInterfaceParser.DebugObjectStruct;

/**
 *
 * @author Quew8
 */
public class DebugLeafNotFoundException extends DebugException {
    private final DebugObjectStruct parent;
    private final String leaf;
    
    public DebugLeafNotFoundException(DebugObjectStruct parent, String leaf) {
        super("\"" + leaf + "\" not found in " + parent.name);
        this.parent = parent;
        this.leaf = leaf;
    }

    public DebugObjectStruct getParent() {
        return parent;
    }

    public String getLeaf() {
        return leaf;
    }
}
