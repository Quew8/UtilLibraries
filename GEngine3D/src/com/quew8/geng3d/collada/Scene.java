package com.quew8.geng3d.collada;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class Scene<T, S> {
    private final Asset asset;
    private final InstanceVisualScene<T, S> visualScene;
    
    public Scene(Asset asset, InstanceVisualScene<T, S> visualScene) {
        this.asset = asset;
        this.visualScene = visualScene;
    }
    
    public Asset getAsset() {
        return asset;
    }
    
    public InstanceVisualScene<T, S> getVisualScene() {
        return visualScene;
    }
    
    public InstanceController<S> findController(String name) {
        if(name.startsWith("#")) {
            return getVisualScene().getController(name.substring(1));
        }
        return getVisualScene().findController(name);
    }
    
    public InstanceGeometry<T> findGeometry(String name) {
        if(name.startsWith("#")) {
            return getVisualScene().getGeometry(name.substring(1));
        }
        return getVisualScene().findGeometry(name);
    }
    
    public InstanceController<S> getController(String path) {
        return getVisualScene().getController(path);
    }
    
    public InstanceGeometry<T> getGeometry(String path) {
        return getVisualScene().getGeometry(path);
    }
    
    public InstanceController<S>[] getControllers() {
        return getVisualScene().getAllControllers();
    }
    
    public InstanceGeometry<T>[] getGeometry() {
        return getVisualScene().getAllGeometry();
    }
    
    @Override
    public String toString() {
        return visualScene.getString("");
    }
}
