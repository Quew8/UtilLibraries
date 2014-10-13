package com.quew8.gutils.content;

import com.quew8.gutils.ResourceLoader;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Quew8
 */
public class Source {
    private ResourceLoader loader;
    private final String idString;
    private final List<String> sources;
    private final Map<String, String> params;
    private final Map<String, Entry<String, String>[]> paramLists;

    public Source(String idString, List<String> sources, Map<String, String> params, Map<String, Entry<String, String>[]> paramLists) {
        this.idString = idString;
        this.sources = sources;
        this.params = params;
        this.paramLists = paramLists;
    }

    public ResourceLoader getLoader() {
        return loader;
    }
    
    protected void setLoader(ResourceLoader loader) {
        this.loader = loader;
    }
    
    public String getSource() {
        return getSource(0);
    }
    
    public String getSource(int index) {
        return sources.get(index);
    }

    public InputStream getStream() {
        return getStream(0);
    }

    public InputStream getStream(int index) {
        return loader.load(getSource(index));
    }

    public URL getURL() {
        return getURL(0);
    }

    public URL getURL(int index) {
        return loader.getURL(getSource(index));
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean hasParam(String s) {
        return params.containsKey(s);
    }
    
    public String getParam(String s) {
        if(!hasParam(s)) {
            throw new IllegalArgumentException("Params doesn't contain key \"" + s + "\"");
        }
        return params.get(s);
    }
    
    public int getParamInt(String s) {
        return Integer.parseInt(getParam(s));
    }
    
    public float getParamFloat(String s) {
        return Float.parseFloat(getParam(s));
    }
    
    public Map<String, Entry<String, String>[]> getParamLists() {
        return paramLists;
    }

    public String getIdString() {
        return idString;
    }
    
    /**/
    
}
