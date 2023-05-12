package com.primitives.Shapes;

import java.util.ArrayList;
import java.util.List;
import com.primitives.Vectors.Vector3;

public class Cube {

    private List<Triangle> tris = new ArrayList<>(12);

    public Cube() {
        Triangle[] t = {
            new Triangle(new Vector3(0, 0, 0), new Vector3(0, 1, 0), new Vector3(1, 1, 0)),
            new Triangle(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 1, 0)),
            new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 1), new Vector3(1, 0, 1)),
            new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 1, 1)),
            new Triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 1), new Vector3(1, 1, 1)),
            new Triangle(new Vector3(0, 1, 0), new Vector3(1, 1, 1), new Vector3(1, 1, 0)),
            new Triangle(new Vector3(1, 1, 1), new Vector3(0, 1, 1), new Vector3(0, 0, 1)),
            new Triangle(new Vector3(1, 1, 1), new Vector3(1, 0, 1), new Vector3(0, 0, 1)),
            new Triangle(new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(0, 1, 0)),
            new Triangle(new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(1, 0, 1)),
            new Triangle(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 0, 1))
        };
        for (Triangle triangle : t) {
            this.tris.add(triangle);
        }
    }

    public Triangle getTriangle(int i) {
        return this.tris.get(i);
    }

    public List<Triangle> getTriangles() {
        return tris;
    }

    public void setTriangle(int i, Triangle t) {
        this.tris.set(i, t);
    }
}
