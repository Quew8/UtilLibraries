package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugNullObjectException extends DebugException {
    private final DebugInterface in;
    private final String object;
    
    public DebugNullObjectException(DebugInterface in, String object) {
        super("\"" + object + "\" is null in " + in.debugGetName());
        this.in = in;
        this.object = object;
    }

    public DebugInterface getIn() {
        return in;
    }

    public String getObject() {
        return object;
    }
    
}
