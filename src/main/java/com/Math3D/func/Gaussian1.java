/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;

/**
 * @author Philip DeCamp
 */
public class Gaussian1 implements Function11 {

    private static final double SQRT_2_PI = Math.sqrt( 2 * Math.PI );


    public static Gaussian1 fromVariance( double var ) {
        return new Gaussian1( 0f, Math.sqrt( var ), 1f );
    }


    public static Gaussian1 fromVariance( double mean, double var ) {
        return new Gaussian1( mean, Math.sqrt( var ), 1f );
    }


    public static Gaussian1 fromVariance( double mean, double var, double integral ) {
        return new Gaussian1( mean, Math.sqrt( var ), integral );
    }


    public static Gaussian1 fromSigma( double sig ) {
        return new Gaussian1( 0f, sig, 1f );
    }


    public static Gaussian1 fromSigma( double mean, double sig ) {
        return new Gaussian1( mean, sig, 1f );
    }


    public static Gaussian1 fromSigma( double mean, double sig, double integral ) {
        return new Gaussian1( mean, sig, integral );
    }


    private final double mMean;
    private final double mSigma;
    private final double mIntegral;

    private final double mCoefArea;
    private final double mCoefX;


    private Gaussian1( double mean, double sigma, double integral ) {
        mMean = mean;
        mSigma = sigma;
        mIntegral = integral;

        mCoefArea = integral / sigma / SQRT_2_PI;
        mCoefX = -1.0 / (2.0 * sigma * sigma);
    }


    public double mean() {
        return mMean;
    }

    public double sigma() {
        return mSigma;
    }

    public double variance() {
        return mSigma * mSigma;
    }

    public double integral() {
        return mIntegral;
    }


    public Gaussian1 setMean( double mean ) {
        return new Gaussian1( mean, mSigma, mIntegral );
    }

    public Gaussian1 setVariance( double var ) {
        return new Gaussian1( mMean, Math.sqrt( var ), mIntegral );
    }

    public Gaussian1 setSigma( double sigma ) {
        return new Gaussian1( mMean, sigma, mIntegral );
    }

    public Gaussian1 setIntegral( double integral ) {
        return new Gaussian1( mMean, mSigma, integral );
    }

    public Gaussian1 translate( double delta ) {
        return new Gaussian1( mMean + delta, mSigma, mIntegral );
    }

    public Gaussian1 scaleDomain( double scale ) {
        return new Gaussian1( mMean * scale, mSigma * scale, mIntegral );
    }

    public Gaussian1 scaleRange( double scale ) {
        return new Gaussian1( mMean, mSigma, mIntegral * scale );
    }

    public Gaussian1 scaleSigma( double scale ) {
        return new Gaussian1( mMean, mSigma * scale, mIntegral );
    }


    public double apply( double x ) {
        x -= mMean;
        return mCoefArea * Math.exp( mCoefX * x * x );
    }

    public boolean equals( Object obj ) {
        if( !(obj instanceof Gaussian1) ) {
            return false;
        }

        Gaussian1 g = (Gaussian1)obj;

        return mMean == g.mMean &&
               mSigma == g.mSigma &&
               mIntegral == g.mIntegral;
    }

    public int hashCode() {
        return (int)Double.doubleToLongBits( mMean + mSigma );
    }

}

