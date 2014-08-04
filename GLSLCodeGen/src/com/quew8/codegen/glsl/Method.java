package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Method extends GLSLElement<Method> {
    private Type returnType;
    private String name;
    private Parameter[] parameters;
    private Block block;

    public Method(Type returnType, String name, Parameter[] parameters, Block block) {
        super("<<returnType|void>> <<name>>(<<, <parameters|void>>>) <<block>>");
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters != null ? parameters : new Parameter[]{};
        this.block = block;
    }
    
    public Method(String name, Block block) {
        this(null, name, null, block);
    }
    
    public Type getReturnType() {
        return returnType;
    }

    public Method setReturnType(Type returnType) {
        this.returnType = returnType;
        return this;
    }

    public String getNameString() {
        return name;
    }

    public Element<GLSLGenData, ?> getName() {
        return Element.<GLSLGenData>wrap(name);
    }

    public Method setName(String name) {
        this.name = name;
        return this;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public Method setParameters(Parameter... parameters) {
        this.parameters = parameters;
        return this;
    }

    public Block getBlock() {
        return block;
    }

    public Method setBlock(Block block) {
        this.block = block;
        return this;
    }

    /*@Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .add(returnType != null, returnType)
                .add(returnType == null, "void")
                .add(name)
                .addNoGap(parameters.length > 0, "(", GLSLCodeGenUtils.getCommaSeperatedList(parameters), ")")
                .addNoGap(parameters.length == 0, "(void)")
                .add(block)
                .get();
    }*/
}
