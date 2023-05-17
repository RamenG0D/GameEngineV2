/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.NoSuchElementException;


/**
 * 3D Axis Aligned Bounding Box
 *
 * @author Philip DeCamp
 */
public class Box3 {

    public float x0;
    public float y0;
    public float z0;
    public float x1;
    public float y1;
    public float z1;

    public Box3() {}


    public Box3( Box3 copy ) {
        x0 = copy.x0;
        y0 = copy.y0;
        z0 = copy.z0;
        x1 = copy.x1;
        y1 = copy.y1;
        z1 = copy.z1;
    }


    public Box3( float x0, float y0, float z0, float x1, float y1, float z1 ) {
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
    }



    public float min( int dim ) {
        switch( dim ) {
        case 0: return x0;
        case 1: return y0;
        case 2: return z0;
        default: throw new NoSuchElementException();
        }
    }


    public void min( int dim, float v ) {
        switch( dim ) {
        case 0: x0 = v; return;
        case 1: y0 = v; return;
        case 2: z0 = v; return;
        default: throw new NoSuchElementException();
        }
    }


    public float max( int dim ) {
        switch( dim ) {
        case 0: return x1;
        case 1: return y1;
        case 2: return z1;
        default: throw new NoSuchElementException();
        }
    }


    public void max( int dim, float v ) {
        switch( dim ) {
        case 0: x1 = v; return;
        case 1: y1 = v; return;
        case 2: z1 = v; return;
        default: throw new NoSuchElementException();
        }
    }


    public float center( int dim ) {
        switch( dim ) {
        case 0: return 0.5f * ( x0 + x1 );
        case 1: return 0.5f * ( y0 + y1 );
        case 2: return 0.5f * ( z0 + z1 );
        default: throw new NoSuchElementException();
        }
    }


    public float span( int dim ) {
        switch( dim ) {
        case 0: return ( x1 - x0 );
        case 1: return ( y1 - y0 );
        case 2: return ( z1 - z0 );
        default: throw new NoSuchElementException();
        }
    }


    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Box3 ) ) {
            return false;
        }

        Box3 b = (Box3)obj;
        return x0 == b.x0 &&
               y0 == b.y0 &&
               z0 == b.z0 &&
               x1 == b.x1 &&
               y1 == b.y1 &&
               z1 == b.z1;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x0 + y0 + z0 ) ^
               Float.floatToIntBits( x1 + y1 + z1 );
    }

    @Override
    public String toString() {
        return Box.format( this );
    }

}

