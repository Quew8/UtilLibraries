package com.quew8.gutils.opengl.texture;

import com.quew8.gutils.PlatformBackend;
import com.quew8.gutils.opengl.GLException;
import com.quew8.gutils.threads.WorkerTask;

import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 * @param <T>
 * @param <S> 
 */
class TextureLoaderTask extends WorkerTask<TextureLoaderTask, TextureLoadVariables, Void, TextureLoadVariables> {
	private LoadedImage loadedImage;
	
	@Override
    public TextureLoadVariables work(TextureLoadVariables input) {
		loadedImage = input.imageLoaders[0].getLoadedImage();
        return input;
    }

    @Override
    public void onPostWork(TextureLoadVariables input) {
        GLException.checkGLError();
        input.texture.bind();
        GLException.checkGLError();
        for(int i = 0; i < input.texParams.getNParams(); i++) {
            glTexParameteri(GL_TEXTURE_2D, input.texParams.getPName(i), input.texParams.getParam(i));
            GLException.checkGLError();
        }
        GLException.checkGLError();
        int destFormat = 
        		input.destFormat < 0 ? 
        		loadedImage.hasAlpha() ? 
        			GL_RGBA : 
        			GL_RGB : 
        		input.destFormat;
    	PlatformBackend.backend.fillTexture_P(loadedImage, destFormat, input.texWidth, input.texHeight);
        input.texParams.run();
        GLException.checkGLError();
        loadedImage.unload();
    }
}
