package com.primitives.Vectors;

public class Vector {

    public static void add(Vector2 a, Vector2 b) {
        a.x += b.x;
        a.y += b.y;
    }

    public static void add(Vector3 a, Vector3 b) {
        a.x += b.x;
        a.y += b.y;
        a.z += b.z;
    }

    public static void add(Vector3 a, Vector2 b) {
        a.x += b.x;
        a.y += b.y;
    }

}
