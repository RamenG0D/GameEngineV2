/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;

import java.io.Serializable;


/**
 * @author Philip DeCamp
 */
public class Gaussian3 implements Serializable, Function31 {


    private static final long serialVersionUID = -8124999237658840759L;


    public static Gaussian3 fromCovariance( double exx,
                                            double eyy,
                                            double ezz,
                                            double exy,
                                            double exz,
                                            double eyz )
    {
        double sigX = Math.sqrt( exx );
        double sigY = Math.sqrt( eyy );
        double sigZ = Math.sqrt( ezz );

        return new Gaussian3( 0f, 0f, 0f,
                              sigX, sigY, sigZ,
                              exy / (sigX * sigY), exz / (sigX * sigZ), eyz / (sigY * sigZ),
                              1f );
    }


    public static Gaussian3 fromCovariance( double meanX,
                                            double meanY,
                                            double meanZ,
                                            double exx,
                                            double eyy,
                                            double ezz,
                                            double exy,
                                            double exz,
                                            double eyz )
    {
        double sigX = Math.sqrt( exx );
        double sigY = Math.sqrt( eyy );
        double sigZ = Math.sqrt( ezz );

        return new Gaussian3( meanX, meanY, meanZ,
                              sigX, sigY, sigZ,
                              exy / sigX / sigY, exz / sigX / sigZ, eyz / sigY / sigZ,
                              1f );
    }


    public static Gaussian3 fromCovariance( double meanX,
                                            double meanY,
                                            double meanZ,
                                            double exx,
                                            double eyy,
                                            double ezz,
                                            double exy,
                                            double exz,
                                            double eyz,
                                            double integral )
    {
        double sigX = Math.sqrt( exx );
        double sigY = Math.sqrt( eyy );
        double sigZ = Math.sqrt( ezz );

        return new Gaussian3( meanX, meanY, meanZ,
                              sigX, sigY, sigZ,
                              exy / sigX / sigY, exz / sigX / sigZ, eyz / sigY / sigZ,
                              integral );
    }


    public static Gaussian3 fromCovariance( double[] params ) {

        if( params.length == 6 ) {
            double sigX = Math.sqrt( params[0] );
            double sigY = Math.sqrt( params[1] );
            double sigZ = Math.sqrt( params[2] );

            return new Gaussian3( 0f, 0f, 0f,
                                  sigX, sigY, sigZ,
                                  params[3] / sigX / sigY, params[4] / sigX / sigZ, params[5] / sigY / sigZ,
                                  1f );
        }

        if( params.length != 9 && params.length != 10 ) {
            throw new IllegalArgumentException( "fromCovariance(double[]) method requires 6, 9, or 10 parameters." );
        }

        double sigX = Math.sqrt( params[3] );
        double sigY = Math.sqrt( params[4] );
        double sigZ = Math.sqrt( params[5] );
        double integral = params.length == 10 ? params[9] : 1f;

        return new Gaussian3( params[0], params[1], params[2],
                              sigX, sigY, sigZ,
                              params[6] / sigX / sigY, params[7] / sigX / sigZ, params[8] / sigY / sigZ,
                              integral );
    }


    public static Gaussian3 fromSigma( double sigX,
                                       double sigY,
                                       double sigZ,
                                       double pxy,
                                       double pxz,
                                       double pyz )
    {
        return new Gaussian3( 0f, 0f, 0f,
                              sigX, sigY, sigZ,
                              pxy, pxz, pyz,
                              1f );
    }


    public static Gaussian3 fromSigma( double meanX,
                                       double meanY,
                                       double meanZ,
                                       double sigX,
                                       double sigY,
                                       double sigZ,
                                       double pxy,
                                       double pxz,
                                       double pyz )
    {
        return new Gaussian3( meanX, meanY, meanZ,
                              sigX, sigY, sigZ,
                              pxy, pxz, pyz,
                              1f );
    }


    public static Gaussian3 fromSigma( double meanX,
                                       double meanY,
                                       double meanZ,
                                       double sigX,
                                       double sigY,
                                       double sigZ,
                                       double pxy,
                                       double pxz,
                                       double pyz,
                                       double integral )
    {
        return new Gaussian3( meanX, meanY, meanZ,
                              sigX, sigY, sigZ,
                              pxy, pxz, pyz,
                              integral );
    }


    public static Gaussian3 fromSigma( double[] params ) {
        if( params.length == 6 ) {
            return new Gaussian3( 0f, 0f, 0f,
                                  params[0], params[1], params[2],
                                  params[3], params[4], params[5],
                                  1f );
        }

        if( params.length != 9 && params.length != 10 ) {
            throw new IllegalArgumentException( "fromSigma(double[]) method requires 6, 9, or 10 parameters." );
        }

        return new Gaussian3( params[0], params[1], params[2],
                              params[3], params[4], params[5],
                              params[6], params[7], params[8],
                              params.length == 10 ? params[9] : 1f );
    }


    private final double mMeanX;
    private final double mMeanY;
    private final double mMeanZ;
    private final double mSigmaX;
    private final double mSigmaY;
    private final double mSigmaZ;
    private final double mPXY;
    private final double mPXZ;
    private final double mPYZ;
    private final double mIntegral;

    private final double mCoefVol;
    private final double mCoefXX;
    private final double mCoefYY;
    private final double mCoefZZ;
    private final double mCoefXY;
    private final double mCoefXZ;
    private final double mCoefYZ;


    private Gaussian3( double meanX, double meanY, double meanZ,
                       double sigX, double sigY, double sigZ,
                       double pxy, double pxz, double pyz,
                       double integral )
    {
        mMeanX = meanX;
        mMeanY = meanY;
        mMeanZ = meanZ;

        mSigmaX = sigX;
        mSigmaY = sigY;
        mSigmaZ = sigZ;

        mPXY = pxy;
        mPXZ = pxz;
        mPYZ = pyz;

        mIntegral = integral;

        final double a11 = mSigmaX * mSigmaX;
        final double a12 = mSigmaX * mSigmaY * mPXY;
        final double a21 = a12;
        final double a13 = mSigmaX * mSigmaZ * mPXZ;
        final double a31 = a13;
        final double a22 = mSigmaY * mSigmaY;
        final double a23 = mSigmaY * mSigmaZ * mPYZ;
        final double a32 = a23;
        final double a33 = mSigmaZ * mSigmaZ;
        final double det = Math.abs( a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31) );

        mCoefVol = (mIntegral / Math.sqrt( 8 * Math.PI * Math.PI * Math.PI * det ));
        mCoefXX = (-0.5 * (a22 * a33 - a23 * a32) / det);
        mCoefXY = ((a12 * a33 - a13 * a32) / det);
        mCoefXZ = (-(a12 * a23 - a13 * a22) / det);
        mCoefYY = (-0.5 * (a11 * a33 - a13 * a31) / det);
        mCoefYZ = ((a11 * a23 - a13 * a21) / det);
        mCoefZZ = (-0.5 * (a11 * a22 - a12 * a21) / det);
    }


    public double meanX() {
        return mMeanX;
    }

    public double meanY() {
        return mMeanY;
    }

    public double meanZ() {
        return mMeanZ;
    }

    public double exx() {
        return mSigmaX * mSigmaX;
    }

    public double eyy() {
        return mSigmaY * mSigmaY;
    }

    public double ezz() {
        return mSigmaZ * mSigmaZ;
    }

    public double exy() {
        return mPXY * mSigmaX * mSigmaY;
    }

    public double exz() {
        return mPXZ * mSigmaX * mSigmaZ;
    }

    public double eyz() {
        return mPYZ * mSigmaY * mSigmaZ;
    }

    public double sigmaX() {
        return mSigmaX;
    }

    public double sigmaY() {
        return mSigmaY;
    }

    public double sigmaZ() {
        return mSigmaZ;
    }

    public double integral() {
        return mIntegral;
    }


    public Gaussian3 setMean( double x, double y, double z ) {
        return new Gaussian3( x, y, z,
                              mSigmaX, mSigmaY, mSigmaZ,
                              mPXY, mPXZ, mPYZ,
                              mIntegral );
    }

    public Gaussian3 setCovariance( double exx, double eyy, double ezz, double exy, double exz, double eyz ) {
        return Gaussian3.fromCovariance( mMeanX, mMeanY, mMeanZ,
                                         exx, eyy, ezz,
                                         exy, exz, eyz,
                                         mIntegral );
    }

    public Gaussian3 setSigma( double sigX, double sigY, double sigZ, double pxy, double pxz, double pyz ) {
        return new Gaussian3( mMeanX, mMeanY, mMeanZ,
                              sigX, sigY, sigZ,
                              pxy, pxz, pyz,
                              mIntegral );
    }

    public Gaussian3 setIntegral( double integral ) {
        return new Gaussian3( mMeanX, mMeanY, mMeanZ,
                              mSigmaX, mSigmaY, mSigmaZ,
                              mPXY, mPXZ, mPYZ,
                              integral );
    }

    public Gaussian3 translate( double dx, double dy, double dz ) {
        return new Gaussian3( mMeanX + dx, mMeanY + dy, mMeanZ + dz,
                              mSigmaX, mSigmaY, mSigmaZ,
                              mPXY, mPXZ, mPYZ,
                              mIntegral );
    }

    public Gaussian3 scaleDomain( double scale ) {
        return new Gaussian3( mMeanX * scale,
                              mMeanY * scale,
                              mMeanZ * scale,
                              mSigmaX * scale,
                              mSigmaY * scale,
                              mSigmaZ * scale,
                              mPXY,
                              mPXZ,
                              mPYZ,
                              mIntegral );
    }

    public Gaussian3 scale( double scaleX, double scaleY, double scaleZ ) {
        return new Gaussian3( mMeanX * scaleX, mMeanY * scaleY, mMeanZ * scaleZ,
                              mSigmaX * scaleX, mSigmaY * scaleY, mSigmaZ * scaleZ,
                              mPXY, mPXZ, mPYZ,
                              mIntegral );
    }

    public Gaussian3 scaleSigma( double scale ) {
        return new Gaussian3( mMeanX, mMeanY, mMeanZ,
                              mSigmaX * scale, mSigmaY * scale, mSigmaZ * scale,
                              mPXY, mPXZ, mPYZ,
                              mIntegral );
    }

    public Gaussian3 scaleSigma( double scaleX, double scaleY, double scaleZ ) {
        return new Gaussian3( mMeanX, mMeanY, mMeanZ,
                              mSigmaX * scaleX, mSigmaY * scaleY, mSigmaZ * scaleZ,
                              mPXY, mPXZ, mPYZ,
                              mIntegral );
    }

    public Gaussian3 scaleRange( double mult ) {
        return new Gaussian3( mMeanX,
                              mMeanY,
                              mMeanZ,
                              mSigmaX,
                              mSigmaY,
                              mSigmaZ,
                              mPXY,
                              mPXZ,
                              mPYZ,
                              mIntegral * mult );
    }


    public double apply( double x, double y, double z ) {
        x -= mMeanX;
        y -= mMeanY;
        z -= mMeanZ;

        return mCoefVol * Math.exp( mCoefXX * x * x + mCoefXY * x * y + mCoefXZ * x * z +
                                    mCoefYY * y * y + mCoefYZ * y * z + mCoefZZ * z * z );
    }


    public boolean isValid() {
        if( Double.isNaN( mCoefVol ) || Double.isInfinite( mCoefVol ) ) {
            return false;
        }

        if( Double.isNaN( mCoefXX ) || Double.isInfinite( mCoefXX ) ) {
            return false;
        }

        if( Double.isNaN( mCoefYY ) || Double.isInfinite( mCoefYY ) ) {
            return false;
        }

        if( Double.isNaN( mCoefZZ ) || Double.isInfinite( mCoefZZ ) ) {
            return false;
        }

        if( Double.isNaN( mCoefXY ) || Double.isInfinite( mCoefXY ) ) {
            return false;
        }

        if( Double.isNaN( mCoefXZ ) || Double.isInfinite( mCoefXZ ) ) {
            return false;
        }

        if( Double.isNaN( mCoefYZ ) || Double.isInfinite( mCoefYZ ) ) {
            return false;
        }

        return true;
    }

    public String toString() {
        return String.format( "Gaussian3 [u = (%f, %f, %f), sig = (%f, %f, %f), p = (%f, %f, %f), vol = %f]",
                              mMeanX, mMeanY, mMeanZ,
                              mSigmaX, mSigmaY, mSigmaZ,
                              mPXY, mPXZ, mPYZ,
                              mIntegral );
    }

    public boolean equals( Object obj ) {
        if( !(obj instanceof Gaussian3) ) {
            return false;
        }

        Gaussian3 g = (Gaussian3)obj;
        return this == g ||
               mMeanX == g.mMeanX &&
               mMeanY == g.mMeanY &&
               mMeanZ == g.mMeanZ &&
               mSigmaX == g.mSigmaX &&
               mSigmaY == g.mSigmaY &&
               mSigmaZ == g.mSigmaZ &&
               mPXY == g.mPXY &&
               mPXZ == g.mPXZ &&
               mPYZ == g.mPYZ &&
               mIntegral == g.mIntegral;
    }

    public int hashCode() {
        return (int)Double.doubleToLongBits( mMeanX + mMeanY + mMeanZ + mSigmaX + mSigmaY + mSigmaZ );
    }

}
