package com.primitives;

import com.primitives.Vectors.Vector3;

public class Transform {
    private Quaternion rotation;
    private Vector3 position;

    public Transform() {
        this(0d, 0d, 0d, 0d, 0d, 0d);
    }

    public Transform(
        double x, double y, double z, 
        double roll, double yaw, double pitch
    ) {
        position = new Vector3(x, y, z);
        rotation = new Quaternion(0, roll, yaw, pitch);
    }

    public Transform(Vector3 v, Quaternion rotation) {
        this.rotation= rotation;
        this.position = v;
    }

    public void rotate(Quaternion r) {
        rotation.set(rotation.getX() * r.getX(), rotation.getY() * r.getY(), rotation.getZ() * r.getZ(), rotation.getW() * r.getW());
    }

    public void rotate(double roll, double yaw, double pitch) {
        rotate(new Quaternion(roll, yaw, pitch, 0));
    }

}
