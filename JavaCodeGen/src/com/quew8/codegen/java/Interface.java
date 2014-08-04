package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Interface extends TypeDef<Interface> {
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
        
        super("<<access> ><<modifier> >interface <<name>>&lt;<<, <genericArgs>>>&gt;< extends <superType>> {\n"
                + "<<\n<!fields>>\n\n><<\n\n<!definedMethods>>\n\n><<\n\n<nestedTypes>>>\n}");
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
    
    public Element<JavaGenData, ?> getName() {
        return wrap(name);
    }

    @Override
    public String[] getGenericArgStrings() {
        return genericArgs;
    }

    public Element<JavaGenData, ?>[] getGenericArgs() {
        return wrap(genericArgs);
    }

    @Override
    public Type getSuperType() {
        return superType;
    }

    @Override
    public Field[] getFields() {
        return fields;
    }

    @Override
    public Method[] getDefinedMethods() {
        return definedMethods;
    }

    public TypeDef[] getNestedTypes() {
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

    /*@Override
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
    }*/

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
