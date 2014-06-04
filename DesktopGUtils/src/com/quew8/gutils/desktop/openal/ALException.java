package com.quew8.gutils.desktop.openal;

import org.lwjgl.openal.AL10;

/**
 * 
 * @author Quew8
 */
public class ALException extends RuntimeException {
    
    public ALException(String errorString) {
        super("AL Error: " + errorString);
    }
    
    public static void checkALError() {
        int error = AL10.alGetError();
        if(error != AL10.AL_NO_ERROR) {
            switch(error) {
                case AL10.AL_INVALID_NAME: throw new ALException("Invalid Name");
                case AL10.AL_INVALID_ENUM: throw new ALException("Invalid Enum");
                case AL10.AL_INVALID_VALUE: throw new ALException("Invalid Value");
                case AL10.AL_INVALID_OPERATION: throw new ALException("Invalid Operation");
                case AL10.AL_OUT_OF_MEMORY: throw new ALException("Out of Memory");
                default: throw new ALException("Unknown Error: " + error);
            }
            
        }
    }
}
