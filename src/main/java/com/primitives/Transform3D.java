package com.primitives;

public class Transform3D implements ITransform {
    private Vector3 lookDir = new Vector3();
	private float rotation, yaw;
    private Vector3 pos = new Vector3();
	private float far = 0.1f, near = 1000f, fov = 70f;

    public Transform3D(
        Vector3 pos, 
        Vector3 lookDir, 
        float far, 
        float near, 
        float fov, 
        float rotation, 
        float yaw
    ) {
        this.far = far;
        this.pos = pos;
        this.near = near;
        this.fov = fov;
        this.lookDir = lookDir;
        this.rotation = rotation;
        this.yaw = yaw;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setLookDir(Vector3 lookDir) {
        this.lookDir = lookDir;
    }

    public Vector3 getPosition() {
        return pos;
    }

    public Vector3 getLookDir() {
        return lookDir;
    }

    public float getRotation() {
        return rotation;
    }

    public float getFOV() {
        return fov;
    }

    public float getFar() {
        return far;
    }

    public float getNear() {
        return near;
    }

    public float getYaw() {
        return yaw;
    }
}
