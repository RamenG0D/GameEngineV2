package com.primitives.Shapes;

import com.Math3D.Vector3;

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
        this(new Vector3(), new Vector3(), new Vector3());
    }

    public void setV1(Vector3 v1) {
        this.v1 = v1;
    }
    public void setV2(Vector3 v2) {
        this.v2 = v2;
    }
    public void setV3(Vector3 v3) {
        this.v3 = v3;
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
