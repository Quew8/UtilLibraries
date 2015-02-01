package com.quew8.geng3d.models.collada;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.models.MeshDataFactory;
import com.quew8.geng3d.models.collada.parser.GeometryParser;

/**
 *
 * @author Quew8
 */
public class InstanceGeometry extends AbstractNode {
    private final String name;
    private final GeometryParser geometry;
    
    public InstanceGeometry(String name, GeometryParser geometry) {
        this.name = name;
        this.geometry = geometry;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public <T> T getGeometry(MeshDataFactory<T, ?> factory, Image texture) {
        return geometry.getGeometry(factory, texture);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    
    @Override
    public String getDesc(String prefix) {
        return prefix + "InstanceGeometry\n" +
                prefix + "Name: " + name + "\n";
    }
}
