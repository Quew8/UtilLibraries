package com.quew8.geng.glslparser;

import com.quew8.codegen.CodeGenUtils;
import com.quew8.codegen.glsl.Directive;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLDirectiveParser extends GLSLParser<GLSLDirectiveParser> {
    private String directive;
    
    @Override
    public void parse(Element element) {
        super.parse(element);
        this.directive = element.getText();
    }
    
    public Directive getDirective() {
        finalized();
        return CodeGenUtils.getElement(Directive.class, "#" + directive);
    }
    
    @Override
    public GLSLDirectiveParser getInstance() {
        return new GLSLDirectiveParser();
    }
    
    @Override
    public void setSource(GLSLDirectiveParser source) {
        this.directive = source.directive;
    }
    
}
