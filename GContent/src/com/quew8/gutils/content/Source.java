package com.quew8.gutils.content;

import com.quew8.gutils.ResourceLoader;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Quew8
 */
public class Source {
    private ResourceLoader loader;
    private final String source;
    private final HashMap<String, String> params;
    private final HashMap<String, Map.Entry<String, String>[]> paramLists;

    public Source(String source, HashMap<String, String> params, HashMap<String, Map.Entry<String, String>[]> paramLists) {
        this.source = source;
        this.params = params;
        this.paramLists = paramLists;
    }

    protected void setLoader(ResourceLoader loader) {
        this.loader = loader;
    }
    
    public String getSource() {
        return source;
    }

    public InputStream getStream() {
        //return Source.class.getClassLoader().getResourceAsStream(source);
        return loader.load(source);
    }

    public URL getURL() {
        //return Source.class.getResource("/" + source);
        return loader.getURL(source);
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public HashMap<String, Map.Entry<String, String>[]> getParamLists() {
        return paramLists;
    }

    public String getIdString() {
        int i = source.lastIndexOf('/');
        if(i == -1) {
            i = source.lastIndexOf('\\');
        }
        String id = source;
        if(i != -1) {
            id = id.substring(i + 1, id.length());
        }
        return id.replace('.', '_');
    }
    
}
