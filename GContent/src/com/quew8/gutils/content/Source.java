package com.quew8.gutils.content;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Quew8
 */
public class Source {
    private final String source;
    private final HashMap<String, String> params;
    private final HashMap<String, Entry<String, String>[]> paramLists;
    
    public Source(String source, HashMap<String, String> params, 
            HashMap<String, Entry<String, String>[]> paramLists) {
        
        this.source = source;
        this.params = params;
        this.paramLists = paramLists;
    }
    
    public String getSource() {
        return source;
    }
    
    public InputStream getStream() {
        return getClass().getClassLoader().getResourceAsStream(source);
    }
    
    public URL getURL() {
        return getClass().getResource("/" + source);
    }
    
    public HashMap<String, String> getParams() {
        return params;
    }

    public HashMap<String, Entry<String, String>[]> getParamLists() {
        return paramLists;
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
