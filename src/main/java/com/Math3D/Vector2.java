/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.NoSuchElementException;


/**
 * 2-dimensional Vector
 *
 * @author Philip DeCamp  
 */
public class Vector2 {

    public float x;
    public float y;


    public Vector2() {}


    public Vector2( float x, float y ) {
        this.x = x;
        this.y = y;
    }


    public Vector2( Vector2 copy ) {
        x = copy.x;
        y = copy.y;
    }



    public float el( int d ) {
        switch( d ) {
        case 0: return x;
        case 1: return y;
        default:
            throw new NoSuchElementException();
        }
    }


    public void el( int d, float v ) {
        switch( d ) {
        case 0: x = v; break;
        case 1: y = v; break;
        default:
            throw new NoSuchElementException();
        }
    }


    @Override
    public boolean equals( Object obj ) {
        if( !( obj instanceof Vector2 ) ) {
            return false;
        }
        Vector2 v = (Vector2)obj;
        // "v == this" is needed to protect against NaNs.
        return v == this || x == v.x && y == v.y;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x + y );
    }

    @Override
    public String toString() {
        return Vector.format( this );
    }

}
