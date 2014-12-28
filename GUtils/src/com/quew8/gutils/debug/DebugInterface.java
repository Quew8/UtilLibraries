package com.quew8.gutils.debug;

import java.util.List;

/**
 *
 * @author Quew8
 */
public interface DebugInterface {
    public String debugGetValue(String param);
    public String debugSetValue(String param, String... value);
    public DebugInterface debugGetObj(String param);
    public void debugOnChangeObj(String in);
    public void debugAddAllParams(List<String> objs, List<String> vals);
}
