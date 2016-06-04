package com.quew8.geng.rendering;

import com.quew8.geng.GameObject;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.interfaces.Disposable;
import com.quew8.geng.interfaces.FinalDrawable;
import com.quew8.geng.rendering.modes.GeometricDataInterpreter;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.ArrayUtils;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexArrayObject;
import com.quew8.gutils.opengl.VertexBufferObject;
import com.quew8.gutils.opengl.VertexBufferObjectOffsets;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Quew8
 * @param <T> 
 * @param <S> 
 */
abstract class ObjGroupRenderer<T, S extends StaticRenderMode> extends GameObject 
        implements Disposable, FinalDrawable {
    
    private final ObjGroup<T, S>[] groups;
    private final VertexArrayObject[] vaos;
    private final VertexBufferObject vbo;
    private final VertexBufferObjectOffsets ibo;
    
    ObjGroupRenderer(ByteBuffer data, int[][] indices, ObjGroup<T, S>[] groups) {
        this.vbo = new VertexBufferObject(
                data, GL_ARRAY_BUFFER, GL_STATIC_DRAW
        );
        this.ibo = new VertexBufferObjectOffsets(
                indices, GL_ELEMENT_ARRAY_BUFFER, GL_STATIC_DRAW
        );
        this.groups = groups;
        this.vaos = new VertexArrayObject[groups.length];
        this.vbo.bind();
        for(int i = 0; i < groups.length; i++) {
            vaos[i] = new VertexArrayObject();
            vaos[i].bind();
            ibo.getVBO().bind();
            for(int j = 0; j < groups[i].getRenderMode().getNAttribs(); j++) {
                glEnableVertexAttribArray(j);
            }
            groups[i].getRenderMode().onPreRendering(vbo);
        }
        VertexArrayObject.unbind();
        vbo.unbind();
    }

    @Override
    public void draw() {
        for(int i = 0; i < groups.length; i++) {
            vaos[i].bind();
            ibo.getVBO().bind();
            drawGroup(groups[i], vbo);
        }
        VertexArrayObject.unbind();
    }
    
    public abstract void drawGroup(ObjGroup<T, S> group, VertexBufferObject vbo);
    
    public void drawIndices(int offset, int nIndices) {
        glDrawElements(GL_TRIANGLES, nIndices, GL_UNSIGNED_INT, offset);
    }

    public void drawElements(int first, int last) {
        drawIndices(ibo.getOffset(first) * 4, ibo.getOffset(last) - ibo.getOffset(first));
    }

    @Override
    public void dispose() {
        vbo.delete();
        ibo.getVBO().delete();
        for(int i = 0; i < groups.length; i++) {
            vaos[i].delete();
            groups[i].getTexture().dispose();
        }
    }
    
    
    abstract static class AbstractFactory<T, S, U extends StaticRenderMode, P> {
        private final ArrayList<GroupFactory<S, U, P>> groupFactories = new ArrayList<>();
        private int stride;
        
        public int addGroup(U renderMode, Texture texture) {
            if(groupFactories.isEmpty()) {
                stride = renderMode.getStride();
            } else if(stride != renderMode.getStride()) {
                throw new IllegalArgumentException("Stride of new group must be the same as previous groups."
                        + "Current Stride " + stride + ", input " + renderMode.getStride());
            }
            groupFactories.add(new GroupFactory<S, U, P>(this, texture, renderMode));
            return groupFactories.size() - 1;
        }
        
        public <T, S> S[] addSingleObject(int group, P list, GeometricDataInterpreter<T, S> 
                interpreter, T obj) {
            
            return addObject(group, list, interpreter, obj)[0];
        }
        
        public <T, S> S[][] addObject(int group, P list, GeometricDataInterpreter<T, S> 
                interpreter, T... objs) {
            
            return groupFactories.get(group).addObject(list, interpreter, objs);
        }
        
        public T construct() {
            int nBytes = 0;
            int totalObjects = 0;
            for(int i = 0; i < groupFactories.size(); i++) {
                GroupFactory<S, U, P> gf = groupFactories.get(i);
                for(int j = 0; j < gf.objectSets.size(); j++) {
                    ObjectWInterpreter<?> set = gf.objectSets.get(j);
                    totalObjects += set.objects.length;
                    nBytes += set.getNBytes();
                }
            }
            ByteBuffer bb = BufferUtils.createByteBuffer(nBytes);
            int[][] indices = new int[totalObjects][];
            int currentTotalObject = 0;
            int indexOffset = 0;
            ObjGroup<S, U>[] groups = ArrayUtils
                    .<ObjGroup<S, U>>createArray(groupFactories.size());
            for(int i = 0; i < groupFactories.size(); i++) {
                GroupFactory<S, U, P> gf = groupFactories.get(i);
                groups[i] = gf.construct(currentTotalObject);
                for(int j = 0; j < gf.objectSets.size(); j++) {
                    ObjectWInterpreter<?> set = gf.objectSets.get(j);
                    for(int k = 0; k < set.objects.length; k++) {
                        set.addData(k, bb);
                        indices[currentTotalObject] = set.getIndices(k);
                        final int lIndexOffset = indexOffset;
                        indices[currentTotalObject] = Arrays.stream(indices[currentTotalObject])
                                .map((index) -> index + lIndexOffset)
                                .toArray();
                        indexOffset += set.getNVertices(k);
                        currentTotalObject++;
                    }
                }
            }
            bb.flip();
            return construct(bb, indices, groups);
        }
        
        public abstract T construct(ByteBuffer bb, int[][] indices, ObjGroup<S, U>[] groups);
        public abstract S getHandle(P list, int start, int end);
    }
    
    private static class GroupFactory<S, U extends StaticRenderMode, P> {
        private final AbstractFactory<?, S, U, P> factory;
        private final Texture texture;
        private final U renderMode;
        private final ArrayList<S> handles = new ArrayList<>();
        private final ArrayList<ObjectWInterpreter<?>> objectSets = new ArrayList<>();
        
        GroupFactory(AbstractFactory<?, S, U, P> factory, Texture texture, U renderMode) {
            this.factory = factory;
            this.texture = texture;
            this.renderMode = renderMode;
        }
        
        <T, K> K[][] addObject(P list, GeometricDataInterpreter<T, K> interpreter,
                T... objs) {
            
            K[][] array = ArrayUtils.<K[]>createArray(objs.length);
            int start = getTotalObjects();
            for(int i = 0; i < objs.length; i++) {
                array[i] = interpreter.toPositions(objs[i]);
            }
            objectSets.add(new ObjectWInterpreter(objs, interpreter));
            int end = start + objs.length;
            handles.add(factory.getHandle(list, start, end));
            return array;
        }
        
        int getTotalObjects() {
            int n = 0;
            for(int i = 0; i < objectSets.size(); i++) {
                n += objectSets.get(i).objects.length;
            }
            return n;
        }
        
        ObjGroup<S, U> construct(int offset) {
            return new ObjGroup(
                    handles.toArray(ArrayUtils.<S>createArray(handles.size())), 
                    renderMode, 
                    texture, 
                    offset
            );
        }
    }
    
    private static class ObjectWInterpreter<T> {
        private final T[] objects;
        private final GeometricDataInterpreter<T, ?> interpreter;

        ObjectWInterpreter(T[] objects, GeometricDataInterpreter<T, ?> interpreter) {
            this.objects = objects;
            this.interpreter = interpreter;
        }
        
        public void addData(int i, ByteBuffer to) {
            interpreter.addVertexData(to, objects[i]);
        }
        
        public int[] getIndices(int i) {
            return interpreter.getIndices(objects[i]);
        }
        
        public int getNVertices(int i) {
            return interpreter.getNVertices(objects[i]);
        }
        
        public int getNBytes() {
            int nBytes = 0;
            for(int i = 0; i < objects.length; i++) {
                nBytes += interpreter.getNBytes(objects[i]);
            }
            return nBytes;
        }
    }
}
