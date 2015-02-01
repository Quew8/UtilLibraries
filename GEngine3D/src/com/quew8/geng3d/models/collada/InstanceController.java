package com.quew8.geng3d.models.collada;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.models.DataFactory;
import com.quew8.geng3d.models.collada.parser.ControllerParser;

/**
 *
 * @author Quew8
 */
public class InstanceController extends AbstractNode {
    private final String name;
    private final Node[] joints;
    private final ControllerParser skin;
    
    public InstanceController(String name, Node[] joints, ControllerParser skin) {
        this.name = name;
        this.joints = joints;
        this.skin = skin;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public <T> T getSkin(DataFactory<?, ?, ?, T> factory, Image texture) {
        return skin.getSkin(joints, factory, texture);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    
    @Override
    public String getDesc(String prefix) {
        return prefix + "InstanceController\n" +
                prefix + "Name: " + name + "\n";
    }
}
