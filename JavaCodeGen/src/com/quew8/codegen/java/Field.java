package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class Field extends Variable {
    private AccessModifier access;
    private Modifier modifier;
    private Type type;
    private String name;
    private String initStatement;
    
    public Field(AccessModifier access, Modifier modifier, Type type, String name, String initStatement) {
        this.access = access;
        this.modifier = modifier;
        this.type = type;
        this.name = name;
        this.initStatement = initStatement;
    }
    
    public Field(Type type, String name) {
        this(AccessModifier.PUBLIC, Modifier.NONE, type, name, null);
    }

    public Field() {
        this(null, null);
    }
    
    protected AccessModifier getAccess() {
        return access;
    }

    protected Modifier getModifier() {
        return modifier;
    }

    @Override
    protected Type getType() {
        return type;
    }

    @Override
    protected String getName() {
        return name;
    }

    protected String getInitStatement() {
        return initStatement;
    }
    
    public Field setAccess(AccessModifier access) {
        this.access = access;
        return this;
    }
    
    public Field setModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public Field setType(Type type) {
        this.type = type;
        return this;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }
    
    public Field setInitStatement(String initStatement) {
        this.initStatement = initStatement;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(access, modifier, getType())
                .add(getName())
                .add(initStatement != null, "=", initStatement)
                .addNoGap(";")
                .get();
    }
}
