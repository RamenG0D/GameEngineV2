/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;

/**
 * @author Philip DeCamp
 */
public class Sigmoid implements Function11 {

    public static Function11 createClampedSigma( double startX, double startY, double stopX, double stopY, double tol ) {
        double w = 2.0 * -Math.log( tol / (1 - tol) );
        return new Sigmoid( startX, stopX, w, startY, stopY, tol );
    }


    private final double mStartX;
    private final double mStopX;
    private final double mW;
    private final double mStartY;
    private final double mStopY;
    private final double mTol;


    private Sigmoid( double startX, double stopX, double w, double startY, double stopY, double tol ) {
        mStartX = startX;
        mStopX = stopX;
        mW = w;
        mStartY = startY;
        mStopY = stopY;
        mTol = tol;
    }


    public double apply( double t ) {
        double x = (t - mStartX) * mW / (mStopX - mStartX);
        if( x < 0 ) {
            return mStartY;
        }
        if( x > mW ) {
            return mStopY;
        }

        double y = 1.0 / (1.0 + Math.exp( mW * 0.5 - x ));
        return ((y - mTol) * (mStopY - mStartY) / (1 - mTol * 2) + mStartY);
    }

}
