package com.quew8.geng.rendering;

import com.quew8.geng.GameObject;
import com.quew8.geng.interfaces.Disposable;
import com.quew8.geng.interfaces.FinalDrawable;
import com.quew8.geng.rendering.modes.GeometricDataInterpreter;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexBuffer;
import com.quew8.gutils.opengl.VertexBuffer.VariableLengthGroupedVertexBuffer;
import com.quew8.gutils.opengl.VertexData;
import java.nio.ByteBuffer;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public class RenderObjGroup<T> extends GameObject implements Disposable, FinalDrawable {
    private final RenderObjectGroupSection<?, ?>[] sections;
    private final VertexData data;
    private final VariableLengthGroupedVertexBuffer ibo;
    
    RenderObjGroup(T[] ta, GeometricDataInterpreter<T, ?> interpreter, RenderObjectGroupSection<?, ?>[] sections) {
        ByteBuffer bb = createDataBuffer(ta, interpreter);
        System.out.println("Creating Buffer: " + BufferUtils.toString(bb.asFloatBuffer(), 40));
        this.data = new VertexBuffer(
                bb, GL_ARRAY_BUFFER, GL_STATIC_DRAW
                );
        this.ibo = new VariableLengthGroupedVertexBuffer(
                createIndexBuffer(ta, interpreter), GL_ELEMENT_ARRAY_BUFFER, GL_STATIC_DRAW
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
        drawIndices(ibo.getByteOffsetOf(first), ibo.getOffsetOf(last) - ibo.getOffsetOf(first));
    }

    @Override
    public void dispose() {
        data.delete();
        ibo.delete();
        for(int i = 0; i < sections.length; i++) {
            sections[i].sectionDispose();
        }
    }
    
    private static <T> ByteBuffer createDataBuffer(T[] objs, GeometricDataInterpreter<T, ?> interpreter) {
        if(objs.length == 0) {
            throw new IllegalArgumentException("");
        }
        int length = interpreter.getNBytes(objs[0]);
        for(int i = 1; i < objs.length; i++) {
            length += interpreter.getNBytes(objs[i]);
        }
        ByteBuffer bb = BufferUtils.createByteBuffer(length);
        for(int i = 0; i < objs.length; i++) {
            interpreter.addVertexData(bb, objs[i]);
        }
        bb.flip();
        return bb;
    }
    
    private static <T> int[][] createIndexBuffer(T[] objs, GeometricDataInterpreter<T, ?> interpreter) {
        if(objs.length == 0) {
            throw new IllegalArgumentException("");
        }
        int[][] indices = new int[objs.length][];
        for(int i = 0; i < objs.length; i++) {
            indices[i] = interpreter.getIndices(objs[i]);
        }
        return indices;
    }
}
