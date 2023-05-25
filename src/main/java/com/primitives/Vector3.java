package com.primitives;

public class Vector3 implements Cloneable {
    public float x, y, z;
    public float w = 1.0f;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    @Override
    public Vector3 clone() {
        try {
            Vector3 clone = (Vector3) super.clone();
            clone.x = this.x;
            clone.y = this.y;
            clone.z = this.z;
            clone.w = this.w;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
