package com.quew8.gutils.desktop.natives;

import com.quew8.gutils.ResourceLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.lwjgl.system.Configuration;

/**
 *
 * @author Quew8
 */
public class Natives {
    public static final String NATIVES_RES_DIR = "com/quew8/gutils/desktop/natives/";
    public static final String[] NATIVE_NAMES = new String[] {
        "glfw.dll",
        "glfw32.dll",
        "jemalloc.dll",
        "jemalloc32.dll",
        "lwjgl.dll",
        "lwjgl32.dll"
    };
    
    private Natives() {}
    
    public static void loadNatives() throws IOException {
        loadNatives(NATIVES_RES_DIR, ResourceLoader.INTERNAL);
    }
    
    public static void loadNatives(String nativeDir, ResourceLoader loader) 
            throws IOException {
        
        loadNatives(nativeDir, NATIVE_NAMES, loader);
    }
    
    public static void loadNatives(String nativeDir, String[] nativeNames, 
            ResourceLoader loader) throws IOException {
        
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        byte[] buff = new byte[2048];
        for(String nativeName: nativeNames) {
            InputStream is = loader.load(nativeDir + nativeName);
            File temp = new File(tmpDir, nativeName);
            try(FileOutputStream os = new FileOutputStream(temp)) {
                int read;
                while((read = is.read(buff)) != -1) {
                    os.write(buff, 0, read);
                }
            }
        }
        setNativesDir(tmpDir);
    }
    
    public static void setNativesDir(File dir) {
        Configuration.LIBRARY_PATH.set(dir.getAbsolutePath());
    }
}
