package com.quew8.gutils;

import com.quew8.gutils.formatting.TextFormatter;
import com.quew8.gutils.opengl.texture.TextureUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Quew8
 */
public abstract class GeneralUtils {
    private static int intEnumCount = Integer.MIN_VALUE;
    public static final ExceptionHandler<IOException> DEFAULT_IO_HANDLER = new ExceptionHandler<IOException>();    
    private static final Random r = new Random();

    private GeneralUtils() {
        
    }
    
    public static TextFormatter readTextIntoFormatter(InputStream input, TextFormatter formatter, ExceptionHandler<IOException> handler) {
        formatter.onPreParse();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line = br.readLine();
            while (line != null) {
                formatter.onLineIn(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            handler.handle(ex);
        }
        formatter.onPostParse();
        return formatter;
    }
    
    public static TextFormatter readTextIntoFormatter(InputStream input, TextFormatter formatter) {
        return readTextIntoFormatter(input, formatter, DEFAULT_IO_HANDLER);
    }
    
    public static TextFormatter readTextIntoDefaultFormatter(InputStream input, ExceptionHandler<IOException> handler) {
        return readTextIntoFormatter(input, new TextFormatter(), handler);
    }
    
    public static TextFormatter readTextIntoDefaultFormatter(InputStream input) {
        return readTextIntoDefaultFormatter(input, DEFAULT_IO_HANDLER);
    }
    
    public static InputStream readFrom(String path, ExceptionHandler<IOException> handler) {
        InputStream stream = TextureUtils.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            handler.handle(new IOException("Could not load: " + path));
        }
        return stream;
    }
    
    public static InputStream readFrom(String path) {
        return readFrom(path, DEFAULT_IO_HANDLER);
    }
    
    public static long getFileSize(File file) {
        return file.length();
    }
    
    public static OutputStream writeTo(File file, ExceptionHandler<IOException> handler) {
        try {
            file.createNewFile();
            return new FileOutputStream(file);
        } catch (IOException ex) {
            handler.handle(ex);
            return null;
        }
    }
    
    public static OutputStream writeTo(File file) {
        return writeTo(file, DEFAULT_IO_HANDLER);
    }
    
    public static InputStream readFrom(File file, ExceptionHandler<IOException> handler) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            handler.handle(ex);
            return null;
        }
    }
    
    public static InputStream readFrom(File file) {
        return readFrom(file, DEFAULT_IO_HANDLER);
    }
    
    public static String getResourceParent(String resourcePath) {
        return resourcePath.substring(0, resourcePath.lastIndexOf('/')) + "/";
    }
    
    public static URL getURLCodeLocation() {
        return GeneralUtils.class.getProtectionDomain().getCodeSource().getLocation();
    }
    
    public static InputStream getCodeLocation(ExceptionHandler<IOException> handler) {
        try {
            return getURLCodeLocation().openStream();
        } catch(IOException ex) {
            handler.handle(ex);
            return null;
        }
    }
    
    public static InputStream getCodeLocation() {
        return getCodeLocation(DEFAULT_IO_HANDLER);
    }
    
    public static String[] getJarContents(FileInputStream fs, FilenameFilter filter, ExceptionHandler<IOException> handler) {
        ArrayList<String> contents = new ArrayList<String>();
        try {
            try(ZipInputStream zipIn = new ZipInputStream(fs)) {
                ZipEntry entry;
                File dir = new File("");
                    while((entry = zipIn.getNextEntry()) != null) {
                        String s = entry.getName();
                        if(entry.isDirectory()) {
                            dir = new File(s);
                        } else {
                            if(filter.accept(dir, s)) {
                                contents.add(s);
                            }
                        }
                    }
                    return contents.toArray(new String[contents.size()]);
            }
        } catch(IOException ex) {
            handler.handle(ex);
            return null;
        }
    }
    
    public static String[] getJarContents(FileInputStream fs, FilenameFilter filter) {
        return getJarContents(fs, filter, DEFAULT_IO_HANDLER);
    }
    
    public static String[] getJarContents(FileInputStream fs) {
        return getJarContents(fs, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });
    }
    
    public static FilenameFilter getFilenameFilter(File directory, String prefix, String suffix) {
        return new BasicFilenameFilter(directory, prefix, suffix);
    }
    
    public static int getIntEnum() {
        return intEnumCount++;
    }
    
    public static int getPlatformConstant() {
        return PlatformBackend.backend.getPlatformConstant();
    }
    
    public static float biLinearInterpolate(
            float x1y1, float x2y1,
            float x1y2, float x2y2,
            float tx, float ty) {
        
        return linearInterpolate(
                linearInterpolate(x1y1, x2y1, tx),
                linearInterpolate(x1y2, x2y2, tx),
                ty);
    }
    
    public static float linearInterpolate(float x1, float x2, float t) {
        return x1 + (t * (x2 - x1));
    }
    
    public static float biCubicInterpolate(
            float x0y0, float x1y0, float x2y0, float x3y0,
            float x0y1, float x1y1, float x2y1, float x3y1,
            float x0y2, float x1y2, float x2y2, float x3y2,
            float x0y3, float x1y3, float x2y3, float x3y3,
            float tx, float ty) {
        
        return cubicInterpolate(
                cubicInterpolate(x0y0, x1y0, x2y0, x3y0, tx),
                cubicInterpolate(x0y1, x1y1, x2y1, x3y1, tx),
                cubicInterpolate(x0y2, x1y2, x2y2, x3y2, tx),
                cubicInterpolate(x0y3, x1y3, x2y3, x3y3, tx),
                ty);
    }
    
    public static float cubicInterpolate(float x0, float x1, float x2, float x3, float t) {
        float a = (x3 - x2) - (x0 - x1);
        float b = /*( x2 - x3 );*/ (x0 - x1) - a;
        float c = (x2 - x0);
        float d = x1;
        float t2 = t * t, t3 = t2 * t;
        return (a * t3) + (b * t2) + (c * t) + d;        
    }
    
    public static Random getRandom() {
        return r;
    }
    
    public static boolean getRandomBool() {
        return r.nextBoolean();
    }
    
    public static long getRandomLong() {
        return r.nextLong();
    }
    
    public static int getRandomInt(int min, int max) {
        int range = max - min;
        if (range <= 0) {
            throw new IllegalArgumentException("Range is less than or equal to zero");
        }
        return r.nextInt(range) + min;
    }
    
    public static int getRandomInt() {
        return r.nextInt();
    }
    
    public static float getRandomFloat(float min, float max) {
        float range = max - min;
        return (r.nextFloat() * range) + min;
    }

    /**
     *
     * @param <T>
     */
    public static class ExceptionHandler<T extends Exception> {
        
        public void handle(T ex) {
            System.err.println("Default Exception Handler");
            throw new RuntimeException(ex);
        }
    }
    
    public static class BasicFilenameFilter implements FilenameFilter {
        private final File directory;
        private final String prefix, suffix;
        
        public BasicFilenameFilter(File directory, String prefix, String suffix) {
            this.directory = directory;
            this.prefix = prefix;
            this.suffix = suffix;
        }
        
        @Override
        public boolean accept(File dir, String name) {
            if(directory != null && !dir.equals(directory)) {
                return false;
            }
            if(prefix != null && !name.startsWith(prefix)) {
                return false;
            }
            return (suffix == null || name.endsWith(suffix));
        }
        
        
    }
}
