/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.Random;


/**
 * Array functions.
 *
 * @author decamp
 */
public final class ArrayUtils {

    
    public static float[] wrap( float... vals ) {
        return vals;
    }

    
    public static double[] wrap( double... vals ) {
        return vals;
    }

    
    public static float[] toFloats( double... vals ) {
        float[] ret = new float[ vals.length ];
        for( int i = 0; i < vals.length; i++ ) {
            ret[i] = (float)vals[i];
        }
        return ret;
    }
    
    
    public static double[] toDoubles( float... vals ) {
        double[] ret = new double[ vals.length ];
        for( int i = 0; i < vals.length; i++ ) {
            ret[i] = vals[i];
        }
        return ret;
    }
    
    
    public static void put( float[] src, float[] dst ) {
        System.arraycopy( src, 0, dst, 0, src.length );
    }

    
    public static void put( double[] src, double[] dst ) {
        System.arraycopy( src, 0, dst, 0, src.length );
    }
    
    
    public static void put( float[] src, int srcOff, float[] dst, int dstOff, int len ) {
        System.arraycopy( src, srcOff, dst, dstOff, len );
    }
    
        
    public static void put( double[] src, int srcOff, double[] dst, int dstOff, int len ) {
        System.arraycopy( src, srcOff, dst, dstOff, len );
    }
    
    
    public static void put( float[] src, double[] dst ) {
        for( int i = 0; i < src.length; i++ ) {
            dst[i] = src[i];
        }
    }
    
    
    public static void put( float[] src, int srcOff, double[] dst, int dstOff, int len ) {
        for( int i = 0; i < len; i++ ) {
            dst[i+dstOff] = src[i+srcOff];
        }
    }

    
    public static void put( double[] src, float[] dst ) {
        for( int i = 0; i < src.length; i++ ) {
            dst[i] = (float)src[i];
        }
    }
    
    
    public static void put( double[] src, int srcOff, float[] dst, int dstOff, int len ) {
        for( int i = 0; i < len; i++ ) {
            dst[i+dstOff] = (float)src[i+srcOff];
        }
    }    
    
    
    public static void mult( float sa, float[] a ) {
        mult( sa, a, 0, a.length );
    }
    
    
    public static void mult( double sa, double[] a ) {
        mult( sa, a, 0, a.length );
    }

    
    public static void mult( float sa, float[] a, int off, int len ) {
        for( int i = off; i < off + len; i++ ) {
            a[i] *= sa;
        }
    }
    
    
    public static void mult( double sa, double[] a, int off, int len ) {
        for( int i = off; i < off + len; i++ ) {
            a[i] *= sa;
        }
    }
    
    
    public static void mult( float[] a, float[] b, float[] out ) {
        mult( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void mult( double[] a, double[] b, double[] out ) {
        mult( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void mult( float[] a, int aOff, float[] b, int bOff, int len, float[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] * b[bOff+i];
        }
    }
    
    
    public static void mult( double[] a, int aOff, double[] b, int bOff, int len, double[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] * b[bOff+i];
        }
    }
    
    
    public static void add( float ta, float[] a ) {
        add( ta, a, 0, a.length );
    }


    public static void add( double ta, double[] a ) {
        add( ta, a, 0, a.length );
    }

    
    public static void add( float ta, float[] a, int off, int len ) {
        for( int i = off; i < off + len; i++ ) {
            a[i] += ta;
        }
    }

    
    public static void add( double ta, double[] a, int off, int len ) {
        for( int i = off; i < off + len; i++ ) {
            a[i] += ta;
        }
    }
    
    
    public static void add( float[] a, float[] b, float[] out ) {
        add( a, 0, b, 0, a.length, out, 0 );
    }

    
    public static void add( double[] a, double[] b, double[] out ) {
        add( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void add( float[] a, int aOff, float[] b, int bOff, int len, float[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] + b[bOff+i];
        }
    }

    
    public static void add( double[] a, int aOff, double[] b, int bOff, int len, double[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] + b[bOff+i];
        }
    }
    
    
    public static float dot( float[] a, float[] b ) {
        return dot( a, 0, b, 0, a.length );
    }
    

    public static double dot( double[] a, double[] b ) {
        return dot( a, 0, b, 0, a.length );
    }
    
    
    public static float dot( float[] a, int aOff, float[] b, int bOff, int len ) {
        float sum = 0f;
        for( int i = 0; i < len; i++ ) {
            sum += a[aOff+i] * b[bOff+i];
        }
        return sum;
    }
    
    
    public static double dot( double[] a, int aOff, double[] b, int bOff, int len ) {
        double sum = 0f;
        for( int i = 0; i < len; i++ ) {
            sum += a[aOff+i] * b[bOff+i];
        }
        return sum;
    }

    
    public static void multAdd( float[] a, float scale, float add ) {
        multAdd( a, 0, a.length, scale, add );
    }
    

    public static void multAdd( double[] a, double scale, double add ) {
        multAdd( a, 0, a.length, scale, add );
    }

    
    public static void multAdd( float[] a, int off, int len, float scale, float add ) {
        for( int i = off; i < off + len; i++ ) {
            a[i] = a[i] * scale + add;
        }
    }
    
    
    public static void multAdd( double[] a, int off, int len, double scale, double add ) {
        for( int i = off; i < off + len; i++ ) {
            a[i] = a[i] * scale + add;
        }
    }
    
    
    public static void multAdd( float sa, float[] a, float sb, float[] b, float[] out ) {
        multAdd( sa, a, 0, sb, b, 0, a.length, out, 0 );
    }
    
    
    public static void multAdd( double sa, double[] a, double sb, double[] b, double[] out ) {
        multAdd( sa, a, 0, sb, b, 0, a.length, out, 0 );
    }
    
    
    public static void multAdd( float sa, float[] a, int offA, float sb, float[] b, int offB, int len, float[] out, int offOut ) {
        for( int i = 0; i < len; i++ ) {
            out[offOut+i] = sa * a[offA+i] + sb * b[offB+i];
        }
    }
    
    
    public static void multAdd( double sa, double[] a, int offA, double sb, double[] b, int offB, int len, double[] out, int offOut ) {
        for( int i = 0; i < len; i++ ) {
            out[offOut+i] = sa * a[offA+i] + sb * b[offB+i];
        }
    }
    
    
    public static void lerp( float[] a, float[] b, float p, float[] out ) {
        lerp( a, 0, b, 0, a.length, p, out, 0 );
    }

    
    public static void lerp( double[] a, double[] b, double p, double[] out ) {
        lerp( a, 0, b, 0, a.length, p, out, 0 );
    }
    
    
    public static void lerp( float[] a, int aOff, float[] b, int bOff, int len, float p, float[] out, int outOff ) { 
        final float q = 1.0f - p;
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = q * a[aOff+i] + p * b[bOff+i]; 
        }
    }


    public static void lerp( double[] a, int aOff, double[] b, int bOff, int len, double p, double[] out, int outOff ) { 
        final double q = 1.0 - p;
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = q * a[aOff+i] + p * b[bOff+i]; 
        }
    }
    
    
    public static float len( float... arr ) {
        return (float)Math.sqrt( lenSquared( arr, 0, arr.length ) );
    }
    
    
    public static double len( double... arr ) {
        return Math.sqrt( lenSquared( arr, 0, arr.length ) );
    }
    
    
    public static float len( float[] arr, int off, int len ) {
        return (float)Math.sqrt( lenSquared( arr, off, len ) );
    }
    
    
    public static double len( double[] arr, int off, int len ) {
        return Math.sqrt( lenSquared( arr, off, len ) );
    }
    
    
    public static float lenSquared( float... arr ) {
        return lenSquared( arr, 0, arr.length );
    }

    
    public static double lenSquared( double... arr ) {
        return lenSquared( arr, 0, arr.length );
    }

    
    public static float lenSquared( float[] arr, int off, int len ) {
        float sum = 0;
        for( int i = off; i < off + len; i++ ) {
            sum += arr[i] * arr[i];
        }
        
        return sum;
    }

    
    public static double lenSquared( double[] arr, int off, int len ) {
        double sum = 0;
        for( int i = off; i < off + len; i++ ) {
            sum += arr[i] * arr[i];
        }
        
        return sum;
    }
    
    
    public static void normalize( float[] arr ) {
        normalize( arr, 0, arr.length, 1f );
    }
    
    
    public static void normalize( double[] arr ) {
        normalize( arr, 0, arr.length, 1.0 );
    }
    
    
    public static void normalize( float[] a, int off, int len, float normLength ) {
        normLength /= len( a, off, len );
        for( int i = 0; i < len; i++ ) {
            a[i+off] *= normLength;
        }
    }
    
    
    public static void normalize( double[] a, int off, int len, double normLength ) {
        normLength /= len( a, off, len );
        for( int i = 0; i < len; i++ ) {
            a[i+off] *= normLength;
        }
    }
    
    
    public static int sum( int... arr ) {
        return sum( arr, 0, arr.length );
    }
    
    
    public static long sum( long... arr ) {
        return sum( arr, 0, arr.length );
    }
    
    
    public static float sum( float... arr ) {
        return sum( arr, 0, arr.length );
    }

    
    public static double sum( double... arr ) {
        return sum( arr, 0, arr.length );
    }
    
    
    public static int sum( int[] arr, int off, int len ) {
        int ret = 0;
        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        return ret;
    }
    

    public static long sum( long[] arr, int off, int len ) {
        long ret = 0;
        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        return ret;
    }
    
    
    public static float sum( float[] arr, int off, int len ) {
        float ret = 0.0f;
        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        return ret;
    }

    
    public static double sum( double[] arr, int off, int len ) {
        double ret = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        
        return ret;
    }

    
    public static float mean( float... arr ) {
        return mean( arr, 0, arr.length );
    }


    public static double mean( double... arr ) {
        return mean( arr, 0, arr.length );
    }

    
    public static float mean( float[] arr, int off, int len ) {
        float sum = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            sum += arr[i];
        }

        return sum / len;
    }

    
    public static double mean( double[] arr, int off, int len ) {
        double sum = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            sum += arr[i];
        }

        return sum / len;
    }


    public static float variance( float... arr ) {
        return variance( arr, 0, arr.length );
    }

    
    public static double variance( double... arr ) {
        return variance( arr, 0, arr.length );
    }


    public static float variance( float[] arr, int off, int len ) {
        return variance( arr, off, len, mean( arr, off, len ) );
    }


    public static double variance( double[] arr, int off, int len ) {
        return variance( arr, off, len, mean( arr, off, len ) );
    }

    
    public static float variance( float[] arr, int off, int len, float mean ) {
        float sum = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            float v = arr[i] - mean;
            sum += v * v;
        }

        return sum / len;
    }


    public static double variance( double[] arr, int off, int len, double mean ) {
        double sum = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            double v = arr[i] - mean;
            sum += v * v;
        }

        return sum / len;
    }
    
    
    public static float min( int... arr ) {
        return min( arr, 0, arr.length );
    }

    
    public static double min( long... arr ) {
        return min( arr, 0, arr.length );
    }
    

    public static float min( float... arr ) {
        return min( arr, 0, arr.length );
    }

    
    public static double min( double... arr ) {
        return min( arr, 0, arr.length );
    }

    
    public static int min( int[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Integer.MAX_VALUE;
        }
        
        int ret = arr[off];
        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < ret ) {
                ret = arr[i];
            }
        }
        return ret;
    }

    
    public static long min( long[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Long.MAX_VALUE;
        }

        long ret = arr[off];
        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < ret ) {
                ret = arr[i];
            }
        }
        return ret;
    }


    public static float min( float[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Float.NaN;
        }

        float ret = arr[off];

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < ret ) {
                ret = arr[i];
            }
        }

        return ret;
    }

    
    public static double min( double[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Double.NaN;
        }

        double ret = arr[off];

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < ret ) {
                ret = arr[i];
            }
        }

        return ret;
    }


    public static int max( int... arr ) {
        return max( arr, 0, arr.length );
    }

    
    public static long max( long... arr ) {
        return max( arr, 0, arr.length );
    }
    
    
    public static float max( float... arr ) {
        return max( arr, 0, arr.length );
    }

    
    public static double max( double... arr ) {
        return max( arr, 0, arr.length );
    }


    public static int max( int[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Integer.MIN_VALUE;
        }

        int ret = arr[off];
        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] > ret ) {
                ret = arr[i];
            }
        }
        return ret;
    }


    public static long max( long[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Long.MIN_VALUE;
        }

        long ret = arr[off];
        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] > ret ) {
                ret = arr[i];
            }
        }
        return ret;
    }

    
    public static float max( float[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Float.NaN;
        }

        float ret = arr[off];
        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] > ret ) {
                ret = arr[i];
            }
        }
        return ret;
    }


    public static double max( double[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Double.NaN;
        }

        double ret = arr[off];
        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] > ret ) {
                ret = arr[i];
            }
        }
        return ret;
    }

    
    public static int[] range( int... arr ) {
        return range( arr, 0, arr.length );
    }

    
    public static long[] range( long... arr ) {
        return range( arr, 0, arr.length );
    }

    
    public static float[] range( float... arr ) {
        return range( arr, 0, arr.length );
    }

    
    public static double[] range( double... arr ) {
        return range( arr, 0, arr.length );
    }


    public static int[] range( int[] arr, int off, int len ) {
        int[] ret = new int[2];
        range( arr, off, len, ret );
        return ret;
    }

    
    public static long[] range( long[] arr, int off, int len ) {
        long[] ret = new long[2];
        range( arr, off, len, ret );
        return ret;
    }
    
    
    public static float[] range( float[] arr, int off, int len ) {
        float[] ret = new float[2];
        range( arr, off, len, ret );
        return ret;
    }

    
    public static double[] range( double[] arr, int off, int len ) {
        double[] ret = new double[2];
        range( arr, off, len, ret );
        return ret;
    }

    
    public static void range( int[] arr, int[] out2x1 ) {
        range( arr, 0, arr.length, out2x1 );
    }


    public static void range( long[] arr, long[] out2x1 ) {
        range( arr, 0, arr.length, out2x1 );
    }
    

    public static void range( float[] arr, float[] out2x1 ) {
        range( arr, 0, arr.length, out2x1 );
    }


    public static void range( double[] arr, double[] out2x1 ) {
        range( arr, 0, arr.length, out2x1 );
    }


    public static void range( int[] arr, int off, int len, int[] out2x1 ) {
        if( len <= 0 ) {
            out2x1[0] = Integer.MAX_VALUE;
            out2x1[1] = Integer.MIN_VALUE;
            return;
        }

        int min = arr[off];
        int max = min;

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < min ) {
                min = arr[i]; 
            }
            if( arr[i] > max ) {
                max = arr[i];
            }
        }

        out2x1[0] = min;
        out2x1[1] = max;
    }
    

    public static void range( long[] arr, int off, int len, long[] out2x1 ) {
        if( len <= 0 ) {
            out2x1[0] = Long.MAX_VALUE;
            out2x1[1] = Long.MIN_VALUE;
            return;
        }

        long min = arr[off];
        long max = min;

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < min ) {
                min = arr[i]; 
            }
            if( arr[i] > max ) {
                max = arr[i];
            }
        }

        out2x1[0] = min;
        out2x1[1] = max;
    }
    

    public static void range( float[] arr, int off, int len, float[] out2x1 ) {
        if( len <= 0 ) {
            out2x1[0] = Float.NaN;
            out2x1[1] = Float.NaN;
            return;
        }

        float min = arr[off];
        float max = min;

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < min ) {
                min = arr[i]; 
            }
            if( arr[i] > max ) {
                max = arr[i];
            }
        }

        out2x1[0] = min;
        out2x1[1] = max;
    }
    

    public static void range( double[] arr, int off, int len, double[] out2x1 ) {
        if( len <= 0 ) {
            out2x1[0] = Double.NaN;
            out2x1[1] = Double.NaN;
            return;
        }

        double min = arr[off];
        double max = min;

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < min ) {
                min = arr[i]; 
            }
            if( arr[i] > max ) {
                max = arr[i];
            }
        }

        out2x1[0] = min;
        out2x1[1] = max;
    }


    public static void fitRange( float[] arr, int off, int len, float min, float max ) {
        float[] range = range( arr, off, len );
        fitRange( arr, off, len, range[0], range[1], min, max );
    }
    
    
    public static void fitRange( double[] arr, int off, int len, double min, double max ) {
        double[] range = range( arr, off, len );
        fitRange( arr, off, len, range[0], range[1], min, max );
    }
    
   
    public static void fitRange( float[] arr, int off, int len, float inMin, float inMax, float outMin, float outMax ) {
        float scale = ( outMax - outMin) / ( inMax - inMin);
        if( Float.isNaN( scale ) ) {
            scale = 0.0f;
        }

        float add = outMin - inMin * scale;

        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }
    

    public static void fitRange( double[] arr, int off, int len, double inMin, double inMax, double outMin, double outMax ) {
        double scale = ( outMax - outMin) / ( inMax - inMin );
        if( Double.isNaN( scale ) ) {
            scale = 0.0f;
        }

        double add = outMin - inMin * scale;
        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }


    public static void clamp( float[] arr, int off, int len, float min, float max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            } else if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }


    public static void clamp( double[] arr, int off, int len, double min, double max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            } else if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }


    public static void clampMin( float[] arr, float min ) {
        clampMin( arr, 0, arr.length, min );
    }


    public static void clampMin( double[] arr, double min ) {
        clampMin( arr, 0, arr.length, min );
    }

    
    public static void clampMin( float[] arr, int off, int len, float min ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            }
        }
    }


    public static void clampMin( double[] arr, int off, int len, double min ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            }
        }
    }

    
    public static void clampMax( float[] arr, float max ) {
        clampMax( arr, 0, arr.length, max );
    }

    
    public static void clampMax( double[] arr, double max ) {
        clampMax( arr, 0, arr.length, max );
    }
    

    public static void clampMax( float[] arr, int off, int len, float max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }

    
    public static void clampMax( double[] arr, int off, int len, double max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }

    
    public static void pow( float[] arr, float exp ) {
        pow( arr, 0, arr.length, exp );
    }

    
    public static void pow( double[] arr, double exp ) {
        pow( arr, 0, arr.length, exp );
    }

    
    public static void pow( float[] arr, int off, int len, float exp ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] = (float)Math.pow( arr[i], exp );
        }
    }


    public static void pow( double[] arr, int off, int len, double exp ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] = Math.pow( arr[i], exp );
        }
    }

    
    public static void exp( float[] arr, float base ) {
        exp( arr, 0, arr.length, base );
    }

    
    public static void exp( double[] arr, double base ) {
        exp( arr, 0, arr.length, base );
    }
    
    
    public static void exp( float[] arr, int off, int len, float base ) {
        double s = 1.0 / Math.exp( base );
        for( int i = off; i < off + len; i++ ) {
            arr[i] = (float)( Math.exp( base ) * s );
        }
    }
    
    
    public static void exp( double[] arr, int off, int len, double base ) {
        double s = 1.0 / Math.exp( base );
        for( int i = off; i < off + len; i++ ) {
            arr[i] = ( Math.exp( base ) * s );
        }
    }
        
    
    public static void shuffle( Object[] arr, Random rand ) {
        shuffle( arr, 0, arr.length, rand );
    }
    
    
    public static void shuffle( Object[] arr, int off, int len, Random rand ) {
        for( int i = 0; i < len-1; i++ ) {
            int j = rand.nextInt( len - i );
            Object temp = arr[i+off];
            arr[i+off]  = arr[j+off];
            arr[j+off]  = temp;
        }
    }
    
    
    
    private ArrayUtils() {}


    
    /**
     * @deprecated Use transform range
     */
    public static void normalize( double[] arr, int off, int len, double min, double max ) {
        double[] range = range( arr, off, len );
        normalize( arr, off, len, range[0], range[1], min, max );
    }

    /**
     * @deprecated Use transform range
     */
    public static void normalize( double[] arr, int off, int len, double inMin, double inMax, double outMin, double outMax ) {
        double scale = ( outMax - outMin) / ( inMax - inMin );
        if( Double.isNaN( scale ) ) {
            scale = 0.0f;
        }

        double add = outMin - inMin * scale;

        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }
    
    /**
     * @deprecated Use transformRange
     */
    public static void normalize( float[] arr, int off, int len, float min, float max ) {
        float[] range = range( arr, off, len );
        normalize( arr, off, len, range[0], range[1], min, max );
    }

    /**
     * @deprecated Use transformRange
     */
    public static void normalize( float[] arr, int off, int len, float inMin, float inMax, float outMin, float outMax ) {
        float scale = ( outMax - outMin) / ( inMax - inMin);
        if( Float.isNaN( scale ) ) {
            scale = 0.0f;
        }

        float add = outMin - inMin * scale;

        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }
    
    /**
     * @deprecated
     */
    public static void mult( float[] arr, float scale ) {
        mult( arr, 0, arr.length, scale );
    }

    /**
     * @deprecated
     */
    public static void mult( double[] arr, double scale ) {
        mult( arr, 0, arr.length, scale );
    }
    
    /**
     * @deprecated
     */
    public static void mult( float[] arr, int off, int len, float scale ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] *= scale;
        }
    }
    
    /**
     * @deprecated
     */
    public static void mult( double[] arr, int off, int len, double scale ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] *= scale;
        }
    }

    /**
     * @deprecated
     */
    public static void add( float[] a, float ta ) {
        add( a, 0, a.length, ta );
    }
    
    /**
     * @deprecated
     */
    public static void add( float[] arr, int off, int len, float amount ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] += amount;
        }
    }

    /**
     * @deprecated
     */
    public static void add( double[] arr, double amount ) {
        add( arr, 0, arr.length, amount );
    }
    
    /**
     * @deprecated
     */
    public static void add( double[] arr, int off, int len, double amount ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] += amount;
        }
    }

    /**
     * @deprecated Use normalize( float[], int, int, float )
     */
    public static void normalize( float length, float[] a, int off, int len ) {
        length /= len( a, off, len );
        for( int i = 0; i < len; i++ ) {
            a[i+off] *= length;
        }
    }
    
    /**
     * @deprecated Use normalize( double[], int, int, double )
     */
    public static void normalize( double length, double[] a, int off, int len ) {
        length /= len( a, off, len );
        for( int i = 0; i < len; i++ ) {
            a[i+off] *= length;
        }
    }
    
}
