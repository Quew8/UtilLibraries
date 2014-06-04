package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class SrcFile extends JavaElement {
    private PackageStatement inPackage;
    private ImportStatement[] imports;
    private TypeDef type;
    
    public SrcFile(PackageStatement inPackage, ImportStatement[] imports, TypeDef type) {
        this.inPackage = inPackage;
        this.imports = imports != null ? imports : new ImportStatement[]{};
        this.type = type;
    }
    
    public SrcFile(Class clazz) {
        this(null, null, clazz);
    }
    
    public SrcFile() {
        this(null);
    }
    
    protected PackageStatement getInPackage() {
        return inPackage;
    }

    public SrcFile setInPackage(PackageStatement inPackage) {
        this.inPackage = inPackage;
        return this;
    }

    protected ImportStatement[] getImports() {
        return imports;
    }

    public SrcFile setImports(ImportStatement... imports) {
        this.imports = imports;
        return this;
    }

    protected TypeDef getType() {
        return type;
    }

    public SrcFile setClazz(TypeDef type) {
        this.type = type;
        return this;
    }

    @Override
    protected MethodDef[] getMethods() {
        return type.getMethods();
    }

    @Override
    protected Variable[] getVariables() {
        return type.getVariables();
    }

    @Override
    protected TypeDef[] getTypes() {
        return type.getTypes();
    }
    
    @Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(inPackage != null, inPackage)
                .addLineSeparated(imports)
                .addLineSeparated(type)
                .get();
    }
}
