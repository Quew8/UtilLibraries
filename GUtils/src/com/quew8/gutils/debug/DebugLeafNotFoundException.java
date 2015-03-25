package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugLeafNotFoundException extends DebugException {
    private final DebugInterface in;
    private final String leaf;

    public DebugLeafNotFoundException(DebugInterface in, String leaf) {
        super("\"" + leaf + "\" not found in " + in.debugGetName());
        this.in = in;
        this.leaf = leaf;
    }

    public DebugInterface getIn() {
        return in;
    }

    public String getLeaf() {
        return leaf;
    }
}
