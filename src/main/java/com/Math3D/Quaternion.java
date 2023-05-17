/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;


import java.util.Random;


/**
 * Methods for quaternions.
 *
 * @author decamp
 */
public final class Quaternion {

    /**
     * Multiply two quaternions.
     * @param a   Input quaternion
     * @param b   Input quaternion
     * @param out Receives product quaternion. May be same object as either input.
     */
    public static void mult( Vector4 a, Vector4 b, Vector4 out ) {
        // These local copies had no effect in performance tests, but whatevs.
        final float a0 = a.x;
        final float a1 = a.y;
        final float a2 = a.z;
        final float a3 = a.w;
        final float b0 = b.x;
        final float b1 = b.y;
        final float b2 = b.z;
        final float b3 = b.w;
        out.x = a0 * b0 - a1 * b1 - a2 * b2 - a3 * b3;
        out.y = a0 * b1 + a1 * b0 + a2 * b3 - a3 * b2;
        out.z = a0 * b2 - a1 * b3 + a2 * b0 + a3 * b1;
        out.w = a0 * b3 + a1 * b2 - a2 * b1 + a3 * b0;
    }

    /**
     * Multiply quaternion and vector.
     *
     * @param quat Input quaternion.
     * @param vec  Input vector.
     * @param out  Output vector. May be same as either input.
     */
    public static void multVec( Vector4 quat, Vector3 vec, Vector3 out ) {
        final float q0 = quat.x;
        final float q1 = quat.y;
        final float q2 = quat.z;
        final float q3 = quat.w;
        final float vx = vec.x;
        final float vy = vec.y;
        final float vz = vec.z;

        out.x = ( q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3 ) * vx +
                ( 2  * ( q1 * q2 - q0 * q3 ) )            * vy +
                ( 2  * ( q1 * q3 + q0 * q2 ) )            * vz;

        out.y = ( 2 * ( q1 * q2 + q0 * q3 ) )             * vx +
                ( q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3 ) * vy +
                ( 2 * ( q2 * q3 - q0 * q1 ) )             * vz;

        out.z = ( 2 * ( q1 * q3 - q0 * q2 ) )             * vx +
                ( 2 * ( q2 * q3 + q0 * q1 ) )             * vy +
                ( q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3 ) * vz;
    }

    /**
     * Normalize quaternion to a valid unit-length.
     */
    public static void normalize( Vector4 q ) {
        Vector.normalize( q );
    }

    /**
     * Converts axis-rotation to quaternion representation.
     * @param rads Radians of rotation
     * @param x    X-coord of rotation axis
     * @param y    Y-coord of rotation axis
     * @param z    Z-coord of rotation axis
     * @param out  Length-4 array that holds rotation on return.
     */
    public static void rotation( float rads, float x, float y, float z, Vector4 out ) {
        float cos = (float)Math.cos( rads * 0.5 );
        float len = (float)Math.sqrt( x * x + y * y + z * z );
        float sin = (float)Math.sin( rads * 0.5 ) / len;
        out.x = cos;
        out.y = sin * x;
        out.z = sin * y;
        out.w = sin * z;
    }

    /**
     * Converts a rotation matrix to an equivalent quaternion.
     * Non-rotation matrices will produce undefined results.
     *
     * @param mat Input matrix.
     * @param out Receives output quaternion
     */
    public static void matToQuat( Matrix3 mat, Vector4 out ) {
        final float r00 = mat.m00;
        final float r11 = mat.m11;
        final float r22 = mat.m22;

        float q0 = (  r00 + r11 + r22 + 1 );
        float q1 = (  r00 - r11 - r22 + 1 );
        float q2 = ( -r00 + r11 - r22 + 1 );
        float q3 = ( -r00 - r11 + r22 + 1 );

        q0 = q0 < 0 ? 0 : (float)Math.sqrt( q0 );
        q1 = q1 < 0 ? 0 : (float)Math.sqrt( q1 );
        q2 = q2 < 0 ? 0 : (float)Math.sqrt( q2 );
        q3 = q3 < 0 ? 0 : (float)Math.sqrt( q3 );

        if( q0 >= q1 && q0 >= q2 && q0 >= q3 ) {
            if( mat.m21 - mat.m12 < 0 ) q1 = -q1;
            if( mat.m02 - mat.m20 < 0 ) q2 = -q2;
            if( mat.m10 - mat.m01 < 0 ) q3 = -q3;
        } else if( q1 >= q2 && q1 >= q3 ) {
            if( mat.m21 - mat.m12 < 0 ) q0 = -q0;
            if( mat.m10 + mat.m01 < 0 ) q2 = -q2;
            if( mat.m02 + mat.m20 < 0 ) q3 = -q3;
        } else if( q2 >= q3 ) {
            if( mat.m02 - mat.m20 < 0 ) q0 = -q0;
            if( mat.m10 + mat.m01 < 0 ) q1 = -q1;
            if( mat.m21 + mat.m12 < 0 ) q3 = -q3;
        } else {
            if( mat.m10 - mat.m01 < 0 ) q0 = -q0;
            if( mat.m20 + mat.m02 < 0 ) q1 = -q1;
            if( mat.m21 + mat.m12 < 0 ) q2 = -q2;
        }

        float r = 1 / (float)Math.sqrt( q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3 );
        out.x = q0 * r;
        out.y = q1 * r;
        out.z = q2 * r;
        out.w = q3 * r;
    }

    /**
     * Converts quaternion to dim4 rotation matrix.
     *
     * @param quat Input quaternion.
     * @param out  Receives output.
     */
    public static void quatToMat( Vector4 quat, Matrix3 out ) {
        final float q0 = quat.x;
        final float q1 = quat.y;
        final float q2 = quat.z;
        final float q3 = quat.w;

        out.m00 = q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3;
        out.m10 = 2 * ( q1 * q2 + q0 * q3 );
        out.m20 = 2 * ( q1 * q3 - q0 * q2 );

        out.m01 = 2 * ( q1 * q2 - q0 * q3 );
        out.m11 = q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3;
        out.m21 = 2 * ( q2 * q3 + q0 * q1 );

        out.m02 = 2 * ( q1 * q3 + q0 * q2 );
        out.m12 = 2 * ( q2 * q3 - q0 * q1 );
        out.m22 = q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3;
    }

    /**
     * Computes spherical interpolation between two quaternions.
     * @param qa  Quaternior
     * @param qb  Quaternion
     * @param t   Blend factor
     * @param out Length-4 vec to receive output.
     */
    public static void slerp( Vector4 qa, Vector4 qb, float t, Vector4 out ) {
        // Calculate angle between them.
        float cosHalfTheta = Vector.dot( qa, qb );

        // if qa=qb or qa=-qb then theta = 0 and we can return qa
        if( cosHalfTheta >= 1.0 || cosHalfTheta <= -1.0 ) {
            Vector.put( qa, out );
            return;
        }

        // Calculate temporary values.
        float halfTheta = (float)Math.acos( cosHalfTheta );
        float sinHalfTheta = (float)Math.sqrt( 1.0 - cosHalfTheta * cosHalfTheta );

        // if theta = 180 degrees then result is not fully defined
        // we could rotate around any axis normal to qa or qb
        if( sinHalfTheta < 0.00001f && sinHalfTheta > -0.00001f ) {
            out.x = ( qa.x * 0.5f + qb.x * 0.5f );
            out.y = ( qa.y * 0.5f + qb.y * 0.5f );
            out.z = ( qa.z * 0.5f + qb.z * 0.5f );
            out.w = ( qa.w * 0.5f + qb.w * 0.5f );
            return;
        }

        float ratioA = (float)Math.sin( (1 - t) * halfTheta ) / sinHalfTheta;
        float ratioB = (float)Math.sin( t * halfTheta ) / sinHalfTheta;
        //calculate Quaternion.
        out.x = ( qa.x * ratioA + qb.x * ratioB );
        out.y = ( qa.y * ratioA + qb.y * ratioB );
        out.z = ( qa.z * ratioA + qb.z * ratioB );
        out.w = ( qa.w * ratioA + qb.w * ratioB );
    }

    /**
     * Converts three random numbers from [0,1] into a quaternion
     * in such a way that if the samples are independent and
     * uniformly distributed, then the resulting quaternions will
     * uniformly represent the domain of quaternion rotations.
     * <p>
     * One implication of this function is that it can be used
     * to uniformly sample a 2-sphere.
     *
     * @param out   Holds output quaternion on return.
     */
    public static void sampleUniform( Random rand, Vector4 out ) {
        randToQuat( rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), out );
    }

    /**
     * Converts three random numbers from [0,1] into a quaternion
     * in such a way that if the samples are independent and
     * uniformly distributed, then the resulting quaternions will
     * uniformly represent the domain of quaternion rotations.
     * <p>
     * One implication of this function is that it can be used
     * to uniformly sample a 2-sphere.
     *
     * @param rand0 Arbitrary val in [0,1]
     * @param rand1 Arbitrary val in [0,1]
     * @param rand2 Arbitrary val in [0,1]
     * @param out   Holds quaternion on return.
     */
    public static void randToQuat( float rand0, float rand1, float rand2, Vector4 out ) {
        float sign0 = 1f;
        float sign1 = 1f;
        float sign2 = 1f;

        // Sort three numbers.
        // Use the sort order to generate 3 random booleans for mSign values.
        if( rand0 > rand1 ) {
            sign0 = -1;
            sign1 = -1;
            float swap = rand0;
            rand0 = rand1;
            rand1 = swap;
        }
        if( rand1 > rand2 ) {
            sign1 = -sign1;
            sign2 = -sign2;
            float swap = rand1;
            rand1 = rand2;
            rand2 = swap;

            if( rand0 > rand1 ) {
                sign0 = -sign0;
                sign1 = -sign1;
                swap  = rand0;
                rand0 = rand1;
                rand1 = swap;
            }
        }

        out.x = sign0 * (rand0        );
        out.y = sign1 * (rand1 - rand0);
        out.z = sign2 * (rand2 - rand1);
        out.w = 1.0f - rand2;
        normalize( out );
    }

    /**
     * Formats quaternion for printing.
     */
    public static String format( Vector4 quat ) {
        return Vector.format( quat );
    }



    /**
     * Multiply two quaternions.
     * @param out Length-4 array to hold output on return. May be the same array as one of the inputs.
     */
    public static void mult( double[] a, double[] b, double[] out ) {
        // These local copies had no effect in performance tests, but whatevs.
        final double a0 = a[0];
        final double a1 = a[1];
        final double a2 = a[2];
        final double a3 = a[3];
        final double b0 = b[0];
        final double b1 = b[1];
        final double b2 = b[2];
        final double b3 = b[3];
        out[0] = a0 * b0 - a1 * b1 - a2 * b2 - a3 * b3;
        out[1] = a0 * b1 + a1 * b0 + a2 * b3 - a3 * b2;
        out[2] = a0 * b2 - a1 * b3 + a2 * b0 + a3 * b1;
        out[3] = a0 * b3 + a1 * b2 - a2 * b1 + a3 * b0;
    }


    public static void multVec3( double[] quat, double[] vec, double[] out ) {
        final double q0 = quat[0];
        final double q1 = quat[1];
        final double q2 = quat[2];
        final double q3 = quat[3];
        final double vx = vec[0];
        final double vy = vec[1];
        final double vz = vec[2];

        out[0] = ( q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3 ) * vx +
                 ( 2  * ( q1 * q2 - q0 * q3 ) )            * vy +
                 ( 2  * ( q1 * q3 + q0 * q2 ) )            * vz;

        out[1] = ( 2 * ( q1 * q2 + q0 * q3 ) )             * vx +
                 ( q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3 ) * vy +
                 ( 2 * ( q2 * q3 - q0 * q1 ) )             * vz;

        out[2] = ( 2 * ( q1 * q3 - q0 * q2 ) )             * vx +
                 ( 2 * ( q2 * q3 + q0 * q1 ) )             * vy +
                 ( q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3 ) * vz;
    }

    /**
     * Normalize quaternion to a valid unit-length.
     */
    public static void normalize( double[] q ) {
        Vector.normalize4( q );
    }

    /**
     * Converts axis-rotation to quaternion representation.
     * @param rads Radians of rotation
     * @param x    X-coord of rotation axis
     * @param y    Y-coord of rotation axis
     * @param z    Z-coord of rotation axis
     * @param out  Length-4 array that holds rotation on return.
     */
    public static void rotation( double rads, double x, double y, double z, double[] out ) {
        double cos = Math.cos( rads * 0.5 );
        double len = Math.sqrt( x * x + y * y + z * z );
        double sin = Math.sin( rads * 0.5 ) / len;
        out[0] = cos;
        out[1] = sin * x;
        out[2] = sin * y;
        out[3] = sin * z;
    }

    /**
     * Converts a rotation matrix to an equivalent quaternion.
     * Non-rotation matrices will produce undefined results.
     *
     * @param mat Length-16 array holding rotation matrix.
     * @param out Length-4 array that holds equivalent quaternion on return.
     */
    public static void mat4ToQuat( double[] mat, double[] out ) {
        final double r00 = mat[ 0];
        final double r11 = mat[ 5];
        final double r22 = mat[10];

        double q0 = (  r00 + r11 + r22 + 1 );
        double q1 = (  r00 - r11 - r22 + 1 );
        double q2 = ( -r00 + r11 - r22 + 1 );
        double q3 = ( -r00 - r11 + r22 + 1 );

        q0 = q0 < 0 ? 0 : Math.sqrt( q0 );
        q1 = q1 < 0 ? 0 : Math.sqrt( q1 );
        q2 = q2 < 0 ? 0 : Math.sqrt( q2 );
        q3 = q3 < 0 ? 0 : Math.sqrt( q3 );

        if( q0 >= q1 && q0 >= q2 && q0 >= q3 ) {
            //System.out.println( "#0" );
            if( mat[6] - mat[9] < 0 ) q1 = -q1;
            if( mat[8] - mat[2] < 0 ) q2 = -q2;
            if( mat[1] - mat[4] < 0 ) q3 = -q3;
        } else if( q1 >= q2 && q1 >= q3 ) {
            //System.out.println( "#1" );
            if( mat[6] - mat[9] < 0 ) q0 = -q0;
            if( mat[1] + mat[4] < 0 ) q2 = -q2;
            if( mat[8] + mat[2] < 0 ) q3 = -q3;
        } else if( q2 >= q3 ) {
            //System.out.println( "#2" );
            if( mat[8] - mat[2] < 0 ) q0 = -q0;
            if( mat[1] + mat[4] < 0 ) q1 = -q1;
            if( mat[6] + mat[9] < 0 ) q3 = -q3;
        } else {
            //System.out.println( "#3" );
            if( mat[1] - mat[4] < 0 ) q0 = -q0;
            if( mat[2] + mat[8] < 0 ) q1 = -q1;
            if( mat[6] + mat[9] < 0 ) q2 = -q2;
        }

        double r = 1.0 / Math.sqrt( q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3 );
        out[0] = q0 * r;
        out[1] = q1 * r;
        out[2] = q2 * r;
        out[3] = q3 * r;
    }

    /**
     * Converts quaternion to dim4 rotation matrix.
     *
     * @param quat Length-4 array holding quaternion.
     * @param out  Length-16 array that holds equivalent matrix on return.
     */
    public static void quatToMat4( double[] quat, double[] out ) {
        final double q0 = quat[0];
        final double q1 = quat[1];
        final double q2 = quat[2];
        final double q3 = quat[3];

        out[ 0] = q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3;
        out[ 1] = 2 * ( q1 * q2 + q0 * q3 );
        out[ 2] = 2 * ( q1 * q3 - q0 * q2 );
        out[ 3] = 0;

        out[ 4] = 2 * ( q1 * q2 - q0 * q3 );
        out[ 5] = q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3;
        out[ 6] = 2 * ( q2 * q3 + q0 * q1 );
        out[ 7] = 0;

        out[ 8] = 2 * ( q1 * q3 + q0 * q2 );
        out[ 9] = 2 * ( q2 * q3 - q0 * q1 );
        out[10] = q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3;
        out[11] = 0;

        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Computes spherical interpolation between two quaternions.
     * @param qa  Quaternior
     * @param qb  Quaternion
     * @param t   Blend factor
     * @param out Length-4 array that holds quaternion output on return.
     */
    public static void slerp( double[] qa, double[] qb, double t, double[] out ) {
        // Calculate angle between them.
        double cosHalfTheta = Vector.dot4( qa, qb );

        // if qa=qb or qa=-qb then theta = 0 and we can return qa
        if( cosHalfTheta >= 1.0 || cosHalfTheta <= -1.0 ) {
            Vector.put4( qa, out );
            return;
        }

        // Calculate temporary values.
        double halfTheta    = Math.acos( cosHalfTheta );
        double sinHalfTheta = Math.sqrt( 1.0 - cosHalfTheta * cosHalfTheta );

        // if theta = 180 degrees then result is not fully defined
        // we could rotate around any axis normal to qa or qb
        if( sinHalfTheta < 0.00001 && sinHalfTheta > -0.00001 ) {
            out[0] = ( qa[0] * 0.5 + qb[0] * 0.5 );
            out[1] = ( qa[1] * 0.5 + qb[1] * 0.5 );
            out[2] = ( qa[2] * 0.5 + qb[2] * 0.5 );
            out[3] = ( qa[3] * 0.5 + qb[3] * 0.5 );
            return;
        }

        double ratioA = Math.sin( (1 - t) * halfTheta ) / sinHalfTheta;
        double ratioB = Math.sin( t * halfTheta ) / sinHalfTheta;
        //calculate Quaternion.
        out[0] = ( qa[0] * ratioA + qb[0] * ratioB );
        out[1] = ( qa[1] * ratioA + qb[1] * ratioB );
        out[2] = ( qa[2] * ratioA + qb[2] * ratioB );
        out[3] = ( qa[3] * ratioA + qb[3] * ratioB );
    }

    /**
     * Converts three random numbers from [0,1] into a quaternion
     * in such a way that if the samples are independent and
     * uniformly distributed, then the resulting quaternions will
     * uniformly represent the domain of quaternion rotations.
     * <p>
     * One implication of this function is that it can be used
     * to uniformly sample a 2-sphere.
     *
     * @param out   Holds output quaternion on return.
     */
    public static void sampleUniform( Random rand, double[] out ) {
        randToQuat( rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), out );
    }

    /**
     * Converts three random numbers from [0,1] into a quaternion
     * in such a way that if the samples are independent and
     * uniformly distributed, then the resulting quaternions will
     * uniformly represent the domain of quaternion rotations.
     * <p>
     * One implication of this function is that it can be used
     * to uniformly sample a 2-sphere.
     *
     * @param rand0 Arbitrary val in [0,1]
     * @param rand1 Arbitrary val in [0,1]
     * @param rand2 Arbitrary val in [0,1]
     * @param out   Holds quaternion on return.
     */
    public static void randToQuat( double rand0, double rand1, double rand2, double[] out ) {
        double sign0 = 1.0;
        double sign1 = 1.0;
        double sign2 = 1.0;

        // Sort three numbers.
        // Use the sort order to generate 3 random booleans for mSign values.
        if( rand0 > rand1 ) {
            sign0 = -1;
            sign1 = -1;
            double swap = rand0;
            rand0 = rand1;
            rand1 = swap;
        }
        if( rand1 > rand2 ) {
            sign1 = -sign1;
            sign2 = -sign2;
            double swap = rand1;
            rand1 = rand2;
            rand2 = swap;

            if( rand0 > rand1 ) {
                sign0 = -sign0;
                sign1 = -sign1;
                swap  = rand0;
                rand0 = rand1;
                rand1 = swap;
            }
        }

        out[0] = sign0 * (rand0        );
        out[1] = sign1 * (rand1 - rand0);
        out[2] = sign2 * (rand2 - rand1);
        out[3] = 1.0f - rand2;
        normalize( out );
    }

    /**
     * Formats quaternion for printing.
     */
    public static String format4( double[] quat ) {
        return Vector.format4( quat );
    }


    private Quaternion() {}


}
