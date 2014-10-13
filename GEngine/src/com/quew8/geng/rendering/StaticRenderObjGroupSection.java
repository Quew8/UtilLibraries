package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.geometry.Texture;
import com.quew8.gutils.opengl.VertexData;

/**
 *
 * @author Quew8
 */
class StaticRenderObjGroupSection extends RenderObjectGroupSection<StaticHandleList.StaticHandle, StaticRenderMode> {

    StaticRenderObjGroupSection(StaticRenderMode instanceRenderMode, Texture image, StaticHandleList.StaticHandle[] handles) {
        super(instanceRenderMode, image, handles);
    }

    @Override
    public void sectionDraw(VertexData vd) {
        int i = 0;
        sectionBind();
        instanceRenderMode.onPreRendering(vd);
        RenderState.setRenderMode(false, instanceRenderMode);
        while (i < handles.length) {
            int start = handles[i].getStart();
            if (handles[i].shouldDraw()) {
                do {
                    handles[i].drawn();
                    i++;
                } while (i < handles.length && handles[i].shouldDraw());
                outer.drawElements(start, handles[i - 1].getEnd());
            }
        }
        instanceRenderMode.onPostRendering();
    }
    
}
