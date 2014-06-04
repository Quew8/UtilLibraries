package com.quew8.gutils.content;

import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class Source {
    private final String source;
    private final HashMap<String, String> params;
    
    public Source(String source, HashMap<String, String> params) {
        this.source = source;
        this.params = params;
    }
    
    public String getSource() {
        return source;
    }
    
    public HashMap<String, String> getParams() {
        return params;
    }
    
    public InputStream getStream() {
        return getClass().getClassLoader().getResourceAsStream(source);
    }
    
    public String getIdString() {
        int i = source.lastIndexOf('/');
        String id = source;
        if(i != -1) {
            id = id.substring(i + 1, id.length());
        }
        return id.replace('.', '_');
    }
}
