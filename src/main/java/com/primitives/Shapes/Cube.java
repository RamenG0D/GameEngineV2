package com.primitives.Shapes;

import java.util.ArrayList;
import java.util.Arrays;

import com.primitives.Vectors.Vector3;

public class Cube extends Mesh {
    private double x, y, z;

    public Cube(double x, double y, double z) {
        super(
            new ArrayList<>(Arrays.asList(new Triangle[] {
                // FRONT
                new Triangle(new Vector3(1, 0, 0), new Vector3(0, 0, 0), new Vector3(0, 1, 0)),
                new Triangle(new Vector3(1, 1, 0), new Vector3(0, 1, 0), new Vector3(1, 0, 0)),
                // BACK
                new Triangle(new Vector3(1, 0, 1), new Vector3(0, 0, 1), new Vector3(0, 1, 1)),
                new Triangle(new Vector3(1, 1, 1), new Vector3(0, 1, 1), new Vector3(1, 0, 1)),
                // LEFT
                new Triangle(new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(0, 1, 1)),
                new Triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 1), new Vector3(0, 0, 0)),
                // RIGHT
                new Triangle(new Vector3(1, 0, 1), new Vector3(1, 0, 0), new Vector3(1, 1, 0)),
                new Triangle(new Vector3(1, 1, 0), new Vector3(1, 1, 1), new Vector3(1, 0, 1))
            }))
        );
        this.x = x;
        this.y = y;
        this.z = z;
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
}
