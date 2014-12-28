package com.quew8.geng.content.parser.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.quew8.geng.content.parser.SourceSheetParser;
import com.quew8.gutils.ResourceLoader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.content.SourceSheet;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class JsonSourceSheetParser implements SourceSheetParser {
    private boolean external = false;
    private final ArrayList<Source> sources = new ArrayList<Source>();
    private String idClassName;
    private String readerClassName;
    
    private static final String
            EXTERNAL = "external",
            ID_CLASS = "id_class",
            READER_CLASS = "reader_class",
            GROUPS = "groups";
    
    @Override
    public void read(String resource, ResourceLoader resourceLoader) {
        JsonElement element = new JsonParser().parse(new InputStreamReader(resourceLoader.load(resource)));
        if(element.isJsonObject()) {
            JsonObject sheetObj = element.getAsJsonObject();
            external = getBoolean(sheetObj, EXTERNAL, false, external);
            idClassName = getString(sheetObj, ID_CLASS, true, null);
            readerClassName = getString(sheetObj, READER_CLASS, true, null);
            if(sheetObj.has(GROUPS)) {
                if(sheetObj.get(GROUPS).isJsonArray()) {
                    JsonArray groupArr = sheetObj.getAsJsonArray(GROUPS);
                    for(JsonElement g: groupArr) {
                        JsonGroupParser gParser = new JsonGroupParser().parse(g);
                        sources.addAll(gParser.getSources());
                    }
                } else {
                    throw new RuntimeException("Type of groups is erronous, expected array found: " + sheetObj.get(GROUPS));
                }
            }
        } else {
            throw new RuntimeException("Type of root is erronous, expected object found: " + element);
        }
    }
    
    @Override
    public SourceSheet getSourceSheet() throws ClassNotFoundException {
        return new SourceSheet(
                external ? 
                        ResourceLoader.EXTERNAL : 
                        ResourceLoader.INTERNAL, 
                getIdClass(), 
                getReaderClass(), 
                getSources()
        );
    }
    
    @Override
    public com.quew8.gutils.content.Source[] getSources() {
        return sources.toArray(new com.quew8.gutils.content.Source[sources.size()]);
    }
    
    @Override
    public com.quew8.gutils.content.Source getSource(int i) {
        return sources.get(i);
    }
    
    @Override
    public int getNSources() {
        return sources.size();
    }
    
    @Override
    public String getIdClassName() {
        return idClassName;
    }
    
    private Class<?> getIdClass() throws ClassNotFoundException {
        return getClass().getClassLoader().loadClass(idClassName);
    }
    
    @Override
    public String getReaderClassName() {
        return readerClassName;
    }
    
    private Class<?> getReaderClass() throws ClassNotFoundException {
        return getClass().getClassLoader().loadClass(readerClassName);
    }
    
    protected static String getPrimitiveAsString(JsonObject obj, String parameter, boolean required, String defaultVal) {
        if(obj.has(parameter)) {
            JsonElement element = obj.get(parameter);
            return parsePrimitiveAsString(element, parameter);
        } else if(required) {
            throw new RuntimeException("Missing parameter: \"" + parameter + "\"");
        } else {
            return defaultVal;
        }
    }
    
    protected static String parsePrimitiveAsString(JsonElement element, String parameter) {
        if(element.isJsonPrimitive()) {
            JsonPrimitive prim = element.getAsJsonPrimitive();
            if(prim.isString()) {
                return prim.getAsString();
            } else if(prim.isBoolean()) {
                return Boolean.toString(prim.getAsBoolean());
            } else if(prim.isNumber()) {
                return prim.getAsNumber().toString();
            }
        }
        throw new RuntimeException("Type of \"" + parameter + "\" is erronous, expected primitive, found " + element);
    }
    
    protected static boolean getBoolean(JsonObject obj, String parameter, boolean required, boolean defaultVal) {
        if(obj.has(parameter)) {
            JsonElement element = obj.get(parameter);
            return parseBoolean(element, parameter);
        } else if(required) {
            throw new RuntimeException("Missing parameter: \"" + parameter + "\"");
        } else {
            return defaultVal;
        }
    }
    
    protected static boolean parseBoolean(JsonElement element, String parameter) {
        if(element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
            return element.getAsJsonPrimitive().getAsBoolean();
        } else {
            throw new RuntimeException("Type of \"" + parameter + "\" is erronous, expected boolean, found " + element);
        }
    }
    
    protected static int getInteger(JsonObject obj, String parameter, boolean required, int defaultVal) {
        if(obj.has(parameter)) {
            JsonElement element = obj.get(parameter);
            return parseInteger(element, parameter);
        } else if(required) {
            throw new RuntimeException("Missing parameter: \"" + parameter + "\"");
        } else {
            return defaultVal;
        }
    }
    
    protected static int parseInteger(JsonElement element, String parameter) {
        if(element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsInt();
        } else {
            throw new RuntimeException("Type of \"" + parameter + "\" is erronous, expected number, found " + element);
        }
    }
    
    protected static String getString(JsonObject obj, String parameter, boolean required, String defaultValue) {
        if(obj.has(parameter)) {
            JsonElement element = obj.get(parameter);
            return parseString(element, parameter);
        } else if(required) {
            throw new RuntimeException("Missing parameter: \"" + parameter + "\"");
        } else {
            return defaultValue;
        }
    }
    
    protected static String parseString(JsonElement element, String parameter) {
        if(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return element.getAsJsonPrimitive().getAsString();
        } else {
            throw new RuntimeException("Type of \"" + parameter + "\" is erronous, expected string, found " + element);
        }
    }
}
