package com.primitives.Shapes;

import com.primitives.Vectors.Vector3;

public final class Triangle {

    private Vector3 v1, v2, v3;

    /** creates a Triangle with the vertecies specified */
    public Triangle(Vector3 v1, Vector3 v2, Vector3 v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    /** inits a Triangle with all zero vectors */
    public Triangle() {
        this(Vector3.IDENTITY(), Vector3.IDENTITY(), Vector3.IDENTITY());
    }

    public void multiply(double scalar) {
        v1 = v1.multiply(scalar);
        v2 = v2.multiply(scalar);
        v3 = v3.multiply(scalar);
    }

    public Vector3 getV1() {
        return v1;
    }

    public Vector3 getV2() {
        return v2;
    }

    public Vector3 getV3() {
        return v3;
    }
    
}
