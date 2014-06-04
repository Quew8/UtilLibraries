package com.quew8.gutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Quew8
 */
public class FileUtils {

    private FileUtils() {
        
    }
    
    public static void copyDirectory(final File from, final File to, final FileDuplicateBehaviour dirDuplicateBehaviour, final FileDuplicateBehaviour fileDuplicateBehaviour) {
        try {
            Files.walkFileTree(from.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path relDir = from.toPath().relativize(dir);
                    Path cpPath = to.toPath().resolve(relDir);
                    if(cpPath.toFile().exists()) {
                        switch(dirDuplicateBehaviour) {
                        case IGNORE: { break; }
                        case REPLACE: {
                            deleteDirectory(cpPath.toFile());
                            cpPath.toFile().mkdir();
                            return FileVisitResult.CONTINUE;
                        }
                        case CANCEL: { return FileVisitResult.TERMINATE; }
                        default: {
                            throw new IllegalArgumentException("dirDuplicateBehaviour was not recognized");
                        }
                        }
                    }
                    cpPath.toFile().mkdir();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path relDir = from.toPath().relativize(file);
                    Path cpPath = to.toPath().resolve(relDir);
                    if(cpPath.toFile().exists()) {
                        switch(fileDuplicateBehaviour) {
                        case IGNORE: { return FileVisitResult.CONTINUE; }
                        case REPLACE: {
                            Files.delete(file);
                            break;
                        }
                        case CANCEL: { return FileVisitResult.TERMINATE; }
                        default: {
                            throw new IllegalArgumentException("fileDuplicateBehaviour was not recognized");
                        }
                        }
                    }
                    Files.copy(file, cpPath);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void copyDirectory(final File from, final File to) {
        copyDirectory(from, to, FileDuplicateBehaviour.IGNORE, FileDuplicateBehaviour.REPLACE);
    }
    
    public static void deleteDirectory(File dir) {
        try {
            Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
                
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean isEmpty(File dir) {
        return dir.listFiles().length == 0;
    }
    
    public static enum FileDuplicateBehaviour {
        IGNORE, REPLACE, CANCEL;
    }
}
