package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Method extends MethodDef<Method> {
    private AccessModifier access;
    private Modifier modifier;
    private String name;
    private Type returnType;
    private Parameter[] parameters;
    private Block block;
    
    public Method(AccessModifier access, Modifier modifier, Type returnType, String name, 
            Parameter[] parameters, Block block) {
        
        super("<<access> ><<modifier> ><<returnType>> <<name>>(<<, <parameters>>>) <<block>>");
        this.access = access;
        this.modifier = modifier;
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters != null ? parameters : new Parameter[]{};
        this.block = block;
    }
    
    public Method(String name, Block block) {
        this(AccessModifier.PUBLIC, Modifier.NONE, null, name, null, block);
    }
    
    public Method(String name) {
        this(AccessModifier.PUBLIC, Modifier.ABSTRACT, null, name, null, null);
    }

    public Method() {
        this(null);
    }
    
    public AccessModifier getAccess() {
        return access;
    }
    
    public Modifier getModifier() {
        return modifier;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String getNameString() {
        return name;
    }

    public Element<JavaGenData, ?> getName() {
        return wrap(name);
    }

    @Override
    public Parameter[] getParameters() {
        return parameters;
    }
    
    protected Block getBlock() {
        return block;
    }

    public Method setAccess(AccessModifier access) {
        this.access = access;
        return this;
    }
    
    public Method setModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public Method setReturnType(Type returnType) {
        this.returnType = returnType;
        return this;
    }

    public Method setName(String name) {
        this.name = name;
        return this;
    }

    public Method setParameters(Parameter... parameters) {
        this.parameters = parameters;
        return this;
    }
    
    public Method setBlock(Block block) {
        this.block = block;
        return this;
    }
    
    /*@Override
    protected String getConstructedCode() {
        return 
                CodeGenUtils.getConstruction()
                        .add(getAccess(), modifier)
                        .add(getReturnType() == null, "void")
                        .add(getReturnType() != null, getReturnType())
                        .add(name)
                        .addNoGap("(", CodeGenUtils.getCommaSeperatedList(getParameters()), ")")
                        .add(block != null, block)
                        .addNoGap(block == null, ";")
                        .get();
    }*/

    @Override
    public Variable[] getVariables() {
        return parameters;
    }
}
