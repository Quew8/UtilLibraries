package com.quew8.gutils;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Quew8
 */
public interface ResourceLoader {
    public static final ResourceLoader INTERNAL = new InternalResourceLoader();
    public static final ResourceLoader EXTERNAL = new ExternalResourceLoader();
    
    public InputStream load(String resource);
    public URL getURL(String resource);
    
    public static class InternalResourceLoader implements ResourceLoader {

        private InternalResourceLoader() {
            
        }
        
        @Override
        public InputStream load(String resourcePath) {
            return GeneralUtils.readFrom(resourcePath);
        }

        @Override
        public URL getURL(String resource) {
            return InternalResourceLoader.class.getResource("/" + resource);
        }
        
    }
    
    public static class ExternalResourceLoader implements ResourceLoader {

        private ExternalResourceLoader() {
            
        }
        
        @Override
        public InputStream load(String resource) {
            return GeneralUtils.readFrom(new File(GeneralUtils.toPlatformPath(resource)));
        }

        @Override
        public URL getURL(String resource) {
            try {
                return new File(GeneralUtils.toPlatformPath(resource)).toURI().toURL();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }
        
    }
}
