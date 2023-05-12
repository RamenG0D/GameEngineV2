package com.Renders;

import com.primitives.Quaternion;
import com.primitives.Vectors.Vector3;

public interface Camera {

    public Vector3 getPosition();

    public Quaternion getRotation();

    public void add(Vector3 v);

    public void sub(Vector3 v);

}
