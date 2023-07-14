package com.Renders;

import java.awt.Graphics;

public interface Camera {

    public void render(Graphics g);

    public void move(double x, double y, double z);

    public void rotate(double degrees);

    public static enum CameraType {
        Perspective2D,
        Perspective3D,
        OrthoGraphic2D, // NOT IMPLEMENTED
        OrthoGraphic3D // NOT IMPLEMENTED
    }

}
