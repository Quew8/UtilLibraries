package com.quew8.gutils;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Quew8
 */
public interface ResourceLoader {
    public static final ResourceLoader INTERNAL = new InternalResourceLoader();
    public static final ResourceLoader EXTERNAL = new ExternalResourceLoader();
    
    public InputStream load(String url);
    
    public static class InternalResourceLoader implements ResourceLoader {

        @Override
        public InputStream load(String resourcePath) {
            return GeneralUtils.readFrom(resourcePath);
        }
        
    }
    
    public static class ExternalResourceLoader implements ResourceLoader {
        
        @Override
        public InputStream load(String url) {
            try {
                return GeneralUtils.readFrom(new File(new URI(url)));
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
        
    }
}
