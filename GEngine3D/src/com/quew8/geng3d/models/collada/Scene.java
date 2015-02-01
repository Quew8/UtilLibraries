package com.quew8.geng3d.models.collada;

/**
 *
 * @author Quew8
 */
public class Scene {
    private final Asset asset;
    private final InstanceVisualScene visualScene;
    
    public Scene(Asset asset, InstanceVisualScene visualScene) {
        this.asset = asset;
        this.visualScene = visualScene;
    }
    
    public Asset getAsset() {
        return asset;
    }
    
    public InstanceVisualScene getVisualScene() {
        return visualScene;
    }
    
    public InstanceController findController(String name) {
        if(name.startsWith("#")) {
            return getVisualScene().getController(name.substring(1));
        }
        return getVisualScene().findController(name);
    }
    
    public InstanceGeometry findGeometry(String name) {
        if(name.startsWith("#")) {
            return getVisualScene().getGeometry(name.substring(1));
        }
        return getVisualScene().findGeometry(name);
    }
    
    public InstanceController getController(String path) {
        return getVisualScene().getController(path);
    }
    
    public InstanceGeometry getGeometry(String path) {
        return getVisualScene().getGeometry(path);
    }
    
    public InstanceController[] getControllers() {
        return getVisualScene().getAllControllers();
    }
    
    public InstanceGeometry[] getGeometry() {
        return getVisualScene().getAllGeometry();
    }
    
    @Override
    public String toString() {
        return visualScene.getString("");
    }
}
