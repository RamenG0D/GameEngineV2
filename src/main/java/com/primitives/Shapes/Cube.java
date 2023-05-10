package com.primitives.Shapes;

import java.util.ArrayList;
import java.util.List;
import com.primitives.Vectors.Vector3;

public class Cube {

    private List<Triangle> tris = new ArrayList<>(12);
    
    private double size;

    /*public Mesh(Triangle[] tris) {
        for(Triangle t : tris) {
            this.tris.add(t);
        }
    }

    public Mesh(List<Triangle> tris) {
        this.tris = tris;
    }*/

    public Cube(double size) {
        Triangle[] t = {
            // front face
            new Triangle(new Vector3(0, 0, 0), new Vector3(0, 1, 0), new Vector3(1, 1, 0)),
            new Triangle(new Vector3(1, 1, 0), new Vector3(1, 0, 0), new Vector3(0, 0, 0)),
            // right face
            new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 1), new Vector3(1, 0, 1)),
            new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 1, 1)),
            // top face
            new Triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 1), new Vector3(1, 1, 1)),
            new Triangle(new Vector3(0, 1, 0), new Vector3(1, 1, 1), new Vector3(1, 1, 0)),
            // bottom face
            new Triangle(new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(1, 0, 1)),
            new Triangle(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 0, 1)),
            // back face
            new Triangle(new Vector3(1, 1, 1), new Vector3(0, 1, 1), new Vector3(0, 0, 1)),
            new Triangle(new Vector3(1, 1, 1), new Vector3(0, 0, 0), new Vector3(0, 0, 0)),
            // left face
            new Triangle(new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(0, 1, 0)),
            new Triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 1), new Vector3(0, 0, 1))
        };
        for (Triangle triangle : t) {
            this.tris.add(triangle);
        }

        if(size == 0) this.size = 1;
        else this.size = size;
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

    public double getSize() {
        return size;
    }

}
