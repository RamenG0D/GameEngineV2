/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;

import com.Math3D.Tolerence;

import java.awt.geom.Path2D;
import static com.Math3D.Tolerence.approxEqual;
import static com.Math3D.Tolerence.approxZero;


/**
 * @author decamp
 */
public class Bezier2d implements Function1N {


    public static Bezier2d create( double[] pointsRef, int pointCount ) {
        if( pointCount < 2 ) {
            throw new IllegalArgumentException( "Bezier curves require at least two points." );
        }
        return new Bezier2d( pointsRef, pointCount );
    }


    public static Bezier2d create( double... coords ) {
        if( coords.length < 4 ) {
            throw new IllegalArgumentException( "Bezier curves require at least two points." );
        }
        return new Bezier2d( coords, coords.length / 2 );
    }



    private final double[] mC;
    private final int mCount;
    private final double[] mWork;


    private Bezier2d( double[] arr, int pointCount ) {
        mC = arr;
        mCount = pointCount;
        mWork = new double[pointCount * 2];
    }



    public void apply( double t, double[] out2x1 ) {
        double s = 1.0 - t;
        double[] w = mWork;
        double[] arr = mC;

        for( int i = 0; i < mCount - 1; i++ ) {
            for( int j = 0; j < (mCount - i - 1) * 2; j += 2 ) {
                w[j] = arr[j] * s + arr[j + 2] * t;
                w[j + 1] = arr[j + 1] * s + arr[j + 3] * t;
            }

            arr = w;
        }

        out2x1[0] = w[0];
        out2x1[1] = w[1];
    }


    public int pointCount() {
        return mCount;
    }


    public void point( int idx, double[] out2x1 ) {
        out2x1[0] = mC[idx * 2];
        out2x1[1] = mC[idx * 2 + 1];
    }


    public double[] pointsRef() {
        return mC;
    }


    public Bezier2d subdivide( double t0, double t1 ) {
        boolean swap = false;

        if( t1 < t0 ) {
            swap = true;
            double tt = t0;
            t0 = t1;
            t1 = tt;
        }

        Bezier2d ret;

        if( Tolerence.approxEqual( t1, 1.0 ) ) {
            if( Tolerence.approxZero( t0, 1.0 ) ) {
                ret = new Bezier2d( mC.clone(), mCount );
            } else {
                ret = subdivideGreater( t0 );
            }

        } else if( Tolerence.approxZero( t1, 1.0 ) ) {
            if( Tolerence.approxZero( t0, 1.0 ) ) {
                ret = subdivideLess( t1 );
            } else {
                ret = subdivideGreater( t0 );
                ret = ret.subdivideLess( -t0 / (1.0 - t0) );
            }

        } else {
            if( Tolerence.approxZero( t0, 1.0 ) ) {
                ret = subdivideLess( t1 );
            } else {
                ret = subdivideLess( t1 );
                ret = ret.subdivideGreater( t0 / t1 );
            }
        }

        if( swap ) {
            for( int i = 0; i < mCount; i += 2 ) {
                int j = mCount * 2 - 2 - i;
                double px = ret.mC[i];
                double py = ret.mC[i + 1];
                ret.mC[i] = ret.mC[j];
                ret.mC[i + 1] = ret.mC[j + 1];
                ret.mC[j] = px;
                ret.mC[j + 1] = py;
            }
        }

        return ret;
    }


    public Path2D toShape() {
        Path2D path = new Path2D.Double();
        appendTo( path, true );
        return path;
    }


    public void appendTo( Path2D path, boolean moveToStart ) {
        if( moveToStart ) {
            path.moveTo( mC[0], mC[1] );
        }

        switch( mCount ) {
        case 2:
            path.lineTo( mC[2], mC[3] );
            break;

        case 3:
            path.quadTo( mC[2], mC[3], mC[4], mC[5] );
            break;

        case 4:
            path.curveTo( mC[2], mC[3], mC[4], mC[5], mC[6], mC[7] );
            break;

        default:
            int breaks = mCount - 3;

            for( int i = 0; i < breaks; i++ ) {
                subdivide( (double)i / breaks, (double)(i + 1) / breaks ).appendTo( path, false );
            }
            break;
        }
    }



    /**
     * @return true iff this Bezier is capable of solving for T.
     */
    public boolean isSolvable() {
        return mCount <= 3;
    }

    /**
     * Finds parameter <tt>t</tt> for a specified value of x. Equivalent to
     * calling solveTFromX(x, 0.5);
     * 
     * @param x
     *            Desired <tt>x</tt> coordinate.
     * @return <tt>t</tt> value where the x-coordinate of this Bezier reaches
     *         <tt>x</tt>. Possibly NaN.
     * @throws UnsupportedOperationException
     *             if <code>isSolvable() == false</code>.
     */
    public double solveTFromX( double x ) {
        return solveTFromX( x, 0.5 );
    }

    /**
     * Finds parameter <tt>t</tt> for a specified value of x.
     * 
     * @param x
     *            Desired <tt>x</tt> coordinate.
     * @param nearestT
     *            In case there are multiple valid values of T, the one closest
     *            to <tt>nearestT</tt> will be returned.
     * @return <tt>t</tt> value where the x-coordinate of this Bezier reaches
     *         <tt>x</tt>.
     * @throws UnsupportedOperationException
     *             if <code>isSolvable() == false</code>.
     */
    public double solveTFromX( double x, double nearestT ) {
        switch( mCount ) {
        case 2:
            return solveLineT( mC[0], mC[2], x, nearestT );

        case 3:
            return solveQuadT( mC[0], mC[2], mC[4], x, nearestT );
        }

        throw new UnsupportedOperationException( "This bezier is not solvable." );
    }

    /**
     * Finds parameter <tt>t</tt> for a specified value of y. Equivalent to
     * calling solveTFromY(y, 0.5);
     * 
     * @param y
     *            Desired <tt>y</tt> coordinate.
     * @return <tt>t</tt> value where the y-coordinate of this Bezier reaches
     *         <tt>y</tt>. Possibly NaN.
     * @throws UnsupportedOperationException
     *             if <code>isSolvable() == false</code>.
     */
    public double solveTFromY( double y ) {
        return solveTFromY( y, 0.5 );
    }

    /**
     * Finds parameter <tt>t</tt> for a specified value of y.
     * 
     * @param y
     *            Desired <tt>y</tt> coordinate.
     * @param nearestT
     *            In case there are multiple valid values of T, the one closest
     *            to <tt>nearestT</tt> will be returned.
     * @return <tt>t</tt> value where the x-coordinate of this Bezier reaches
     *         <tt>y</tt>.
     * @throws UnsupportedOperationException
     *             if <code>isSolvable() == false</code>.
     */
    public double solveTFromY( double y, double nearestT ) {
        switch( mCount ) {
        case 2:
            return solveLineT( mC[1], mC[3], y, nearestT );

        case 3:
            return solveQuadT( mC[1], mC[3], mC[5], y, nearestT );
        }

        throw new UnsupportedOperationException( "This bezier is not solvable." );
    }



    private Bezier2d subdivideLess( double t ) {
        final double s = 1.0 - t;
        double[] work = mWork;
        double[] cp = new double[mCount * 2];
        double[] temp = mC;

        cp[0] = temp[0];
        cp[1] = temp[1];

        for( int i = 0; i < mCount - 1; i++ ) {
            for( int j = 0; j < (mCount - i - 1) * 2; j += 2 ) {
                work[j] = temp[j] * s + temp[j + 2] * t;
                work[j + 1] = temp[j + 1] * s + temp[j + 3] * t;
            }

            cp[i * 2 + 2] = work[0];
            cp[i * 2 + 3] = work[1];
            temp = work;
        }

        return new Bezier2d( cp, mCount );
    }


    private Bezier2d subdivideGreater( double t ) {
        final double s = 1.0 - t;
        double[] work = mWork;
        double[] cp = new double[mCount * 2];
        double[] temp = mC;

        cp[mCount * 2 - 2] = temp[mCount * 2 - 2];
        cp[mCount * 2 - 1] = temp[mCount * 2 - 1];

        for( int i = 0; i < mCount - 1; i++ ) {
            for( int j = 0; j < (mCount - i - 1) * 2; j += 2 ) {
                work[j] = temp[j] * s + temp[j + 2] * t;
                work[j + 1] = temp[j + 1] * s + temp[j + 3] * t;
            }

            cp[(mCount - i) * 2 - 4] = work[(mCount - i) * 2 - 4];
            cp[(mCount - i) * 2 - 3] = work[(mCount - i) * 2 - 3];
            temp = work;
        }

        return new Bezier2d( cp, mCount );

    }


    private static double solveLineT( double p0, double p1, double x, double nearestT ) {
        double div = p1 - p0;
        if( !Tolerence.approxZero( div, x - p0 ) ) {
            return (x - p0) / div;
        }
        if( Tolerence.approxEqual( p0, x ) ) {
            return nearestT;
        }
        return Double.NaN;
    }


    private static double solveQuadT( double p0, double p1, double p2, double x, double nearestT ) {
        double a = p0 - 2.0 * p1 + p2;
        
        if( Tolerence.approxZero( a, 1.0 ) ) {
            return solveLineT( p0, p2, x, nearestT );
        }

        double b = 2.0 * p1 - 2.0 * p0;
        double c = p0 - x;

        double v = b * b - 4.0 * a * c;
        if( v < 0 ) {
            return Double.NaN;
        }

        v = Math.sqrt( v );

        double t0 = (-b - v) / (2.0 * a);
        double t1 = (-b + v) / (2.0 * a);

        return (Math.abs( t0 - nearestT ) <= Math.abs( t1 - nearestT )) ? t0 : t1;
    }

}
