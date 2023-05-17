/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.NoSuchElementException;


/**
 * 3-dimensional vector.
 *
 * @author Philip DeCamp
 */
public class Vector3 {

    public float x;
    public float y;
    public float z;


    public Vector3() {
        this(0, 0, 0);
    }


    public Vector3( float x, float y, float z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector3( Vector3 copy ) {
        x = copy.x;
        y = copy.y;
        z = copy.z;
    }



    public float getElement( int d ) {
        switch( d ) {
        case 0: return x;
        case 1: return y;
        case 2: return z;
        default:
            throw new NoSuchElementException();
        }
    }


    public void setElement( int d, float v ) {
        switch( d ) {
        case 0: x = v; return;
        case 1: y = v; return;
        case 2: z = v; return;
        default:
            throw new NoSuchElementException();
        }
    }


    @Override
    public boolean equals( Object obj ) {
        if( !( obj instanceof Vector3 ) ) {
            return false;
        }

        Vector3 v = (Vector3)obj;
        // v == this is necessary to catch NaNs.
        return v == this || x == v.x && y == v.y && z == v.z;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x + y + z );
    }

    @Override
    public String toString() {
        return Vector.format( this );
    }

}
