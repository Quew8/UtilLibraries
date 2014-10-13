package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Field extends Variable<Field> {
    private AccessModifier access;
    private Modifier modifier;
    private Type type;
    private String name;
    private String initStatement;
    
    public Field(AccessModifier access, Modifier modifier, Type type, String name, String initStatement) {
        super("<<access> ><<modifier> ><<type>> <<name>>< = <initStatement>>;");
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
    
    public AccessModifier getAccess() {
        return access;
    }

    public Modifier getModifier() {
        return modifier;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getNameString() {
        return name;
    }

    public Element<JavaGenData, ?> getName() {
        return wrap(name);
    }

    public String getInitStatementString() {
        return initStatement;
    }

    public Element<JavaGenData, ?> getInitStatement() {
        return wrap(initStatement);
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
    
    /*@Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(access, modifier, getType())
                .add(getName())
                .add(initStatement != null, "=", initStatement)
                .addNoGap(";")
                .get();
    }*/
}
