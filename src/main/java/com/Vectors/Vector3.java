package com.Vectors;

public class Vector3 {
    protected int x, y, z;

    public Vector3() {}

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int Normalize() {
        return x * y * z;
    }

}
