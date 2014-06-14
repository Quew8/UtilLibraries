package com.quew8.geng3d.collada;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class Scene<T, S> {
    private final InstanceVisualScene<T, S> visualScene;
    
    public Scene(InstanceVisualScene<T, S> visualScene) {
        this.visualScene = visualScene;
    }
    
    public InstanceVisualScene<T, S> getVisualScene() {
        return visualScene;
    }
    
    public InstanceController<S> findController(String name) {
        return getVisualScene().findController(name);
    }
    
    public InstanceGeometry<T> findGeometry(String name) {
        return getVisualScene().findGeometry(name);
    }
    
    @SuppressWarnings("unchecked")
    public InstanceController<S>[] getControllers() {
        ArrayList<InstanceController<S>> to = new ArrayList<InstanceController<S>>();
        visualScene.addControllers(to);
        return to.toArray(Scene.<InstanceController<S>>getArray(to.size()));
    }
    
    @SuppressWarnings("unchecked")
    public InstanceGeometry<T>[] getGeometry() {
        ArrayList<InstanceGeometry<T>> to = new ArrayList<InstanceGeometry<T>>();
        visualScene.addGeometry(to);
        return to.toArray(Scene.<InstanceGeometry<T>>getArray(to.size()));
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(int length, T... array) {
        return Arrays.copyOf(array, length);
    }
}
