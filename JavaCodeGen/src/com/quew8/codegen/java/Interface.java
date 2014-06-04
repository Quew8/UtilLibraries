package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class Interface extends TypeDef {
    private AccessModifier access;
    private Modifier modifier;
    private String name;
    private String[] genericArgs;
    private Type superType;
    private Field[] fields;
    private Method[] definedMethods;
    private TypeDef[] nestedTypes;

    public Interface(AccessModifier access, Modifier modifier, String name, 
            String[] genericArgs, Type superType, Field[] fields, 
            Method[] definedMethods, TypeDef[] nestedTypes) {
        
        this.access = access;
        this.modifier = modifier;
        this.name = name;
        this.genericArgs = genericArgs != null ? genericArgs : new String[]{};
        this.superType = superType;
        this.fields = fields != null ? fields : new Field[]{};
        this.definedMethods = definedMethods != null ? definedMethods : new Method[]{};
        this.nestedTypes = nestedTypes != null ? nestedTypes : new TypeDef[]{};
    }
    
    public Interface(String name) {
        this(AccessModifier.PUBLIC, Modifier.NONE, name, null, null, null, null, null);
    }
    
    public Interface() {
        this(null);
    }
    
    protected AccessModifier getAccess() {
        return access;
    }

    protected Modifier getModifier() {
        return modifier;
    }
    
    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected String[] getGenericArgs() {
        return genericArgs;
    }

    @Override
    protected Type getSuperType() {
        return superType;
    }

    @Override
    protected Field[] getFields() {
        return fields;
    }

    @Override
    protected Method[] getDefinedMethods() {
        return definedMethods;
    }

    protected TypeDef[] getNestedTypes() {
        return nestedTypes;
    }

    public Interface setAccess(AccessModifier access) {
        this.access = access;
        return this;
    }

    public Interface setModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public Interface setName(String name) {
        this.name = name;
        return this;
    }

    public Interface setGenericArgs(String... genericArgs) {
        this.genericArgs = genericArgs;
        return this;
    }

    public Interface setSuperType(Type superType) {
        this.superType = superType;
        return this;
    }

    public Interface setFields(Field... fields) {
        this.fields = fields;
        return this;
    }

    public Interface setDefinedMethods(Method... definedMethods) {
        this.definedMethods = definedMethods;
        return this;
    }

    public Interface setNestedTypes(TypeDef... nestedTypes) {
        this.nestedTypes = nestedTypes;
        return this;
    }

    @Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(access, modifier)
                .add("interface", name)
                .add(superType != null, "extends")
                .add(superType != null, superType)
                .add("{")
                .addNewline(
                        JavaCodeGenUtils.shiftRight(
                                JavaCodeGenUtils.getConstruction()
                                        .addLineSeparated(
                                                JavaCodeGenUtils.getNewlineList(fields),
                                                JavaCodeGenUtils.getLineSeperatedList(definedMethods),
                                                JavaCodeGenUtils.getLineSeperatedList(nestedTypes)
                                        )
                                        .get()
                        )
                )
                .addNewline("}")
                .get();
    }

    @Override
    protected MethodDef[] getMethods() {
        return JavaCodeGenUtils.combineElements(MethodDef.class, definedMethods, JavaCodeGenUtils.getMethodsOf(nestedTypes));
    }

    @Override
    protected Variable[] getVariables() {
        return JavaCodeGenUtils.combineElements(Variable.class, fields, JavaCodeGenUtils.getVariablesOf(nestedTypes, definedMethods));
    }

    @Override
    protected TypeDef[] getTypes() {
        return nestedTypes;
    }
    
    
}
