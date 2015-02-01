package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Texture;
import com.quew8.gutils.opengl.VertexData;

/**
 *
 * @author Quew8
 */
abstract class RenderObjectGroupSection<T, S> {
    protected final S instanceRenderMode;
    protected Texture tex;
    protected final T[] handles;
    protected RenderObjGroup<?> outer;

    RenderObjectGroupSection(S instanceRenderMode, Texture tex, T[] handles) {
        this.instanceRenderMode = instanceRenderMode;
        this.tex = tex;
        this.handles = handles;
    }

    protected void sectionBind() {
        tex.bind();
    }

    protected abstract void sectionDraw(VertexData vd);

    protected void sectionDispose() {
        tex.dispose();
    }

    public void setTexture(Texture tex) {
        this.tex = tex;
    }
    
    protected final void setOuter(RenderObjGroup<?> outer) {
        this.outer = outer;
    }
    
}
