/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

/**
 * Tolerance functions. Help for protecting against rounding errors.
 *  
 * @author Philip DeCamp  
 */
public final class Tolerence {
    
    public static final double EPS           = 0x0.0000000000001p-1;    // 64-bit machine epsilon
    public static final double ABS_ERR       = 0x0.0000000000001p-1017; // Double.MIN_VALUE * 32.0
    public static final double REL_ERR       = 0x0.0000000000001p4;     // EPS * 32.0
    public static final double SQRT_ABS_ERR  = Math.sqrt( ABS_ERR );
    
    public static final float  FEPS           = 0x0.000002p-1f;          // 32-bit machine epsilon
    public static final float  FABS_ERR       = 0x0.000002p-121f;        // Float.MIN_VALUE * 32.0f
    public static final float  FREL_ERR       = 0x0.000002p4f;           // FEPS * 32.0f
    public static final float  FSQRT_ABS_ERR  = (float)Math.sqrt( FABS_ERR ); 
    
    
    /**
     * Equivalent to {@code approxError( a, b, REL_ERR, ABS_ERR );}
     */
    public static boolean approxEqual( double a, double b ) {
        return approxEqual( a, b, REL_ERR, ABS_ERR );
    }
    
    /**
     * Computes if two numbers are approximately equal. 
     * This method will compute both absolute and relative differences, and
     * return true if either difference is below a threshold.
     * <p>
     * The absolute error is simply the magnitude of the difference between {@code a} and {@code b}:
     * {@code Math.abs(a-b)}.
     * The absolute error is normally very small. The default mVal is ABS_ERR.
     * <p>
     * The relative error is the absolute error divided by the larger of the two magnitudes of {@code a} and
     * {@code b}: {@code abs(a-b)/max(abs(a),abs(b))}. A functional maxRelError should be no smaller than
     * Tolerance.EPS.  The default mVal is REL_ERR.
     * 
     * @param a           Input value
     * @param b           Input value
     * @param maxRelError Maximum relative error.
     * @param maxAbsError Maximum absolute error.
     * @return true if {@code a} and {@code b} are approximately equal.
     */
    public static boolean approxEqual( double a, double b, double maxRelError, double maxAbsError ) {
        double diff = a - b;
        
        if( diff < 0.0 ) {
            diff = -diff;
        }        
        
        if( diff < maxAbsError ) {
            return true;
        }
        
        if( a < 0.0 ) {
            a = -a;
        }
        
        if( b < 0.0 ) {
            b = -b;
        }
        
        if( a > b ) {
            return diff < a * maxRelError;
        } else {
            return diff < b * maxRelError;
        }
    }

    /**
     * Compares two numbers with allowance for errors.
     * 
     * @param a A mVal.
     * @param b A mVal.
     * @return 0 if a approximately equals b, -1 if a is smaller, 1 if a is greater.
     * 
     * @see #approxEqual
     */
    public static int approxComp( double a, double b ) {
        return approxEqual( a, b, REL_ERR, ABS_ERR ) ? 0 : ( a < b ? -1 : 1 );
    }
    
    /**
     * Compares two numbers with allowance for errors.
     * 
     * @param a A mVal.
     * @param b A mVal.
     * @param maxRelErr   Maximum relative error
     * @param maxAbsErr   Maximum absolute error.
     * @return 0 if a approximately equals b, -1 if a is smaller, 1 if a is greater.
     * 
     * @see #approxEqual
     */
    public static int approxComp( double a, double b, double maxRelErr, double maxAbsErr ) {
        return approxEqual( a, b, maxRelErr, maxAbsErr ) ? 0 : ( a < b ? -1 : 1 );
    }
    
    /**
     * Equivalent to {@code approxZero( v, ref, REL_ERR, ABS_ERR );}
     */
    public static boolean approxZero( double v, double ref ) {
        return approxZero( v, ref, REL_ERR, ABS_ERR ); 
    }
    
    /**
     * Determines if some mVal is effectively zero compared to some reference.
     * This method can be called when determining if values {@code ref} and 
     * {@code v} can be combined in a meaningful way, for example, before
     * computing {@code ref / v}. 
     * <p>
     * Like {@code approxEqual}, this method makes to checks. The first check
     * ensures that {@code abs(v)} is above some minimum absolute error. 
     * The second ensures that {@code abs(v / ref)} is above some minimum relative error.
     * 
     * @param v       Some mVal
     * @param ref     A reference mVal that might be divided or multiplied by
     * @param relErr  Minimum ratio between abs(v) and abs(ref) for abs(v) to be considered non-zero.
     * @param absErr  Minimum mVal abs(v) must be to be considered non-zero.
     * @return true iff v is approximately equal to 0 relative to ref.
     */
    public static boolean approxZero( double v, double ref, double relErr, double absErr ) {
        if( v < absErr && -v < absErr ) {
            return true;
        }
        
        relErr *= ( ref >= 0.0 ? ref : -ref );
        return v < relErr && -v < relErr;
    }
    
    /**
     * Equivalent to {@code approxError( a, b, FREL_ERR, FABS_ERR );}
     */
    public static boolean approxEqual( float a, float b ) {
        return approxEqual( a, b, FREL_ERR, FABS_ERR );
    }
    
    /**
     * Computes if two numbers are approximately equal, protecting around rounding 
     * errors. This method will compute both absolute and relative differences, and
     * return true if either difference is below a threshold.
     * <p>
     * The absolute error is simply the magnitude of the difference between {@code a} and {@code b}:
     * {@code Math.abs(a-b)}.
     * The absolute error is normally very small. The default is FABS_ERR.
     * <p>
     * The relative error is the absolute error divided by the larger of the two magnitudes of {@code a} and
     * {@code b}: {@code abs(a-b)/max(abs(a),abs(b))}. A functional maxRelError should be no smaller than
     * Tolerance.FEPS. The default mVal is FREL_ERR.
     * 
     * @param a           Input value
     * @param b           Input value
     * @param maxRelError Maximum relative error.
     * @param maxAbsError Maximum absolute error.
     * @return true if {@code a} and {@code b} are approximately equal.
     */
    public static boolean approxEqual( float a, float b, float maxRelError, float maxAbsError ) {
        float diff = a - b;
        
        if( diff < 0.0f )
            diff = -diff;
        
        if( diff < maxAbsError )
            return true;
        
        if( a < 0.0f )
            a = -a;
        
        if( b < 0.0f )
            b = -b;
        
        if( a < b ) {
            diff /= b;
        }else{
            diff /= a;
        }
        
        return diff < maxRelError;
        
    }

    /**
     * Compares two numbers with allowance for errors.
     * 
     * @param a A mVal.
     * @param b A mVal.
     * @return 0 if a approximately equals b, -1 if a is smaller, 1 if a is greater.
     * 
     * @see #approxEqual
     */
    public static int approxComp( float a, float b ) {
        return approxEqual( a, b, FREL_ERR, FABS_ERR ) ? 0 : ( a < b ? -1 : 1 );
    }
    
    /**
     * Compares two numbers with allowance for errors.
     * 
     * @param a A mVal.
     * @param b A mVal.
     * @param maxRelErr   Maximum relative error
     * @param maxAbsErr   Maximum absolute error.
     * @return 0 if a approximately equals b, -1 if a is smaller, 1 if a is greater.
     * 
     * @see #approxEqual
     */
    public static int approxComp( float a, float b, float maxRelErr, float maxAbsErr ) {
        return approxEqual( a, b, maxRelErr, maxAbsErr ) ? 0 : ( a < b ? -1 : 1 );
    }
    
    /**
     * Equivalent to {@code approxZero( v, ref, REL_ERR, ABS_ERR );}
     */
    public static boolean approxZero( float v, float ref ) {
        return approxZero( v, ref, FREL_ERR, FABS_ERR ); 
    }
    
    /**
     * Determines if some mVal is effectively zero compared to some reference.
     * This method can be called when determining if values {@code ref} and 
     * {@code v} can be combined in a meaningful way, for example, before
     * computing {@code ref / v}. 
     * <p>
     * Like {@code approxEqual}, this method makes to checks. The first check
     * ensures that {@code abs(v)} is above some minimum absolute error. 
     * The second ensures that {@code abs(v / ref)} is above some minimum relative error.
     * 
     * @param v       Some mVal
     * @param ref     A reference mVal that might be divided or multiplied by
     * @param relErr  Minimum ratio between abs(v) and abs(ref) for abs(v) to be considered non-zero.
     * @param absErr  Minimum mVal abs(v) must be to be considered non-zero.
     * @return true iff v is approximately equal to 0 relative to ref.
     */
    public static boolean approxZero( float v, float ref, float relErr, float absErr ) {
        if( v < absErr && -v < absErr ) {
            return true;
        }
        
        relErr *= ( ref >= 0.0 ? ref : -ref );
        return v < relErr && -v < relErr;
    }

    
        
    private Tolerence() {}
    
    
    
    /**
     * Equivalent to {@code approxError( a, b, maxRelError, ABS_ERR );}
     */
    @Deprecated public static boolean approxEqual( double a, double b, double maxRelError ) {
        return approxEqual( a, b, maxRelError, ABS_ERR );
    }

    /**
     * Equivalent to {@code approxZero( v, ref, relErr, ABS_ERR )}
     * @param v      Some mVal.
     * @param ref    Another mVal to compare {@code v} with.
     * @param relErr Maximum relative error
     * @return true if v is approximately zero.
     */
    @Deprecated public static boolean approxZero( float v, float ref, float relErr ) {
        return approxZero( v, ref, relErr, FABS_ERR );
    }

    /**
     * @param v Some mVal
     * @return true iff {@code abs(v) < ABS_ERR}
     * 
     * @deprecated You will almost never be dealing with doubles with minimal exponents,
     * so you pretty much always need to judge zeroness against some reference.
     *  
     */
    @Deprecated public static boolean approxZero( double v ) {
        return v < ABS_ERR && -v < ABS_ERR;
    }

    /**
     * @param v Some mVal
     * @return true iff {@code abs(v) < ABS_ERR}
     * 
     * @deprecated You will almost never be dealing with doubles with minimal exponents,
     * so you pretty much always need to judge zeroness against some reference.
     */
    @Deprecated public static boolean approxZero( float v ) {
        return v < FABS_ERR && v < -FABS_ERR;
    }
    
    /**
     * Equivalent to {@code approxZero( v, ref, relErr, ABS_ERR )}
     */
    @Deprecated public static boolean approxZero( double v, double ref, double relErr ) {
        return approxZero( v, ref, relErr, ABS_ERR );
    }

    /**
     * Equivalent to {@code approxError( a, b, maxRelError, FABS_ERR );}
     */
    @Deprecated public static boolean approxEqual( float a, float b, float maxRelError ) {
        return approxEqual( a, b, maxRelError, FABS_ERR );
    }


    @Deprecated public static final double TOL = 1E-10;


    @Deprecated public static final float FTOL = 1E-5f;

}
