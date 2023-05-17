/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

/**
 * Angle Functions.
 *
 * @author Philip DeCamp  
 */
public class Angle {

    public static final double HALF_PI    = ( 0.5 * Math.PI );
    public static final double PI         = Math.PI;
    public static final double TWO_PI     = ( 2.0 * Math.PI );

    public static final double RAD_TO_DEG = PI / 180.0;
    public static final double DEG_TO_RAD = 180.0 / PI;

    /**
     * Normalizes an angle to fall within in range [-PI,PI].
     *
     * @param ang Angle in radians.
     * @return equivalent angle in [-PI, PI].
     * @see #normalize( double, double )
     */
    public static double normalize( double ang ) {
        return ang - TWO_PI * Math.floor( ( ang + Math.PI ) / TWO_PI );
    }

    /**
     * Normalizes an angle to fall within a 2PI range centered on a specified
     * mVal. For a range of [-PI, PI], call {@code normalize( ang, 0.0 ) }.
     * For a range of [0, 2PI], call {@code normalize( ang, PI ) }.
     *
     * @param ang  Angle in radians.
     * @param center center of the desired 2&pi; interval for the result
     * @return an equivalent angle between [center - PI, center + PI].
     */
    public static double normalize( double ang, double center ) {
        return ang - TWO_PI * Math.floor( ( ang + PI - center ) / TWO_PI );
    }

    /**
     * Computes an angle between [-PI, PI] given the sin and cosine of the angle.
     * This function exists because {@code cos( 0.5*PI ) } equals
     * {@code cos( -0.5*PI )}, so you can't recover the full angle from
     * {@link java.lang.Math#acos(double) } or {@link java.lang.Math#asin(double)}
     * alone.
     *
     * @param sinAng Sine of an angle.
     * @param cosAng Cosine of same angle.
     * @return the original angle
     */
    public static double asincos( double sinAng, double cosAng ) {
        return ( sinAng >= 0.0 ? Math.acos( cosAng ) : -Math.acos( cosAng ) );
    }
    
}
