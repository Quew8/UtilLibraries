package com.quew8.gutils.desktop.openal;

import com.quew8.gutils.BufferUtils;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

/**
 * 
 * @author Quew8
 */
public class ALUtils {
    /*private static HashMap<Integer, IntBuffer> buffers;
    
    private ALUtils() {
        
    }
    
    public static void init() {
        try {
            AL.create();
            ALException.checkALError();
            buffers = new HashMap<>();
        } catch (LWJGLException ex) {
            System.err.println("AL context could not be created: ");
            Logger.getLogger(ALUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    public static int getIDForBuffer(int name) {
        return buffers.get(name).get(0);
    }
    
    public static void createBuffer(int id, String ref) {
        WaveData wd;
        IntBuffer idBuff = BufferUtils.createIntBuffer(1);
        idBuff.put(new int[]{0});
        idBuff.rewind();
        AL10.alGenBuffers(idBuff);
        ALException.checkALError();
        ref = "resources/sounds/" + ref;
        wd = WaveData.create(ALUtils.class.getClassLoader().getResourceAsStream(ref));
        if(wd == null) {
            throw new RuntimeException("An exception occured loading " + ref);
        }
        AL10.alBufferData(idBuff.get(0), wd.format, wd.data, wd.samplerate);
        wd.dispose();
        ALException.checkALError();
        buffers.put(id, idBuff);
    }
    
    public static void destroyBuffer(int id) {
        AL10.alDeleteBuffers(id);
        buffers.remove(id);
    }
    
    public static void destroy() {
        for (Entry<Integer, IntBuffer> entry : buffers.entrySet()) {
            IntBuffer id = entry.getValue();
            AL10.alDeleteBuffers(id);
            buffers.remove(entry.getKey());
        }
        AL.destroy();
    }*/
}
