package com.quew8.gutils.debug;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public class DebugInterfaceParser {
    private static final Pattern SET_PATTERN = Pattern.compile("set[\\s]+([\\w\\.]+)([\\s]+([\\w\\-][\\w\\s\\-\\.]*))");
    private static final Pattern GET_PATTERN = Pattern.compile("get[\\s]+([\\w\\.]+)");
    private static final Pattern SHOW_PATTERN = Pattern.compile("show([\\s]+([\\w\\.]+))?");
    private String inputString = "";
    private final InputStream in;
    private final PrintStream out;

    public DebugInterfaceParser(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void parse(DebugInterface root) {
        try {
            while(in.available() > 0) {
                char c = (char) in.read();
                if(c == -1) {
                    return;
                } else if(c == '\n' || c == '\r') {
                    try {
                        Matcher m;
                        if((m = GET_PATTERN.matcher(inputString)).matches()) {
                            FetchResult fetch = fetch(root, m.group(1));
                            out.println(fetch.di.debugGetValue(fetch.param));
                        } else if((m = SET_PATTERN.matcher(inputString)).matches()) {
                            FetchResult fetch = fetch(root, m.group(1));
                            String[] values = m.group(3).split("[\\s]+");
                            if(values.length == 0) {
                                out.println("Set command cannot have no arguments");
                                return;
                            }
                            fetch.di.debugSetValue(fetch.param, values);
                            registerChange(root, m.group(1));
                        } else if((m = SHOW_PATTERN.matcher(inputString)).matches()) {
                            String path = m.group(2);
                            DebugInterface di;
                            if(path == null || path.isEmpty()) {
                                di = root;
                            } else {
                                String[] sections = path.split("\\.");
                                di = fetchObject(root, sections, sections.length);
                            }
                            showAvailableParams(di);
                            showAvailableObjects(di);
                        } else {
                            System.out.println("No Match");
                        }
                    } catch(DebugObjectNotFoundException ex) {
                        out.println(ex.getMessage());
                        out.println("Available Object Params:");
                        String[] objs = ex.getIn().debugGetObjects();
                        for(int j = 0; j < objs.length; j++) {
                            out.println("    " + objs[j]);
                        }
                    } catch(DebugParamNotFoundException ex) {
                        out.println(ex.getMessage());
                        out.println("Available Params:");
                        String[] params = ex.getIn().debugGetParams();
                        for(int j = 0; j < params.length; j++) {
                            out.println("    " + params[j]);
                        }
                    } catch(DebugNullObjectException ex) {
                        out.println(ex.getMessage());
                    } catch(DebugException ex) {
                        out.println(ex.getMessage());
                    }
                    inputString = "";
                } else {
                    inputString += c;
                }
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void showAvailableParams(DebugInterface di) {
        String[] params = di.debugGetParams();
        if(params.length == 0) {
            out.println("No Available Params In \"" + di.debugGetName() + "\":");
        } else {
            out.println("Available Params In \"" + di.debugGetName() + "\":");
            for(int j = 0; j < params.length; j++) {
                out.println("    " + params[j]);
            }
        }
    }
    
    private void showAvailableObjects(DebugInterface di) {
        String[] objs = di.debugGetObjects();
        if(objs.length == 0) {
            out.println("No Available Objects In \"" + di.debugGetName() + "\":");
        } else {
            out.println("Available Objects In \"" + di.debugGetName() + "\":");
            for(int j = 0; j < objs.length; j++) {
                out.println("    " + objs[j]);
            }
        }
    }
    
    private FetchResult fetch(DebugInterface root, String fetchString) throws DebugException {
        String[] sections = fetchString.split("\\.");
        return new FetchResult(
                fetchObject(root, sections, sections.length - 1), 
                sections[sections.length - 1]
        );
    }
    
    private DebugInterface fetchObject(DebugInterface root, String[] path, int length) throws DebugException {
        DebugInterface di = root;
        DebugInterface lastDI;
        for(int i = 0; i < length; i++) {
            lastDI = di;
            di = di.debugGetObj(path[i]);
            if(di == null) {
                throw new DebugNullObjectException(lastDI, path[i]);
            }
        }
        return di;
    }
    
    private void registerChange(DebugInterface root, String fetchString) throws DebugException {
        String[] sections = fetchString.split("\\.");
        DebugInterface di = root;
        for(int i = 0; i < sections.length - 1; i++) {
            di.debugOnChangeObj(sections[i]);
            di = di.debugGetObj(sections[i]);
        }
    }
    
    private static class FetchResult {
        private final DebugInterface di;
        private final String param;

        FetchResult(DebugInterface di, String param) {
            this.di = di;
            this.param = param;
        }
    }
    
}
