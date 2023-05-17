/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import com.Math3D.Vector3;


/**
 * @author decamp
 */
public class Sphere implements Volume {

    public final Vector3  mPos;
    public       float mRad;

    public Sphere( float x, float y, float z, float rad ) {
        mPos = new Vector3( x, y, z );
        mRad = rad;
    }


    public float x() {
        return mPos.x;
    }

    public float y() {
        return mPos.y;
    }

    public float z() {
        return mPos.z;
    }

    public float rad() {
        return mRad;
    }


    public boolean contains( float x, float y, float z ) {
        float dx = x - mPos.x;
        float dy = y - mPos.y;
        float dz = z - mPos.z;
        return dx * dx + dy * dy + dz * dz <= mRad * mRad;
    }


    public Aabb bounds() {
        return Aabb.fromCenter( mPos.x, mPos.y, mPos.z, mRad * 2f, mRad * 2f, mRad * 2f );
    }


    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Sphere) ) {
            return false;
        }

        if( obj == this ) {
            return true;
        }

        Sphere s = (Sphere)obj;
        return mRad == s.mRad && mPos.equals( s.mPos);
    }

    @Override
    public int hashCode() {
        return mPos.hashCode() ^ Float.floatToIntBits( mRad );
    }

    @Override
    public String toString() {
        return String.format( "Sphere [%f, %f, %f,  rad: %f]", mPos.x, mPos.y, mPos.z, mRad );
    }
}
