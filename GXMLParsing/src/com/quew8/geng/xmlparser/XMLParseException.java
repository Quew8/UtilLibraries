package com.quew8.geng.xmlparser;

/**
 *
 * @author Quew8
 */
public class XMLParseException extends RuntimeException {
    
    public XMLParseException(String message) {
        super(message);
    }
    
    public XMLParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public XMLParseException(Throwable cause) {
        super(cause);
    }
    
}
