package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.geometry.Texture;
import com.quew8.gutils.opengl.VertexData;

/**
 *
 * @author Quew8
 */
class DynamicRenderObjSection<T> extends RenderObjectGroupSection<DynamicHandleList<T>.DynamicHandle, DynamicRenderMode<T>> {
    
    public DynamicRenderObjSection(DynamicRenderMode<T> instanceRenderMode, Texture image, DynamicHandleList<T>.DynamicHandle[] handles) {
        super(instanceRenderMode, image, handles);
    }

    @Override
    public void sectionDraw(VertexData vd) {
        instanceRenderMode.onPreRendering(vd);
        RenderState.setRenderMode(instanceRenderMode);
        instanceRenderMode.bindGeneralTexture(tex);
        for(int i = 0; i < handles.length; i++) {
            while(handles[i].hasNext()) {
                if(handles[i].shouldDrawNext()) {
                    RenderState.setModelMatrix(handles[i].getNextMatrixForDraw());
                    instanceRenderMode.onPreDraw(handles[i].getNextData());
                    outer.drawElements(handles[i].getStart(), handles[i].getEnd());
                    instanceRenderMode.onPostDraw(handles[i].getNextData());
                }
            }
        }
        instanceRenderMode.onPostRendering();
    }
    
}
