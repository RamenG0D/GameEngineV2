/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;


/**
 * Methods for transform objects.
 */
public final class Transform {

    public static void put( Transform3 a, Transform3 out ) {
        Vector.put( a.mPos, out.mPos );
        Matrix.put( a.mRot, out.mRot );
    }


    public static void identity( Transform3 a ) {
        Vector.put( 0, 0, 0, a.mPos );
        Matrix.identity( a.mRot );
    }


    public static void mult( Transform3 a, Vector3 vec, Vector3 out ) {
        Vector3 p = a.mPos;
        float x = p.x;
        float y = p.y;
        float z = p.z;
        Matrix.mult( a.mRot, vec, out );
        out.x += x;
        out.y += y;
        out.z += z;
    }


    public static void mult( Transform3 a, Vector4 vec, Vector4 out ) {
        Vector3 p = a.mPos;
        float vw = vec.w;
        float x = p.x;
        float y = p.y;
        float z = p.z;
        Matrix.mult( a.mRot, vec, out );
        out.x += vw * x;
        out.y += vw * y;
        out.z += vw * z;
    }


    public static void mult( Transform3 a, Transform3 b, Transform3 out ) {
        mult( a, b.mPos, out.mPos );
        Matrix.mult( a.mRot, b.mRot, out.mRot );
    }


    public static void invert( Transform3 tr, Transform3 out ) {
        Matrix.invert( tr.mRot, out.mRot );
        Vector3 p = out.mPos;
        Matrix.mult( out.mRot, tr.mPos, p );
        p.x = -p.x;
        p.y = -p.y;
        p.z = -p.z;
    }


    public static void orthoInvert( Transform3 tr, Transform3 out ) {
        Matrix.transpose( tr.mRot, out.mRot );
        Vector3 p = out.mPos;
        Matrix.mult( out.mRot, tr.mPos, p );
        p.x = -p.x;
        p.y = -p.y;
        p.z = -p.z;
    }


    public static void matToTrans( Matrix4 a, Transform3 out ) {
        Matrix.put( a, out.mRot );
        Vector.put( a.m03, a.m13, a.m23, out.mPos );
    }


    public static void transToMat( Transform3 tr, Matrix4 out ) {
        final Matrix3 rot = tr.mRot;
        out.m00 = rot.m00;
        out.m10 = rot.m10;
        out.m20 = rot.m20;
        out.m30 = 0;
        out.m01 = rot.m01;
        out.m11 = rot.m11;
        out.m21 = rot.m21;
        out.m31 = 0;
        out.m02 = rot.m02;
        out.m12 = rot.m12;
        out.m22 = rot.m22;
        out.m32 = 0;
        out.m03 = tr.mPos.x;
        out.m13 = tr.mPos.y;
        out.m23 = tr.mPos.z;
        out.m33 = 1;
    }


    public static void lerp( Transform3 a, Transform3 b, float t, Vector4 workA, Vector4 workB, Transform3 out ) {
        Vector.lerp( a.mPos, b.mPos, t, out.mPos );
        Matrix.slerp(a.mRot, b.mRot, t, workA, workB, out.mRot );
    }


    public static String format( Transform3 tr ) {
        StringBuilder sb = new StringBuilder();
        Matrix3 mat = tr.mRot;
        Vector3 pos = tr.mPos;
        sb.append( "[" );
        sb.append( String.format( Vector.FORMAT4, mat.m00, mat.m01, mat.m02, pos.x ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT4, mat.m10, mat.m11, mat.m12, pos.y ) ).append( '\n' );
        sb.append( String.format( Vector.FORMAT4, mat.m20, mat.m21, mat.m22, pos.z ) );
        sb.append( "]");
        return sb.toString();
    }

}
