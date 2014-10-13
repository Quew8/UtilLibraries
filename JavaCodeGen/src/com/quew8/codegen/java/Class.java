package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Class extends TypeDef<Class> {
    private AccessModifier access;
    private Modifier modifier;
    private String name;
    private String[] genericArgs;
    private Type superType;
    private Type[] impldInterfaces;
    private Field[] fields;
    private Block[] blocks;
    private Block[] staticBlocks;
    private Constructor[] constructors;
    private Method[] definedMethods;
    private TypeDef[] nestedTypes;
    
    public Class(AccessModifier access, Modifier modifier, String name, 
            String[] genericArgs, Type superType, Type[] impldInterfaces, 
            Field[] fields, Block[] blocks, Block[] staticBlocks, 
            Constructor[] constructors, Method[] definedMethods, TypeDef[] nestedTypes) {
        
        super("<<access> ><<modifier> >class <<name>><&lt;<, <genericArgs>>&gt;>< extends <superType>>< implement <, <impldInterfaces>>> {\n"
                + "<<\n<!fields>>\n\n><<\n\n<!blocks>>\n\n><<\n\n<!staticBlocks>>\n\n><<\n\n<!constructors>>\n\n><<\n\n<!definedMethods>>\n\n><<\n\n<!nestedTypes>>\n\n>}");
        this.access = access;
        this.modifier = modifier;
        this.name = name;
        this.genericArgs = genericArgs != null ? genericArgs : new String[]{};
        this.superType = superType;
        this.impldInterfaces = impldInterfaces != null ? impldInterfaces : new Type[]{};
        this.fields = fields != null ? fields : new Field[]{};
        this.blocks = blocks != null ? blocks : new Block[]{};
        this.staticBlocks = staticBlocks != null ? staticBlocks : new Block[]{};
        this.constructors = constructors != null ? constructors : new Constructor[]{};
        this.definedMethods = definedMethods != null ? definedMethods : new Method[]{};
        this.nestedTypes = nestedTypes != null ? nestedTypes : new TypeDef[]{};
        for(Constructor c: this.constructors) {
            c.setClass(this);
        }
    }

    public Class(String name) {
        this(AccessModifier.PUBLIC, Modifier.NONE, name, null, null, null, null, null, null, null, null, null);
    }

    public Class() {
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

    public Type[] getImpldInterfaces() {
        return impldInterfaces;
    }

    @Override
    public Field[] getFields() {
        return fields;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public Block[] getStaticBlocks() {
        return staticBlocks;
    }
    
    public Constructor[] getConstructors() {
        return constructors;
    }

    @Override
    public Method[] getDefinedMethods() {
        return definedMethods;
    }
    
    public TypeDef[] getNestedTypes() {
        return nestedTypes;
    }
    
    public Class setAccess(AccessModifier access) {
        this.access = access;
        return this;
    }

    public Class setModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public Class setName(String name) {
        this.name = name;
        return this;
    }
    
    public Class setGenericArgs(String... genericArgs) {
        this.genericArgs = genericArgs;
        return this;
    }
    
    public Class setSuperType(Type superType) {
        this.superType = superType;
        return this;
    }

    public Class setImpldInterfaces(Type... impldInterfaces) {
        this.impldInterfaces = impldInterfaces;
        return this;
    }

    public Class setFields(Field... fields) {
        this.fields = fields;
        return this;
    }

    public Class setBlocks(Block... blocks) {
        this.blocks = blocks;
        return this;
    }

    public Class setStaticBlocks(Block... staticBlocks) {
        this.staticBlocks = staticBlocks;
        return this;
    }
    
    public Class setConstructors(Constructor... constructors) {
        for(Constructor c: constructors) {
            c.setClass(this);
        }
        this.constructors = constructors;
        return this;
    }

    public Class setMethods(Method... definedMethods) {
        this.definedMethods = definedMethods;
        return this;
    }

    public Class setNestedTypes(TypeDef... nestedTypes) {
        this.nestedTypes = nestedTypes;
        return this;
    }
    
    @Override
    protected TypeDef[] getTypes() {
        return JavaCodeGenUtils.combineElements(TypeDef.class, nestedTypes, JavaCodeGenUtils.getTypesOf(nestedTypes));
    }
    
    @Override
    protected MethodDef[] getMethods() {
        return JavaCodeGenUtils.combineElements(MethodDef.class, definedMethods, constructors, JavaCodeGenUtils.getMethodsOf(nestedTypes));
    }

    @Override
    protected Variable[] getVariables() {
        return JavaCodeGenUtils.combineElements(Variable.class, fields, JavaCodeGenUtils.getVariablesOf(constructors, definedMethods, nestedTypes));
    }
    
    protected Type getBaseType() {
        return new Type(name);
    }
    
}
