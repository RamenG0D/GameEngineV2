package com.primitives.Shapes;

import java.util.ArrayList;
import java.util.Arrays;
import com.primitives.Mesh;
import com.primitives.Texture;

public class Cube extends Mesh {
    private float x, y, z;

    public Cube(float x, float y, float z, String path) {
        super(new ArrayList<>(Arrays.asList(
            // SOUTH
            new Triangle(new float[]{0, 0, 0, 0, 1, 0, 1, 1, 0}, new float[]{0, 1, 0, 0, 1, 0}),
            new Triangle(new float[]{0, 0, 0, 1, 1, 0, 1, 0, 0}, new float[]{0, 1, 1, 0, 1, 1}),
            // EAST
            new Triangle(new float[]{1, 0, 0, 1, 1, 0, 1, 1, 1}, new float[]{0, 1, 0, 0, 1, 0}),
            new Triangle(new float[]{1, 0, 0, 1, 1, 1, 1, 0, 1}, new float[]{0, 1, 1, 0, 1, 1}),
            // NORTH
            new Triangle(new float[]{1, 0, 1, 1, 1, 1, 0, 1, 1}, new float[]{0, 1, 0, 0, 1, 0}),
            new Triangle(new float[]{1, 0, 1, 0, 1, 1, 0, 0, 1}, new float[]{0, 1, 1, 0, 1, 1}),
            // WEST
            new Triangle(new float[]{0, 0, 1, 0, 1, 1, 0, 1, 0}, new float[]{0, 1, 0, 0, 1, 0}),
            new Triangle(new float[]{0, 0, 1, 0, 1, 0, 0, 0, 0}, new float[]{0, 1, 1, 0, 1, 1}),
            // TOP
            new Triangle(new float[]{0, 1, 0, 0, 1, 1, 1, 1, 1}, new float[]{0, 1, 0, 0, 1, 0}),
            new Triangle(new float[]{0, 1, 0, 1, 1, 1, 1, 1, 0}, new float[]{0, 1, 1, 0, 1, 1}),
            // BOTTOM
            new Triangle(new float[]{1, 0, 1, 0, 0, 1, 0, 0, 0}, new float[]{0, 1, 0, 0, 1, 0}),
            new Triangle(new float[]{1, 0, 1, 0, 0, 0, 1, 0, 0}, new float[]{0, 1, 1, 0, 1, 1})
        )), new Texture(path));
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
