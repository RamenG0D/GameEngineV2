package com.primitives.Shapes;

import java.awt.Color;
import com.primitives.Vectors.Vector3;

public final class Triangle {

	public Vector3[] verts;
    public Color color;

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
        verts = new Vector3[] { p1, p2, p3 };
    }

	public String toString() {
        return verts[0] + "\n" + verts[1] + "\n" + verts[2] + "\n" + color + "\n";
    }
}
