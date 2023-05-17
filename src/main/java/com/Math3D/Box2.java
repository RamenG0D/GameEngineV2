/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.NoSuchElementException;


/**
 * 2D Axis-Aligned Bounding-Box.
 *
 * @author decamp
 */
public final class Box2 {

    /** Minimum X coord**/
    public float x0;
    /** Minimum Y coord**/
    public float y0;
    /** Maximum X coord**/
    public float x1;
    /** Maximum Y coord**/
    public float y1;

    public Box2() {}


    public Box2( Box2 copy ) {
        x0 = copy.x0;
        y0 = copy.y0;
        x1 = copy.x1;
        y1 = copy.y1;
    }


    public Box2( float x0, float y0, float x1, float y1 ) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }



    public float min( int dim ) {
        switch( dim ) {
        case 0: return x0;
        case 1: return y0;
        default: throw new NoSuchElementException();
        }
    }


    public void min( int dim, float v ) {
        switch( dim ) {
        case 0: x0 = v; return;
        case 1: y0 = v; return;
        default: throw new NoSuchElementException();
        }
    }


    public float max( int dim ) {
        switch( dim ) {
        case 0: return x1;
        case 1: return y1;
        default: throw new NoSuchElementException();
        }
    }


    public void max( int dim, float v ) {
        switch( dim ) {
        case 0: x1 = v; return;
        case 1: y1 = v; return;
        default: throw new NoSuchElementException();
        }
    }


    public float center( int dim ) {
        switch( dim ) {
        case 0: return 0.5f * ( x0 + x1 );
        case 1: return 0.5f * ( y0 + y1 );
        default: throw new NoSuchElementException();
        }
    }


    public float span( int dim ) {
        switch( dim ) {
        case 0: return ( x1 - x0 );
        case 1: return ( y1 - y0 );
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
               x1 == b.x1 &&
               y1 == b.y1;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x0 + y0 ) ^
               Float.floatToIntBits( x1 + y1 );
    }

    @Override
    public String toString() {
        return Box.format( this );
    }

}
