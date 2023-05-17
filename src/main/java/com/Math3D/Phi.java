package com.Math3D;

/**
 * Class contains the implementation of: <br>
 * - Normal Cumulative Distribution Function <br>
 * - Inverse Normal Cumulative Distribution Function. <br>
 * - Error Function <br>
 * - Complimentary Error Function <br>
 * - Scaled Complimentary Error Function <br>
 * - Inverse Error Function <br>
 *
 * @author Sherali Karimov (sherali.karimov@proxima-tech.com)
 * 
 * The algorithm implemented in this class can be found here:
 * 
 * http://home.online.no/~pjacklam/notes/invnorm/
 *
 * (this file modified by Philip DeCamp)
 */
public class Phi {

    /**
     * Normal distribution function with 0 mean ond unit standard dev.
     * Same as a gaussian function. Ofter designated with Greek lower case phi.
     * 
     * @param x Difference from mean.
     * @return {@code (1/sqrt(2pi)) * e^(-1/2*x^2) }
     */
    public static double n( double x ) {
        return INV_SQRT2PI * Math.exp( -0.5 * x * x );
    }
    
    /**
     * Standard normal cumulative distribution function. (probit function).
     * 
     * @param x Any mVal
     * @return Integral of the standard normal distribution from -inf to x.
     */
    public static double ncdf( double x ) {
        return 0.5 * ( 1.0 + erf( x * INV_SQRT_2 ) );
    }

    /**
     * Inverse of the standard normal cumulative distribution function with refinement.
     * 
     * @param y Value between 0 and 1.
     * @return x such that normalCdf( x ) == y. 
     */
    public static double ncdfInv( double y ) {
        double z = ncdfInvFast( y );
        return refineNcdfInv( z, y );
    }
    
    /**
     * Inverse of the standard normal cumulative distribution function, without refinement.
     * 
     * @param y Value between 0 and 1.
     * @return x such that normalCdf( x ) == y. 
     */
    public static double ncdfInvFast( double y ) {
        if( y >= P_LOW ) {
            if( y <= P_HIGH ) {
                // Rational approximation for central region:
                double q = y - 0.5D;
                double r = q * q;
                return ( ( ( ( ( ICDF_A[0] * r + ICDF_A[1] ) * r + ICDF_A[2] ) * r + ICDF_A[3] ) * r + ICDF_A[4] ) * r + ICDF_A[5] ) * q / 
                       ( ( ( ( ( ICDF_B[0] * r + ICDF_B[1] ) * r + ICDF_B[2] ) * r + ICDF_B[3] ) * r + ICDF_B[4] ) * r + 1 );
            } else if( y < 1 ) {
                // Rational approximation for upper region:
                double q = Math.sqrt( -2 * Math.log( 1 - y ) );
                return -( ( ( ( ( ICDF_C[0] * q + ICDF_C[1] ) * q + ICDF_C[2] ) * q + ICDF_C[3] ) * q + ICDF_C[4] ) * q + ICDF_C[5] ) / 
                          ( ( ( ( ICDF_D[0] * q + ICDF_D[1] ) * q + ICDF_D[2] ) * q + ICDF_D[3] ) * q + 1 );
            } else if( y > 1 ) {
                return Double.NaN;
            } else {
                // y == 1.0
                return Double.POSITIVE_INFINITY;
            }
        } else if( y > 0 ) {
            // Rational approximation for lower region:
            double q = Math.sqrt( -2 * Math.log( y ) );
            return ( ( ( ( ( ICDF_C[0] * q + ICDF_C[1] ) * q + ICDF_C[2] ) * q + ICDF_C[3] ) * q + ICDF_C[4] ) * q + ICDF_C[5] ) / 
                     ( ( ( ( ICDF_D[0] * q + ICDF_D[1] ) * q + ICDF_D[2] ) * q + ICDF_D[3] ) * q + 1 );
        } else if( y < 0 ) {
            return Double.NaN;
        } else {
            // y == 0 
            return Double.NEGATIVE_INFINITY;
        }
    }
    
    /**
     * Error function. 
     * 
     * @param x Any mVal.
     * @return the integral of the Gaussian distribution function from 0 to x.
     */
    public static double erf( double x ) { 
        return calerf(x, 0); 
    }
    
    /**
     * Complementary error function.
     * 
     * @param x Any mVal.
     * @return the integral of the Gaussian distribution function from x to inf, or 1.0 - erf( x ).
     * 
     */
    public static double erfc( double x ) { 
        return calerf(x, 1); 
    }
    
    /**
     * Scaled complementary error function.
     * 
     * @param x Value
     * @return e^(x*x) * erfc( x )
     */
    public static double erfcx( double x ) { 
        return calerf( x, 2 ); 
    }
    
    /**
     * Inverse of the error function with refinement.
     * 
     * @param y Value
     * @return x such that erf( x ) == y.
     */
    public static double erfInvPrecise( double y ) {
        return INV_SQRT_2 * ncdfInv( 0.5 * ( y + 1.0 ) );
    }
    
    /**
     * Inverse of the error function.
     * 
     * @param y Value  
     * @return x such that erf( x ) == y.
     */
    public static double erfInvFast( double y ) {
        return INV_SQRT_2 * ncdfInvFast( 0.5 * ( y + 1.0 ) );
    }
    
    
        
    /* ********************************************
     * Original algorythm and Perl implementation can
     * be found at:
     * http://www.math.uio.no/~jacklam/notes/invnorm/index.html
     * Author:
     *  Peter J. Acklam
     *  jacklam@math.uio.no
     * ****************************************** */
    
    private static final double INV_SQRT_2  = 1.0 / Math.sqrt( 2.0 );
    private static final double SQRT_PI     = Math.sqrt( Math.PI );
    private static final double INV_SQRT2PI = 1.0 / Math.sqrt( Math.PI * 2.0 );
    private static final double P_LOW       = 0.02425;
    private static final double P_HIGH      = 1.0 - P_LOW;
     
    private static final double THRESHOLD = 0.46875;
    
    // Coefficients in rational approximations.
    private static final double ICDF_A[] = { -3.969683028665376e+01,  
                                              2.209460984245205e+02,
                                             -2.759285104469687e+02,  
                                              1.383577518672690e+02,
                                             -3.066479806614716e+01,  
                                              2.506628277459239e+00 };

    private static final double ICDF_B[] = { -5.447609879822406e+01,
                                              1.615858368580409e+02,
                                             -1.556989798598866e+02,  
                                              6.680131188771972e+01,
                                             -1.328068155288572e+01 };

    private static final double ICDF_C[] = { -7.784894002430293e-03, 
                                             -3.223964580411365e-01,
                                             -2.400758277161838e+00, 
                                             -2.549732539343734e+00,
                                              4.374664141464968e+00,  
                                              2.938163982698783e+00 };

    private static final double ICDF_D[] = { 7.784695709041462e-03,  
                                             3.224671290700398e-01,
                                             2.445134137142996e+00,  
                                             3.754408661907416e+00 };
    
    //------------------------------------------------------------------
    //  Coefficients for approximation to  erf  in first interval
    //------------------------------------------------------------------
    private static final double ERF_A[] = { 3.16112374387056560E00, 
                                            1.13864154151050156E02,
                                            3.77485237685302021E02, 
                                            3.20937758913846947E03,
                                            1.85777706184603153E-1 };

    private static final double ERF_B[] = { 2.36012909523441209E01, 
                                            2.44024637934444173E02,
                                            1.28261652607737228E03, 
                                            2.84423683343917062E03 };

    //------------------------------------------------------------------
    //  Coefficients for approximation to  erfc  in second interval
    //------------------------------------------------------------------
    private static final double ERF_C[] = { 5.64188496988670089E-1, 
                                            8.88314979438837594E0,
                                            6.61191906371416295E01, 
                                            2.98635138197400131E02,
                                            8.81952221241769090E02, 
                                            1.71204761263407058E03,
                                            2.05107837782607147E03, 
                                            1.23033935479799725E03,
                                            2.15311535474403846E-8 };

    private static final double ERF_D[] = { 1.57449261107098347E01,
                                            1.17693950891312499E02,
                                            5.37181101862009858E02,
                                            1.62138957456669019E03,
                                            3.29079923573345963E03,
                                            4.36261909014324716E03,
                                            3.43936767414372164E03,
                                            1.23033935480374942E03 };

    //------------------------------------------------------------------
    //  Coefficients for approximation to  erfc  in third interval
    //------------------------------------------------------------------
    private static final double ERF_P[] = { 3.05326634961232344E-1,
                                            3.60344899949804439E-1,
                                            1.25781726111229246E-1,
                                            1.60837851487422766E-2,
                                            6.58749161529837803E-4,
                                            1.63153871373020978E-2 };

    private static final double ERF_Q[] = { 2.56852019228982242E00,
                                            1.87295284992346047E00,
                                            5.27905102951428412E-1,
                                            6.05183413124413191E-2,
                                            2.33520497626869185E-3 };

    /* **************************************
     * Hardware dependant constants were calculated
     * on Dell "Dimension 4100":
     * - Pentium III 800 MHz
     * running Microsoft Windows 2000
     * ************************************* */
//    private static final double X_MIN   = Double.MIN_VALUE;
//    private static final double X_INF   = Double.MAX_VALUE;
    private static final double X_NEG   = -9.38241396824444;
    private static final double X_SMALL = 1.110223024625156663E-16;
    private static final double X_BIG   = 9.194E0;
    private static final double X_HUGE  = 1 / ( 2 * Math.sqrt( X_SMALL ) );
    private static final double X_MAX   = 1 / ( SQRT_PI * Double.MIN_VALUE );

    /*******************************************
     * ORIGINAL FORTRAN version can be found at:
     * http://www.netlib.org/specfun/erf
     ********************************************
     C------------------------------------------------------------------
     C
     C   THIS PACKET COMPUTES THE ERROR AND COMPLEMENTARY ERROR FUNCTIONS
     C   FOR REAL ARGUMENTS  ARG.  IT CONTAINS TWO FUNCTION TYPE
     C   SUBPROGRAMS,  ERF  AND  ERFC  (OR  DERF  AND  DERFC),  AND ONE
     C   SUBROUTINE TYPE SUBPROGRAM,  CALERF.  THE CALLING STATEMENTS
     C   FOR THE PRIMARY ENTRIES ARE
     C
     C                   Y=ERF(X)     (OR   Y=DERF(X) )
     C   AND
     C                   Y=ERFC(X)    (OR   Y=DERFC(X) ).
     C
     C   THE ROUTINE  CALERF  IS INTENDED FOR INTERNAL PACKET USE ONLY,
     C   ALL COMPUTATIONS WITHIN THE PACKET BEING CONCENTRATED IN THIS
     C   ROUTINE.  THE FUNCTION SUBPROGRAMS INVOKE  CALERF  WITH THE
     C   STATEMENT
     C          CALL CALERF(ARG,RESULT,JINT)
     C   WHERE THE PARAMETER USAGE IS AS FOLLOWS
     C
     C      FUNCTION                     PARAMETERS FOR CALERF
     C       CALL              ARG                  RESULT          JINT
     C     ERF(ARG)      ANY REAL ARGUMENT         ERF(ARG)          0
     C     ERFC(ARG)     ABS(ARG) .LT. XMAX        ERFC(ARG)         1
     C
     C   THE MAIN COMPUTATION EVALUATES NEAR MINIMAX APPROXIMATIONS
     C   FROM "RATIONAL CHEBYSHEV APPROXIMATIONS FOR THE ERROR FUNCTION"
     C   BY W. J. CODY, MATH. COMP., 1969, PP. 631-638.  THIS
     C   TRANSPORTABLE PROGRAM USES RATIONAL FUNCTIONS THAT THEORETICALLY
     C       APPROXIMATE  ERF(X)  AND  ERFC(X)  TO AT LEAST 18 SIGNIFICANT
     C   DECIMAL DIGITS.  THE ACCURACY ACHIEVED DEPENDS ON THE ARITHMETIC
     C   SYSTEM, THE COMPILER, THE INTRINSIC FUNCTIONS, AND PROPER
     C   SELECTION OF THE MACHINE-DEPENDENT CONSTANTS.
     C
     C  AUTHOR: W. J. CODY
     C          MATHEMATICS AND COMPUTER SCIENCE DIVISION
     C          ARGONNE NATIONAL LABORATORY
     C          ARGONNE, IL 60439
     C
     C  LATEST MODIFICATION: JANUARY 8, 1985
     C
     C------------------------------------------------------------------
     */
    private static double calerf( double x, int type ) {
        double result = 0;
        double mag = Math.abs( x );
        double magMag;
        double xNum;
        double xDen;

        if( mag <= THRESHOLD ) {
            magMag = 0.0;
            if( mag > X_SMALL )
                magMag = mag * mag;
            xNum = ERF_A[4] * magMag;
            xDen = magMag;
            for( int i = 0; i < 3; i++ ) {
                xNum = (xNum + ERF_A[i]) * magMag;
                xDen = (xDen + ERF_B[i]) * magMag;
            }
            result = x * (xNum + ERF_A[3]) / (xDen + ERF_B[3]);
            if( type != 0 )
                result = 1 - result;
            if( type == 2 )
                result = Math.exp( magMag ) * result;
            return result;
        } else if( mag <= 4.0 ) {
            xNum = ERF_C[8] * mag;
            xDen = mag;
            for( int i = 0; i < 7; i++ ) {
                xNum = (xNum + ERF_C[i]) * mag;
                xDen = (xDen + ERF_D[i]) * mag;
            }
            result = ( xNum + ERF_C[7] ) / ( xDen + ERF_D[7] );
            if( type != 2 ) {
                magMag = Math.round( mag * 16.0D ) / 16.0D;
                double del = (mag - magMag) * (mag + magMag);
                result = Math.exp( -magMag * magMag ) * Math.exp( -del ) * result;
            }
        } else {
            result = 0.0;
            if( mag >= X_BIG && (type != 2 || mag >= X_MAX) ) {
                    
            } else if( mag >= X_BIG && mag >= X_HUGE ) {
                result = SQRT_PI / mag;
            } else {
                magMag = 1.0 / (mag * mag);
                xNum = ERF_P[5] * magMag;
                xDen = magMag;
                for( int i = 0; i < 4; i++ ) {
                    xNum = (xNum + ERF_P[i]) * magMag;
                    xDen = (xDen + ERF_Q[i]) * magMag;
                }
                result = magMag * ( xNum + ERF_P[4] ) / ( xDen + ERF_Q[4] );
                result = ( SQRT_PI - result ) / mag;
                if( type != 2 ) {
                    magMag = Math.round( mag * 16.0 ) / 16.0;
                    double del = (mag - magMag) * (mag + magMag);
                    result = Math.exp( -magMag * magMag ) * Math.exp( -del ) * result;
                }
            }
        }

        if( type == 0 ) {
            result = (0.5 - result) + 0.5;
            if( x < 0 )
                result = -result;
        } else if( type == 1 ) {
            if( x < 0 ) {
                result = 2.0D - result;
            }
        } else {
            if( x < 0 ) {
                if( x < X_NEG ) {
                    result = Double.POSITIVE_INFINITY;
                } else {
                    magMag = Math.round( x * 16.0D ) / 16.0D;
                    double del = (x - magMag) * (x + magMag);
                    mag = Math.exp( magMag * magMag ) * Math.exp( del );
                    result = (mag + mag) - result;
                }
            }
        }
        
        return result;
    }
    
    /*****************************************************
     * Refining algorythm is based on Halley rational method
     * for finding roots of equations as described at:
     * http://www.math.uio.no/~jacklam/notes/invnorm/index.html
     * by:
     *  Peter J. Acklam
     *  jacklam@math.uio.no
     *************************************************** */
    private static double refineNcdfInv( double result, double input ) {
        if( input > 0 && input < 1) {
            double err = ncdf( result ) - input;
            double u = err * Math.sqrt( 2.0 * Math.PI ) * Math.exp( 0.5 * result * result );
            result = result - u / ( 1.0 + result * u / 2.0 );
        }
        
        return result;
    }
    
}
