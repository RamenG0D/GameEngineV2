package com.Renders;

import java.awt.Graphics;

import com.primitives.ITransform;
import com.primitives.Transform2D;
import com.primitives.World;

public class Perspective2D extends Camera {
    private Transform2D transform;

    public Perspective2D(float x, float y) {
        //
    }

    public void render(World world, Graphics g) {
    }

    @Override
    public CameraType getType() {
        return CameraType.Perspective2D;
    }

    @Override
    public ITransform getTransform() {
        return transform;
    }
    
}
