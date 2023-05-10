package com.doom3d.renderer;

import com.primitives.Matrix4;
import com.primitives.Quaternion;
import com.primitives.Vectors.Vector3;

public class Camera {

    private double x, y, z;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Camera() {}

    public void add(Vector3 v) {
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
    }

    public void subtract(Vector3 v) {
        this.x -= v.getX();
        this.y -= v.getY();
        this.z -= v.getZ();
    }

    public Matrix4 getProjectionMatrix() {
        Matrix4 projectionMatrix = new Matrix4(x, y, z);
        return projectionMatrix;
    }

    public Vector3 transform(Vector3 point) {
        Matrix4 projectionMatrix = getProjectionMatrix();
        Quaternion point4D = new Quaternion(point.getX(), point.getY(), point.getZ(), 1);
        Vector3 tPoint = Matrix4.multiply(projectionMatrix, point4D);
        /*new Vector3(
            point4D.getX() * projectionMatrix.get(3, 0) 
            +point4D.getY() * projectionMatrix.get(3, 1) 
            +point4D.getZ() * projectionMatrix.get(3, 2), 
            point4D.getX() * projectionMatrix.get(2, 0) 
            +point4D.getY() * projectionMatrix.get(2, 1) 
            +point4D.getZ() * projectionMatrix.get(2, 2),
            0
        );*/

        System.out.println(tPoint.toString());
        //System.out.println("camX: "+x+" camY: "+y+" camZ: "+z);

        return tPoint;
    }
}