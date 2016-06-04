package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.DataInput;
import com.quew8.geng3d.models.SemanticSet;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.ArrayUtils;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class VertexWeightsParser extends XMLParser implements DataInput {
    private static final String
            COUNT = "count";
    
    private static final String
            INPUT = "input",
            VCOUNT = "vcount",
            V = "v";
    
    private final ArrayList<InputParser> inputs = new ArrayList<InputParser>();
    private int[] vCount;
    private int[] allIndices;

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INPUT, (XMLElementParser) (Element element) -> {
            inputs.add(VertexWeightsParser.this.parseWith(element, new InputParser()));
        });
        to.put(VCOUNT, (XMLElementParser) (Element element) -> {
            CollParseUtils.parseUIntsInto(element.getText(), vCount, 0, vCount.length);
            allIndices = new int[getNIndices()];
        });
        to.put(V, (XMLElementParser) (Element element) -> {
            CollParseUtils.parseUIntsInto(element.getText(), allIndices, 0, allIndices.length);
        });
        return to;
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(COUNT, (XMLIntAttributeParser) (int value, Element parent) -> {
            vCount = new int[value];
        });
        return to;
    }
    
    private int getNVertices() {
        return ArrayUtils.sumArray(vCount);
    }
    
    private int getNIndices() {
        return getNVertices() * getIndexStride();
    }
    
    @Override
    public int[] getVCount() {
        return vCount;
    }

    private int getIndexStride() {
        int n = inputs.get(0).getOffset();
        for (int i = 1; i < inputs.size(); i++) {
            n = Math.max(n, inputs.get(i).getOffset());
        }
        return n + 1;
    }

    private int[] constructIndices(InputParser input, int index, int n) {
        int[] indices = new int[n];
        int indexStride = getIndexStride();
        int indexPos = (index * indexStride) + input.getOffset();
        for (int i = 0; i < n; i++) {
            indices[i] = allIndices[indexPos];
            indexPos += indexStride;
        }
        return indices;
    }

    private InputParser getInput(SemanticSet source) {
        for (InputParser in : inputs) {
            if (in.getSemanticSet().equals(source)) {
                return in;
            }
        }
        throw new RuntimeException("Requested Nonexistent Semantic: " + source);
    }

    @Override
    public void putData(SemanticSet source, Matrix[] dest, int offset, int index, int n) {
        InputParser input = getInput(source);
        int[] indices = constructIndices(input, index, n);
        input.putData(dest, offset, indices);
    }

    @Override
    public void putData(SemanticSet source, String[] dest, int offset, int index, int n) {
        InputParser input = getInput(source);
        int[] indices = constructIndices(input, index, n);
        input.putData(dest, offset, indices);
    }

    @Override
    public void putData(SemanticSet source, float[] dest, int offset, int index, int n) {
        InputParser input = getInput(source);
        int[] indices = constructIndices(input, index, n);
        Float[] tempData = new Float[input.getNValuesPerVertex() * n];
        input.putData(tempData, offset, indices);
        for (int i = 0; i < tempData.length; i++) {
            dest[offset + i] = tempData[i];
        }
    }
}
