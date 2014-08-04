package com.quew8.codegen;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 * @param <U>
 */
public class Generator<T, S extends Element<T, ?>, U extends Evaluator<T, S, U>> {
    private static final Pattern KEY_PATTERN = Pattern.compile("~([\\w\\(\\)\\[\\]\\<\\>\\s,]+)~");
    
    private final T data;
    private final S element;
    private final U evaluator;
    private final FetchMethod<U>[] fetchers;
    private final HashMap<String, String> replacements;
    
    public Generator(T data, U evaluator, S element, HashMap<String, String> replacements) {
        evaluator.prepare(element);
        this.data = data;
        this.element = element;
        this.evaluator = evaluator;
        this.fetchers = evaluator.getFetchMethods();
        this.replacements = replacements;
    }
    
    public Generator(T data, U evaluator, S element) {
        this(data, evaluator, element, new HashMap<String, String>());
    }
    
    public String generate() {
        String code = element.construct(data);
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
                return ((Element) result).construct(data);
            } else {
                return (String) result;
            }
        }
    }
    
    protected Object evaluate(String statement) {
        for(FetchMethod<U> m: fetchers) {
            Object obj = m.evaluate(this, statement);
            if(obj != null) {
                return obj;
            }
        }
        throw new RuntimeException("Generator statement \"" + statement + "\" not recognized as a replacable key or method invocation.");
    }
    
    public U getEvaluator() {
        return evaluator;
    }
}
