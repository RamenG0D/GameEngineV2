/*
 * Copyright (c) 2015. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import com.Math3D.*;


/**
 * @author Philip DeCamp
 */
public class Polygons {

    public static float area( Vector2[] p ) {
        return area( p, 0, p.length );
    }

    /**
     * Computes area of polygon defined by points starting at {@code p[off]}.
     * <p>
     * The first point does not need to be repeated. For example,
     * a triangle should have {@code len == 3}. However, if the first
     * point is repeated, it will not affect the answer.
     *
     * @param p   Array of points of polygon.
     * @param off Offset into array.
     * @param len Number of points in polygon.
     * @return Area of polygon.
     * The area is non-negative if polygon is oriented counter-clockwise,
     * non-positive if oriented clockwise.
     */
    public static float area( Vector2[] p, int off, int len ) {
        if( len <= 0 ) {
            return Float.NaN;
        }

        final int end = off + len;
        Vector2  a   = p[end - 1];
        float sum = 0;

        for( int i = off; i < end; i++ ) {
            Vector2 b = p[i];
            sum += ( a.x - b.x ) * ( a.y + b.y );
            a = b;
        }

        return 0.5f * sum;
    }

    /**
     * Computes centroid of polygon.
     * <p>
     * The first point does not need to be repeated. For example,
     * a triangle should have {@code len == 3}. However, if the first
     * point is repeated, it will not affect the answer.
     *
     * @param p   Array of points of polygon.
     * @param off Offset into array.
     * @param len Number of points in polygon.
     * @param out Holds centroid on return.
     * @return Area of polygon.
     * The area is non-negative if polygon is oriented counter-clockwise,
     * non-positive if oriented clockwise.
     */
    public static float centroid( Vector2[] p, int off, int len, Vector2 out ) {
        if( len <= 0 ) {
            out.x = out.y = Float.NaN;
            return Float.NaN;
        }

        final int end = off + len;

        Vector2  a    = p[end - 1];
        float cx   = 0;
        float cy   = 0;
        float area = 0;

        for( int i = off; i < end; i++ ) {
            Vector2 b = p[i];
            double part = ( a.x * b.y ) - ( b.x * a.y );

            cx += ( a.x + b.x ) * part;
            cy += ( a.y + b.y ) * part;
            area += part;

            a = b;
        }

        out.x = ( 1.0f / 3.0f ) / area * cx;
        out.y = ( 1.0f / 3.0f ) / area * cy;
        return 0.5f * area;
    }


    public static float triArea( Vector2 a, Vector2 b, Vector2 c ) {
        return 0.5f * ( (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y) );
    }


    public static float triArea( Vector3 a, Vector3 b, Vector3 c ) {
        float x = (b.y - a.y) * (c.z - a.z) - (c.y - a.y) * (b.z - a.z);
        float y = (b.z - a.z) * (c.x - a.x) - (c.z - a.z) * (b.x - a.x);
        float z = (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
        return (float)( 0.5 * Math.sqrt( x * y + y * y + z * z ) );
    }



    public static double area2( double[][] p ) {
        return area2( p, 0, p.length );
    }


    public static double area2( double[][] p, int off, int len ) {
        double[] p0 = p[len-1+off];
        double sum  = 0.0;

        for( int i = 0; i < len; i++ ) {
            double[] p1 = p[i+off];
            sum += ( p1[0] - p0[0] ) * ( p1[1] + p0[1] );
            p0 = p1;
        }

        return ( sum < 0.0 ? -sum : sum ) * 0.5;
    }


    public static double triArea2( double[] a, double[] b, double[] c ) {
        double sum = ( b[0] - a[0] ) * ( b[1] + a[1] );
        sum += ( c[0] - b[0] ) * ( c[1] + b[1] );
        sum += ( a[0] - c[0] ) * ( a[1] + c[1] );
        return ( sum < 0.0 ? -sum : sum ) * 0.5;
    }


    public static double triArea3( double[] a, double[] b, double[] c ) {
        double x = (b[1] - a[1]) * (c[2] - a[2]) - (c[1] - a[1]) * (b[2] - a[2]);
        double y = (b[2] - a[2]) * (c[0] - a[0]) - (c[2] - a[2]) * (b[0] - a[0]);
        double z = (b[0] - a[0]) * (c[1] - a[1]) - (c[0] - a[0]) * (b[1] - a[1]);
        return 0.5 * Math.sqrt( x * x + y * y + z * z );
    }

}
