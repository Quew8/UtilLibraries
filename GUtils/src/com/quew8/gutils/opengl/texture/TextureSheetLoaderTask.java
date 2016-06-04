package com.quew8.gutils.opengl.texture;

import static com.quew8.gutils.opengl.OpenGL.GL_RGB;
import static com.quew8.gutils.opengl.OpenGL.GL_RGBA;

import com.quew8.gutils.PlatformUtils;
import static com.quew8.gutils.opengl.OpenGL.GL_TEXTURE_2D;
import com.quew8.gutils.threads.WorkerTask;

/**
 * Task to load an array of images and load these into an OpenGL texture grid
 * wise. Must be supplied with the number of columns and rows in the grid as
 * well as the dimensions of each cell of the grid. Images are loaded in column-
 * major order.
 *
 * @author Quew8
 */
class TextureSheetLoaderTask extends WorkerTask<TextureSheetLoaderTask, TextureLoadVariables, Void, TextureLoadVariables> {
    private final int cellWidth, cellHeight;
    private final int borderSize;
    private final int nColumns, nRows;
    private LoadedImage[] images;

    TextureSheetLoaderTask(int cellWidth, int cellHeight, int borderSize,
            int nColumns, int nRows) {

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.borderSize = borderSize;
        this.nColumns = nColumns;
        this.nRows = nRows;
    }

    @Override
    public TextureLoadVariables work(TextureLoadVariables input) {
        images = new LoadedImage[input.imageLoaders.length];
        for(int i = 0; i < images.length; i++) {
            images[i] = input.imageLoaders[i].getLoadedImage();
            if(images[i].getWidth() > cellWidth || images[i].getHeight() > cellHeight) {
                throw new IllegalArgumentException("Texture is greater than supplied size");
            }
        }
        return input;
    }

    @Override
    public void onPostWork(TextureLoadVariables input) {
        int destFormat
                = input.destFormat < 0
                        ? images[0].hasAlpha()
                                ? GL_RGBA
                                : GL_RGB
                        : input.destFormat;
        input.texture.bind();
        TextureUtils.fillEmptyTexture(input.texture, input.texWidth, input.texHeight, destFormat, input.texParams);
        int sx = cellWidth + borderSize;
        int sy = cellHeight + borderSize;
        for(int x = 0, j = 0; x < nColumns; x++) {
            for(int y = 0; y < nRows; y++, j++) {
                PlatformUtils.fillSubTexture(sx * x, sy * y, images[j]);
                images[j].unload();
            }
        }
        input.texParams.setAllParams(GL_TEXTURE_2D);
    }

}
