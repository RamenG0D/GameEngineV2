package com.primitives.Vectors;

public class Vector3 extends Vector {
    public double x, y, z;
    public double w = 1.0;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public String toString() {
        return "[ x: "+x+", y: "+y+", z: "+z+" ]";
    }
}
