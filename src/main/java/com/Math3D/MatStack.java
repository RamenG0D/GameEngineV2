/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.Arrays;
import java.util.EmptyStackException;


/**
 * Matrix stack.
 */
public class MatStack {

    private Matrix4[] mStack;
    private int mPos = 0;
    public final Matrix4 mMat = new Matrix4();


    public MatStack() {
        mStack = new Matrix4[4];
        for( int i = 0; i < mStack.length; i++ ) {
            mStack[i] = new Matrix4();
        }
        identity();
    }


    public Matrix4 get() {
        return mMat;
    }



    public void get( Matrix4 out ) {
        Matrix.put( mMat, out );
    }


    public void set( Matrix4 m ) {
        Matrix.put( m, mMat );
    }


    public void set( Matrix3 m ) {
        Matrix.put( m, mMat );
    }
    
    
    public void push() {
        ensureCapacity( mPos + 1 );
        Matrix.put( mMat, mStack[mPos++] );
    }

    
    public void pop() {
        if( --mPos < 0 ) {
            mPos = 0;
            throw new EmptyStackException();
        }
        Matrix.put( mStack[mPos], mMat );
    }

    
    public void identity() {
        Matrix.identity( mMat );
    }


    public void mult( Matrix4 m ) {
        Matrix.mult( mMat, m, mMat );
    }


    public void mult( Matrix3 m ) {
        Matrix.mult( mMat, m, mMat );
    }

    
    public void premult( Matrix4 m ) {
        Matrix.mult( m, mMat, mMat );
    }


    public void premult( Matrix3 m ) {
        Matrix.mult( m, mMat, mMat );
    }


    public void invert() {
        Matrix.invert( mMat, mMat );
    }


    public void translate( float dx, float dy, float dz ) {
        Matrix.translate( mMat, dx, dy, dz, mMat );
    }


    public void preTranslate( float dx, float dy, float dz ) {
        Matrix.preTranslate( dx, dy, dz, mMat, mMat );
    }

    
    public void rotate( float radians, float x, float y, float z ) {
        Matrix.rotate( mMat, radians, x, y, z, mMat );
    }


    public void preRotate( float radians, float x, float y, float z ) {
        Matrix.preRotate( radians, x, y, z, mMat, mMat );
    }


    public void scale( float sx, float sy, float sz, float sw ) {
        Matrix.scale( mMat, sx, sy, sz, sw, mMat );
    }


    public void preScale( float sx, float sy, float sz, float sw ) {
        Matrix.preScale( sx, sy, sz, sw, mMat, mMat );
    }


    public void multOrtho( float left, float right, float bottom, float top, float near, float far ) {
        Matrix.multOrtho( mMat, left, right, bottom, top, near, far, mMat );
    }


    public void setOrtho( float left, float right, float bottom, float top, float near, float far ) {
        Matrix.getOrtho( left, right, bottom, top, near, far, mMat );
    }


    public void multFrustum( float left, float right, float bottom, float top, float near, float far ) {
        Matrix.multFrustum( mMat, left, right, bottom, top, near, far, mMat );
    }


    public void setFrustum( float left, float right, float bottom, float top, float near, float far ) {
        Matrix.getFrustum( left, right, bottom, top, near, far, mMat );
    }


    public int size() {
        return mPos;
    }


    public void clear() {
        mPos = 0;
        identity();
    }

    
    public void ensureCapacity( int minCapacity ) {
        int oldCapacity = mStack.length;
        
        if( minCapacity > oldCapacity ) {
            int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            if( newCapacity < minCapacity ) {
                newCapacity = minCapacity;
            }
            mStack = Arrays.copyOf( mStack, newCapacity );
            for( int i = oldCapacity; i < newCapacity; i++ ) {
                mStack[i] = new Matrix4();
            }
        }
    }
    
}
