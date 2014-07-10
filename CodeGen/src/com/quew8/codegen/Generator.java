package com.quew8.codegen;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class Generator<T extends Element, S extends Evaluator<T, S>> {
    private static final Pattern KEY_PATTERN = Pattern.compile("~([\\w\\(\\)\\[\\]\\<\\>\\s,]+)~");
    
    private final T element;
    private final S evaluator;
    private final FetchMethod<S>[] fetchers;
    private final HashMap<String, String> replacements;
    
    public Generator(S evaluator, T element, HashMap<String, String> replacements) {
        evaluator.prepare(element);
        this.element = element;
        this.evaluator = evaluator;
        this.fetchers = evaluator.getFetchMethods();
        this.replacements = replacements;
    }
    
    public Generator(S evaluator, T element) {
        this(evaluator, element, new HashMap<String, String>());
    }
    
    public String generate() {
        String code = element.getCode();
        Matcher matcher = KEY_PATTERN.matcher(code);
        int lastReadIndex = 0;
        String formattedCode = "";
        while(matcher.find()) {
            String key = matcher.group(1);
            formattedCode += code.substring(lastReadIndex, matcher.start()) + replace(key);
            lastReadIndex = matcher.end();
        }
        formattedCode += code.substring(lastReadIndex);
        return formattedCode;
    }
    
    private String replace(String key) {
        if(replacements.containsKey(key)) {
            return replacements.get(key);
        } else {
            Object result = evaluate(key);
            if(result instanceof Element) {
                return ((Element) result).getCode();
            } else {
                return (String) result;
            }
        }
    }
    
    protected Object evaluate(String statement) {
        for(FetchMethod<S> m: fetchers) {
            Object obj = m.evaluate(this, statement);
            if(obj != null) {
                return obj;
            }
        }
        throw new RuntimeException("Generator statement \"" + statement + "\" not recognized as a replacable key or method invocation.");
    }
    
    public S getEvaluator() {
        return evaluator;
    }
}
