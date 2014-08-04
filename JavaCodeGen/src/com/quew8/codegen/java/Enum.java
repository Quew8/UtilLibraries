package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class Enum extends TypeDef<Enum> {
    private AccessModifier access;
    private Modifier modifier;
    private String name;
    private EnumValue[] values;
    private Constructor[] constructors;
    private Field[] fields;
    private Method[] definedMethods;
    private TypeDef[] nestedTypes;

    public Enum(AccessModifier access, Modifier modifier, String name, 
            EnumValue[] values, Constructor[] constructors, Field[] fields, 
            Method[] definedMethods, TypeDef[] nestedTypes) {
        
        super("<<access> ><<modifier> >enum <<name>> {\n"
                + "<<, <values>>\n\n><<\n\n<constructors>>\n\n><<\n<fields>>\n\n><<\n\n<definedMethods>>\n\n><<\n\n<nestedTypes>>\n\n>"
                + "\n}");
        this.access = access;
        this.modifier = modifier;
        this.name = name;
        this.values = values != null ? values : new EnumValue[]{};
        this.constructors = constructors != null ? constructors : new Constructor[]{};
        this.fields = fields != null ? fields : new Field[]{};
        this.definedMethods = definedMethods != null ? definedMethods : new Method[]{};
        this.nestedTypes = nestedTypes != null ? nestedTypes : new TypeDef[]{};
        for(EnumValue v: this.values) {
            v.setEnumType(this);
        }
    }

    public Enum(String name) {
        this(AccessModifier.PUBLIC, Modifier.NONE, name, null, null, null, null, null);
    }
    
    public Enum() {
        this(null);
    }
    
    public Enum setAccess(AccessModifier access) {
        this.access = access;
        return this;
    }

    public Enum setModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public Enum setName(String name) {
        this.name = name;
        return this;
    }

    public Enum setValues(EnumValue... values) {
        this.values = values;
        for(EnumValue v: this.values) {
            v.setEnumType(this);
        }
        return this;
    }

    public Enum setConstructors(Constructor... constructors) {
        this.constructors = constructors;
        return this;
    }

    public Enum setFields(Field... fields) {
        this.fields = fields;
        return this;
    }

    public Enum setDefinedMethods(Method... definedMethods) {
        this.definedMethods = definedMethods;
        return this;
    }

    public Enum setNestedTypes(TypeDef... nestedTypes) {
        this.nestedTypes = nestedTypes;
        return this;
    }
    
    public AccessModifier getAccess() {
        return access;
    }
    
    public Modifier getModifier() {
        return modifier;
    }
    
    @Override
    public String getNameString() {
        return name;
    }

    @Override
    public Type getSuperType() {
        return new Type("Enum<" + name + ">");
    }

    @Override
    public String[] getGenericArgStrings() {
        return new String[]{};
    }
    
    public EnumValue[] getValues() {
        return values;
    }
    
    public Constructor[] getConstructors() {
        return constructors;
    }

    @Override
    public Field[] getFields() {
        return fields;
    }

    @Override
    public Method[] getDefinedMethods() {
        return definedMethods;
    }

    protected TypeDef[] getNestesTypes() {
        return nestedTypes;
    }
    
    /*@Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(access, modifier)
                .add("enum", name, "{")
                .addNewline(
                        JavaCodeGenUtils.shiftRight(
                                JavaCodeGenUtils.getConstruction()
                                        .addLineSeparated(
                                                JavaCodeGenUtils.getCommaSeperatedList(values) + ";",
                                                JavaCodeGenUtils.getLineSeperatedList(constructors),
                                                JavaCodeGenUtils.getNewlineList(fields),
                                                JavaCodeGenUtils.getLineSeperatedList(definedMethods),
                                                JavaCodeGenUtils.getLineSeperatedList(nestedTypes)
                                        )
                                        .get()
                        )
                )
                .addNewline("}")
                .get();
    }*/

    @Override
    public MethodDef[] getMethods() {
        return JavaCodeGenUtils.combineElements(MethodDef.class, definedMethods, JavaCodeGenUtils.getMethodsOf(nestedTypes));
    }

    @Override
    public Variable[] getVariables() {
        return JavaCodeGenUtils.combineElements(Variable.class, values, fields, JavaCodeGenUtils.getVariablesOf(definedMethods, nestedTypes));
    }

    @Override
    public TypeDef[] getTypes() {
        return JavaCodeGenUtils.combineElements(TypeDef.class, nestedTypes, JavaCodeGenUtils.getTypesOf(nestedTypes));
    }
    
    
    
}
