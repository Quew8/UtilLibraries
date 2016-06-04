package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng3d.models.collada.Scene;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class ColladaParser extends XMLParser {
    private static final String
            VERSION = "version";
    private static final String
            ASSET = "asset",
            LIB_IMGS = "library_images",
            LIB_FXS = "library_effects",
            LIB_MTLS = "library_materials",
            LIB_GEOMS = "library_geometries",
            LIB_CNTRLRS = "library_controllers",
            LIB_VIS_SCENES = "library_visual_scenes",
            SCENE = "scene";
    
    private String version;
    private AssetParser asset;
    private SceneParser scene;
    
    public ColladaParser() {
        
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(ASSET, (XMLElementParser) (Element element) -> {
            if(asset != null) {
                throw new XMLParseException("COLLADA element cannot have more than one asset element");
            }
            asset = ColladaParser.this.parseWith(element, new AssetParser());            
        });
        to.put(LIB_IMGS, (XMLElementParser) (Element element) -> {});
        to.put(LIB_FXS, (XMLElementParser) (Element element) -> {});
        to.put(LIB_MTLS, (XMLElementParser) (Element element) -> {});
        to.put(LIB_GEOMS, (XMLElementParser) (Element element) -> {});
        to.put(LIB_CNTRLRS, (XMLElementParser) (Element element) -> {});
        to.put(LIB_VIS_SCENES, (XMLElementParser) (Element element) -> {});
        to.put(SCENE, (XMLElementParser) (Element element) -> {
            scene = ColladaParser.this.parseWith(element, new SceneParser());
        });
        return to;
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(VERSION, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            if(version != null) {
                throw new XMLParseException("Cannot have multiple version attributes");
            }
            this.version = attribute.getValue();
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(version == null) {
            return new XMLParseException("COLLADA element must have version attribute");
        }
        if(asset == null) {
            return new XMLParseException("COLLADA element must have one asset element");
        }
        if(scene == null) {
            return new XMLParseException("COLLADA element must have one scene element");
        }
        return super.onParsingDone();
    }
    
    public Scene getScene() {
        return scene.getScene(asset.getAsset());
    }
    
}
