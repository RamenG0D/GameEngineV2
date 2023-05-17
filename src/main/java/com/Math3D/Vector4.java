/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.NoSuchElementException;


/**
 * 4-dimensional vector.
 *
 * @author Philip DeCamp
 */
public class Vector4 extends Vector3 {

    public float w;


    public Vector4() {}


    public Vector4( float x, float y, float z, float w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    public Vector4( Vector4 copy ) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
        this.w = copy.w;
    }



    public float el( int d ) {
        switch( d ) {
        case 0: return x;
        case 1: return y;
        case 2: return z;
        case 3: return w;
        default:
            throw new NoSuchElementException();
        }
    }


    public void el( int d, float v ) {
        switch( d ) {
        case 0: x = v; return;
        case 1: y = v; return;
        case 2: z = v; return;
        case 3: w = v; return;
        default:
            throw new NoSuchElementException();
        }
    }


    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Vector4 ) ) {
            return false;
        }

        Vector4 v = (Vector4)obj;
        // v == this is necessary to catch NaNs.
        return v == this || x == v.x && y == v.y && z == v.z && w == v.w;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x + y + z + w );
    }

    @Override
    public String toString() {
        return Vector.format( this );
    }

}
