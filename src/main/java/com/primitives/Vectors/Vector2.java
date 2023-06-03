package com.primitives.Vectors;

public class Vector2 extends Vector {
    public double x, y;

    public Vector2() {this(0, 0);}
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector3 toVector3() {
        return new Vector3(x, y, 0);
    }
}
