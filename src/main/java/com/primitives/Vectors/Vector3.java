package com.primitives.Vectors;

public class Vector3 {

    public static final Vector3 X = new Vector3(1, 0, 0);
    public static final Vector3 Y = new Vector3(0, 1, 0);
    public static final Vector3 Z = new Vector3(0, 0, 1);
    public static final Vector3 NEG_X = new Vector3(-1, 0, 0);
    public static final Vector3 NEG_Y = new Vector3(0, -1, 0);
    public static final Vector3 NEG_Z = new Vector3(0, 0, -1);
    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    
    private double x;
    private double y;
    private double z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the x-component of the vector.
     *
     * @return the x-component
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-component of the vector.
     *
     * @param x the x-component
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-component of the vector.
     *
     * @return the y-component
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-component of the vector.
     *
     * @param y the y-component
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the z-component of the vector.
     *
     * @return the z-component
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the z-component of the vector.
     *
     * @param z the z-component
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Adds the given vector to this vector.
     *
     * @param vector the vector to add
     */
    public Vector3 add(Vector3 vector) {
        return new Vector3(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    /**
     * Subtracts the given vector from this vector.
     *
     * @param vector the vector to subtract
     * @return this vector
     */
    public Vector3 subtract(Vector3 vector) {
        return new Vector3(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    /**
     * Multiplies this vector by the given scalar.
     *
     * @param scalar the scalar to multiply by
     */
    public Vector3 multiply(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /**
     * Divides this vector by the given scalar.
     *
     * @param scalar the scalar to divide by
     */
    public Vector3 divide(double scalar) {
        return new Vector3(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    /**
     * Gets the dot product of this vector and the given vector.
     *
     * @param vector the vector to take the dot product with
     * @return the dot product
     */
    public double dot(Vector3 vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    /**
     * Gets the cross product of this vector and the given vector.
     *
     * @param vector the vector to take the cross product with
     * @return the cross product
     */
    public Vector3 cross(Vector3 vector) {
        return new Vector3(
            this.y * vector.z - this.z * vector.y,
            this.z * vector.x - this.x * vector.z,
            this.x * vector.y - this.y * vector.x
        );
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public void normalize() {
        // Calculate the length of the vector.
        double length = this.length();
        // Divide each component of the vector by the length.
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    @Override
    public String toString() {
        return "x: "+x+" y: "+y+" z: "+z;
    }
}
