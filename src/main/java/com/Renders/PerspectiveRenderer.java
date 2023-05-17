package com.Renders;

import com.Math3D.Quaternion;
import com.Math3D.Vector2;

public class PerspectiveRenderer extends Camera {
    private double z;

    public PerspectiveRenderer(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public Vector2 getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public Quaternion getRotation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRotation'");
    }
}
