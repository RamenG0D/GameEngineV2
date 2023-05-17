/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;


/**
 * Trans3 (3D Transform) represents a rotation and translation, in that order.
 */
public class Transform3 {
    public final Vector3 mPos = new Vector3();
    public final Matrix3 mRot = new Matrix3(1,0,0,0,1,0,0,0,1);


    public Transform3() {}

    public Transform3( Vector3 pos, Matrix3 rot ) {
        Vector.put( pos, mPos );
        Matrix.put( rot, mRot );
    }

    public Transform3( Transform3 copy ) {
        this( copy.mPos, copy.mRot );
    }


    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Transform3) ) {
            return false;
        }
        Transform3 tr = (Transform3)obj;
        return tr == this || mPos.equals( tr.mPos ) && mRot.equals( tr.mRot );
    }

    @Override
    public int hashCode() {
        return mPos.hashCode() ^ mRot.hashCode();
    }

    @Override
    public String toString() {
        return Transform.format( this );
    }

}
