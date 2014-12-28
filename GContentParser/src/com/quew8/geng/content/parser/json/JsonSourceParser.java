package com.quew8.geng.content.parser.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.quew8.gutils.content.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Quew8
 */
class JsonSourceParser {
    private static final String
            ID_STRING = "id",
            INPUTS = "inputs";
    
    private final String dir;
    private String idString = null;
    private final HashMap<Integer, String> indexedSources = new HashMap<Integer, String>();
    private final ArrayList<String> unindexedSources = new ArrayList<String>();
    private final HashMap<String, String> params = new HashMap<String, String>();
    private final HashMap<String, Map.Entry<String, String>[]> paramLists = new HashMap<String, Map.Entry<String, String>[]>();
    
    JsonSourceParser(String dir) {
        this.dir = dir;
    }
    
    public JsonSourceParser parse(JsonElement element) {
        if(element.isJsonObject()) {
            JsonObject sourceObj = element.getAsJsonObject();
            for(Entry<String, JsonElement> entry: sourceObj.entrySet()) {
                switch(entry.getKey()) {
                    case ID_STRING: {
                        idString = JsonSourceSheetParser.parseString(entry.getValue(), ID_STRING);
                        break;
                    }
                    case INPUTS: {
                        if(entry.getValue().isJsonArray()) {
                            for(JsonElement input: entry.getValue().getAsJsonArray()) {
                                if(input.isJsonObject()) {
                                    JsonInputParser inputParser = new JsonInputParser().parse(input);
                                    if(inputParser.getIndex() != -1) {
                                        indexedSources.put(inputParser.getIndex(), inputParser.getSource());
                                    } else {
                                        unindexedSources.add(inputParser.getSource());
                                    }
                                } else {
                                    unindexedSources.add(JsonSourceSheetParser.parseString(input, "source"));
                                }
                            }
                        } else {
                            throw new RuntimeException("Erronous type of inputs, expected array found " + entry.getValue());
                        }
                        break;
                    }
                    default: {
                        if(entry.getValue().isJsonArray()) {
                            for(JsonElement param: entry.getValue().getAsJsonArray()) {
                                if(param.isJsonObject()) {
                                    JsonObject paramObj = param.getAsJsonObject();
                                    HashMap<String, String> set = new HashMap<String, String>();
                                    for(Entry<String, JsonElement> paramListEntry: paramObj.entrySet()) {
                                        set.put(paramListEntry.getKey(), JsonSourceSheetParser.parsePrimitiveAsString(paramListEntry.getValue(), "param_list_entry_value"));
                                    }
                                    paramLists.put(entry.getKey(), set.entrySet().toArray(JsonSourceParser.<Entry<String, String>>getArray(set.size())));
                                } else {
                                    throw new RuntimeException("Erronous type of param_list_entry, expected object, found " + param);
                                }
                            }
                        } else {
                            params.put(entry.getKey(), JsonSourceSheetParser.parseString(entry.getValue(), "param_value"));
                        }
                        break;
                    }
                }
            }
            return this;
        } else {
            throw new RuntimeException("Type of source is erronous, expected object found " + element);
        }
    }
    
    public Source getSource() {
        String[] sources = new String[indexedSources.size() + unindexedSources.size()];
        int j = 0;
        for(int i = 0; i < sources.length; i++) {
            if(indexedSources.containsKey(i)) {
                sources[i] = dir + indexedSources.get(i);
            } else {
                if(j >= unindexedSources.size()) {
                    throw new RuntimeException("Not enough unindexed sources to fill gaps in indexed sources");
                } else {
                    sources[i] = dir + unindexedSources.get(j++);
                }
            }
        }
        String id = idString == null ? getIdString(sources) : idString;
        return new Source(id, Arrays.asList(sources), params, paramLists);
    }
    
    private static String getIdString(String[] sources) {
        String s = getIdString(sources[0]);
        for(int i = 1; i < sources.length; i++) {
            s += getIdString(sources[i]);
        }
        return s;
    }
    
    private static String getIdString(String source) {
        int i = source.lastIndexOf('/');
        if(i == -1) {
            i = source.lastIndexOf('\\');
        }
        String id = source;
        if(i != -1) {
            id = id.substring(i + 1, id.length());
        }
        return id.replace('.', '_');
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(int length, T... array) {
        return Arrays.copyOf(array, length);
    }
}
