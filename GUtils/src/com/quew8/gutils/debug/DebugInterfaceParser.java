package com.quew8.gutils.debug;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public class DebugInterfaceParser {
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
                    ArrayList<String> objParams = new ArrayList<String>();
                    ArrayList<String> valueParams = new ArrayList<String>();
                    Matcher m = Pattern.compile("([sg]et)[\\s]+([\\w\\.]+)([\\s]+([\\w\\-][\\w\\s\\-]*))?").matcher(inputString);
                    if(m.matches()) {
                        boolean set = m.group(1).equals("set");
                        String[] sections = m.group(2).split("\\.");
                        DebugInterface di = root;
                        for(int i = 0; i < sections.length - 1; i++) {
                            DebugInterface lastDI = di;
                            di = di.debugGetObj(sections[i]);
                            if(di == null) {
                                out.println(sections[i] + " at " + i + " not found. Available Object Params:");
                                lastDI.debugAddAllParams(objParams, valueParams);
                                for(int j = 0; j < objParams.size(); j++) {
                                    out.println(objParams.get(j));
                                }
                                return;
                            }
                        }
                        if(set) {
                            String[] values = m.group(4).split("[\\s]+");
                            if(values.length == 0) {
                                out.println("Set command cannot have no arguments");
                                return;
                            }
                            String error = di.debugSetValue(sections[sections.length - 1], values);
                            if(error != null) {
                                out.println("Error: " + error);
                            } else {
                                di = root;
                                for(int i = 0; i < sections.length - 1; i++) {
                                    di.debugOnChangeObj(sections[i]);
                                    di = di.debugGetObj(sections[i]);
                                }
                            }
                        } else {
                            String stringValue = di.debugGetValue(sections[sections.length - 1]);
                            if(stringValue == null) {
                                out.println(sections[sections.length - 1] + " not found. Available Value Params:");
                                di.debugAddAllParams(objParams, valueParams);
                                for(int j = 0; j < valueParams.size(); j++) {
                                    out.println(valueParams.get(j));
                                }
                            } else {
                                out.println(stringValue);
                            }
                        }
                    } else {
                        System.out.println("No Match");
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
    
}
