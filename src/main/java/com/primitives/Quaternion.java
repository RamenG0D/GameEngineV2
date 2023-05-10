package com.primitives;

import com.primitives.Vectors.Vector3;

public class Quaternion {
    private double x, y, z, w;

    public Quaternion() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double[] getAsArray() {
        return new double[] { this.x, this.y, this.z, this.w };
    }

    public static Quaternion arrayToQuaternion(double[] array) {
        return new Quaternion(array[0], array[1], array[2], array[3]);
    }

    public void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public Vector3 divideByW() {
        return new Vector3(x/w, y/w, z/w);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }
}
