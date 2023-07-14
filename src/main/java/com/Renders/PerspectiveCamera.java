package com.Renders;

import java.awt.Color;
import java.awt.Graphics;
import com.primitives.Vectors.Vector3;

public class PerspectiveCamera {

    public PerspectiveCamera() {}

    public void render(Graphics g) {
    }

    public void drawTriangle(Graphics g, Vector3[] v) {
        g.setColor(Color.WHITE);
        g.drawLine((int)v[0].x, (int)v[0].y, (int)v[1].x, (int)v[1].y);
        g.drawLine((int)v[1].x, (int)v[1].y, (int)v[2].x, (int)v[2].y);
        g.drawLine((int)v[2].x, (int)v[2].y, (int)v[0].x, (int)v[0].y);
    }
}
