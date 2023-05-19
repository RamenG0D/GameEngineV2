package com.primitives.Shapes;

import java.awt.Color;

import com.demos.Util;
import com.primitives.Vector2;
import com.primitives.Vector3;

public final class Triangle implements Cloneable {

	public Vector3[] p = new Vector3[]{new Vector3(), new Vector3(), new Vector3()};
    public Vector2[] t = new Vector2[]{new Vector2(), new Vector2(), new Vector2()};
    public Color col;

    public Triangle() {}

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
        this.p = new Vector3[]{p1, p2, p3};
    }

    public Triangle(float[] p, float[] t) {
        this.p = new Vector3[]{
                new Vector3(p[0], p[1], p[2]),
                new Vector3(p[3], p[4], p[5]),
                new Vector3(p[6], p[7], p[8])
        };
        this.t = new Vector2[]{
                new Vector2(t[0], t[1]),
                new Vector2(t[2], t[3]),
                new Vector2(t[4], t[5])
        };
    }

	public String toString() {
        return this.p[0] + "\n" + this.p[1] + "\n" + this.p[2] + "\n" + this.col + "\n"+ this.t[0] + "\n" + this.t[1] + "\n" + this.t[2] + "\n";
    }

    @Override
    public Triangle clone() {
        Triangle t = new Triangle(this.p[0], this.p[1], this.p[2]);
        t.t = this.t;
        return t;
    }

}
