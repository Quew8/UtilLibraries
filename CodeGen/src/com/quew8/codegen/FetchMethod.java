package com.quew8.codegen;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class FetchMethod<T extends Evaluator<?, T>> {
    private final String name;
    private final Pattern pattern;
    private final String expectedParams;

    public FetchMethod(String name, String expectedParams) {
        this.name = name;
        this.pattern = Pattern.compile(name + "\\(([^~]*)\\)");
        this.expectedParams = expectedParams;
    }

    public String getName() {
        return name;
    }

    public Object evaluate(Generator<?, T> gen, String statement) {
        Matcher m = pattern.matcher(statement);
        if (m.matches()) {
            String[] argStrings = splitArgs(m.group(1));
            Object[] args = new Object[argStrings.length];
            for (int i = 0; i < args.length; i++) {
                args[i] = gen.evaluate(argStrings[i]);
            }
            try {
                return run(gen.getEvaluator(), args);
            } catch (ClassCastException | NumberFormatException ex) {
                throw new RuntimeException("Illegal Parameters on " + name + ", expected: " + expectedParams);
            }
        } else {
            return null;
        }
    }

    public abstract Object run(T eval, Object[] args);
    
    private static String[] splitArgs(String s) {
        String[] split = s.split("[\\s]*,[\\s]*");
        ArrayList<String> args = new ArrayList<String>();
        int i = 0;
        String argSoFar = "";
        for(String s1: split) {
            argSoFar += argSoFar.isEmpty() ? s1 : ", " + s1;
            for(int j = 0; j < s1.length(); j++) {
                if(s1.charAt(j) == '(') {
                    i++;
                } else if(s1.charAt(j) == ')') {
                    i--;
                }
            }
            if(i == 0) {
                args.add(argSoFar);
                argSoFar = "";
            }
        }
        return args.toArray(new String[args.size()]);
    }
}
