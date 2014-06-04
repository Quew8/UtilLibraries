package com.quew8.gutils;

import java.io.InputStream;

/**
 *
 * @author Quew8
 */
public class DirectoryInputStreamLocator extends ResourceLocator<String, InputStream> {
    private final String directory;
    private final ResourceLoader loader;
    
    public DirectoryInputStreamLocator(String directory, ResourceLoader loader, ResourceLocator<String, InputStream> subLocator) {
        super(subLocator);
        this.directory = 
                directory.endsWith("/") ? 
                directory : 
                directory + "/";
        this.loader = loader;
    }
    
    public DirectoryInputStreamLocator(String directory, ResourceLocator<String, InputStream> subLocator) {
        this(directory, ResourceLoader.INTERNAL, subLocator);
    }
    
    public DirectoryInputStreamLocator(String directory, ResourceLoader loader) {
        this(directory, loader, null);
    }
    
    public DirectoryInputStreamLocator(String directory) {
        this(directory, ResourceLoader.INTERNAL, null);
    }
    
    @Override
    public InputStream localLocate(String fileName) {
        return loader.load(directory + fileName);
    }
    
}
