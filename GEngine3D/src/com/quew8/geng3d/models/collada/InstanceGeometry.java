package com.quew8.geng3d.models.collada;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.models.MeshDataFactory;
import com.quew8.geng3d.models.collada.parser.GeometryParser;

/**
 *
 * @author Quew8
 */
public class InstanceGeometry extends AbstractNode {
    private final String instanceName;
    private final String geometryName;
    private final GeometryParser geometry;
    
    public InstanceGeometry(String instanceName, String geometryName, GeometryParser geometry) {
        this.instanceName = instanceName;
        this.geometryName = geometryName;
        this.geometry = geometry;
    }

    @Override
    public String getName() {
        return instanceName;
    }

    public String getGeometryName() {
        return geometryName;
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
                prefix + "Instance Name: " + instanceName + "\n" +
                prefix + "Geometry Name: " + geometryName + "\n";
    }
}
