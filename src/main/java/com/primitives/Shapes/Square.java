package com.primitives.Shapes;

import com.primitives.Vectors.Vector3;

public class Square {
    
    private Triangle t1, t2;

    public Square(Triangle t1, Triangle t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public Square(
        Vector3 v1, Vector3 v2, Vector3 v3, 
        Vector3 v4, Vector3 v5, Vector3 v6
    ) {
        this.t1 = new Triangle(v1, v2, v3);
        this.t2 = new Triangle(v4, v5, v6);
    }

    public Triangle getTriangle(int i) {
        return (i == 0) ? t1 : t2;
    }

}
