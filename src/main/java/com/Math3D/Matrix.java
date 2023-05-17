/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static com.Math3D.Tolerence.*;


/**
 * Matrix functions.
 *
 * @author Philip DeCamp
 */
public final class Matrix {


    //=== MAT3 Functions ======================================


    public static void put( Matrix3 mat, Matrix3 out ) {
        out.m00 = mat.m00;
        out.m01 = mat.m01;
        out.m02 = mat.m02;
        out.m10 = mat.m10;
        out.m11 = mat.m11;
        out.m12 = mat.m12;
        out.m20 = mat.m20;
        out.m21 = mat.m21;
        out.m22 = mat.m22;
    }


    public static void put( Matrix3 mat, Matrix4 out ) {
        out.m00 = mat.m00;
        out.m01 = mat.m01;
        out.m02 = mat.m02;
        out.m03 = 0;
        out.m10 = mat.m10;
        out.m11 = mat.m11;
        out.m12 = mat.m12;
        out.m23 = 0;
        out.m20 = mat.m20;
        out.m21 = mat.m21;
        out.m22 = mat.m22;
        out.m23 = 0;
        out.m30 = 0;
        out.m31 = 0;
        out.m32 = 0;
        out.m33 = 1;
    }


    public static void put( Matrix3 mat, ByteBuffer bb ) {
        bb.putFloat( mat.m00 );
        bb.putFloat( mat.m10 );
        bb.putFloat( mat.m20 );
        bb.putFloat( mat.m01 );
        bb.putFloat( mat.m11 );
        bb.putFloat( mat.m21 );
        bb.putFloat( mat.m02 );
        bb.putFloat( mat.m12 );
        bb.putFloat( mat.m22 );
    }


    public static void put( ByteBuffer bb, Matrix3 mat ) {
        mat.m00 = bb.getFloat();
        mat.m10 = bb.getFloat();
        mat.m20 = bb.getFloat();
        mat.m01 = bb.getFloat();
        mat.m11 = bb.getFloat();
        mat.m21 = bb.getFloat();
        mat.m02 = bb.getFloat();
        mat.m12 = bb.getFloat();
        mat.m22 = bb.getFloat();
    }


    public static void put( Matrix3 mat, FloatBuffer fb ) {
        fb.put( mat.m00 );
        fb.put( mat.m10 );
        fb.put( mat.m20 );
        fb.put( mat.m01 );
        fb.put( mat.m11 );
        fb.put( mat.m21 );
        fb.put( mat.m02 );
        fb.put( mat.m12 );
        fb.put( mat.m22 );
    }


    public static void put( FloatBuffer fb, Matrix3 mat ) {
        mat.m00 = fb.get();
        mat.m10 = fb.get();
        mat.m20 = fb.get();
        mat.m01 = fb.get();
        mat.m11 = fb.get();
        mat.m21 = fb.get();
        mat.m02 = fb.get();
        mat.m12 = fb.get();
        mat.m22 = fb.get();
    }


    public static void put( float[] arr, Matrix3 mat ) {
        mat.m00 = arr[0];
        mat.m10 = arr[1];
        mat.m20 = arr[2];
        mat.m01 = arr[3];
        mat.m11 = arr[4];
        mat.m21 = arr[5];
        mat.m02 = arr[6];
        mat.m12 = arr[7];
        mat.m22 = arr[8];
    }


    public static void put( Matrix3 mat, float[] arr ) {
        arr[0] = mat.m00;
        arr[1] = mat.m10;
        arr[2] = mat.m20;
        arr[3] = mat.m01;
        arr[4] = mat.m11;
        arr[5] = mat.m21;
        arr[6] = mat.m02;
        arr[7] = mat.m12;
        arr[8] = mat.m22;
    }


    public static void put( float v, Matrix3 out ) {
        out.m00 = v;
        out.m10 = v;
        out.m20 = v;
        out.m01 = v;
        out.m11 = v;
        out.m21 = v;
        out.m02 = v;
        out.m12 = v;
        out.m22 = v;
    }


    public static void mult( Matrix3 a, Matrix3 b, Matrix3 out ) {
        float a0 = a.m00;
        float a1 = a.m10;
        float a2 = a.m20;
        float a3 = a.m01;
        float a4 = a.m11;
        float a5 = a.m21;
        float a6 = a.m02;
        float a7 = a.m12;
        float a8 = a.m22;
        float b0 = b.m00;
        float b1 = b.m10;
        float b2 = b.m20;
        out.m00 = a0*b0 + a3*b1 + a6*b2;
        out.m10 = a1*b0 + a4*b1 + a7*b2;
        out.m20 = a2*b0 + a5*b1 + a8*b2;
        b0 = b.m01;
        b1 = b.m11;
        b2 = b.m21;
        out.m01 = a0*b0 + a3*b1 + a6*b2;
        out.m11 = a1*b0 + a4*b1 + a7*b2;
        out.m21 = a2*b0 + a5*b1 + a8*b2;
        b0 = b.m02;
        b1 = b.m12;
        b2 = b.m22;
        out.m02 = a0*b0 + a3*b1 + a6*b2;
        out.m12 = a1*b0 + a4*b1 + a7*b2;
        out.m22 = a2*b0 + a5*b1 + a8*b2;
    }


    public static void mult( Matrix3 a, Vector3 b, Vector3 out ) {
        float t0 = a.m00*b.x + a.m01*b.y + a.m02*b.z;
        float t1 = a.m10*b.x + a.m11*b.y + a.m12*b.z;
        float t2 = a.m20*b.x + a.m21*b.y + a.m22*b.z;
        out.x = t0;
        out.y = t1;
        out.z = t2;
    }


    public static void mult( Matrix3 a, Vector4 b, Vector4 out ) {
        mult( a, (Vector3)b, out );
        out.w = b.w;
    }


    public static void multAdd( float sa, Matrix3 a, float sb, Matrix3 b, Matrix3 out ) {
        out.m00 = sa * a.m00 + sb * b.m00;
        out.m10 = sa * a.m10 + sb * b.m10;
        out.m20 = sa * a.m20 + sb * b.m20;
        out.m01 = sa * a.m01 + sb * b.m01;
        out.m11 = sa * a.m11 + sb * b.m11;
        out.m21 = sa * a.m21 + sb * b.m21;
        out.m02 = sa * a.m02 + sb * b.m02;
        out.m12 = sa * a.m12 + sb * b.m12;
        out.m22 = sa * a.m22 + sb * b.m22;
    }

    /**
     * @param mat    Input matrix
     * @param out    Array to hold inverted matrix on return.
     * @return true if matrix determinant is not near zero and accurate invert was found.
     */
    public static boolean invert( Matrix3 mat, Matrix3 out ) {
        float c00 = mat.m11 * mat.m22 - mat.m21 * mat.m12;
        float c01 = mat.m20 * mat.m12 - mat.m10 * mat.m22;
        float c02 = mat.m10 * mat.m21 - mat.m20 * mat.m11;
        float c10 = mat.m21 * mat.m02 - mat.m01 * mat.m22;
        float c11 = mat.m00 * mat.m22 - mat.m20 * mat.m02;
        float c12 = mat.m20 * mat.m01 - mat.m00 * mat.m21;
        float c20 = mat.m01 * mat.m12 - mat.m11 * mat.m02;
        float c21 = mat.m10 * mat.m02 - mat.m00 * mat.m12;
        float c22 = mat.m00 * mat.m11 - mat.m10 * mat.m01;

        // Compute determinant
        float invDet  = mat.m00 * c00 + mat.m01 * c01 + mat.m02 * c02;
        // Check if invertible
        boolean valid = invDet > FSQRT_ABS_ERR || -invDet > FSQRT_ABS_ERR;
        // Invert determinant
        invDet = 1f / invDet;

        out.m00 = c00 * invDet;
        out.m10 = c01 * invDet;
        out.m20 = c02 * invDet;
        out.m01 = c10 * invDet;
        out.m11 = c11 * invDet;
        out.m21 = c12 * invDet;
        out.m02 = c20 * invDet;
        out.m12 = c21 * invDet;
        out.m22 = c22 * invDet;

        return valid;
    }


    public static void transpose( Matrix3 a, Matrix3 out ) {
        float a0 = a.m00;
        float a1 = a.m10;
        float a2 = a.m20;
        float a3 = a.m01;
        float a4 = a.m11;
        float a5 = a.m21;
        float a6 = a.m02;
        float a7 = a.m12;
        float a8 = a.m22;
        out.m00 = a0;
        out.m10 = a3;
        out.m20 = a6;
        out.m01 = a1;
        out.m11 = a4;
        out.m21 = a7;
        out.m02 = a2;
        out.m12 = a5;
        out.m22 = a8;
    }


    public static float det( Matrix3 mat ) {
        return mat.m00 * ( mat.m11 * mat.m22 - mat.m21 * mat.m12 ) +
               mat.m01 * ( mat.m20 * mat.m12 - mat.m10 * mat.m22 ) +
               mat.m02 * ( mat.m10 * mat.m21 - mat.m20 * mat.m11 );
    }


    public static Matrix3 identity3() {
        Matrix3 ret = new Matrix3();
        ret.m00 = 1f;
        ret.m11 = 1f;
        ret.m22 = 1f;
        return ret;
    }


    public static void identity( Matrix3 out ) {
        out.m00 = 1f;
        out.m10 = 0f;
        out.m20 = 0f;
        out.m01 = 0f;
        out.m11 = 1f;
        out.m21 = 0f;
        out.m02 = 0f;
        out.m12 = 0f;
        out.m22 = 1f;
    }


    public static void getScale( float sx, float sy, float sz, Matrix3 out ) {
        out.m00 = sx;
        out.m10 = 0f;
        out.m20 = 0f;
        out.m01 = 0f;
        out.m11 = sy;
        out.m21 = 0f;
        out.m02 = 0f;
        out.m12 = 0f;
        out.m22 = sz;
    }


    public static void scale( Matrix3 mat, float sx, float sy, float sz, Matrix3 out ) {
        out.m00 = sx * mat.m00;
        out.m10 = sx * mat.m10;
        out.m20 = sx * mat.m20;
        out.m01 = sy * mat.m01;
        out.m11 = sy * mat.m11;
        out.m21 = sy * mat.m21;
        out.m02 = sz * mat.m02;
        out.m12 = sz * mat.m12;
        out.m22 = sz * mat.m22;
    }


    public static void preScale( float sx, float sy, float sz, Matrix3 mat, Matrix3 out ) {
        out.m00 = sx * mat.m00;
        out.m10 = sy * mat.m10;
        out.m20 = sz * mat.m20;
        out.m01 = sx * mat.m01;
        out.m11 = sy * mat.m11;
        out.m21 = sz * mat.m21;
        out.m02 = sx * mat.m02;
        out.m12 = sy * mat.m12;
        out.m22 = sz * mat.m22;
    }


    public static void getRotation( float radians, float x, float y, float z, Matrix3 out ) {
        float c = (float)Math.cos( radians );
        float s = (float)Math.sin( radians );

        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        out.m00 = x*x*(1-c)+c;
        out.m10 = x*y*(1-c)+z*s;
        out.m20 = x*z*(1-c)-y*s;

        out.m01 = x*y*(1-c)-z*s;
        out.m11 = y*y*(1-c)+c;
        out.m21 = y*z*(1-c)+x*s;

        out.m02 = x*z*(1-c)+y*s;
        out.m12 = y*z*(1-c)-x*s;
        out.m22 = z*z*(1-c)+c;
    }

    /**
     * Multiplies an arbitrary matrix with a getRotate4 matrix.
     *
     * @param mat     Input matrix.
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotate( Matrix3 mat, float radians, float x, float y, float z, Matrix3 out ) {
        final float c = (float)Math.cos( radians );
        final float s = (float)Math.sin( radians );
        final float invSum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= invSum;
        y *= invSum;
        z *= invSum;

        final float a00 = x*x*(1-c)+c;
        final float a01 = x*y*(1-c)+z*s;
        final float a02 = x*z*(1-c)-y*s;
        final float a04 = x*y*(1-c)-z*s;
        final float a05 = y*y*(1-c)+c;
        final float a06 = y*z*(1-c)+x*s;
        final float a08 = x*z*(1-c)+y*s;
        final float a09 = y*z*(1-c)-x*s;
        final float a10 = z*z*(1-c)+c;

        float b0 = mat.m00;
        float b1 = mat.m01;
        float b2 = mat.m02;
        out.m00 = a00*b0 + a01*b1 + a02*b2;
        out.m01 = a04*b0 + a05*b1 + a06*b2;
        out.m02 = a08*b0 + a09*b1 + a10*b2;
        b0 = mat.m10;
        b1 = mat.m11;
        b2 = mat.m12;
        out.m10 = a00*b0 + a01*b1 + a02*b2;
        out.m11 = a04*b0 + a05*b1 + a06*b2;
        out.m12 = a08*b0 + a09*b1 + a10*b2;
        b0 = mat.m20;
        b1 = mat.m21;
        b2 = mat.m22;
        out.m20 = a00*b0 + a01*b1 + a02*b2;
        out.m21 = a04*b0 + a05*b1 + a06*b2;
        out.m22 = a08*b0 + a09*b1 + a10*b2;
    }

    /**
     * Multiplies a getRotate4 matrix with an arbitrary matrix.
     *
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param mat     Input matrix.
     * @param out     Length-16 array to hold output on return.
     */
    public static void preRotate( float radians, float x, float y, float z, Matrix3 mat, Matrix3 out ) {
        float c = (float)Math.cos( radians );
        float s = (float)Math.sin( radians );
        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        float a00 = x*x*(1-c)+c;
        float a01 = x*y*(1-c)+z*s;
        float a02 = x*z*(1-c)-y*s;
        float a04 = x*y*(1-c)-z*s;
        float a05 = y*y*(1-c)+c;
        float a06 = y*z*(1-c)+x*s;
        float a08 = x*z*(1-c)+y*s;
        float a09 = y*z*(1-c)-x*s;
        float a10 = z*z*(1-c)+c;
        float b0 = mat.m00;
        float b1 = mat.m10;
        float b2 = mat.m20;
        out.m00 = a00*b0 + a04*b1 + a08*b2;
        out.m10 = a01*b0 + a05*b1 + a09*b2;
        out.m20 = a02*b0 + a06*b1 + a10*b2;
        b0 = mat.m01;
        b1 = mat.m11;
        b2 = mat.m21;
        out.m01 = a00*b0 + a04*b1 + a08*b2;
        out.m11 = a01*b0 + a05*b1 + a09*b2;
        out.m21 = a02*b0 + a06*b1 + a10*b2;
        b0 = mat.m02;
        b1 = mat.m12;
        b2 = mat.m22;
        out.m02 = a00*b0 + a04*b1 + a08*b2;
        out.m12 = a01*b0 + a05*b1 + a09*b2;
        out.m22 = a02*b0 + a06*b1 + a10*b2;
    }

    /**
     * Removes any getTranslation/scaling3/skew or other non-rotation3
     * transformations from a matrix.
     *
     * @param mat 3x3 homography matrix to turn into strict rotation3 matrix.
     */
    public static void normalizeRotationMatrix( Matrix3 mat ) {
        float d;

        //Normalize length of X-axis.
        d = (float)Math.sqrt( mat.m00 * mat.m00 + mat.m10 * mat.m10 + mat.m20 * mat.m20 );
        if( d > FSQRT_ABS_ERR || d > -FSQRT_ABS_ERR ) {
            d = 1f / d;
            mat.m00 *= d;
            mat.m10 *= d;
            mat.m20 *= d;
        } else {
            mat.m00 = 1;
            mat.m10 = 0;
            mat.m20 = 0;
        }

        //Orthogonalize Y-axis to X-axis
        d = mat.m00 * mat.m01 + mat.m10 * mat.m11 + mat.m20 * mat.m21;
        mat.m01 -= d * mat.m00;
        mat.m11 -= d * mat.m10;
        mat.m21 -= d * mat.m20;

        //Normalize Y-axis.
        d = (float)Math.sqrt( mat.m01 * mat.m01 + mat.m11 * mat.m11 + mat.m21 * mat.m21 );
        if( d > FSQRT_ABS_ERR || d > -FSQRT_ABS_ERR ) {
            d = 1.0f / d;
            mat.m01 *= d;
            mat.m11 *= d;
            mat.m21 *= d;
        } else {
            Vector3 ortho = new Vector3();
            Vector.chooseOrtho( mat.m00, mat.m10, mat.m20, ortho );
            mat.m01 = ortho.x;
            mat.m11 = ortho.y;
            mat.m21 = ortho.z;
        }

        //Compute Z-axis
        mat.m02 = mat.m10 * mat.m21 - mat.m20 * mat.m11;
        mat.m12 = mat.m20 * mat.m01 - mat.m00 * mat.m21;
        mat.m22 = mat.m00 * mat.m11 - mat.m10 * mat.m01;
    }


    public static void basisVecsToRotation( Vector3 x, Vector3 y, Matrix3 out ) {
        out.m00 = x.x;
        out.m10 = x.y;
        out.m20 = x.z;
        out.m01 = y.x;
        out.m11 = y.y;
        out.m21 = y.z;
        out.m02 = x.y * y.z - y.y * x.z;
        out.m12 = x.z * y.x - y.z * x.x;
        out.m22 = x.x * y.y - y.x * x.y;
    }


    public static void basisVecsToRotation( Vector3 x, Vector3 y, Vector3 z, Matrix3 out ) {
        out.m00 = x.x;
        out.m10 = x.y;
        out.m20 = x.z;
        out.m01 = y.x;
        out.m11 = y.y;
        out.m21 = y.z;
        out.m02 = z.x;
        out.m12 = z.y;
        out.m22 = z.z;
    }

    /**
     * Computes spherical interpolation on two rotation matrices.
     *
     * @see bits.math3d.Quaternion#slerp
     *
     * @param rotA  Rotation matrix
     * @param rotB  Rotation matrix
     * @param t     Interpolation parameter. 0 = {@code rotA}, 1 = {@code rotB}, 0.5 = halfway between.
     * @param workA Workspace
     * @param workB Workspace
     * @param out   Holds interpolated rotation on output.
     */
    public static void slerp( Matrix3 rotA, Matrix3 rotB, float t, Vector4 workA, Vector4 workB, Matrix3 out ) {
        Quaternion.matToQuat( rotA, workA );
        Quaternion.matToQuat( rotB, workB );
        Quaternion.slerp( workA, workB, t, workA );
        Quaternion.quatToMat( workA, out );
    }


    public static String format( Matrix3 mat ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "[" );
        sb.append( String.format( Vector.FORMAT3, mat.m00, mat.m01, mat.m02 ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT3, mat.m10, mat.m11, mat.m12 ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT3, mat.m20, mat.m21, mat.m22 ) );
        sb.append( "]");
        return sb.toString();
    }


    public static boolean isNaN( Matrix3 mat ) {
        return Float.isNaN( mat.m00 ) ||
               Float.isNaN( mat.m10 ) ||
               Float.isNaN( mat.m20 ) ||
               Float.isNaN( mat.m01 ) ||
               Float.isNaN( mat.m11 ) ||
               Float.isNaN( mat.m21 ) ||
               Float.isNaN( mat.m02 ) ||
               Float.isNaN( mat.m12 ) ||
               Float.isNaN( mat.m22 );
    }



    //=== MAT4 Functions ========================================


    public static void put( Matrix4 mat, Matrix4 out ) {
        out.m00 = mat.m00;
        out.m01 = mat.m01;
        out.m02 = mat.m02;
        out.m03 = mat.m03;

        out.m10 = mat.m10;
        out.m11 = mat.m11;
        out.m12 = mat.m12;
        out.m13 = mat.m13;

        out.m20 = mat.m20;
        out.m21 = mat.m21;
        out.m22 = mat.m22;
        out.m23 = mat.m23;

        out.m30 = mat.m30;
        out.m31 = mat.m31;
        out.m32 = mat.m32;
        out.m33 = mat.m33;
    }


    public static void put( Matrix4 mat, Matrix3 out ) {
        out.m00 = mat.m00;
        out.m01 = mat.m01;
        out.m02 = mat.m02;
        out.m10 = mat.m10;
        out.m11 = mat.m11;
        out.m12 = mat.m12;
        out.m20 = mat.m20;
        out.m21 = mat.m21;
        out.m22 = mat.m22;
    }


    public static void put( Matrix4 mat, ByteBuffer bb ) {
        bb.putFloat( mat.m00 );
        bb.putFloat( mat.m10 );
        bb.putFloat( mat.m20 );
        bb.putFloat( mat.m30 );
        bb.putFloat( mat.m01 );
        bb.putFloat( mat.m11 );
        bb.putFloat( mat.m21 );
        bb.putFloat( mat.m31 );
        bb.putFloat( mat.m02 );
        bb.putFloat( mat.m12 );
        bb.putFloat( mat.m22 );
        bb.putFloat( mat.m32 );
        bb.putFloat( mat.m03 );
        bb.putFloat( mat.m13 );
        bb.putFloat( mat.m23 );
        bb.putFloat( mat.m33 );
    }


    public static void put( ByteBuffer bb, Matrix4 mat ) {
        mat.m00 = bb.getFloat();
        mat.m10 = bb.getFloat();
        mat.m20 = bb.getFloat();
        mat.m30 = bb.getFloat();
        mat.m01 = bb.getFloat();
        mat.m11 = bb.getFloat();
        mat.m21 = bb.getFloat();
        mat.m31 = bb.getFloat();
        mat.m02 = bb.getFloat();
        mat.m12 = bb.getFloat();
        mat.m22 = bb.getFloat();
        mat.m32 = bb.getFloat();
        mat.m03 = bb.getFloat();
        mat.m13 = bb.getFloat();
        mat.m23 = bb.getFloat();
        mat.m33 = bb.getFloat();
    }


    public static void put( Matrix4 mat, FloatBuffer fb ) {
        fb.put( mat.m00 );
        fb.put( mat.m10 );
        fb.put( mat.m20 );
        fb.put( mat.m30 );
        fb.put( mat.m01 );
        fb.put( mat.m11 );
        fb.put( mat.m21 );
        fb.put( mat.m31 );
        fb.put( mat.m02 );
        fb.put( mat.m12 );
        fb.put( mat.m22 );
        fb.put( mat.m32 );
        fb.put( mat.m03 );
        fb.put( mat.m13 );
        fb.put( mat.m23 );
        fb.put( mat.m33 );
    }


    public static void put( FloatBuffer fb, Matrix4 mat ) {
        mat.m00 = fb.get();
        mat.m10 = fb.get();
        mat.m20 = fb.get();
        mat.m30 = fb.get();
        mat.m01 = fb.get();
        mat.m11 = fb.get();
        mat.m21 = fb.get();
        mat.m31 = fb.get();
        mat.m02 = fb.get();
        mat.m12 = fb.get();
        mat.m22 = fb.get();
        mat.m32 = fb.get();
        mat.m03 = fb.get();
        mat.m13 = fb.get();
        mat.m23 = fb.get();
        mat.m33 = fb.get();
    }


    public static void put( Matrix4 mat, float[] arr ) {
        arr[ 0] = mat.m00;
        arr[ 1] = mat.m10;
        arr[ 2] = mat.m20;
        arr[ 3] = mat.m30;
        arr[ 4] = mat.m01;
        arr[ 5] = mat.m11;
        arr[ 6] = mat.m21;
        arr[ 7] = mat.m31;
        arr[ 8] = mat.m02;
        arr[ 9] = mat.m12;
        arr[10] = mat.m22;
        arr[11] = mat.m32;
        arr[12] = mat.m03;
        arr[13] = mat.m13;
        arr[14] = mat.m23;
        arr[15] = mat.m33;
    }


    public static void put( float[] arr, Matrix4 mat ) {
        mat.m00 = arr[ 0];
        mat.m10 = arr[ 1];
        mat.m20 = arr[ 2];
        mat.m30 = arr[ 3];
        mat.m01 = arr[ 4];
        mat.m11 = arr[ 5];
        mat.m21 = arr[ 6];
        mat.m31 = arr[ 7];
        mat.m02 = arr[ 8];
        mat.m12 = arr[ 9];
        mat.m22 = arr[10];
        mat.m32 = arr[11];
        mat.m03 = arr[12];
        mat.m13 = arr[13];
        mat.m23 = arr[14];
        mat.m33 = arr[15];
    }


    public static void put( float v, Matrix4 out ) {
        out.m00 = v;
        out.m10 = v;
        out.m20 = v;
        out.m30 = v;
        out.m01 = v;
        out.m11 = v;
        out.m21 = v;
        out.m31 = v;
        out.m02 = v;
        out.m12 = v;
        out.m22 = v;
        out.m32 = v;
        out.m03 = v;
        out.m13 = v;
        out.m23 = v;
        out.m33 = v;
    }

    /**
     * Multiplies two matrices.
     *
     * @param a   Size-4 matrix.
     * @param b   Size-4 matrix.
     * @param out Size-4 matrix. On return, holds <tt>a * b</tt>. May be same array as <tt>a</tt> or <tt>b</tt>.
     */
    public static void mult( Matrix4 a, Matrix4 b, Matrix4 out ) {
        // I tested many configurations for multiplication.
        // This one was the fastest as well as avoids array aliasing without branching.
        float a00 = a.m00;
        float a10 = a.m10;
        float a20 = a.m20;
        float a30 = a.m30;
        float a01 = a.m01;
        float a11 = a.m11;
        float a21 = a.m21;
        float a31 = a.m31;
        float a02 = a.m02;
        float a12 = a.m12;
        float a22 = a.m22;
        float a32 = a.m32;
        float a03 = a.m03;
        float a13 = a.m13;
        float a23 = a.m23;
        float a33 = a.m33;
        float b0 = b.m00;
        float b1 = b.m10;
        float b2 = b.m20;
        float b3 = b.m30;
        out.m00 = a00*b0 + a01*b1 + a02*b2 + a03*b3;
        out.m10 = a10*b0 + a11*b1 + a12*b2 + a13*b3;
        out.m20 = a20*b0 + a21*b1 + a22*b2 + a23*b3;
        out.m30 = a30*b0 + a31*b1 + a32*b2 + a33*b3;
        b0 = b.m01;
        b1 = b.m11;
        b2 = b.m21;
        b3 = b.m31;
        out.m01 = a00*b0 + a01*b1 + a02*b2 + a03*b3;
        out.m11 = a10*b0 + a11*b1 + a12*b2 + a13*b3;
        out.m21 = a20*b0 + a21*b1 + a22*b2 + a23*b3;
        out.m31 = a30*b0 + a31*b1 + a32*b2 + a33*b3;
        b0 = b.m02;
        b1 = b.m12;
        b2 = b.m22;
        b3 = b.m32;
        out.m02 = a00*b0 + a01*b1 + a02*b2 + a03*b3;
        out.m12 = a10*b0 + a11*b1 + a12*b2 + a13*b3;
        out.m22 = a20*b0 + a21*b1 + a22*b2 + a23*b3;
        out.m32 = a30*b0 + a31*b1 + a32*b2 + a33*b3;
        b0 = b.m03;
        b1 = b.m13;
        b2 = b.m23;
        b3 = b.m33;
        out.m03 = a00*b0 + a01*b1 + a02*b2 + a03*b3;
        out.m13 = a10*b0 + a11*b1 + a12*b2 + a13*b3;
        out.m23 = a20*b0 + a21*b1 + a22*b2 + a23*b3;
        out.m33 = a30*b0 + a31*b1 + a32*b2 + a33*b3;
    }

    /**
     * Multiplies two matrices.
     *
     * @param a   Size-4 matrix.
     * @param b   Size-3 matrix.
     * @param out Size-4 matrix. On return, holds <tt>a * b</tt>. May be same array as <tt>a</tt> or <tt>b</tt>.
     */
    public static void mult( Matrix4 a, Matrix3 b, Matrix4 out ) {
        float a00 = a.m00;
        float a10 = a.m10;
        float a20 = a.m20;
        float a30 = a.m30;
        float a01 = a.m01;
        float a11 = a.m11;
        float a21 = a.m21;
        float a31 = a.m31;
        float a02 = a.m02;
        float a12 = a.m12;
        float a22 = a.m22;
        float a32 = a.m32;
        float b0 = b.m00;
        float b1 = b.m10;
        float b2 = b.m20;
        //float b3 = b.m30;
        out.m00 = a00*b0 + a01*b1 + a02*b2;
        out.m10 = a10*b0 + a11*b1 + a12*b2;
        out.m20 = a20*b0 + a21*b1 + a22*b2;
        out.m30 = a30*b0 + a31*b1 + a32*b2;
        b0 = b.m01;
        b1 = b.m11;
        b2 = b.m21;
        out.m01 = a00*b0 + a01*b1 + a02*b2;
        out.m11 = a10*b0 + a11*b1 + a12*b2;
        out.m21 = a20*b0 + a21*b1 + a22*b2;
        out.m31 = a30*b0 + a31*b1 + a32*b2;
        b0 = b.m02;
        b1 = b.m12;
        b2 = b.m22;
        out.m02 = a00*b0 + a01*b1 + a02*b2;
        out.m12 = a10*b0 + a11*b1 + a12*b2;
        out.m22 = a20*b0 + a21*b1 + a22*b2;
        out.m32 = a30*b0 + a31*b1 + a32*b2;
        out.m03 = a.m03;
        out.m13 = a.m13;
        out.m23 = a.m23;
        out.m33 = a.m33;
    }

    /**
     * Multiplies two matrices.
     *
     * @param a   Size-4 matrix.
     * @param b   Size-4 matrix.
     * @param out Size-4 matrix. On return, holds <tt>a * b</tt>. May be same array as <tt>a</tt> or <tt>b</tt>.
     */
    public static void mult( Matrix3 a, Matrix4 b, Matrix4 out ) {
        float a00 = a.m00;
        float a10 = a.m10;
        float a20 = a.m20;
        float a01 = a.m01;
        float a11 = a.m11;
        float a21 = a.m21;
        float a02 = a.m02;
        float a12 = a.m12;
        float a22 = a.m22;
        float b0 = b.m00;
        float b1 = b.m10;
        float b2 = b.m20;
        out.m00 = a00*b0 + a01*b1 + a02*b2;
        out.m10 = a10*b0 + a11*b1 + a12*b2;
        out.m20 = a20*b0 + a21*b1 + a22*b2;
        out.m30 = b.m30;
        b0 = b.m01;
        b1 = b.m11;
        b2 = b.m21;
        out.m01 = a00*b0 + a01*b1 + a02*b2;
        out.m11 = a10*b0 + a11*b1 + a12*b2;
        out.m21 = a20*b0 + a21*b1 + a22*b2;
        out.m31 = b.m31;
        b0 = b.m02;
        b1 = b.m12;
        b2 = b.m22;
        out.m02 = a00*b0 + a01*b1 + a02*b2;
        out.m12 = a10*b0 + a11*b1 + a12*b2;
        out.m22 = a20*b0 + a21*b1 + a22*b2;
        out.m32 = b.m32;
        b0 = b.m03;
        b1 = b.m13;
        b2 = b.m23;
        out.m03 = a00*b0 + a01*b1 + a02*b2;
        out.m13 = a10*b0 + a11*b1 + a12*b2;
        out.m23 = a20*b0 + a21*b1 + a22*b2;
        out.m33 = b.m33;
    }


    public static void mult( Matrix4 a, Vector3 b, Vector3 out ) {
        float b0 = b.x;
        float b1 = b.y;
        float b2 = b.z;
        float x = a.m00*b0 + a.m01*b1 + a.m02*b2 + a.m03;
        float y = a.m10*b0 + a.m11*b1 + a.m12*b2 + a.m13;
        float z = a.m20*b0 + a.m21*b1 + a.m22*b2 + a.m23;
        float w = a.m30*b0 + a.m31*b1 + a.m32*b2 + a.m33;
        w = 1.0f / w;
        out.x = x * w;
        out.y = y * w;
        out.z = z * w;
    }


    public static void mult( Matrix4 a, Vector4 b, Vector4 out ) {
        float t0 = a.m00*b.x + a.m01*b.y + a.m02*b.z + a.m03*b.w;
        float t1 = a.m10*b.x + a.m11*b.y + a.m12*b.z + a.m13*b.w;
        float t2 = a.m20*b.x + a.m21*b.y + a.m22*b.z + a.m23*b.w;
        float t3 = a.m30*b.x + a.m31*b.y + a.m32*b.z + a.m33*b.w;
        out.x = t0;
        out.y = t1;
        out.z = t2;
        out.w = t3;
    }


    public static void multAdd( float sa, Matrix4 a, float sb, Matrix4 b, Matrix4 out ) {
        out.m00 = sa * a.m00 + sb * b.m00;
        out.m10 = sa * a.m10 + sb * b.m10;
        out.m20 = sa * a.m20 + sb * b.m20;
        out.m30 = sa * a.m30 + sb * b.m30;
        out.m01 = sa * a.m01 + sb * b.m01;
        out.m11 = sa * a.m11 + sb * b.m11;
        out.m21 = sa * a.m21 + sb * b.m21;
        out.m31 = sa * a.m31 + sb * b.m31;
        out.m02 = sa * a.m02 + sb * b.m02;
        out.m12 = sa * a.m12 + sb * b.m12;
        out.m22 = sa * a.m22 + sb * b.m22;
        out.m32 = sa * a.m32 + sb * b.m32;
        out.m03 = sa * a.m03 + sb * b.m03;
        out.m13 = sa * a.m13 + sb * b.m13;
        out.m23 = sa * a.m23 + sb * b.m23;
        out.m33 = sa * a.m33 + sb * b.m33;
    }

    /**
     * @param mat Input matrix
     * @return determinent of matrix
     */
    public static float det( Matrix4 mat ) {
        float s0 = ( mat.m00 * mat.m11 - mat.m10 * mat.m01 ) * ( mat.m22 * mat.m33 - mat.m32 * mat.m23 );
        float s1 = ( mat.m00 * mat.m12 - mat.m10 * mat.m02 ) * ( mat.m21 * mat.m33 - mat.m31 * mat.m23 );
        float s2 = ( mat.m00 * mat.m13 - mat.m10 * mat.m03 ) * ( mat.m21 * mat.m32 - mat.m31 * mat.m22 );
        float s3 = ( mat.m01 * mat.m12 - mat.m11 * mat.m02 ) * ( mat.m20 * mat.m33 - mat.m30 * mat.m23 );
        float s4 = ( mat.m01 * mat.m13 - mat.m11 * mat.m03 ) * ( mat.m20 * mat.m32 - mat.m30 * mat.m22 );
        float s5 = ( mat.m02 * mat.m13 - mat.m12 * mat.m03 ) * ( mat.m20 * mat.m31 - mat.m30 * mat.m21 );
        return s0 - s1 + s2 + s3 - s4 + s5;
    }

    /**
     * @param mat Input matrix
     * @param out Array to hold inverted matrix on return.
     * @return true if matrix determinant is not near zero and accurate invert was found.
     */
    public static boolean invert( Matrix4 mat, Matrix4 out ) {
        float s0 = mat.m00 * mat.m11 - mat.m10 * mat.m01;
        float s1 = mat.m00 * mat.m12 - mat.m10 * mat.m02;
        float s2 = mat.m00 * mat.m13 - mat.m10 * mat.m03;
        float s3 = mat.m01 * mat.m12 - mat.m11 * mat.m02;
        float s4 = mat.m01 * mat.m13 - mat.m11 * mat.m03;
        float s5 = mat.m02 * mat.m13 - mat.m12 * mat.m03;

        float c5 = mat.m22 * mat.m33 - mat.m32 * mat.m23;
        float c4 = mat.m21 * mat.m33 - mat.m31 * mat.m23;
        float c3 = mat.m21 * mat.m32 - mat.m31 * mat.m22;
        float c2 = mat.m20 * mat.m33 - mat.m30 * mat.m23;
        float c1 = mat.m20 * mat.m32 - mat.m30 * mat.m22;
        float c0 = mat.m20 * mat.m31 - mat.m30 * mat.m21;

        // Compute determinant
        float invdet = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
        // Check if invertible.
        boolean ret  = invdet > FSQRT_ABS_ERR || -invdet > FSQRT_ABS_ERR;
        // Invert determinant
        invdet = 1.0f / invdet;

        float t00 = ( mat.m11 * c5 - mat.m12 * c4 + mat.m13 * c3) * invdet;
        float t01 = (-mat.m10 * c5 + mat.m12 * c2 - mat.m13 * c1) * invdet;
        float t02 = ( mat.m10 * c4 - mat.m11 * c2 + mat.m13 * c0) * invdet;
        float t03 = (-mat.m10 * c3 + mat.m11 * c1 - mat.m12 * c0) * invdet;
        float t04 = (-mat.m01 * c5 + mat.m02 * c4 - mat.m03 * c3) * invdet;
        float t05 = ( mat.m00 * c5 - mat.m02 * c2 + mat.m03 * c1) * invdet;
        float t06 = (-mat.m00 * c4 + mat.m01 * c2 - mat.m03 * c0) * invdet;
        float t07 = ( mat.m00 * c3 - mat.m01 * c1 + mat.m02 * c0) * invdet;
        float t08 = ( mat.m31 * s5 - mat.m32 * s4 + mat.m33 * s3) * invdet;
        float t09 = (-mat.m30 * s5 + mat.m32 * s2 - mat.m33 * s1) * invdet;
        float t10 = ( mat.m30 * s4 - mat.m31 * s2 + mat.m33 * s0) * invdet;
        float t11 = (-mat.m30 * s3 + mat.m31 * s1 - mat.m32 * s0) * invdet;
        float t12 = (-mat.m21 * s5 + mat.m22 * s4 - mat.m23 * s3) * invdet;
        float t13 = ( mat.m20 * s5 - mat.m22 * s2 + mat.m23 * s1) * invdet;
        float t14 = (-mat.m20 * s4 + mat.m21 * s2 - mat.m23 * s0) * invdet;
        float t15 = ( mat.m20 * s3 - mat.m21 * s1 + mat.m22 * s0) * invdet;

        out.m00 = t00;
        out.m10 = t01;
        out.m20 = t02;
        out.m30 = t03;
        out.m01 = t04;
        out.m11 = t05;
        out.m21 = t06;
        out.m31 = t07;
        out.m02 = t08;
        out.m12 = t09;
        out.m22 = t10;
        out.m32 = t11;
        out.m03 = t12;
        out.m13 = t13;
        out.m23 = t14;
        out.m33 = t15;

        return ret;
    }


    public static void transpose( Matrix4 mat, Matrix4 out ) {
        // About 15% faster without local copies.
        float a00 = mat.m00;
        float a01 = mat.m10;
        float a02 = mat.m20;
        float a03 = mat.m30;
        float a04 = mat.m01;
        float a05 = mat.m11;
        float a06 = mat.m21;
        float a07 = mat.m31;
        float a08 = mat.m02;
        float a09 = mat.m12;
        float a10 = mat.m22;
        float a11 = mat.m32;
        float a12 = mat.m03;
        float a13 = mat.m13;
        float a14 = mat.m23;
        float a15 = mat.m33;

        out.m00 = a00;
        out.m10 = a04;
        out.m20 = a08;
        out.m30 = a12;

        out.m01 = a01;
        out.m11 = a05;
        out.m21 = a09;
        out.m31 = a13;

        out.m02 = a02;
        out.m12 = a06;
        out.m22 = a10;
        out.m32 = a14;

        out.m03 = a03;
        out.m13 = a07;
        out.m23 = a11;
        out.m33 = a15;
    }


    public static void identity( Matrix4 out ) {
        out.m00 = 1;  out.m11 = 1;  out.m01 = 0;  out.m03 = 0;
        out.m10 = 0;  out.m21 = 0;  out.m12 = 0;  out.m13 = 0;
        out.m20 = 0;  out.m31 = 0;  out.m22 = 1;  out.m23 = 0;
        out.m30 = 0;  out.m02 = 0;  out.m32 = 0;  out.m33 = 1;
    }

    /**
     * Computes a getRotate4 matrix.
     *
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void getRotation( float radians, float x, float y, float z, Matrix4 out ) {
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);
        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        out.m00 = x*x*(1-c)+c;
        out.m10 = x*y*(1-c)+z*s;
        out.m20 = x*z*(1-c)-y*s;
        out.m30 = 0;

        out.m01 = x*y*(1-c)-z*s;
        out.m11 = y*y*(1-c)+c;
        out.m21 = y*z*(1-c)+x*s;
        out.m31 = 0;

        out.m02 = x*z*(1-c)+y*s;
        out.m12 = y*z*(1-c)-x*s;
        out.m22 = z*z*(1-c)+c;
        out.m32 = 0.0f;

        out.m03 = 0;
        out.m13 = 0;
        out.m23 = 0;
        out.m33 = 1;
    }

    /**
     * Multiplies an arbitrary matrix with a getRotate4 matrix.
     *
     * @param mat     Input matrix.
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotate( Matrix4 mat, float radians, float x, float y, float z, Matrix4 out ) {
        final float c = (float)Math.cos( radians );
        final float s = (float)Math.sin( radians );
        final float invSum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= invSum;
        y *= invSum;
        z *= invSum;

        final float a00 = x*x*(1-c)+c;
        final float a01 = x*y*(1-c)+z*s;
        final float a02 = x*z*(1-c)-y*s;
        final float a04 = x*y*(1-c)-z*s;
        final float a05 = y*y*(1-c)+c;
        final float a06 = y*z*(1-c)+x*s;
        final float a08 = x*z*(1-c)+y*s;
        final float a09 = y*z*(1-c)-x*s;
        final float a10 = z*z*(1-c)+c;

        float b0 = mat.m00;
        float b1 = mat.m01;
        float b2 = mat.m02;
        float b3 = mat.m03;
        out.m00 = a00*b0 + a01*b1 + a02*b2;
        out.m01 = a04*b0 + a05*b1 + a06*b2;
        out.m02 = a08*b0 + a09*b1 + a10*b2;
        out.m03 = b3;
        b0 = mat.m10;
        b1 = mat.m11;
        b2 = mat.m12;
        b3 = mat.m13;
        out.m10 = a00*b0 + a01*b1 + a02*b2;
        out.m11 = a04*b0 + a05*b1 + a06*b2;
        out.m12 = a08*b0 + a09*b1 + a10*b2;
        out.m13 = b3;
        b0 = mat.m20;
        b1 = mat.m21;
        b2 = mat.m22;
        b3 = mat.m23;
        out.m20 = a00*b0 + a01*b1 + a02*b2;
        out.m21 = a04*b0 + a05*b1 + a06*b2;
        out.m22 = a08*b0 + a09*b1 + a10*b2;
        out.m23 = b3;
        b0 = mat.m30;
        b1 = mat.m31;
        b2 = mat.m32;
        b3 = mat.m33;
        out.m30 = a00*b0 + a01*b1 + a02*b2;
        out.m31 = a04*b0 + a05*b1 + a06*b2;
        out.m32 = a08*b0 + a09*b1 + a10*b2;
        out.m33 = b3;
    }

    /**
     * Multiplies a getRotate4 matrix with an arbitrary matrix.
     *
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param mat     Input matrix.
     * @param out     Length-16 array to hold output on return.
     */
    public static void preRotate( float radians, float x, float y, float z, Matrix4 mat, Matrix4 out ) {
        float c = (float)Math.cos( radians );
        float s = (float)Math.sin( radians );
        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        float a00 = x*x*(1-c)+c;
        float a01 = x*y*(1-c)+z*s;
        float a02 = x*z*(1-c)-y*s;
        float a04 = x*y*(1-c)-z*s;
        float a05 = y*y*(1-c)+c;
        float a06 = y*z*(1-c)+x*s;
        float a08 = x*z*(1-c)+y*s;
        float a09 = y*z*(1-c)-x*s;
        float a10 = z*z*(1-c)+c;
        float b0 = mat.m00;
        float b1 = mat.m10;
        float b2 = mat.m20;
        float b3 = mat.m30;
        out.m00 = a00*b0 + a04*b1 + a08*b2;
        out.m10 = a01*b0 + a05*b1 + a09*b2;
        out.m20 = a02*b0 + a06*b1 + a10*b2;
        out.m30 = b3;
        b0 = mat.m01;
        b1 = mat.m11;
        b2 = mat.m21;
        b3 = mat.m31;
        out.m01 = a00*b0 + a04*b1 + a08*b2;
        out.m11 = a01*b0 + a05*b1 + a09*b2;
        out.m21 = a02*b0 + a06*b1 + a10*b2;
        out.m31 = b3;
        b0 = mat.m02;
        b1 = mat.m12;
        b2 = mat.m22;
        b3 = mat.m32;
        out.m02 = a00*b0 + a04*b1 + a08*b2;
        out.m12 = a01*b0 + a05*b1 + a09*b2;
        out.m22 = a02*b0 + a06*b1 + a10*b2;
        out.m32 = b3;
        b0 = mat.m03;
        b1 = mat.m13;
        b2 = mat.m23;
        b3 = mat.m33;
        out.m03 = a00*b0 + a04*b1 + a08*b2;
        out.m13 = a01*b0 + a05*b1 + a09*b2;
        out.m23 = a02*b0 + a06*b1 + a10*b2;
        out.m33 = b3;
    }


    public static void getScale( float sx, float sy, float sz, float sw, Matrix4 out ) {
        out.m00 = sx;
        out.m10 = 0.0f;
        out.m20 = 0.0f;
        out.m30 = 0.0f;
        out.m01 = 0.0f;
        out.m11 = sy;
        out.m21 = 0.0f;
        out.m31 = 0.0f;
        out.m02 = 0.0f;
        out.m12 = 0.0f;
        out.m22 = sz;
        out.m32 = 0.0f;
        out.m03 = 0.0f;
        out.m13 = 0.0f;
        out.m23 = 0.0f;
        out.m33 = sw;
    }


    public static void scale( Matrix4 mat, float sx, float sy, float sz, float sw, Matrix4 out ) {
        out.m00 = sx * mat.m00;
        out.m10 = sx * mat.m10;
        out.m20 = sx * mat.m20;
        out.m30 = sx * mat.m30;
        out.m01 = sy * mat.m01;
        out.m11 = sy * mat.m11;
        out.m21 = sy * mat.m21;
        out.m31 = sy * mat.m31;
        out.m02 = sz * mat.m02;
        out.m12 = sz * mat.m12;
        out.m22 = sz * mat.m22;
        out.m32 = sz * mat.m32;
        out.m03 = sw * mat.m03;
        out.m13 = sw * mat.m13;
        out.m23 = sw * mat.m23;
        out.m33 = sw * mat.m33;
    }


    public static void preScale( float sx, float sy, float sz, float sw, Matrix4 mat, Matrix4 out ) {
        out.m00 = sx * mat.m00;
        out.m10 = sy * mat.m10;
        out.m20 = sz * mat.m20;
        out.m30 = sw * mat.m30;
        out.m01 = sx * mat.m01;
        out.m11 = sy * mat.m11;
        out.m21 = sz * mat.m21;
        out.m31 = sw * mat.m31;
        out.m02 = sx * mat.m02;
        out.m12 = sy * mat.m12;
        out.m22 = sz * mat.m22;
        out.m32 = sw * mat.m32;
        out.m03 = sx * mat.m03;
        out.m13 = sy * mat.m13;
        out.m23 = sz * mat.m23;
        out.m33 = sw * mat.m33;
    }


    public static void getTranslation( float tx, float ty, float tz, Matrix4 out ) {
        out.m00 = 1;
        out.m10 = 0;
        out.m20 = 0;
        out.m30 = 0;
        out.m01 = 0;
        out.m11 = 1;
        out.m21 = 0;
        out.m31 = 0;
        out.m02 = 0;
        out.m12 = 0;
        out.m22 = 1;
        out.m32 = 0;
        out.m03 = tx;
        out.m13 = ty;
        out.m23 = tz;
        out.m33 = 1;
    }


    public static void translate( Matrix4 mat, float tx, float ty, float tz, Matrix4 out ) {
        float b0 = mat.m00;
        float b1 = mat.m01;
        float b2 = mat.m02;
        float b3 = mat.m03;
        out.m00 = b0;
        out.m01 = b1;
        out.m02 = b2;
        out.m03 = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat.m10;
        b1 = mat.m11;
        b2 = mat.m12;
        b3 = mat.m13;
        out.m10 = b0;
        out.m11 = b1;
        out.m12 = b2;
        out.m13 = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat.m20;
        b1 = mat.m21;
        b2 = mat.m22;
        b3 = mat.m23;
        out.m20 = b0;
        out.m21 = b1;
        out.m22 = b2;
        out.m23 = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat.m30;
        b1 = mat.m31;
        b2 = mat.m32;
        b3 = mat.m33;
        out.m30 = b0;
        out.m31 = b1;
        out.m32 = b2;
        out.m33 = tx * b0 + ty * b1 + tz * b2 + b3;
    }


    public static void preTranslate( float tx, float ty, float tz, Matrix4 mat, Matrix4 out ) {
        float b0 = mat.m00;
        float b1 = mat.m10;
        float b2 = mat.m20;
        float b3 = mat.m30;
        out.m00 = b0 + tx * b3;
        out.m10 = b1 + ty * b3;
        out.m20 = b2 + tz * b3;
        out.m30 = b3;
        b0 = mat.m01;
        b1 = mat.m11;
        b2 = mat.m21;
        b3 = mat.m31;
        out.m01 = b0 + tx * b3;
        out.m11 = b1 + ty * b3;
        out.m21 = b2 + tz * b3;
        out.m31 = b3;
        b0 = mat.m02;
        b1 = mat.m12;
        b2 = mat.m22;
        b3 = mat.m32;
        out.m02 = b0 + tx * b3;
        out.m12 = b1 + ty * b3;
        out.m22 = b2 + tz * b3;
        out.m32 = b3;
        b0 = mat.m03;
        b1 = mat.m13;
        b2 = mat.m23;
        b3 = mat.m33;
        out.m03 = b0 + tx * b3;
        out.m13 = b1 + ty * b3;
        out.m23 = b2 + tz * b3;
        out.m33 = b3;
    }


    public static void multFrustum( Matrix4 mat, float left, float right, float bottom, float top, float near, float far, Matrix4 out ) {
        float a, b, c;

        a = 2.0f * near / (right - left);
        out.m00 = a * mat.m00;
        out.m10 = a * mat.m10;
        out.m20 = a * mat.m20;
        out.m30 = a * mat.m30;

        a = 2 * near / (top - bottom);
        out.m01 = a * mat.m01;
        out.m11 = a * mat.m11;
        out.m21 = a * mat.m21;
        out.m31 = a * mat.m31;

        a = (right + left) / (right - left);
        b = (top + bottom) / (top - bottom);
        c = -(far + near) / (far - near);
        out.m02 = a * mat.m00 + b * mat.m01 + c * mat.m02 - mat.m03;
        out.m12 = a * mat.m10 + b * mat.m11 + c * mat.m12 - mat.m13;
        out.m22 = a * mat.m20 + b * mat.m21 + c * mat.m22 - mat.m23;
        out.m32 = a * mat.m30 + b * mat.m31 + c * mat.m32 - mat.m33;

        a = -(2 * far * near) / (far - near);
        out.m03 = a * mat.m03;
        out.m13 = a * mat.m13;
        out.m23 = a * mat.m23;
        out.m33 = a * mat.m33;
    }


    public static void getFrustum( float left, float right, float bottom, float top, float near, float far, Matrix4 out ) {
        out.m00 = 2.0f * near / (right - left);
        out.m10 = 0;
        out.m20 = 0;
        out.m30 = 0;

        out.m01 = 0;
        out.m11 = 2 * near / (top - bottom);
        out.m21 = 0;
        out.m31 = 0;

        out.m02 = (right + left) / (right - left);
        out.m12 = (top + bottom) / (top - bottom);
        out.m22 = -(far + near) / (far - near);
        out.m32 = -1;

        out.m03 = 0;
        out.m13 = 0;
        out.m23 = -(2 * far * near) / (far - near);
        out.m33 = 0;
    }


    public static void multOrtho( Matrix4 mat, float left, float right, float bottom, float top, float near, float far, Matrix4 out ) {
        float a, b, c;

        a = 2f / ( right - left );
        out.m00 = a * mat.m00;
        out.m10 = a * mat.m10;
        out.m20 = a * mat.m20;
        out.m30 = a * mat.m30;

        a = 2f / ( top - bottom );
        out.m01 = a * mat.m01;
        out.m11 = a * mat.m11;
        out.m21 = a * mat.m21;
        out.m31 = a * mat.m31;

        a = -2.0f / (far - near);
        out.m02 = a * mat.m02;
        out.m12 = a * mat.m12;
        out.m22 = a * mat.m22;
        out.m32 = a * mat.m32;

        a = -(right + left) / (right - left);
        b = -(top + bottom) / (top - bottom);
        c = -(far + near) / (far - near);

        out.m03 = a * mat.m00 + b * mat.m01 + c * mat.m02 + mat.m03;
        out.m13 = a * mat.m10 + b * mat.m11 + c * mat.m12 + mat.m13;
        out.m23 = a * mat.m20 + b * mat.m21 + c * mat.m22 + mat.m23;
        out.m33 = a * mat.m30 + b * mat.m31 + c * mat.m32 + mat.m33;
    }


    public static void getOrtho( float left, float right, float bottom, float top, float near, float far, Matrix4 out ) {
        out.m00 = 2.0f / (right - left);
        out.m10 = 0;
        out.m20 = 0;
        out.m30 = 0;

        out.m01 = 0;
        out.m11 = 2.0f / (top - bottom);
        out.m21 = 0;
        out.m31 = 0;

        out.m02 = 0;
        out.m12 = 0;
        out.m22 = -2.0f / (far - near);
        out.m32 = 0;

        out.m03 = -(right + left) / (right - left);
        out.m13 = -(top + bottom) / (top - bottom);
        out.m23 = -(far + near) / (far - near);
        out.m33 = 1;
    }


    public static void getViewport( float x, float y, float w, float h, Matrix4 out ) {
        out.m00 = 0.5f * w;
        out.m10 = 0;
        out.m20 = 0;
        out.m30 = 0;

        out.m01 = 0;
        out.m11 = 0.5f * h;
        out.m21 = 0;
        out.m31 = 0;

        out.m02 = 0;
        out.m12 = 0;
        out.m22 = 1;
        out.m32 = 0;

        out.m03 = 0.5f * w + x;
        out.m13 = 0.5f * h + y;
        out.m23 = 0;
        out.m33 = 1;
    }


    public static void getViewportDepth( float x, float y, float w, float h, float near, float far, Matrix4 out ) {
        out.m00 = 0.5f * w;
        out.m10 = 0;
        out.m20 = 0;
        out.m30 = 0;
        out.m01 = 0;

        out.m11 = 0.5f * h;
        out.m21 = 0;
        out.m31 = 0;

        out.m02 = 0;
        out.m12 = 0;
        out.m22 = 0.5f * ( far - near );
        out.m32 = 0;

        out.m03 = 0.5f * w + x;
        out.m13 = 0.5f * h + y;
        out.m23 = 0.5f * ( far + near );
        out.m33 = 1;
    }


    public static void lookAt( Matrix4 eyeVec, Matrix4 centerVec, Matrix4 upVec, Matrix4 outMat ) {
        float fx  = centerVec.m00 - eyeVec.m00;
        float fy  = centerVec.m10 - eyeVec.m10;
        float fz  = centerVec.m20 - eyeVec.m20;
        float len = (float)( 1.0 / Math.sqrt( fx * fx + fy * fy + fz * fz ) );
        fx *= len;
        fy *= len;
        fz *= len;

        float ux = upVec.m00;
        float uy = upVec.m10;
        float uz = upVec.m20;
        len = (float)( 1.0 / Math.sqrt( ux * ux + uy * uy + uz * uz ) );
        ux /= len;
        uy /= len;
        uz /= len;

        float sx = fy * uz - fz * uy;
        float sy = fz * ux - fx * uz;
        float sz = fx * uy - fy * ux;

        ux = sy * fz - sz * fy;
        uy = sz * fx - sx * fz;
        uz = sx * fy - sy * fx;

        outMat.m00 = sx;
        outMat.m10 = ux;
        outMat.m20 = -fx;
        outMat.m30 = 0;
        outMat.m01 = sy;
        outMat.m11 = uy;
        outMat.m21 = -fy;
        outMat.m31 = 0;
        outMat.m02 = sz;
        outMat.m12 = uz;
        outMat.m22 = -fz;
        outMat.m32 = 0;
        outMat.m03 = -(sx * eyeVec.m00 + sy * eyeVec.m10 + sz * eyeVec.m20);
        outMat.m13 = -(ux * eyeVec.m00 + uy * eyeVec.m10 + uz * eyeVec.m20);
        outMat.m23 = fx * eyeVec.m00 + fy * eyeVec.m10 + fz * eyeVec.m20;
        outMat.m33 = 1;
    }


    public static void viewport( float x, float y, float w, float h, Matrix4 out ) {
        out.m00 = w * 0.5f;
        out.m10 = 0;
        out.m20 = 0;
        out.m30 = 0;

        out.m01 = 0;
        out.m11 = h * 0.5f;
        out.m21 = 0;
        out.m31 = 0;

        out.m02 = 0;
        out.m12 = 0;
        out.m22 = 1;
        out.m32 = 0;

        out.m03 = w * 0.5f + x;
        out.m13 = h * 0.5f + y;
        out.m23 = 0;
        out.m33 = 1;
    }

    /**
     * Removes any getTranslate4/getScale4/skew or other non-getRotate4
     * transformations from a matrix.
     *
     * @param mat 4x4 homography matrix to turn into strict getRotate4 matrix.
     */
    public static void normalizeRotationMatrix( Matrix4 mat ) {
        float d;

        //Kill getTranslate4, scalings.
        mat.m30 = 0;
        mat.m31 = 0;
        mat.m32 = 0;
        mat.m03 = 0;
        mat.m13 = 0;
        mat.m23 = 0;
        mat.m33 = 1;

        //Normalize length of X-axis.
        d = (float)Math.sqrt( mat.m00 * mat.m00 + mat.m10 * mat.m10 + mat.m20 * mat.m20 );
        if( d > FSQRT_ABS_ERR || -d > FSQRT_ABS_ERR ) {
            d = 1f / d;
            mat.m00 *= d;
            mat.m10 *= d;
            mat.m20 *= d;
        }else{
            mat.m00 = 1;
            mat.m10 = 0;
            mat.m20 = 0;
        }

        //Orthogonalize Y-axis to X-axis
        d = mat.m00 * mat.m01 + mat.m10 * mat.m11 + mat.m20 * mat.m21;
        mat.m01 -= d * mat.m00;
        mat.m11 -= d * mat.m10;
        mat.m21 -= d * mat.m20;

        //Normalize Y-axis.
        d = (float)Math.sqrt( mat.m01 * mat.m01 + mat.m11 * mat.m11 + mat.m21 * mat.m21 );
        if( d > FSQRT_ABS_ERR || -d > FSQRT_ABS_ERR ) {
            d = 1.0f / d;
            mat.m01 *= d;
            mat.m11 *= d;
            mat.m21 *= d;
        }else{
            Vector3 orth = new Vector3();
            Vector.chooseOrtho( mat.m00, mat.m10, mat.m20, orth );
            mat.m01 = orth.x;
            mat.m11 = orth.y;
            mat.m21 = orth.z;
        }

        //Compute Z-axis
        mat.m02 = mat.m10*mat.m21 - mat.m20*mat.m11;
        mat.m12 = mat.m20*mat.m01 - mat.m00*mat.m21;
        mat.m22 = mat.m00*mat.m11 - mat.m10*mat.m01;
    }


    public static void axesToTransform( Vector3 x, Vector3 y, Matrix4 out ) {
        Vector3 z = new Vector3();
        Vector.cross( x, y, z );
        axesToTransform( x, y, z, out );
    }


    public static void axesToTransform( Vector3 x, Vector3 y, Vector3 z, Matrix4 out ) {
        out.m00 = x.x;
        out.m10 = x.y;
        out.m20 = x.z;
        out.m30 = 0;
        out.m01 = y.x;
        out.m11 = y.y;
        out.m21 = y.z;
        out.m31 = 0;
        out.m02 = z.x;
        out.m12 = z.y;
        out.m22 = z.z;
        out.m32 = 0;
        out.m03 = 0;
        out.m13 = 0;
        out.m23 = 0;
        out.m33 = 1;
    }

    /**
     * This method will adjust a getRotate4 matrix so that one of the basis vectors will
     * be parallel to an axis.
     *
     * @param mat   Input getRotate4 matrix.
     * @param basis Basis vector to align to axis. {@code 0 == x-basis, 1 == y-basis, 2 == z-basis}.
     * @param out   Holds modified getRotate4 matrix on output.
     */
    public static void alignBasisVectorToAxis( Matrix4 mat, int basis, Matrix4 out ) {
        Vector3 x;
        Vector3 y;
        Vector3 z;

        switch( basis ) {
        case 0:
        {
            // Clamp specified vector to nearest axis.
            x = new Vector3();
            Vector.nearestAxis( mat.m00, mat.m10, mat.m20, x );
            y = new Vector3( mat.m01, mat.m11, mat.m21 );
            z = new Vector3( mat.m02, mat.m12, mat.m23 );

            Vector.cross( x, y, z );
            Vector.normalize( z );
            Vector.cross( z, x, y );
            break;
        }
        case 1:
        {
            // Clamp specified vector to nearest axis.
            y = new Vector3();
            Vector.nearestAxis( mat.m01, mat.m11, mat.m21, y );
            z = new Vector3( mat.m02, mat.m12, mat.m22 );
            x = new Vector3( mat.m00, mat.m10, mat.m20 );

            Vector.cross( y, x, z );
            Vector.normalize( x );
            Vector.cross( x, y, z );
            break;
        }
        default:
        {
            // Clamp specified vector to nearest axis.
            z = new Vector3();
            Vector.nearestAxis( mat.m02, mat.m12, mat.m22, z );
            x = new Vector3( mat.m00, mat.m10, mat.m20 );
            y = new Vector3( mat.m01, mat.m11, mat.m21 );

            Vector.cross( z, x, y );
            Vector.normalize( y );
            Vector.cross( y, z, x );
            break;
        }}

        axesToTransform( x, y, z, out );
    }

    /**
     * Makes the smallest angular adjustment possible to a vector such that
     * the resulting vector will lie inside the closed surface of a cone.
     * In other words, after calling {@code clampToCone3},
     * {@code ang3(vec, coneAxis) <= coneRads}.
     *
     * @param vec       Vector to clamp
     * @param coneAxis  Axis of cone
     * @param coneRads  Angle between axis and surface of cone.
     * @param outVec    Vector with smallest possible angular distance from {@code vec} that lies inside cone.  May be {@code null}.
     * @param outMat    Rotation matrix used to rotate4 vector onto cone.  May be {@code null}
     * @return true if any adjustment made, false if output is identical to input
     */
    public static boolean clampToCone( Vector3 vec, Vector3 coneAxis, float coneRads, Vector3 outVec, Matrix4 outMat ) {
        float cosAng = Vector.cosAng( vec, coneAxis );
        float ang    = (float)Math.acos( cosAng );

        if( ang != ang ) {
            ang = cosAng > 0.0f ? 0.0f : (float)Math.PI;
        }
        if( ang <= coneRads ) {
            if( outVec != null ) {
                outVec.x = vec.x;
                outVec.y = vec.y;
                outVec.z = vec.z;
            }
            if( outMat != null ) {
                Matrix.identity( outMat );
            }
            return false;
        }

        Vector3 rotAxis = new Vector3();
        Matrix4 rot     = outMat == null ? new Matrix4() : outMat;

        if( ang < Math.PI * (1.0 + FREL_ERR )) {
            Vector.cross( vec, coneAxis, rotAxis );
        }else{
            Vector.chooseOrtho( coneAxis.x, coneAxis.y, coneAxis.z, rotAxis );
        }

        Matrix.getRotation( ang - coneRads * (1.0f - FREL_ERR), rotAxis.x, rotAxis.y, rotAxis.z, rot );
        if( outVec != null ) {
            Matrix.mult( rot, vec, outVec );
        }

        return true;
    }


    public static String format( Matrix4 mat ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "[" );
        sb.append( String.format( Vector.FORMAT4, mat.m00, mat.m01, mat.m02, mat.m03 ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT4, mat.m10, mat.m11, mat.m12, mat.m13 ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT4, mat.m20, mat.m21, mat.m22, mat.m23 ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT4, mat.m30, mat.m31, mat.m32, mat.m33 ) );
        sb.append( "]");
        return sb.toString();
    }


    public static boolean isNaN( Matrix4 mat ) {
        return Float.isNaN( mat.m00 ) ||
               Float.isNaN( mat.m10 ) ||
               Float.isNaN( mat.m20 ) ||
               Float.isNaN( mat.m30 ) ||
               Float.isNaN( mat.m01 ) ||
               Float.isNaN( mat.m11 ) ||
               Float.isNaN( mat.m21 ) ||
               Float.isNaN( mat.m31 ) ||
               Float.isNaN( mat.m02 ) ||
               Float.isNaN( mat.m12 ) ||
               Float.isNaN( mat.m22 ) ||
               Float.isNaN( mat.m32 ) ||
               Float.isNaN( mat.m03 ) ||
               Float.isNaN( mat.m13 ) ||
               Float.isNaN( mat.m23 ) ||
               Float.isNaN( mat.m33 );
    }



    //=== DOUBLE2[16] Arrays

    /**
     * Multiplies two matrices.
     *
     * @param a   Length-16 array. Holds matrix in column-major ordering.
     * @param b   Length-16 array. Holds matrix in column-major ordering.
     * @param out Length-16 array. On return, holds <tt>a * b</tt>. May be same array as <tt>a</tt> or <tt>b</tt>.
     */
    public static void mult4( double[] a, double[] b, double[] out ) {
        // I tested many configurations for multiplication.
        // This one was the fastest as well as avoids array aliasing without branching.
        double a00 = a[ 0];
        double a01 = a[ 1];
        double a02 = a[ 2];
        double a03 = a[ 3];
        double a04 = a[ 4];
        double a05 = a[ 5];
        double a06 = a[ 6];
        double a07 = a[ 7];
        double a08 = a[ 8];
        double a09 = a[ 9];
        double a10 = a[10];
        double a11 = a[11];
        double a12 = a[12];
        double a13 = a[13];
        double a14 = a[14];
        double a15 = a[15];
        double b0 = b[0];
        double b1 = b[1];
        double b2 = b[2];
        double b3 = b[3];
        out[ 0] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[ 1] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[ 2] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[ 3] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
        b0 = b[4];
        b1 = b[5];
        b2 = b[6];
        b3 = b[7];
        out[ 4] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[ 5] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[ 6] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[ 7] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
        b0 = b[8];
        b1 = b[9];
        b2 = b[10];
        b3 = b[11];
        out[ 8] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[ 9] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[10] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[11] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
        b0 = b[12];
        b1 = b[13];
        b2 = b[14];
        b3 = b[15];
        out[12] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[13] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[14] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[15] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
    }


    public static void multAdd4( float sa, float[] a, float sb, float[] b, float[] out ) {
        out[ 0] = sa * a[ 0] + sb * b[ 0];
        out[ 1] = sa * a[ 1] + sb * b[ 1];
        out[ 2] = sa * a[ 2] + sb * b[ 2];
        out[ 3] = sa * a[ 3] + sb * b[ 3];
        out[ 4] = sa * a[ 4] + sb * b[ 4];
        out[ 5] = sa * a[ 5] + sb * b[ 5];
        out[ 6] = sa * a[ 6] + sb * b[ 6];
        out[ 7] = sa * a[ 7] + sb * b[ 7];
        out[ 8] = sa * a[ 8] + sb * b[ 8];
        out[ 9] = sa * a[ 9] + sb * b[ 9];
        out[10] = sa * a[10] + sb * b[10];
        out[11] = sa * a[11] + sb * b[11];
        out[12] = sa * a[12] + sb * b[12];
        out[13] = sa * a[13] + sb * b[13];
        out[14] = sa * a[14] + sb * b[14];
        out[15] = sa * a[15] + sb * b[15];
    }


    public static void mult4Vec3( double[] a, double[] b, double[] out ) {
        double b0 = b[0];
        double b1 = b[1];
        double b2 = b[2];
        double x = a[ 0]*b0 + a[ 4]*b1 + a[ 8]*b2 + a[12];
        double y = a[ 1]*b0 + a[ 5]*b1 + a[ 9]*b2 + a[13];
        double z = a[ 2]*b0 + a[ 6]*b1 + a[10]*b2 + a[14];
        double w = a[ 3]*b0 + a[ 7]*b1 + a[11]*b2 + a[15];
        w = 1.0 / w;
        out[0] = x * w;
        out[1] = y * w;
        out[2] = z * w;
    }


    public static void mult4Vec3( double[] a, int offA, double[] b, int offB, double[] out, int offOut ) {
        double x = a[ 0+offA]*b[0+offB] + a[ 4+offA]*b[1+offB] + a[ 8+offA]*b[2+offB] + a[12+offA];
        double y = a[ 1+offA]*b[0+offB] + a[ 5+offA]*b[1+offB] + a[ 9+offA]*b[2+offB] + a[13+offA];
        double z = a[ 2+offA]*b[0+offB] + a[ 6+offA]*b[1+offB] + a[10+offA]*b[2+offB] + a[14+offA];
        double w = a[ 3+offA]*b[0+offB] + a[ 7+offA]*b[1+offB] + a[11+offA]*b[2+offB] + a[15+offA];
        w = 1.0 / w;
        out[0+offOut] = x * w;
        out[1+offOut] = y * w;
        out[2+offOut] = z * w;
    }


    public static void mult4Vec4( double[] a, double[] b, double[] out ) {
        double t0 = a[ 0]*b[0] + a[ 4]*b[1] + a[ 8]*b[2] + a[12]*b[3];
        double t1 = a[ 1]*b[0] + a[ 5]*b[1] + a[ 9]*b[2] + a[13]*b[3];
        double t2 = a[ 2]*b[0] + a[ 6]*b[1] + a[10]*b[2] + a[14]*b[3];
        double t3 = a[ 3]*b[0] + a[ 7]*b[1] + a[11]*b[2] + a[15]*b[3];
        out[0] = t0;
        out[1] = t1;
        out[2] = t2;
        out[3] = t3;
    }

    /**
     * @param mat Input matrix
     * @param out Array to hold inverted matrix on return.
     * @return true if matrix determinant is not near zero and accurate invert was found.
     */
    public static boolean invert4( double[] mat, double[] out ) {
        double s0 = mat[0+0*4] * mat[1+1*4] - mat[1+0*4] * mat[0+1*4];
        double s1 = mat[0+0*4] * mat[1+2*4] - mat[1+0*4] * mat[0+2*4];
        double s2 = mat[0+0*4] * mat[1+3*4] - mat[1+0*4] * mat[0+3*4];
        double s3 = mat[0+1*4] * mat[1+2*4] - mat[1+1*4] * mat[0+2*4];
        double s4 = mat[0+1*4] * mat[1+3*4] - mat[1+1*4] * mat[0+3*4];
        double s5 = mat[0+2*4] * mat[1+3*4] - mat[1+2*4] * mat[0+3*4];

        double c5 = mat[2+2*4] * mat[3+3*4] - mat[3+2*4] * mat[2+3*4];
        double c4 = mat[2+1*4] * mat[3+3*4] - mat[3+1*4] * mat[2+3*4];
        double c3 = mat[2+1*4] * mat[3+2*4] - mat[3+1*4] * mat[2+2*4];
        double c2 = mat[2+0*4] * mat[3+3*4] - mat[3+0*4] * mat[2+3*4];
        double c1 = mat[2+0*4] * mat[3+2*4] - mat[3+0*4] * mat[2+2*4];
        double c0 = mat[2+0*4] * mat[3+1*4] - mat[3+0*4] * mat[2+1*4];

        // Compute determinant
        double invdet = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
        // Check if invertible.
        boolean ret  = invdet > SQRT_ABS_ERR || -invdet > SQRT_ABS_ERR;
        // Invert determinant
        invdet = 1.0 / invdet;

        double t00 = ( mat[1+1*4] * c5 - mat[1+2*4] * c4 + mat[1+3*4] * c3) * invdet;
        double t01 = (-mat[1+0*4] * c5 + mat[1+2*4] * c2 - mat[1+3*4] * c1) * invdet;
        double t02 = ( mat[1+0*4] * c4 - mat[1+1*4] * c2 + mat[1+3*4] * c0) * invdet;
        double t03 = (-mat[1+0*4] * c3 + mat[1+1*4] * c1 - mat[1+2*4] * c0) * invdet;
        double t04 = (-mat[0+1*4] * c5 + mat[0+2*4] * c4 - mat[0+3*4] * c3) * invdet;
        double t05 = ( mat[0+0*4] * c5 - mat[0+2*4] * c2 + mat[0+3*4] * c1) * invdet;
        double t06 = (-mat[0+0*4] * c4 + mat[0+1*4] * c2 - mat[0+3*4] * c0) * invdet;
        double t07 = ( mat[0+0*4] * c3 - mat[0+1*4] * c1 + mat[0+2*4] * c0) * invdet;
        double t08 = ( mat[3+1*4] * s5 - mat[3+2*4] * s4 + mat[3+3*4] * s3) * invdet;
        double t09 = (-mat[3+0*4] * s5 + mat[3+2*4] * s2 - mat[3+3*4] * s1) * invdet;
        double t10 = ( mat[3+0*4] * s4 - mat[3+1*4] * s2 + mat[3+3*4] * s0) * invdet;
        double t11 = (-mat[3+0*4] * s3 + mat[3+1*4] * s1 - mat[3+2*4] * s0) * invdet;
        double t12 = (-mat[2+1*4] * s5 + mat[2+2*4] * s4 - mat[2+3*4] * s3) * invdet;
        double t13 = ( mat[2+0*4] * s5 - mat[2+2*4] * s2 + mat[2+3*4] * s1) * invdet;
        double t14 = (-mat[2+0*4] * s4 + mat[2+1*4] * s2 - mat[2+3*4] * s0) * invdet;
        double t15 = ( mat[2+0*4] * s3 - mat[2+1*4] * s1 + mat[2+2*4] * s0) * invdet;

        out[ 0] = t00;
        out[ 1] = t01;
        out[ 2] = t02;
        out[ 3] = t03;
        out[ 4] = t04;
        out[ 5] = t05;
        out[ 6] = t06;
        out[ 7] = t07;
        out[ 8] = t08;
        out[ 9] = t09;
        out[10] = t10;
        out[11] = t11;
        out[12] = t12;
        out[13] = t13;
        out[14] = t14;
        out[15] = t15;

        return ret;
    }


    public static void transpose4( double[] mat, double[] out ) {
        // About 15% faster without local copies.
        double a00 = mat[ 0];
        double a01 = mat[ 1];
        double a02 = mat[ 2];
        double a03 = mat[ 3];
        double a04 = mat[ 4];
        double a05 = mat[ 5];
        double a06 = mat[ 6];
        double a07 = mat[ 7];
        double a08 = mat[ 8];
        double a09 = mat[ 9];
        double a10 = mat[10];
        double a11 = mat[11];
        double a12 = mat[12];
        double a13 = mat[13];
        double a14 = mat[14];
        double a15 = mat[15];

        out[ 0] = a00;
        out[ 1] = a04;
        out[ 2] = a08;
        out[ 3] = a12;

        out[ 4] = a01;
        out[ 5] = a05;
        out[ 6] = a09;
        out[ 7] = a13;

        out[ 8] = a02;
        out[ 9] = a06;
        out[10] = a10;
        out[11] = a14;

        out[12] = a03;
        out[13] = a07;
        out[14] = a11;
        out[15] = a15;
    }


    public static void identity4( double[] out ) {
        out[ 0] = 1.0;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = 1.0;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }

    /**
     * Computes a getRotate4 matrix.
     *
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void getRotate4( double radians, double x, double y, double z, double[] out ) {
        double c     = Math.cos( radians );
        double s     = Math.sin( radians );
        double scale = 1 / Math.sqrt( x*x + y*y + z*z );
        x *= scale;
        y *= scale;
        z *= scale;

        out[0 ] = x*x*(1-c)+c;
        out[1 ] = x*y*(1-c)+z*s;
        out[2 ] = x*z*(1-c)-y*s;
        out[3 ] = 0;

        out[4 ] = x*y*(1-c)-z*s;
        out[5 ] = y*y*(1-c)+c;
        out[6 ] = y*z*(1-c)+x*s;
        out[7 ] = 0;

        out[8 ] = x*z*(1-c)+y*s;
        out[9 ] = y*z*(1-c)-x*s;
        out[10] = z*z*(1-c)+c;
        out[11] = 0.0;

        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Multiplies an arbitrary matrix with a getRotate4 matrix.
     *
     * @param mat     Input matrix.
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotate4( double[] mat, double radians, double x, double y, double z, double[] out ) {
        double c = Math.cos( radians );
        double s = Math.sin( radians );
        double invSum = 1 / Math.sqrt( x*x + y*y + z*z );
        x *= invSum;
        y *= invSum;
        z *= invSum;

        double a00 = x*x*(1-c)+c;
        double a01 = x*y*(1-c)+z*s;
        double a02 = x*z*(1-c)-y*s;
        double a04 = x*y*(1-c)-z*s;
        double a05 = y*y*(1-c)+c;
        double a06 = y*z*(1-c)+x*s;
        double a08 = x*z*(1-c)+y*s;
        double a09 = y*z*(1-c)-x*s;
        double a10 = z*z*(1-c)+c;

        double b0 = mat[ 0];
        double b1 = mat[ 4];
        double b2 = mat[ 8];
        double b3 = mat[12];
        out[ 0] = a00*b0 + a01*b1 + a02*b2;
        out[ 4] = a04*b0 + a05*b1 + a06*b2;
        out[ 8] = a08*b0 + a09*b1 + a10*b2;
        out[12] = b3;
        b0 = mat[ 1];
        b1 = mat[ 5];
        b2 = mat[ 9];
        b3 = mat[13];
        out[ 1] = a00*b0 + a01*b1 + a02*b2;
        out[ 5] = a04*b0 + a05*b1 + a06*b2;
        out[ 9] = a08*b0 + a09*b1 + a10*b2;
        out[13] = b3;
        b0 = mat[ 2];
        b1 = mat[ 6];
        b2 = mat[10];
        b3 = mat[14];
        out[ 2] = a00*b0 + a01*b1 + a02*b2;
        out[ 6] = a04*b0 + a05*b1 + a06*b2;
        out[10] = a08*b0 + a09*b1 + a10*b2;
        out[14] = b3;
        b0 = mat[ 3];
        b1 = mat[ 7];
        b2 = mat[11];
        b3 = mat[15];
        out[ 3] = a00*b0 + a01*b1 + a02*b2;
        out[ 7] = a04*b0 + a05*b1 + a06*b2;
        out[11] = a08*b0 + a09*b1 + a10*b2;
        out[15] = b3;
    }

    /**
     * Multiplies a getRotate4 matrix with an arbitrary matrix.
     *
     * @param radians Degree of getRotate4.
     * @param x       X-Coord of getRotate4 axis.
     * @param y       Y-Coord of getRotate4 axis.
     * @param z       Z-Coord of getRotate4 axis.
     * @param mat     Input matrix.
     * @param out     Length-16 array to hold output on return.
     */
    public static void preRotate4( double radians, double x, double y, double z, double[] mat, double[] out ) {
        double c = Math.cos( radians );
        double s = Math.sin( radians );
        double sum = 1 / Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        double a00 = x*x*(1-c)+c;
        double a01 = x*y*(1-c)+z*s;
        double a02 = x*z*(1-c)-y*s;
        double a04 = x*y*(1-c)-z*s;
        double a05 = y*y*(1-c)+c;
        double a06 = y*z*(1-c)+x*s;
        double a08 = x*z*(1-c)+y*s;
        double a09 = y*z*(1-c)-x*s;
        double a10 = z*z*(1-c)+c;
        double b0 = mat[0];
        double b1 = mat[1];
        double b2 = mat[2];
        double b3 = mat[3];
        out[ 0] = a00*b0 + a04*b1 + a08*b2;
        out[ 1] = a01*b0 + a05*b1 + a09*b2;
        out[ 2] = a02*b0 + a06*b1 + a10*b2;
        out[ 3] = b3;
        b0 = mat[4];
        b1 = mat[5];
        b2 = mat[6];
        b3 = mat[7];
        out[ 4] = a00*b0 + a04*b1 + a08*b2;
        out[ 5] = a01*b0 + a05*b1 + a09*b2;
        out[ 6] = a02*b0 + a06*b1 + a10*b2;
        out[ 7] = b3;
        b0 = mat[8];
        b1 = mat[9];
        b2 = mat[10];
        b3 = mat[11];
        out[ 8] = a00*b0 + a04*b1 + a08*b2;
        out[ 9] = a01*b0 + a05*b1 + a09*b2;
        out[10] = a02*b0 + a06*b1 + a10*b2;
        out[11] = b3;
        b0 = mat[12];
        b1 = mat[13];
        b2 = mat[14];
        b3 = mat[15];
        out[12] = a00*b0 + a04*b1 + a08*b2;
        out[13] = a01*b0 + a05*b1 + a09*b2;
        out[14] = a02*b0 + a06*b1 + a10*b2;
        out[15] = b3;
    }


    public static void getScale4( double sx, double sy, double sz, double[] out ) {
        out[ 0] = sx;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = sy;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = sz;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }


    public static void scale4( double[] mat, double sx, double sy, double sz, double[] out ) {
        out[ 0] = sx * mat[ 0];
        out[ 1] = sx * mat[ 1];
        out[ 2] = sx * mat[ 2];
        out[ 3] = sx * mat[ 3];
        out[ 4] = sy * mat[ 4];
        out[ 5] = sy * mat[ 5];
        out[ 6] = sy * mat[ 6];
        out[ 7] = sy * mat[ 7];
        out[ 8] = sz * mat[ 8];
        out[ 9] = sz * mat[ 9];
        out[10] = sz * mat[10];
        out[11] = sz * mat[11];
        out[12] =      mat[12];
        out[13] =      mat[13];
        out[14] =      mat[14];
        out[15] =      mat[15];
    }


    public static void preScale4( double sx, double sy, double sz, double[] mat, double[] out ) {
        out[ 0] = sx * mat[ 0];
        out[ 1] = sy * mat[ 1];
        out[ 2] = sz * mat[ 2];
        out[ 3] =      mat[ 3];
        out[ 4] = sx * mat[ 4];
        out[ 5] = sy * mat[ 5];
        out[ 6] = sz * mat[ 6];
        out[ 7] =      mat[ 7];
        out[ 8] = sx * mat[ 8];
        out[ 9] = sy * mat[ 9];
        out[10] = sz * mat[10];
        out[11] =      mat[11];
        out[12] = sx * mat[12];
        out[13] = sy * mat[13];
        out[14] = sz * mat[14];
        out[15] =      mat[15];
    }


    public static void getTranslate4( double tx, double ty, double tz, double[] out ) {
        out[ 0] = 1.0;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = 1.0;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        out[12] = tx;
        out[13] = ty;
        out[14] = tz;
        out[15] = 1.0;
    }


    public static void translate4( double[] mat, double tx, double ty, double tz, double[] out ) {
        double b0 = mat[ 0];
        double b1 = mat[ 4];
        double b2 = mat[ 8];
        double b3 = mat[12];
        out[ 0] = b0;
        out[ 4] = b1;
        out[ 8] = b2;
        out[12] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 1];
        b1 = mat[ 5];
        b2 = mat[ 9];
        b3 = mat[13];
        out[ 1] = b0;
        out[ 5] = b1;
        out[ 9] = b2;
        out[13] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 2];
        b1 = mat[ 6];
        b2 = mat[10];
        b3 = mat[14];
        out[ 2] = b0;
        out[ 6] = b1;
        out[10] = b2;
        out[14] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 3];
        b1 = mat[ 7];
        b2 = mat[11];
        b3 = mat[15];
        out[ 3] = b0;
        out[ 7] = b1;
        out[11] = b2;
        out[15] = tx * b0 + ty * b1 + tz * b2 + b3;
    }


    public static void preTranslate4( double tx, double ty, double tz, double[] mat, double[] out ) {
        double b0 = mat[0];
        double b1 = mat[1];
        double b2 = mat[2];
        double b3 = mat[3];
        out[0] = b0 + tx * b3;
        out[1] = b1 + ty * b3;
        out[2] = b2 + tz * b3;
        out[3] = b3;
        b0 = mat[4];
        b1 = mat[5];
        b2 = mat[6];
        b3 = mat[7];
        out[ 4] = b0 + tx * b3;
        out[ 5] = b1 + ty * b3;
        out[ 6] = b2 + tz * b3;
        out[ 7] = b3;
        b0 = mat[8];
        b1 = mat[9];
        b2 = mat[10];
        b3 = mat[11];
        out[ 8] = b0 + tx * b3;
        out[ 9] = b1 + ty * b3;
        out[10] = b2 + tz * b3;
        out[11] = b3;
        b0 = mat[12];
        b1 = mat[13];
        b2 = mat[14];
        b3 = mat[15];
        out[12] = b0 + tx * b3;
        out[13] = b1 + ty * b3;
        out[14] = b2 + tz * b3;
        out[15] = b3;
    }


    public static void getFrustum4( double left,
                                    double right,
                                    double bottom,
                                    double top,
                                    double near,
                                    double far,
                                    double[] out ) {
        out[ 0] = 2.0 * near / (right - left);
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = 2 * near / (top - bottom);
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = (right + left) / (right - left);
        out[ 9] = (top + bottom) / (top - bottom);
        out[10] = -(far + near) / (far - near);
        out[11] = -1;

        out[12] = 0;
        out[13] = 0;
        out[14] = -(2 * far * near) / (far - near);
        out[15] = 0;
    }


    public static void getOrtho4( double left,
                                  double right,
                                  double bottom,
                                  double top,
                                  double near,
                                  double far,
                                  double[] out ) {
        out[ 0] = 2.0 / (right - left);
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = 2.0 / (top - bottom);
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = 0;
        out[ 9] = 0;
        out[10] = -2.0 / (far - near);
        out[11] = 0;

        out[12] = -(right + left) / (right - left);
        out[13] = -(top + bottom) / (top - bottom);
        out[14] = -(far + near) / (far - near);
        out[15] = 1;
    }


    public static void getLookAt4( double[] eyeVec, double[] centerVec, double[] upVec, double[] outMat ) {
        double fx  = centerVec[0] - eyeVec[0];
        double fy  = centerVec[1] - eyeVec[1];
        double fz  = centerVec[2] - eyeVec[2];
        double len = ( 1.0 / Math.sqrt( fx * fx + fy * fy + fz * fz ) );
        fx *= len;
        fy *= len;
        fz *= len;

        double ux = upVec[0];
        double uy = upVec[1];
        double uz = upVec[2];
        len = ( 1.0 / Math.sqrt( ux * ux + uy * uy + uz * uz ) );
        ux /= len;
        uy /= len;
        uz /= len;

        double sx = fy * uz - fz * uy;
        double sy = fz * ux - fx * uz;
        double sz = fx * uy - fy * ux;

        ux = sy * fz - sz * fy;
        uy = sz * fx - sx * fz;
        uz = sx * fy - sy * fx;

        outMat[ 0] = sx;
        outMat[ 1] = ux;
        outMat[ 2] = -fx;
        outMat[ 3] = 0;
        outMat[ 4] = sy;
        outMat[ 5] = uy;
        outMat[ 6] = -fy;
        outMat[ 7] = 0;
        outMat[ 8] = sz;
        outMat[ 9] = uz;
        outMat[10] = -fz;
        outMat[11] = 0;
        outMat[12] = -(sx * eyeVec[0] + sy * eyeVec[1] + sz * eyeVec[2]);
        outMat[13] = -(ux * eyeVec[0] + uy * eyeVec[1] + uz * eyeVec[2]);
        outMat[14] = fx * eyeVec[0] + fy * eyeVec[1] + fz * eyeVec[2];
        outMat[15] = 1;
    }


    public static void getViewport4( double x, double y, double w, double h, double[] out ) {
        out[ 0] = w * 0.5;
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = h * 0.5;
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = 0;
        out[ 9] = 0;
        out[10] = 1;
        out[11] = 0;

        out[12] = w * 0.5 + x;
        out[13] = h * 0.5 + y;
        out[14] = 0;
        out[15] = 1;
    }


    public static void getViewportDepth( double x, double y, double w, double h, double near, double far, double[] out ) {
        out[ 0] = w * 0.5;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;

        out[ 4] = 0.0;
        out[ 5] = h * 0.5;
        out[ 6] = 0.0;
        out[ 7] = 0.0;

        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = ( far - near ) * 0.5;
        out[11] = 0.0;

        out[12] = w * 0.5 + x;
        out[13] = h * 0.5 + y;
        out[14] = ( far + near ) * 0.5;
        out[15] = 1.0;
    }

    /**
     * Removes any getTranslate4/getScale4/skew or other non-getRotate4
     * transformations from a matrix.
     *
     * @param mat 4x4 homography matrix to turn into strict getRotate4 matrix.
     */
    public static void normalizeRotationMatrix4( double[] mat ) {
        double d;

        //Kill getTranslate4, scalings.
        mat[ 3] = 0;
        mat[ 7] = 0;
        mat[11] = 0;
        mat[12] = 0;
        mat[13] = 0;
        mat[14] = 0;
        mat[15] = 1;

        //Normalize length of X-axis.
        d = Math.sqrt( mat[0] * mat[0] + mat[1] * mat[1] + mat[2] * mat[2] );
        if( d > SQRT_ABS_ERR || -d > SQRT_ABS_ERR ) {
            d = 1 / d;
            mat[0] *= d;
            mat[1] *= d;
            mat[2] *= d;
        }else{
            mat[0] = 1;
            mat[1] = 0;
            mat[2] = 0;
        }

        //Orthogonalize Y-axis to X-axis
        d = mat[0] * mat[4] + mat[1] * mat[5] + mat[2] * mat[6];
        mat[4] -= d * mat[0];
        mat[5] -= d * mat[1];
        mat[6] -= d * mat[2];

        //Normalize Y-axis.
        d = Math.sqrt( mat[4] * mat[4] + mat[5] * mat[5] + mat[6] * mat[6] );
        if( d > SQRT_ABS_ERR || -d > SQRT_ABS_ERR ) {
            d = 1.0 / d;
            mat[4] *= d;
            mat[5] *= d;
            mat[6] *= d;
        }else{
            double m0 = mat[0];
            double m1 = mat[1];
            double m2 = mat[2];
            Vector.chooseOrtho3( m0, m1, m2, mat );
            mat[4] = mat[0];
            mat[5] = mat[1];
            mat[6] = mat[2];
            mat[0] = m0;
            mat[1] = m1;
            mat[2] = m2;
        }

        //Compute Z-axis
        mat[ 8] = mat[1]*mat[6] - mat[2]*mat[5];
        mat[ 9] = mat[2]*mat[4] - mat[0]*mat[6];
        mat[10] = mat[0]*mat[5] - mat[1]*mat[4];
    }


    public static void axes3ToTransform4( double[] x, double[] y, double[] out ) {
        double[] z = new double[3];
        Vector.cross3( x, y, z );
        axes3ToTransform4( x, y, z, out );
    }


    public static void axes3ToTransform4( double[] x, double[] y, double[] z, double[] out ) {
        out[ 0] = x[0];
        out[ 1] = x[1];
        out[ 2] = x[2];
        out[ 3] = 0;
        out[ 4] = y[0];
        out[ 5] = y[1];
        out[ 6] = y[2];
        out[ 7] = 0;
        out[ 8] = z[0];
        out[ 9] = z[1];
        out[10] = z[2];
        out[11] = 0;
        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * This method will adjust a getRotate4 matrix so that one of the basis vectors will
     * be parallel to an axis.
     *
     * @param mat   Input getRotate4 matrix.
     * @param basis Basis vector to align to axis. {@code 0 == x-basis, 1 == y-basis, 2 == z-basis}.
     * @param out   Holds modified getRotate4 matrix on output.
     */
    public static void alignBasisVectorToAxis4( double[] mat, int basis, double[] out ) {
        out[12] = mat[basis*4  ];
        out[13] = mat[basis*4+1];
        out[14] = mat[basis*4+2];

        // Clamp specified vector to nearest axis.
        Vector.nearestAxis3( mat[basis * 4], mat[basis * 4 + 1], mat[basis * 4 + 2], out );

        // Move into correct column.
        out[basis*4  ] = out[0];
        out[basis*4+1] = out[1];
        out[basis*4+2] = out[2];

        // Cross with next axis.
        final int basis1 = ( basis + 1 ) % 3;
        final int basis2 = ( basis + 2 ) % 3;

        Vector.cross3( out, basis * 4, mat, basis1 * 4, out, basis2 * 4 );
        ArrayUtils.normalize( out, basis2 * 4, 3, 1.0 );
        Vector.cross3( out, basis2 * 4, out, basis * 4, out, basis1 * 4 );

        out[ 3] = 0.0;
        out[ 7] = 0.0;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }

    /**
     * Makes the smallest angular adjustment possible to a vector such that
     * the resulting vector will lie inside the closed surface of a cone.
     * In other words, after calling {@code clampToCone3},
     * {@code ang3(vec, coneAxis) <= coneRads}.
     *
     * @param vec       Vector to clamp
     * @param coneAxis  Axis of cone
     * @param coneRads  Angle between axis and surface of cone.
     * @param outVec    Vector with smallest possible angular distance from {@code vec} that lies inside cone.  May be {@code null}.
     * @param outMat    Rotation matrix used to rotate4 vector onto cone.  May be {@code null}
     * @return true if any adjustment made, false if output is identical to input
     */
    public static boolean clampToCone4( double[] vec,
                                        double[] coneAxis,
                                        double coneRads,
                                        double[] outVec,
                                        double[] outMat ) {
        double cosAng = Vector.cosAng3( vec, coneAxis );
        double ang    = Math.acos( cosAng );

        if( ang != ang ) {
            ang = cosAng > 0.0 ? 0.0 : Math.PI;
        }
        if( ang <= coneRads ) {
            if( outVec != null ) {
                outVec[0] = vec[0];
                outVec[1] = vec[1];
                outVec[2] = vec[2];
            }
            if( outMat != null ) {
                Matrix.identity4( outMat );
            }
            return false;
        }

        double[] rotAxis = new double[3];
        double[] rot     = outMat == null ? new double[16] : outMat;

        if( ang < Math.PI * (1.0 + REL_ERR )) {
            Vector.cross3( vec, coneAxis, rotAxis );
        }else{
            Vector.chooseOrtho3( coneAxis[0], coneAxis[1], coneAxis[2], rotAxis );
        }

        Matrix.getRotate4( ang - coneRads * (1.0 - REL_ERR), rotAxis[0], rotAxis[1], rotAxis[2], rot );
        if( outVec != null ) {
            Matrix.mult4Vec3( rot, vec, outVec );
        }

        return true;
    }


    public static String format4( double[] mat ) {
        StringBuilder sb = new StringBuilder();
        for( int r = 0; r < 4; r++ ) {
            if ( r == 0 ) {
                sb.append( "[[ " );
            } else {
                sb.append( " [ " );
            }

            sb.append( String.format( "% 7.4f  % 7.4f  % 7.4f  % 7.4f", mat[r   ], mat[r+4], mat[r+8], mat[r+12] ) );

            if( r == 3 ) {
                sb.append( " ]]" );
            } else {
                sb.append( " ]\n" );
            }
        }

        return sb.toString();
    }


    public static boolean isNaN4( double[] mat ) {
        for( int i = 0; i < 16; i++ ) {
            if( Double.isNaN( mat[i] ) ) {
                return true;
            }
        }
        return false;
    }



    private Matrix() {}

}
