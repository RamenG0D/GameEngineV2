package com.Renders;

import com.primitives.Vector3;

public abstract class Camera implements ICamera {
    protected Vector3 pos = new Vector3();
    private float aspectRatio;

    public void setAspect(float aspect) {
        this.aspectRatio = aspect;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public Camera(float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void setX(float x) {
        this.pos.x = x;
    }

    public void setY(float y) {
        this.pos.y = y;
    }
}
