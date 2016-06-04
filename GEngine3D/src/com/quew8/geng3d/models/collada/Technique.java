package com.quew8.geng3d.models.collada;

/**
 *
 * @author Quew8
 */
public class Technique {
    private final Phong phong;

    public Technique(Phong phong) {
        this.phong = phong;
    }

    public Phong getPhong() {
        return phong;
    }
}
