package com.quew8.geng.collada;

import java.util.Objects;

/**
 * 
 * @author Quew8
 */
public class SemanticSet {
    public static final SemanticSet 
            VERTEX_0 = new SemanticSet(Semantic.VERTEX, 0),
            NORMAL_0 = new SemanticSet(Semantic.NORMAL, 0),
            TEXCOORD_0 = new SemanticSet(Semantic.TEXCOORD, 0),
            TEXCOORD_1 = new SemanticSet(Semantic.TEXCOORD, 1),
            WEIGHT_0 = new SemanticSet(Semantic.WEIGHT, 0),
            JOINT_0 = new SemanticSet(Semantic.JOINT, 0);
    
    private final Semantic semantic;
    private final int set;

    public SemanticSet(Semantic semantic, int set) {
        this.semantic = semantic;
        this.set = set;
    }

    boolean matches(Semantic semantic, int set) {
        return this.semantic == semantic && this.set == set;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.semantic);
        hash = 29 * hash + this.set;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SemanticSet other = (SemanticSet) obj;
        if (this.semantic != other.semantic) {
            return false;
        }
        return this.set == other.set;
    }

    @Override
    public String toString() {
        return "SemanticSet{" + semantic + ", set=" + set + '}';
    }
    
}
