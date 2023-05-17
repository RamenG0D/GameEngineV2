package com.Renders;

public abstract class Camera implements ICamera {
    //private Quaternion rotation = Quaternion;
    private double x, y;
    

    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*public Vector2 getPosition() {
        return new Vector2(x, y);
    }*/

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /*public Quaternion getRotation() {
        return rotation;
    }*/
}
