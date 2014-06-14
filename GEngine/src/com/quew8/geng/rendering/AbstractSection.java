package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.gutils.opengl.VertexData;

/**
 *
 * @author Quew8
 */
abstract class AbstractSection<T, S> {
    protected final S instanceRenderMode;
    protected Image image;
    protected final T[] handles;
    protected MeshGroup<?> outer;

    public AbstractSection(S instanceRenderMode, Image image, T[] handles) {
        this.instanceRenderMode = instanceRenderMode;
        this.image = image;
        this.handles = handles;
    }

    protected void sectionBind() {
        image.bind();
    }

    protected abstract void sectionDraw(VertexData vd);

    protected void sectionDispose() {
        image.dispose();
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    protected final void setOuter(MeshGroup<?> outer) {
        this.outer = outer;
    }
    
}
