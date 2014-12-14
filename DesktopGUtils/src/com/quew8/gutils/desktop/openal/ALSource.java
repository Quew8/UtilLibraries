package com.quew8.gutils.desktop.openal;

import com.quew8.gutils.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.openal.AL10;

/**
 * 
 * @author Quew8
 */
public class ALSource {
    /*private final IntBuffer idBuff;
    
    public ALSource(int bufferName, boolean loop, float[] position, float[] velocity, float pitch, float gain) {
        idBuff = BufferUtils.createIntBuffer(1);
        AL10.alGenSources(idBuff);
        ALException.checkALError();
        FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3);
        FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3);
        sourcePos.put(position);
        sourcePos.rewind();
        sourceVel.put(velocity);
        sourceVel.rewind();
        AL10.alSourcei(idBuff.get(0), AL10.AL_BUFFER, ALUtils.getIDForBuffer(bufferName));
        AL10.alSourcef(idBuff.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(idBuff.get(0), AL10.AL_GAIN, 1.0f);
        AL10.alSource(idBuff.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSource(idBuff.get(0), AL10.AL_VELOCITY, sourceVel);
        AL10.alSourcei(idBuff.get(0), AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
        ALException.checkALError();
    }
    
    public void play() {
        AL10.alSourcePlay(idBuff);
    }
    
    public void pause() {
        AL10.alSourcePause(idBuff);
    }
    
    public void stop() {
        AL10.alSourceStop(idBuff);
    }
    
    public void destroy() {
        AL10.alDeleteSources(idBuff);
    }*/
}
