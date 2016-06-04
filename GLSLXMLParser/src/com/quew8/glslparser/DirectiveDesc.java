package com.quew8.glslparser;

import com.quew8.codegen.glsl.Directive;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public abstract class DirectiveDesc {
    public static final int GLSL_MIN_VERSION = 110, GLSL_MAX_VERSION = 440;
    public static final int TYPE_VERSION = 0x0, 
            TYPE_EXTENSION = 0x1, 
            TYPE_OTHER = 0x2;
    
    public abstract Directive getDirective();
    
    public static Directive[] organize(int minGLSL, int maxGLSL, ArrayList<DirectiveDesc> descs) {
        ArrayList<Directive> directives = new ArrayList<Directive>();
        for(DirectiveDesc desc: descs) {
            if(desc instanceof VersionDesc) {
                minGLSL = Math.max(minGLSL, ((VersionDesc)desc).getMin());
                maxGLSL = Math.min(maxGLSL, ((VersionDesc)desc).getMax());
                if(minGLSL > maxGLSL) {
                    throw new RuntimeException("No inclusive GLSL version.");
                }
            }
        }
        int version = maxGLSL;
        directives.add(Directive.getVersion(Integer.toString(version)));
        HashMap<String, Boolean> enabledExtensions = new HashMap<String, Boolean>();
        ArrayList<String> generalDirs = new ArrayList<String>();
        for(DirectiveDesc desc: descs) {
            if(desc instanceof ExtensionDesc) {
                ExtensionDesc eDesc = (ExtensionDesc)desc;
                if(eDesc.isApplicable(version)) {
                    if(enabledExtensions.containsKey(eDesc.getExtension())) {
                        if(enabledExtensions.get(eDesc.getExtension()) != eDesc.isEnable()) {
                            throw new RuntimeException("Conflicting enablings of extension: \"" + eDesc.getExtension() + "\"");
                        }
                    } else {
                        enabledExtensions.put(eDesc.getExtension(), eDesc.isEnable());
                        directives.add(desc.getDirective());
                    }
                }
            } else if(desc instanceof GeneralDirectiveDesc) {
                GeneralDirectiveDesc gDesc = (GeneralDirectiveDesc)desc;
                if(!generalDirs.contains(gDesc.getBody())) {
                    generalDirs.add(gDesc.getBody());
                    directives.add(gDesc.getDirective());
                }
            }
        }
        return directives.toArray(new Directive[directives.size()]);
    }
}
