package com.quew8.codegen.java;

import com.quew8.codegen.Evaluator;
import com.quew8.codegen.FetchMethod;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class JavaEvaluator implements Evaluator<JavaGenData, JavaElement<?>, JavaEvaluator> {
    public static final JavaEvaluator INSTANCE = new JavaEvaluator();
    
    @SuppressWarnings("unchecked")
    private static final FetchMethod<JavaEvaluator>[] fetchMethods = getArray(
        new FetchMethod<JavaEvaluator>("VAR", "String") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                return eval.getVariable((String) args[0]);
            }
        },
        new FetchMethod<JavaEvaluator>("METHOD", "Method") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                return eval.getMethod((String) args[0]);
            }
        },
        new FetchMethod<JavaEvaluator>("METHOD_W_ARGS", "String, (String|Type)...") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                Type[] params = new Type[args.length - 1];
                for(int i = 0; i < params.length; i++) {
                    if(args[i + 1] instanceof String) {
                        params[i] = new Type((String)args[i + 1]);
                    } else {
                        params[i] = (Type) args[i + 1];
                    }
                }
                return eval.getMethod((String) args[0], params);
            }
        },
        new FetchMethod<JavaEvaluator>("TYPE", "String") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                return eval.getType((String) args[0]);
            }
        },
        new FetchMethod<JavaEvaluator>("SUPERTYPE_OF", "Type") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                return ((TypeDef) args[0]).getSuperType();
            }
        },
        new FetchMethod<JavaEvaluator>("INTERFACE_OF", "Class, int") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                Type[] interfaces = ((Class) args[0]).getImpldInterfaces();
                int index = Integer.parseInt((String)args[1]);
                if(index >= interfaces.length) {
                    throw new RuntimeException("Requested " + index + "th index when only " + interfaces.length + " exist");
                }
                return interfaces[index];
            }
        },
        new FetchMethod<JavaEvaluator>("NAME_OF", "(Variable|Method|Type)") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                if(args[0] instanceof Variable) {
                    ((Variable) args[0]).getNameString();
                } else if(args[0] instanceof MethodDef) {
                    return ((MethodDef) args[0]).getNameString();
                } else {
                    return ((TypeDef) args[0]).getNameString();
                }
                return ((Variable) args[0]).getType();
            }
        },
        new FetchMethod<JavaEvaluator>("TYPE_OF", "Variable") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                return ((Variable) args[0]).getType();
            }
        },
        new FetchMethod<JavaEvaluator>("RETURN_TYPE_OF", "Method") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                return ((MethodDef) args[0]).getReturnType();
            }
        },
        new FetchMethod<JavaEvaluator>("PARAM_OF", "Method, (int|String)") {
            @Override
            public Object run(JavaEvaluator eval, Object[] args) {
                Parameter[] params = ((MethodDef) args[0]).getParameters();
                if(args[1] instanceof String) {
                    for(Parameter param : params) {
                        if (param.getNameString().matches((String) args[1])) {
                            return param;
                        }
                    }
                    throw new RuntimeException("No Parameter Found With Name: " + args[1]);
                } else {
                    int index = Integer.parseInt((String)args[1]);
                    if(index >= params.length) {
                        throw new RuntimeException("Requested " + index + "th index when only " + params.length + " exist");
                    }
                    return params[index];
                }
            }
        },
        new FetchMethod<JavaEvaluator>("FIELD_OF", "Class, int") {
            @Override
            public Object run(JavaEvaluator gen, Object[] args) {
                Variable[] fields = ((Class) args[0]).getFields();
                if(args[1] instanceof String) {
                    for(Variable v : fields) {
                        if (v.getNameString().matches((String) args[1])) {
                            return v;
                        }
                    }
                    throw new RuntimeException("No Field Found With Name: " + args[1]);
                } else {
                    int index = Integer.parseInt((String)args[1]);
                    if(index >= fields.length) {
                        throw new RuntimeException("Requested " + index + "th index when only " + fields.length + " exist");
                    }
                    return fields[index];
                }
            }
        },
        new FetchMethod<JavaEvaluator>("GENERIC_ARG_OF", "Variable, int") {
            @Override
            public Object run(JavaEvaluator gen, Object[] args) {
                Type[] genericTypes = ((Type) args[0]).getGenerics();
                int index = Integer.parseInt((String)args[1]);
                if(index >= genericTypes.length) {
                    throw new RuntimeException("Requested " + index + "th index when only " + genericTypes.length + " exist");
                }
                return genericTypes[index];
            }
        }
    );
    
    private TypeDef[] types;
    private MethodDef[] methods;
    private Variable[] variables;
    
    private JavaEvaluator() {
        
    }
    
    @Override
    public void prepare(JavaElement element) {
        this.types = element.getTypes();
        this.methods = element.getMethods();
        this.variables = element.getVariables();
    }
    
    @Override
    public FetchMethod<JavaEvaluator>[] getFetchMethods() {
        return fetchMethods;
    }
    
    public Variable getVariable(String name) {
        for(Variable v: variables) {
            if(v.getNameString().matches(name)) {
                return v;
            }
        }
        throw new RuntimeException("No Variable Found With Name: " + name);
    }
    
    public MethodDef getMethod(String name) {
        for(MethodDef m: methods) {
            if(m.getNameString().matches(name)) {
                return m;
            }
        }
        throw new RuntimeException("No Method Found With Name: " + name);
    }
    
    public MethodDef getMethod(String name, Type[] params) {
        for(MethodDef m: methods) {
            if(m.matchesDef(name, params)) {
                return m;
            }
        }
        throw new RuntimeException("No Method Found With Name: " + name + ", Params: " + Arrays.toString(params));
    }
    
    public TypeDef getType(String name) {
        for(TypeDef t: types) {
            if(t.getNameString().matches(name)) {
                return t;
            }
        }
        throw new RuntimeException("No Method Found With Name: " + name);
    }
    
    @SuppressWarnings("unchecked")
    private static FetchMethod<JavaEvaluator>[] getArray(FetchMethod<JavaEvaluator>... args) {
        return args;
    }
}
