package com.quew8.codegen;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class Element<T, S extends Element<T, S>> {
    private final HashMap<String, Element<T, ?>> defaultValues = new HashMap<String, Element<T, ?>>();
    private final HashMap<String, Element<T, ?>[]> defaultValuess = new HashMap<String, Element<T, ?>[]>();
    private String definition;
    
    public Element(String definition) {
        this.definition = definition;
    }
    
    public String construct(T data) {
        String d = getDefinition();
        String s = "";
        int lastIndex = 0;
        int depth = 0;
        int maxDepth = 0;
        for(int i = 0; i < d.length(); i++) {
            if(d.charAt(i) == '<') {
                if(depth == 0) {
                    s += d.substring(lastIndex, i);
                    lastIndex = i;
                }
                depth++;
                maxDepth++;
            } else if(d.charAt(i) == '>') {
                depth--;
                if(depth == 0) {
                    String s2 = evaluate(d.substring(lastIndex, i+1), maxDepth, data);
                    s += s2;
                    lastIndex = i+1;
                    maxDepth = 0;
                }
            }
        }
        if(lastIndex < d.length()) {
            s += d.substring(lastIndex);
        }
        s = s.replaceAll(Pattern.quote("&lt;"), Matcher.quoteReplacement("<"));
        s = s.replaceAll(Pattern.quote("&gt;"), Matcher.quoteReplacement(">"));
        return s;
    }
    
    private String evaluate(String expression, int depth, T data) {
        switch(depth) {
            case 2: {
                Expression exp = Expression.evaluate(this, data, expression);
                if(!exp.reference.elements[0].isEmpty()) {
                    String s = exp.pre + exp.reference.elements[0] + exp.post;
                    if(exp.reference.indent) {
                        return CodeGenUtils.shiftRight(s);
                    } else {
                        return s;
                    }
                } else {
                    return exp.elseExpression;
                }
            }
            case 3: {
                LoopExpression exp = LoopExpression.evaluate(this, data, expression);
                String[] sa = exp.reference.elements;
                if(sa.length == 0) { //If list is empty.
                    return exp.elseExpression;
                } else {
                    int i = 0;
                    while(sa[i].isEmpty()) { //Skip foward to first non-empty element.
                        i++;
                        if(i >= sa.length) { //If reached end then all empty.
                            return exp.elseExpression;
                        }
                    }
                    String s = exp.pre + sa[i++];
                    for(; i < sa.length; i++) {
                        if(!sa[i].isEmpty()) {
                            s += exp.separator + sa[i];
                        }
                    }
                    s += exp.post;
                    if(exp.reference.indent) {
                        return CodeGenUtils.shiftRight(s);
                    } else {
                        return s;
                    }
                }
            }
            default: throw new RuntimeException("Malformed Expression");
        }
    }
    
    private Object fetchValue(String ref) {
        String methodName = "get" + Character.toUpperCase(ref.charAt(0)) + ref.substring(1);
        try {
            java.lang.reflect.Method meth = getClass().getMethod(methodName);
            Class<?> clazz = getClass();
            while(clazz.isAnonymousClass()) {
                try {
                    clazz = clazz.getSuperclass();
                    meth = clazz.getMethod(methodName);
                } catch(NoSuchMethodException ex) {
                    break;
                }
            }
            return meth.invoke(this);
        } catch(NoSuchMethodException ex) {
            return fetchNotFoundValue(methodName, ref);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } 
    }
    
    public Object fetchNotFoundValue(String methodName, String ref) {
        if(defaultValues.containsKey(ref)) {
            return defaultValues.get(ref);
        } else if(defaultValuess.containsKey(ref)) {
            return defaultValuess.get(ref);
        } else {
            throw new RuntimeException("Could not find value with reference: " + ref + ", method " + methodName + " in " + getClass().getName());
        }
    }
    
    public String getDefinition() {
        return definition;
    }
    
    public S setDefinition(String definition) {
        this.definition = definition;
        return self();
    }
    
    public S putDefaultValue(String ref, Element<T, ?> value) {
        defaultValues.put(ref, value);
        return self();
    }
    
    public S putDefaultValues(String ref, Element<T, ?>... values) {
        defaultValuess.put(ref, values);
        return self();
    }
    
    private S self() {
        return (S) this;
    }
    
    public static <T> Element<T, ?> wrap(String s) {
        return new PoorSelfElement<T>(s);
    }
    
    public static <T> Element<T, ?>[] wrap(String[] sa) {
        Element<T, ?>[] array = getArray(sa.length);
        for(int i = 0; i < sa.length; i++) {
            array[i] = wrap(sa[i]);
        }
        return array;
    }
    
    private static <T> T[] getArray(int length, T... ta) {
        return Arrays.copyOf(ta, length);
    }
    
    private static class Reference {
        private static final String REF_PATTERN_STRING = "\\A([!])?([\\w]+)\\Z";
        private static final Pattern REF_PATTERN = Pattern.compile(REF_PATTERN_STRING);
        private static final String INDEXED_REF_PATTERN_STRING = "\\A([!])?([\\w]+)\\[([\\d]+)\\]\\Z";
        private static final Pattern INDEXED_REF_PATTERN = Pattern.compile(INDEXED_REF_PATTERN_STRING);
        public final String[] elements;
        public final boolean indent;

        public Reference(String[] elements, boolean indent) {
            this.elements = elements;
            this.indent = indent;
        }

        public static <T> Reference evaluate(Element<T, ?> parent, T data, String expression) {
            Matcher m = REF_PATTERN.matcher(expression);
            boolean indexed = false;
            if(!m.find()) {
                m = INDEXED_REF_PATTERN.matcher(expression);
                if(!m.find()) {
                    throw new RuntimeException("Malformed Reference: \"" + expression + "\"");
                }
            }
            boolean indent = m.group(1) != null;
            Object target = parent.fetchValue(m.group(2));
            if(target == null) {
                return new Reference(new String[]{""}, indent);
            } else if(target instanceof Element) {
                return new Reference(new String[]{((Element<T, ?>)target).construct(data)}, indent);
            } else if(target instanceof Element[]) {
                Element[] refs = (Element<T, ?>[]) target;
                if(indexed) {
                    int index = Integer.parseInt(m.group(3));
                    return new Reference(new String[]{refs[index].construct(data)}, indent);
                } else {
                    String[] result = new String[refs.length];
                    for(int i = 0; i < refs.length; i++) {
                        result[i] = refs[i].construct(data);
                    }
                    return new Reference(result, indent);
                }
            } else {
                throw new RuntimeException("Type of referenced value invalid: " + target.getClass().getName());
            }
        }
        
    }
    
    private static class Expression {
        public static final String ELEMENT_PATTERN_STRING = "\\<([^\\<\\>]*)\\<([^\\<\\>\\|]+)(\\|([^\\<\\>]+))?\\>([^\\<\\>]*)\\>";
        public static final Pattern ELEMENT_PATTERN = Pattern.compile(ELEMENT_PATTERN_STRING);
        public final String pre, post, elseExpression;
        public final Reference reference;

        private Expression(String pre, String post, Reference reference, String elseExpression) {
            this.pre = pre;
            this.post = post;
            this.reference = reference;
            this.elseExpression = elseExpression != null ? elseExpression : "";
        }

        public static <T> Expression evaluate(Element<T, ?> parent, T data, String expression) {
            Matcher m = ELEMENT_PATTERN.matcher(expression);
            if(!m.find()) {
                throw new RuntimeException("Malformed Expression: \"" + expression + "\"");
            }
            return new Expression(
                    m.group(1), 
                    m.group(5), 
                    Reference.evaluate(parent, data, m.group(2)), 
                    m.group(4)
            );
        }
        
    }
    
    private static class LoopExpression extends Expression {
        public static final String LOOP_ELEMENT_PATTERN_STRING = "\\<([^\\<\\>]*)" + ELEMENT_PATTERN_STRING + "([^\\<\\>]*)\\>";
        public static final Pattern LOOP_ELEMENT_PATTERN = Pattern.compile(LOOP_ELEMENT_PATTERN_STRING);
        public final String separator;

        private LoopExpression(String pre, String post, Reference reference, String elseExpression, String separator) {
            super(pre, post, reference, elseExpression);
            this.separator = separator;
        }

        public static <T> LoopExpression evaluate(Element<T, ?> parent, T data, String expression) {
            Matcher m = LOOP_ELEMENT_PATTERN.matcher(expression);
            if(!m.find()) {
                throw new RuntimeException("Malformed Expression: \"" + expression + "\"");
            }
            return new LoopExpression(
                    m.group(1),
                    m.group(7),
                    Reference.evaluate(parent, data, m.group(3)),
                    m.group(5),
                    m.group(2) + m.group(6)
            );
        }
    }
}
