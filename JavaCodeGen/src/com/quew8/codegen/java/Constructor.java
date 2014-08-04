package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class Constructor extends MethodDef<Constructor> {
    private AccessModifier access;
    private Class clazz;
    private Parameter[] parameters;
    private Block codeBlock;
    
    public Constructor(AccessModifier access, Class clazz, Parameter[] parameters, Block codeBlock) {
        super("<<access> ><<returnType>>(<<, <parameters>>>) <<codeBlock>>");
        this.access = access;
        this.clazz = clazz;
        this.parameters = parameters != null ? parameters : new Parameter[]{};
        this.codeBlock = codeBlock;
    }
    
    public Constructor(Block codeBlock) {
        this(AccessModifier.PUBLIC, null, null, codeBlock);
    }

    public Constructor() {
        this(null);
    }
    
    public AccessModifier getAccess() {
        return access;
    }
    
    public Class getClazz() {
        return clazz;
    }

    @Override
    public Parameter[] getParameters() {
        return parameters;
    }

    public Block getCodeBlock() {
        return codeBlock;
    }
    
    @Override
    public Type getReturnType() {
        return clazz.getBaseType();
    }
    
    @Override
    public String getNameString() {
        return getReturnType().getNameString();
    }

    public Constructor setAccess(AccessModifier access) {
        this.access = access;
        return this;
    }
    
    public Constructor setClass(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    public Constructor setParameters(Parameter... parameters) {
        this.parameters = parameters;
        return this;
    }
    
    public Constructor setCodeBlock(Block codeBlock) {
        this.codeBlock = codeBlock;
        return this;
    }
    
    /*@Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add(getAccess(), getReturnType())
                .addNoGap("(", CodeGenUtils.getCommaSeperatedList(getParameters()), ")")
                .add(codeBlock)
                .get();
    }*/

    @Override
    protected Variable[] getVariables() {
        return parameters;
    }
    
}
