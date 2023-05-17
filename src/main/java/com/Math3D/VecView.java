/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.NoSuchElementException;

/**
 * @author Philip DeCamp
 */
public interface VecView<T> {

    int dim();
    double get( T item, int n );
    void set( T item, int n, double v );
    double x( T item );
    void x( T item, double x );
    double y( T item );
    void y( T item, double y );
    double z( T item );
    void z( T item, double z );
    double w( T item );
    void w( T item, double w );


    class FloatArrayView implements VecView<float[]> {

        private final int mDim;

        public FloatArrayView( int dim ) {
            mDim = dim;
        }

        public int    dim(                               ) { return mDim; }
        public double get( float[] item, int n           ) { return item[n]; }
        public void   set( float[] item, int n, double v ) { item[n] = (float)v; }

        public double x  ( float[] item           ) { return item[0]; }
        public double y  ( float[] item           ) { return item[1]; }
        public double z  ( float[] item           ) { return item[2]; }
        public double w  ( float[] item           ) { return item[3]; }

        public void   x  ( float[] item, double x ) { item[0] = (float)x; }
        public void   y  ( float[] item, double y ) { item[1] = (float)y; }
        public void   z  ( float[] item, double z ) { item[2] = (float)z; }
        public void   w  ( float[] item, double w ) { item[3] = (float)w; }
    }


    class DoubleArrayView implements VecView<double[]> {

        private final int mDim;

        public DoubleArrayView( int dim ) {
            mDim = dim;
        }

        public int    dim(                               ) { return mDim; }
        public double get( double[] item, int n           ) { return item[n]; }
        public void   set( double[] item, int n, double v ) { item[n] = v; }

        public double x  ( double[] item           ) { return item[0]; }
        public double y  ( double[] item           ) { return item[1]; }
        public double z  ( double[] item           ) { return item[2]; }
        public double w  ( double[] item           ) { return item[3]; }

        public void   x  ( double[] item, double x ) { item[0] = x; }
        public void   y  ( double[] item, double y ) { item[1] = y; }
        public void   z  ( double[] item, double z ) { item[2] = z; }
        public void   w  ( double[] item, double w ) { item[3] = w; }
    }


    VecView<Vector3> VEC3 = new VecView<Vector3>() {
        public int dim() { return 3; }
        public double x( Vector3 item )           { return item.x; }
        public void   x( Vector3 item, double x ) { item.x = (float)x; }
        public double y( Vector3 item )           { return item.y; }
        public void   y( Vector3 item, double y ) { item.y = (float)y; }
        public double z( Vector3 item )           { return item.z; }
        public void   z( Vector3 item, double z ) { item.z = (float)z; }
        public double w( Vector3 item )           { throw new NoSuchElementException(); }
        public void   w( Vector3 item, double w ) { throw new NoSuchElementException(); }

        public double get( Vector3 item, int n ) {
            switch( n ) {
            case 0: return item.x;
            case 1: return item.y;
            case 2: return item.z;
            default:
                throw new NoSuchElementException();
            }
        }

        public void set( Vector3 item, int n, double v ) {
            switch( n ) {
            case 0: item.x = (float)v; return;
            case 1: item.y = (float)v; return;
            case 2: item.z = (float)v; return;
            default:
                throw new NoSuchElementException();
            }
        }

    };

}
