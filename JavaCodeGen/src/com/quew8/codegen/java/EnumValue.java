package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class EnumValue extends Variable {
    private Enum enumType;
    private String name;
    private String args;

    public EnumValue(Enum enumType, String name, String args) {
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
    
    protected Enum getEnumType() {
        return enumType;
    }
    
    protected String getArgs() {
        return args;
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
    protected String getName() {
        return name;
    }

    @Override
    protected Type getType() {
        return enumType.getType();
    }

    @Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(name)
                .addNoGap("(", args, ")")
                .get();
    }
}
