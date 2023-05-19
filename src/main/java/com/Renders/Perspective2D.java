package com.Renders;

import java.awt.Graphics;

import com.primitives.World;

public class Perspective2D extends Camera {

    public Perspective2D(float x, float y) {
        super(x, y);
    }

    public void render(World world, Graphics g) {
    }

    @Override
    public CameraType getType() {
        return CameraType.Perspective2D;
    }
    
}
