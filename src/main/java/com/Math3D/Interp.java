/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;


/**
 * Interpolation functions
 * 
 * @author decamp
 */
public final class Interp {


    public static double bezier( double begin, double control, double end, double t ) {
        final double s = 1.0 - t;
        return s * s * begin + s * t * control + t * t * end;
    }


    public static void bezier( double[] begin, double[] control, double[] end, double t, double[] out ) {
        final int    dim = begin.length;
        final double s   = 1.0 - t;
        final double c0  = s * s;
        final double c1  = 2.0 * s * t;
        final double c2  = t * t;

        for( int i = 0; i < dim; i++ ) {
            out[i] = c0 * begin[i] + c1 * control[i] + c2 * end[i];
        }
    }


    public static double bezier( double begin, double control0, double control1, double end, double t ) {
        final double s = 1.0 - t;
        return s * s * s * begin +
               3.0 * s * s * t * control0 +
               3.0 * s * t * t * control1 +
               t * t * t * end;
    }


    public static void bezier( double[] begin, double[] control0, double[] control1, double[] end, double t, double[] out ) {
        final int    dim = begin.length;
        final double s   = 1.0 - t;
        final double c0  = s * s * s;
        final double c1  = 3.0 * s * s * t;
        final double c2  = 3.0 * s * t * t;
        final double c3  = t * t * t;

        for( int i = 0; i < dim; i++ ) {
            out[i] = c0 * begin[i] + c1 * control0[i] + c2 * control1[i] + c3 * end[i];
        }
    }

    /**
     * Cubic spline interpolation.
     *  
     * @param x0 Point before point before sample.
     * @param x1 Point before sample.
     * @param x2 Point after sample.
     * @param x3 Point after point after sample.
     * @param t  Curve position to sample, parameterized between [0,1].
     * @return interpolated point.
     */
    public static double cubic( double x0, double x1, double x2, double x3, double t ) {
        double tt = t * t;
        double a0 = x3 - x2 - x0 + x1;
        double a1 = x0 - x1 - a0;
        double a2 = x2 - x0;
        return a0 * t * tt + a1 * tt + a2 * t + x1;
    }

    /**
     * Cubic spline interpolation.
     *
     * @param x0  Point before point before sample.
     * @param x1  Point before sample.
     * @param x2  Point after sample.
     * @param x3  Point after point after sample.
     * @param t   Curve position to sample, parameterized between [0,1].
     * @param out Holds interpolated point on return.
     */
    public static void cubic( double[] x0, double[] x1, double[] x2, double[] x3, double t, double[] out ) {
        final int dim = x0.length;
        for( int i = 0; i < dim; i++ ) {
            out[i] = cubic( x0[i], x1[i], x2[i], x3[i], t );
        }
    }

    /**
     * Catmull-Rom spline interpolation.
     * <p>
     * Similar to cubic spline, but smoother
     * due to use of derivatives.
     * 
     * @param x0 Point before point before sample.
     * @param x1 Point before sample.
     * @param x2 Point after sample.
     * @param x3 Point after point after sample.
     * @param t  Curve position to sample, parameterized between [0,1].
     * @return interpolated point.
     */
    public static double catmull( double x0, double x1, double x2, double x3, double t ) {
        double tt = t * t;
        double a0 = -0.5 * x0 + 1.5 * x1 - 1.5 * x2 + 0.5 * x3;
        double a1 = x0 - 2.5 * x1 + 2 * x2 - 0.5 * x3;
        double a2 = -0.5 * x0 + 0.5 * x2;
        return a0 * t * tt + a1 * tt + a2 * t + x1;
    }

    /**
     * Catmull-Rom spline interpolation.
     * <p>
     * Similar to cubic spline, but smoother
     * due to use of derivatives.
     *
     * @param x0  Point before point before sample.
     * @param x1  Point before sample.
     * @param x2  Point after sample.
     * @param x3  Point after point after sample.
     * @param t   Curve position to sample, parameterized between [0,1].
     * @param out On return, holds interpolated point.
     */
    public static void catmull( double[] x0, double[] x1, double[] x2, double[] x3, double t, double[] out ) {
        final int dim   = x0.length;
        for( int i = 0; i < dim; i++ ) {
            out[i] = catmull( x0[i], x1[i], x2[i], x3[i], t );
        }
    }

    /**
     * Hermite spline interpolation.
     * 
     * @param x0 Point before point before sample
     * @param x1 Point before sample
     * @param x2 Point after sample
     * @param x3 Point after point after sample
     * @param t  Curve position to sample, parameterized between [0,1].
     * @param tension 1 is high, 0 is normal, -1 is low
     * @param bias    0 is even, positive is towards first segment, negative is towards other.
     * @return interpolated point
     */
    public static double hermite( double x0,
                                  double x1,
                                  double x2,
                                  double x3,
                                  double t,
                                  double tension,
                                  double bias )
    {
        double tt  = t * t;
        double ttt = tt * t;
        tension = 0.5 - 0.5 * tension;
        
        double m0  = (x1-x0) * (1+bias) * tension +
                     (x2-x1) * (1-bias) * tension;
        
        double m1 = (x2-x1) * (1+bias) * tension +
                    (x3-x2) * (1-bias) * tension;
        
        double a0 =  2 * ttt - 3 * tt     + 1;
        double a1 =      ttt - 2 * tt + t    ;
        double a2 =      ttt -     tt        ;
        double a3 = -2 * ttt + 3 * tt        ;
        
        return a0*x1 + a1*m0 + a2*m1 + a3*x2;
    }

    /**
     * Hermite spline interpolation.
     *
     * @param x0 Point before point before sample
     * @param x1 Point before sample
     * @param x2 Point after sample
     * @param x3 Point after point after sample
     * @param t  Curve position to sample, parameterized between [0,1].
     * @param tension 1 is high, 0 is normal, -1 is low
     * @param bias    0 is even, positive is towards first segment, negative is towards other.
     * @param out     Holds interpolated point on return.
     */
    public static void hermite( double[] x0,
                                double[] x1,
                                double[] x2,
                                double[] x3,
                                double t,
                                double tension,
                                double bias,
                                double[] out )
    {
        final int dim = x0.length;
        final double tt  = t * t;
        final double ttt = tt * t;
        final double a0  =  2 * ttt - 3 * tt     + 1;
        final double a1  =      ttt - 2 * tt + t    ;
        final double a2  =      ttt -     tt        ;
        final double a3  = -2 * ttt + 3 * tt        ;

        tension = 0.5 - 0.5 * tension;

        for( int i = 0; i < dim; i++ ) {
            double m0  = (x1[i]-x0[i]) * (1+bias) * tension +
                         (x2[i]-x1[i]) * (1-bias) * tension;

            double m1 = (x2[i]-x1[i]) * (1+bias) * tension +
                        (x3[i]-x2[i]) * (1-bias) * tension;

            out[i] = a0 * x1[i] + a1 * m0 + a2 * m1 + a3 * x2[i];
        }
    }


    /**
     * Linear interpolation.
     *
     * @param x0 Value before sample
     * @param x1 Value after sample
     * @param t  Parameterized position to sample, parameterized between [0,1].
     * @return   interpolated mVal
     */
    public static float lerp( float x0, float x1, float t ) {
        return x0 * ( 1.0f - t ) + x1 * t;
    }


    private Interp() {}
    
}
