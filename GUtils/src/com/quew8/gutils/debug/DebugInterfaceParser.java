package com.quew8.gutils.debug;

import com.quew8.gutils.collections.ExpandingStack;
import com.quew8.gutils.collections.Stack;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Quew8
 */
public class DebugInterfaceParser {
    private static final String HELP_MESSAGE = 
            "Available Commands:\n"
            + "\"help\": display this help message\n"
            + "\"set [param_path] [value]\": sets the specified param to the specified value.\n"
            + "\"get [param_path]\": returns the value of the specified param.\n"
            + "\"show ([param_object_path])\": displays all params and param objects in the specified param object. If no path given does so for the root object\n"
            + "\"visual (remove) [param_path]\": creates gui to set specified param. If remove is specified, removes said gui element.";
    private static final Pattern HELP_PATTERN = Pattern.compile("help");
    private static final Pattern SET_PATTERN = Pattern.compile("set[\\s]+([\\w\\.]+)([\\s]+([\\w\\-][\\w\\s\\-\\.]*))");
    private static final Pattern GET_PATTERN = Pattern.compile("get[\\s]+([\\w\\.]+)");
    private static final Pattern SHOW_PATTERN = Pattern.compile("show([\\s]+([\\w\\.]+))?");
    private static final Pattern VISUAL_PATTERN = Pattern.compile("visual[\\s]+([\\w\\.]+)");
    private static final Pattern VISUAL_REMOVE_PATTERN = Pattern.compile("visual[\\s]+remove[\\s]+([\\w\\.]+)");
    private String inputString = "";
    private final InputStream in;
    private final PrintStream out;
    private JFrame frame = null;
    private JButton refreshButton = null;
    private boolean requiresRefresh = false;
    private final HashMap<String, VisualParamField> fields = new HashMap<String, VisualParamField>();
    private final Stack<SetParamJob> setStack = new ExpandingStack<SetParamJob>(SetParamJob.class, 1);
    
    public DebugInterfaceParser(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void forceCloseWindows() {
        if(frame != null) {
            frame.dispose();
            fields.clear();
            setStack.reset();
        }
    }
    
    public void parse(Object rootObj) {
        try {
            DebugObjectStruct root = getObject(rootObj);
            if(requiresRefresh) {
                for(String s: fields.keySet()) {
                    FetchResult fetch = fetch(root, s);
                    fields.get(s).setField(getParamValue(getParam(fetch.parent, fetch.param)));
                }
                requiresRefresh = false;
            }
            while(!setStack.isEmpty()) {
                SetParamJob job = setStack.pop();
                FetchResult fetch = fetch(root, job.param);
                if(job.value.isEmpty()) {
                    out.println("Set command cannot have no arguments");
                    return;
                }
                setParamValue(getParam(fetch.parent, fetch.param), job.value);
                registerChange(root, job.param);
            }
            while(in.available() > 0) {
                char c = (char) in.read();
                if(c == -1) {
                    return;
                } else if(c == '\n' || c == '\r') {
                    String thisInputString = inputString.trim();
                    inputString = "";
                    Matcher m;
                    if((m = HELP_PATTERN.matcher(thisInputString)).matches()) {
                        out.println(HELP_MESSAGE);
                    } else if((m = GET_PATTERN.matcher(inputString)).matches()) {
                        FetchResult fetch = fetch(root, m.group(1));
                        out.println(getParamValue(getParam(fetch.parent, fetch.param)));
                    } else if((m = SET_PATTERN.matcher(thisInputString)).matches()) {
                        FetchResult fetch = fetch(root, m.group(1));
                        if(m.group(3).isEmpty()) {
                            out.println("Set command cannot have no arguments");
                            return;
                        }
                        setParamValue(getParam(fetch.parent, fetch.param), m.group(3));
                        registerChange(root, m.group(1));
                    } else if((m = SHOW_PATTERN.matcher(thisInputString)).matches()) {
                        String path = m.group(2);
                        DebugObjectStruct obj;
                        if(path == null || path.isEmpty()) {
                            obj = root;
                        } else {
                            String[] sections = path.split("\\.");
                            obj = fetchObjectParam(root, sections, sections.length);
                        }
                        showAvailableParams(obj);
                        showAvailableObjectParams(obj);
                    }  else if((m = VISUAL_PATTERN.matcher(thisInputString)).matches()) {
                        if(!fields.containsKey(m.group(1))) {
                            String paramPath = m.group(1);
                            FetchResult fetch = fetch(root, m.group(1));
                            DebugParamStruct param = getParam(fetch.parent, fetch.param);
                            VisualParamField paramField;
                            String currentValue = getParamValue(param);
                            DebugFloatSliderField floatField = getVisualField(
                                    param, DebugFloatSliderField.class, DebugFloatSliderFields.class,
                                    (DebugFloatSliderField t) -> t.target(),
                                    (DebugFloatSliderFields s) -> s.value()
                            );
                            DebugIntSliderField intField = getVisualField(
                                    param, DebugIntSliderField.class, DebugIntSliderFields.class,
                                    (DebugIntSliderField t) -> t.target(),
                                    (DebugIntSliderFields s) -> s.value()
                            );
                            DebugToggleField boolField = getVisualField(
                                    param, DebugToggleField.class, DebugToggleFields.class,
                                    (DebugToggleField t) -> t.target(),
                                    (DebugToggleFields s) -> s.value()
                            );
                            if(floatField != null) {
                                paramField = new FloatSliderParamField(paramPath, floatField, currentValue);
                            } else if(intField != null) {
                                paramField = new IntSliderParamField(paramPath, intField, currentValue);
                            } else if(boolField != null) {
                                paramField = new ToggleButtonParamField(paramPath, boolField, currentValue);
                            } else {
                                paramField = new TextFieldParamField(paramPath, currentValue);
                            }
                            fields.put(m.group(1), paramField);
                            createGUI();
                        }
                    } else if((m = VISUAL_REMOVE_PATTERN.matcher(thisInputString)).matches()) {
                        if(fields.containsKey(m.group(1))) {
                            fields.remove(m.group(1));
                            createGUI();
                        }
                    } else {
                        out.println("No Match.");
                        out.println(HELP_MESSAGE);
                    }
                } else {
                    inputString += c;
                }
            }
        } catch(DebugSettingFinalParamException ex) {
            out.println("\"" + ex.getParam() + " in \"" + ex.getParam().name + "\" is final.");
        } catch(DebugObjectParamNotFoundException ex) {
            out.println(ex.getMessage());
            showAvailableObjectParams(ex.getParent());
        } catch(DebugParamNotFoundException ex) {
            out.println(ex.getMessage());
            showAvailableParams(ex.getParent());
        } catch(DebugNullObjectException ex) {
            out.println(ex.getMessage());
        } catch(DebugException ex) {
            out.println(ex.getMessage());
            if(ex.getCause() != null) {
                throw new RuntimeException(ex.getCause());
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void showAvailableParams(DebugObjectStruct parent) {
        DebugParamStruct[] params = getAllParams(parent);
        if(params.length == 0) {
            out.println("No Available Params In \"" + parent.name + "\":");
        } else {
            out.println("Available Params In \"" + parent.name + "\":");
            for(int j = 0; j < params.length; j++) {
                out.println("    " + params[j].name);
            }
        }
    }
    
    private void showAvailableObjectParams(DebugObjectStruct parent) {
        DebugObjectParamStruct[] params = getAllObjectParams(parent);
        if(params.length == 0) {
            out.println("No Available Objects In \"" + parent.name + "\":");
        } else {
            out.println("Available Objects In \"" + parent.name + "\":");
            for(int j = 0; j < params.length; j++) {
                out.println("    " + params[j].name);
            }
        }
    }
    
    private void createGUI() {
        if(frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 200);
            frame.setTitle("Debug Window");
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}

                @Override
                public void windowClosed(WindowEvent e) {
                    frame = null;
                    fields.clear();
                }

                @Override
                public void windowIconified(WindowEvent e) {}
                @Override
                public void windowDeiconified(WindowEvent e) {}
                @Override
                public void windowActivated(WindowEvent e) {}
                @Override
                public void windowDeactivated(WindowEvent e) {}

            });
            refreshButton = new JButton("Refresh");
            refreshButton.addActionListener((ActionEvent e) -> {
                requiresRefresh = true;
            });
            frame.setVisible(true);
        }
        GridBagLayout layout = new GridBagLayout();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(layout);
        GridBagConstraints labelContstraint = new GridBagConstraints();
        labelContstraint.gridx = 0;
        labelContstraint.insets = new Insets(10, 10, 10, 10);
        labelContstraint.weightx = 0f;
        GridBagConstraints fieldContstraint = new GridBagConstraints();
        fieldContstraint.gridx = 1;
        fieldContstraint.fill = GridBagConstraints.BOTH;
        fieldContstraint.insets = new Insets(10, 10, 10, 10);
        fieldContstraint.weightx = 1f;
        int i = 0;
        for(String s: fields.keySet()) {
            labelContstraint.gridy = i;
            fieldContstraint.gridy = i;
            frame.getContentPane().add(new JLabel(s), labelContstraint);
            frame.getContentPane().add(fields.get(s).getComponent(), fieldContstraint);
            i++;
        }
        GridBagConstraints refreshContstraint = new GridBagConstraints();
        refreshContstraint.gridx = 0;
        refreshContstraint.gridy = i;
        refreshContstraint.gridwidth = 2;
        frame.getContentPane().add(refreshButton, refreshContstraint);
        frame.setVisible(true);
    }
    
    private FetchResult fetch(DebugObjectStruct root, String fetchString) throws DebugException {
        String[] sections = fetchString.split("\\.");
        return new FetchResult(
                fetchObjectParam(root, sections, sections.length - 1), 
                sections[sections.length - 1]
        );
    }
    
    private DebugObjectStruct fetchObjectParam(DebugObjectStruct root, String[] path, int length) throws DebugException {
        DebugObjectStruct obj = root;
        Object nextObj;
        for(int i = 0; i < length; i++) {
            DebugObjectParamStruct param = getObjectParam(obj, path[i]);
            nextObj = getObjectParamValue(param);
            if(nextObj == null) {
                throw new DebugNullObjectException(param);
            }
            obj = getObject(nextObj);
        }
        return obj;
    }
    
    private void registerChange(DebugObjectStruct root, String fetchString) throws DebugException {
        String[] sections = fetchString.split("\\.");
        DebugObjectStruct obj = root;
        for(int i = 0; i < sections.length - 1; i++) {
            construct(obj.dObj.changeNotifier()).notifyObjectParamChange(obj.obj, sections[i]);
            obj = getObject(getObjectParamValue(getObjectParam(obj, sections[i])));
        }
        construct(obj.dObj.changeNotifier()).notifyParamChange(obj.obj, sections[sections.length - 1]);
    }
    
    private void setParamValue(DebugParamStruct param, String value) throws DebugException {
        try {
            if(
                    param.param.finalParam() || 
                    (param.pointedField == null && Modifier.isFinal(param.field.getModifiers())) ||
                    (param.pointedField != null && Modifier.isFinal(param.pointedField.getModifiers()))
                    ) {
                throw new DebugSettingFinalParamException(param);
            }
            param.field.setAccessible(true);
            if(param.pointedField == null) {
                param.field.set(param.parent.obj, construct(param.param.interpreter()).interpret(value));
            } else {
                param.pointedField.setAccessible(true);
                Object pointedParent = param.field.get(param.parent.obj);
                if(pointedParent == null && !Modifier.isStatic(param.pointedField.getModifiers())) {
                    throw new DebugNullObjectException(param);
                }
                param.pointedField.set(pointedParent, construct(param.param.interpreter()).interpret(value));
            }
        } catch(IllegalAccessException ex) {
            throw new DebugException("Could Not Complete", ex);
        }
    }
    
    private String getParamValue(DebugParamStruct param) throws DebugException {
        try {
            param.field.setAccessible(true);
            if(param.pointedField == null) {
                return construct(param.param.interpreter()).toString(param.field.get(param.parent.obj));
            } else {
                param.pointedField.setAccessible(true);
                Object pointedParent = param.field.get(param.parent.obj);
                if(pointedParent == null && !Modifier.isStatic(param.pointedField.getModifiers())) {
                    throw new DebugNullObjectException(param);
                }
                return construct(param.param.interpreter()).toString(param.pointedField.get(pointedParent));
            }
        } catch(IllegalAccessException ex) {
            throw new DebugException("Could Not Complete", ex);
        }
    }
    
    private Object getObjectParamValue(DebugObjectParamStruct param) throws DebugException {
        try {
            param.field.setAccessible(true);
            Object value = param.field.get(param.parent.obj);
            if(param.param.wrapper() == DebugObjectWrapper.class) {
                return value;
            } else {
                DebugObjectWrapper wrapper = construct(param.param.wrapper());
                wrapper.wrap(value);
                return wrapper;
            }
        } catch(IllegalAccessException ex) {
            throw new DebugException("Could Not Complete", ex);
        }
    }
    
    private static DebugParamStruct getParam(DebugObjectStruct parent, String paramName) throws DebugParamNotFoundException {
        for(Field f: parent.obj.getClass().getDeclaredFields()) {
            DebugParamStruct struct;
            DebugParam param = f.getAnnotation(DebugParam.class);
            DebugParams params;
            if(param != null) {
                struct = getParam(parent, param, f);
                if(struct.name.equals(paramName)) {
                    return struct;
                }
            } else if((params = f.getAnnotation(DebugParams.class)) != null) {
                for(DebugParam dp: params.value()) {
                    struct = getParam(parent, dp, f);
                    if(struct.name.equals(paramName)) {
                        return struct;
                    }
                }
            }
        }
        throw new DebugParamNotFoundException(parent, paramName);
    }
    
    private static DebugParamStruct[] getAllParams(DebugObjectStruct parent) {
        ArrayList<DebugParamStruct> list = new ArrayList<>();
        for(Field f: parent.obj.getClass().getDeclaredFields()) {
            DebugParam param = f.getAnnotation(DebugParam.class);
            DebugParams params;
            if(param != null) {
                list.add(getParam(parent, param, f));
            } else if((params = f.getAnnotation(DebugParams.class)) != null) {
                for(DebugParam dp: params.value()) {
                    list.add(getParam(parent, dp, f));
                }
            }
        }
        return list.toArray(new DebugParamStruct[list.size()]);
    }
    
    private static DebugParamStruct getParam(DebugObjectStruct parent, DebugParam param, Field field) {
        String name = param.name().isEmpty() ?
                field.getName() :
                param.name();
        Field pointedField = null;
        if(!param.pointer().isEmpty()) {
            for(Field f: field.getType().getDeclaredFields()) {
                if(f.getName().equals(param.pointer())) {
                    pointedField = f;
                    break;
                }
            }
            if(pointedField == null) {
                throw new DebugPointedParamNotFoundException(parent, field, param);
            }
        }
        return new DebugParamStruct(parent, param, name, field, pointedField);
    }
    
    private static DebugObjectParamStruct getObjectParam(DebugObjectStruct parent, String paramName) throws DebugObjectParamNotFoundException {
        for(Field f: parent.obj.getClass().getDeclaredFields()) {
            DebugObjectParam param = f.getAnnotation(DebugObjectParam.class);
            if(param != null) {
                String name = param.name().isEmpty() ?
                        f.getName() :
                        param.name();
                if(name.equals(paramName)) {
                    return new DebugObjectParamStruct(parent, param, name, f, null);
                }
            }
        }
        throw new DebugObjectParamNotFoundException(parent, paramName);
    }
    
    private static DebugObjectParamStruct[] getAllObjectParams(DebugObjectStruct parent) {
        ArrayList<DebugObjectParamStruct> list = new ArrayList<>();
        for(Field f: parent.obj.getClass().getDeclaredFields()) {
            DebugObjectParam param = f.getAnnotation(DebugObjectParam.class);
            if(param != null) {
                String name = param.name().isEmpty() ?
                        f.getName() :
                        param.name();
                list.add(new DebugObjectParamStruct(parent, param, name, f, null));
            }
        }
        return list.toArray(new DebugObjectParamStruct[list.size()]);
    }
    
    private static DebugObjectStruct getObject(Object obj) throws DebugNotObjectException {
        DebugObject dObj = obj.getClass().getAnnotation(DebugObject.class);
        if(dObj != null) {
            String name = dObj.name().isEmpty() ? 
                    obj.getClass().getSimpleName() : 
                    dObj.name();
            return new DebugObjectStruct(obj, dObj, name);
        } else {
            throw new DebugNotObjectException(obj);
        }
    }
    
    /*private static DebugFloatSliderField getFloatSliderField(DebugParamStruct param) throws DebugAmbiguousAnnotationException {
        DebugFloatSliderField slider = param.field.getAnnotation(DebugFloatSliderField.class);
        if(slider != null) {
            if(slider.target().isEmpty() || slider.target().equals(param.name)) {
                return slider;
            }
        } else {
            DebugFloatSliderFields sliders = param.field.getAnnotation(DebugFloatSliderFields.class);
            if(sliders != null) {
                for(DebugFloatSliderField s: sliders.value()) {
                    if(s.target().isEmpty()) {
                        throw new DebugAmbiguousAnnotationException(param);
                    } else if(s.target().equals(param.name)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }*/
    
    /*private static DebugIntSliderField getIntSliderField(DebugParamStruct param) throws DebugAmbiguousAnnotationException {
        DebugIntSliderField slider = param.field.getAnnotation(DebugIntSliderField.class);
        if(slider != null) {
            if(slider.target().isEmpty() || slider.target().equals(param.name)) {
                return slider;
            }
        } else {
            DebugIntSliderFields sliders = param.field.getAnnotation(DebugIntSliderFields.class);
            if(sliders != null) {
                for(DebugIntSliderField s: sliders.value()) {
                    if(s.target().isEmpty()) {
                        throw new DebugAmbiguousAnnotationException(param);
                    } else if(s.target().equals(param.name)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }*/
    
    private static <T extends Annotation, S extends Annotation> T getVisualField(DebugParamStruct param, 
            Class<T> fieldClazz, Class<S> repeatClazz, Function<T, String> getTarget, 
            Function<S, T[]> getValue) throws DebugAmbiguousAnnotationException {
        
        T field = param.field.getAnnotation(fieldClazz);
        if(field != null) {
            if(getTarget.apply(field).isEmpty() || getTarget.apply(field).equals(param.name)) {
                return field;
            }
        } else {
            S fields = param.field.getAnnotation(repeatClazz);
            if(fields != null) {
                for(T t: getValue.apply(fields)) {
                    if(getTarget.apply(t).isEmpty()) {
                        throw new DebugAmbiguousAnnotationException(param);
                    } else if(getTarget.apply(t).equals(param.name)) {
                        return t;
                    }
                }
            }
        }
        return null;
    }
    
    private static <T> T construct(Class<? extends T> clazz) throws DebugException {
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for(Constructor<?> c: constructors) {
                if(c.getParameterCount() == 0) {
                    c.setAccessible(true);
                    return (T) c.newInstance();
                }
            }
            throw new NoSuchMethodException("No default constructor found");
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DebugException("Could not create instance of " + clazz.getSimpleName(), ex);
        }
    }
    
    protected static class DebugObjectStruct {
        public final Object obj;
        public final DebugObject dObj;
        public final String name;

        private DebugObjectStruct(Object obj, DebugObject dObj, String name) {
            this.obj = obj;
            this.dObj = dObj;
            this.name = name;
        }
    }
    
    protected static class DebugParamStruct {
        public final DebugObjectStruct parent;
        public final DebugParam param;
        public final String name;
        public final Field field;
        public final Field pointedField;

        private DebugParamStruct(DebugObjectStruct parent, DebugParam param, String name, Field field, Field pointedField) {
            this.parent = parent;
            this.param = param;
            this.name = name;
            this.field = field;
            this.pointedField = pointedField;
        }
    }
    
    protected static class DebugObjectParamStruct {
        public final DebugObjectStruct parent;
        public final DebugObjectParam param;
        public final String name;
        public final Field field;
        public final Field pointedField;

        private DebugObjectParamStruct(DebugObjectStruct parent, DebugObjectParam param, String name, Field field, Field pointedField) {
            this.parent = parent;
            this.param = param;
            this.name = name;
            this.field = field;
            this.pointedField = pointedField;
        }
    }
    
    private static class FetchResult {
        private final DebugObjectStruct parent;
        private final String param;

        FetchResult(DebugObjectStruct parent, String param) {
            this.parent = parent;
            this.param = param;
        }
    }
    
    private static class SetParamJob {
        private final String param;
        private final String value;

        SetParamJob(String param, String value) {
            this.param = param;
            this.value = value;
        }
    }
    
    private static interface VisualParamField {
        public JComponent getComponent();
        public void setField(String value);
    }
    
    private class TextFieldParamField implements VisualParamField {
        private final String paramPath;
        private final JTextField field;

        TextFieldParamField(String paramPath, String initial) {
            this.paramPath = paramPath;
            this.field = new JTextField();
            field.setText(initial);
            field.addActionListener((ActionEvent e) -> {
                setStack.push(new SetParamJob(paramPath, field.getText()));
            });
        }
        
        @Override
        public JComponent getComponent() {
            return field;
        }

        @Override
        public void setField(String value) {
            field.setText(value);
        }
        
    }
    
    private class IntSliderParamField implements VisualParamField {
        private final String paramPath;
        private final DebugIntSliderField param;
        private final JSlider slider;
        
        IntSliderParamField(String paramPath, DebugIntSliderField param, String initial) {
            this.paramPath = paramPath;
            this.param = param;
            this.slider = new JSlider();
            slider.setMinimum(param.min());
            slider.setMaximum(param.max());
            slider.setValue(Integer.parseInt(initial));
            slider.setPaintLabels(true);
            slider.setPaintTicks(true);
            slider.addChangeListener((ChangeEvent e) -> {
                setStack.push(new SetParamJob(paramPath, Integer.toString(slider.getValue())));
            });
        }
        
        @Override
        public JComponent getComponent() {
            return slider;
        }

        @Override
        public void setField(String value) {
            slider.setValue(Integer.parseInt(value));
        }
    }
    
    private class FloatSliderParamField implements VisualParamField {
        private final String paramPath;
        private final DebugFloatSliderField param;
        private final JSlider slider;
        private final HashMap<Float, Integer> forwardMap;
        private final HashMap<Integer, Float> backwardMap;
        
        FloatSliderParamField(String paramPath, DebugFloatSliderField param, String initial) {
            this.paramPath = paramPath;
            this.param = param;
            this.slider = new JSlider();
            int nDivisions = (int) Math.ceil((param.max() - param.min()) / param.step());
            Hashtable labelTable = new Hashtable();
            forwardMap = new HashMap<Float, Integer>();
            backwardMap = new HashMap<Integer, Float>();
            float f = param.min();
            for(int i = 0; i < nDivisions; i++) {
                forwardMap.put(f, i);
                backwardMap.put(i, f);
                labelTable.put(i, new JLabel(String.format("%.3f", f)));
                f += param.step();
            }
            slider.setMinimum(0);
            slider.setMaximum(nDivisions);
            slider.setLabelTable(labelTable);
            slider.setValue(getClosest(Float.parseFloat(initial)));
            slider.setPaintLabels(true);
            slider.setPaintTicks(true);
            slider.addChangeListener((ChangeEvent e) -> {
                setStack.push(new SetParamJob(paramPath, Float.toString(backwardMap.get(slider.getValue()))));
            });
        }
        
        @Override
        public JComponent getComponent() {
            return slider;
        }

        @Override
        public void setField(String value) {
            slider.setValue(getClosest(Float.parseFloat(value)));
        }
        
        public final int getClosest(float f) {
            if(f < param.min()) {
                return forwardMap.get(param.min());
            } else {
                float g = param.min();
                while(g < param.max()) {
                    float g2 = g + param.step();
                    if(g <= f && g2 >= f) {
                        if(g2 - f > f - g) {
                            return forwardMap.get(g);
                        } else {
                            return forwardMap.get(g2);
                        }
                    }
                    g = g2;
                }
                return forwardMap.get(g);
            }
        }
    }
    
    private class ToggleButtonParamField implements VisualParamField {
        private final String paramPath;
        private final JToggleButton toggleButton;
        
        ToggleButtonParamField(String paramPath, DebugToggleField param, String initial) {
            this.paramPath = paramPath;
            this.toggleButton = new JToggleButton();
            toggleButton.setSelected(Boolean.parseBoolean(initial));
            toggleButton.addChangeListener((ChangeEvent e) -> {
                setStack.push(new SetParamJob(paramPath, Boolean.toString(toggleButton.isSelected())));
            });
        }
        
        @Override
        public JComponent getComponent() {
            return toggleButton;
        }

        @Override
        public void setField(String value) {
            toggleButton.setSelected(Boolean.parseBoolean(value));
        }
    }
}
