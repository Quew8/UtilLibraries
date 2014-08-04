package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class EnumValue extends Variable<EnumValue> {
    private Enum enumType;
    private String name;
    private String args;

    public EnumValue(Enum enumType, String name, String args) {
        super("<<name>>(<<args>>)");
        this.enumType = enumType;
        this.name = name;
        this.args = args;
    }

    public EnumValue(String name, String args) {
        this(null, name, args);
    }
    
    public EnumValue() {
        this(null, null);
    }
    
    public Enum getEnumType() {
        return enumType;
    }
    
    public String getArgsString() {
        return args;
    }
    
    public Element<JavaGenData, ?> getArgs() {
        return wrap(args);
    }
    
    public EnumValue setEnumType(Enum enumType) {
        this.enumType = enumType;
        return this;
    }
    
    public EnumValue setName(String name) {
        this.name = name;
        return this;
    }
    
    public EnumValue setArgs(String args) {
        this.args = args;
        return this;
    }
    
    @Override
    public String getNameString() {
        return name;
    }
    
    public Element<JavaGenData, ?> getName() {
        return wrap(name);
    }

    @Override
    public Type getType() {
        return enumType.getType();
    }

    /*@Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(name)
                .addNoGap("(", args, ")")
                .get();
    }*/
}
