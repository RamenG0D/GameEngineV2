package com.primitives;

import com.primitives.Vectors.Vector3;

public class Matrix4 {
    private double[][] matrix = new double[4][4];

    public Matrix4() {
        //IDENTITY().getMatrix();
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public Matrix4 multiply(double scalar) {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result.set(
                    row,
                    col,
                    this.get(row, col) 
                    + result.get(row, col)
                    * scalar
                );
            }
        }
        return result;
    }

    public void multiply(Vector3 v1, Vector3 v2) {
        v2.setX(v1.getX() * matrix[0][0] + v1.getY() * matrix[1][0] + v1.getZ() * matrix[2][0] + matrix[3][0]);
        v2.setY(v1.getX() * matrix[0][1] + v1.getY() * matrix[1][1] + v1.getZ() * matrix[2][1] + matrix[3][1]);
        v2.setZ(v1.getX() * matrix[0][2] + v1.getY() * matrix[1][2] + v1.getZ() * matrix[2][2] + matrix[3][2]);
        double w = v1.getX() * matrix[0][3] + v1.getY() * matrix[1][3] + v1.getZ() * matrix[2][3] + matrix[3][3];

        if (w != 0.0f) {
            v2.setX(v2.getX() / w); 
            v2.setY(v2.getY() / w); 
            v2.setZ(v2.getZ() / w);
        }
    }

    public void rotateZ(Quaternion rotation) {
        set(0, 0, Math.cos(rotation.getX()));
		set(0, 1, Math.sin(rotation.getY()));
		set(1, 0, -Math.sin(rotation.getZ()));
		set(1, 1, Math.cos(rotation.getX()));
		set(2, 2, 1);
		set(3, 3, 1);
    }

    public void rotateX(Quaternion rotation) {
        set(0, 0, 1);
		set(1, 1, Math.cos(rotation.getX()));
		set(1, 2, Math.sin(rotation.getY()));
		set(2, 1, -Math.sin(rotation.getZ()));
		set(2, 2, Math.cos(rotation.getX()));
		set(3, 3, 1);
    }

    public static Vector3 multiply(Matrix4 pm, Quaternion point) {
        double[] vec = point.getAsArray();
        double[] res = new double[pm.getMatrix().length];

        for(int row = 0; row < vec.length; row++) {
            double r = 0.0;
            for(int col = 0; col < res.length; col++) {
                r += pm.matrix[row][col] * vec[col];
            }
            res[row] = r;
        }
        return new Vector3(res[0], res[1], res[3]);
    }

    public Matrix4 multiply(Matrix4 other) {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result.set(
                    row,
                    col,
                    this.get(row, col) 
                    + result.get(row, col) 
                    * other.get(row, col) 
                );
            }
        }
        return result;
    }

    public static final Matrix4 IDENTITY() {
        return new Matrix4(new double[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
            }
        );
    }

    public Matrix4(double[][] m) {
        this.matrix = m;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < matrix.length; row++) {
            builder.append("-------------\n");
            for (int col = 0; col < matrix.length; col++) {
                builder.append(matrix[row][col]).append(" ");
            }
            builder.append("-------------\n");
        }
        return builder.toString();
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double get(int row, int column) {
        return matrix[row][column];
    }

    public void set(int row, int column, double val) {
        this.matrix[row][column] = val;
    }

    /*public static double[] matrixMultiply(double[][] matrix, double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }*/

    public Matrix4 divide(Matrix4 other) {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result.set(
                    row,
                    col,
                    this.get(row, col) 
                    + result.get(row, col) 
                    / other.get(row, col) 
                );
            }
        }
        return result;
    }

    public Matrix4 add(Matrix4 other) {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result.set(
                    row,
                    col,
                    this.get(row, col) + 
                    other.get(row, col)
                );
            }
        }
        return result;
    }

    public Matrix4 subtract(Matrix4 other) {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result.set(
                    row,
                    col,
                    this.get(row, col) - 
                    other.get(row, col)
                );
            }
        }
        return result;
    }
}
