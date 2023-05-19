package com.primitives;

public class Vector2 {
    public float u, v;
    public float w = 1.0f;

    public Vector2() {this(0, 0);}
    public Vector2(float u, float v) {
        this.u = u;
        this.v = v;
    }
}
