package com.primitives.Shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.Math3D.Vector3;

public class Cube {
    private List<Triangle> tris = new ArrayList<>();
    private double x, y, z;

    public Cube(double x, double y, double z) {
        this.tris.add(new Triangle(new Vector3(0, 0, 0), new Vector3(0, 1, 0), new Vector3(1, 1, 0)));
        this.tris.add(new Triangle(new Vector3(0, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 0, 0)));
        this.tris.add(new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 1, 1)));
        this.tris.add(new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 1), new Vector3(1, 0, 1)));
        this.tris.add(new Triangle(new Vector3(1, 0, 1), new Vector3(1, 1, 1), new Vector3(0, 1, 1)));
        this.tris.add(new Triangle(new Vector3(1, 0, 1), new Vector3(0, 1, 1), new Vector3(0, 0, 1)));
        this.tris.add(new Triangle(new Vector3(0, 0, 1), new Vector3(0, 1, 1), new Vector3(0, 1, 0)));
        this.tris.add(new Triangle(new Vector3(0, 0, 1), new Vector3(0, 1, 0), new Vector3(0, 0, 0)));
        this.tris.add(new Triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 1), new Vector3(1, 1, 1)));
        this.tris.add(new Triangle(new Vector3(0, 1, 0), new Vector3(1, 1, 1), new Vector3(1, 1, 0)));
        this.tris.add(new Triangle(new Vector3(1, 0, 1), new Vector3(0, 0, 1), new Vector3(0, 0, 0)));
        this.tris.add(new Triangle(new Vector3(1, 0, 1), new Vector3(0, 0, 0), new Vector3(1, 0, 0)));

        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Color color;

    public Color getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Triangle getTriangle(int i) {
        return this.tris.get(i);
    }

    public List<Triangle> getTriangles() {
        return tris;
    }
}
