package com.primitives;

import com.primitives.Vectors.Vector3;

public class Matrix4 {
    private double[][] matrix;

    public Matrix4() {
        matrix = new double[4][4];

        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                if(row == col) matrix[row][col] = 1;
                else matrix[row][col] = 0;
            }
        }
    }

    public Matrix4(double x, double y, double z) {
        matrix = new double[3][3];

        matrix[0][0] = 1;matrix[1][0] = 0;matrix[2][0] = x;
        matrix[0][1] = 0;matrix[1][1] = 1;matrix[2][1] = y;
        matrix[0][2] = 0;matrix[1][2] = 0;matrix[2][2] = z;
    }

    public final static Matrix4 IDENTITY = new Matrix4(new double[][] {
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 1}
    });

    public Matrix4(double[][] m) {
        this.matrix = m;
    }

    /*public static Matrix identity() {
        Matrix result = new Matrix();

        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                if(row == col) result.set(row, col, 1);
                else result.set(row, col, 0);
            }
        }
        return result;
    }*/

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
