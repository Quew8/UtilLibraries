package com.quew8.gutils.opengl.texture;

import com.quew8.gutils.PlatformBackend;
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
        input.texture.bind();
        for(int i = 0; i < input.texParams.getNParams(); i++) {
            glTexParameteri(GL_TEXTURE_2D, input.texParams.getPName(i), input.texParams.getParam(i));
        }
    	PlatformBackend.backend.fillTexture_P(loadedImage, input.destFormat, input.texWidth, input.texHeight);
        input.texParams.run();
        loadedImage.unload();
    }
}
