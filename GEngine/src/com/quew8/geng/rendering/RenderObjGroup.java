package com.quew8.geng.rendering;

import com.quew8.geng.GameObject;
import com.quew8.geng.interfaces.Disposable;
import com.quew8.geng.interfaces.FinalDrawable;
import com.quew8.geng.rendering.modes.interfaces.GeometricDataInterpreter;

import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexBuffer;
import com.quew8.gutils.opengl.VertexBuffer.VariableLengthGroupedVertexBuffer;
import com.quew8.gutils.opengl.VertexData;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public class RenderObjGroup<T> extends GameObject implements Disposable, FinalDrawable {
    private final RenderObjectGroupSection<?, ?>[] sections;
    private final VertexData data;
    private final VariableLengthGroupedVertexBuffer ibo;
    
    RenderObjGroup(T[] ta, GeometricDataInterpreter<T> interpreter, RenderObjectGroupSection<?, ?>[] sections) {
        this.data = new VertexBuffer(
                interpreter.toVertexData(ta), GL_ARRAY_BUFFER, GL_STATIC_DRAW
                );
        this.ibo = new VariableLengthGroupedVertexBuffer(
                interpreter.toIndexData(ta), GL_ELEMENT_ARRAY_BUFFER, GL_STATIC_DRAW
                );
        this.sections = sections;
        for(RenderObjectGroupSection<?, ?> as: sections) {
            as.setOuter(this);
        }
    }
    
    public void bindAll() {
        data.bind();
        ibo.bind();
    }

    @Override
    public void draw() {
        bindAll();
        for(int i = 0; i < sections.length; i++) {
            sections[i].sectionDraw(data);
        }
    }
    
    public void drawIndices(int offset, int nIndices) {
        glDrawElements(GL_TRIANGLES, nIndices, GL_UNSIGNED_INT, offset);
    }

    public void drawElements(int first, int last) {
        drawIndices(ibo.getByteOffsetOf(first), ibo.getOffsetOf(last)-ibo.getOffsetOf(first));
    }

    @Override
    public void dispose() {
        data.delete();
        ibo.delete();
        for(int i = 0; i < sections.length; i++) {
            sections[i].sectionDispose();
        }
    }
}
