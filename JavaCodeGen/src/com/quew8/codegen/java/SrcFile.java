package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class SrcFile extends JavaElement<SrcFile> {
    private PackageStatement inPackage;
    private ImportStatement[] imports;
    private TypeDef type;
    
    public SrcFile(PackageStatement inPackage, ImportStatement[] imports, TypeDef type) {
        super("<<inPackage>\n\n><<\n<imports>>\n\n><<type>>");
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
    
    public PackageStatement getInPackage() {
        return inPackage;
    }

    public SrcFile setInPackage(PackageStatement inPackage) {
        this.inPackage = inPackage;
        return this;
    }

    public ImportStatement[] getImports() {
        return imports;
    }

    public SrcFile setImports(ImportStatement... imports) {
        this.imports = imports;
        return this;
    }

    public TypeDef getType() {
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
    
    /*@Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add(inPackage != null, inPackage)
                .addLineSeparated(
                        JavaCodeGenUtils.getConstruction()
                                .addNewline(imports)
                                .get()
                )
                .addLineSeparated(type)
                .get();
    }*/
}
