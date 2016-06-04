package com.quew8.geng3d.models.collada;

/**
 *
 * @author Quew8
 */
public class Material {
    private final String name;
    private final Effect effect;

    public Material(String name, Effect effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public Effect getEffect() {
        return effect;
    }
}
