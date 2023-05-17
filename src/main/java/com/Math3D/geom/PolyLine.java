/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import com.Math3D.*;
import java.util.Arrays;


/**
 * @author Philip DeCamp
 */
public class PolyLine {

    public Vector3[] mVerts;
    public int mSize;


    public PolyLine() {
        this( 10 );
    }


    public PolyLine( Vector3[] vertsRef ) {
        mVerts = vertsRef;
    }


    public PolyLine( int len ) {
        mVerts = new Vector3[len];
        for( int i = 0; i < len; i++ ) {
            mVerts[i] = new Vector3();
        }
    }


    public void ensureCapacity( int minCap ) {
        final int oldCap = mVerts.length;
        if( minCap <= oldCap ) {
            return;
        }

        int newCap = ( oldCap * 3 ) / 2 + 1;
        if( newCap < minCap ) {
            newCap = minCap;
        }

        mVerts = Arrays.copyOf( mVerts, newCap );
        for( int i = oldCap; i < newCap; i++ ) {
            mVerts[i] = new Vector3();
        }
    }

}
