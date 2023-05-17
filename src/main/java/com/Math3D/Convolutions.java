/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

/**
 * Convolution functions
 *
 * @author Philip DeCamp
 */
public class Convolutions {

    /** abcd|00000000 **/
    public static final int BORDER_ZERO          = 0;
    /** abcd|CCCCCCCC **/
    public static final int BORDER_CONSTANT      = 1;
    /** abcd|dddddddd **/
    public static final int BORDER_REPEAT        = 2;
    /** abcd|cbabcdcb  **/
    public static final int BORDER_REFLECT_INNER = 3;
    /** abcd|dcbaabcd **/
    public static final int BORDER_REFLECT_OUTER = 4;
    /** abcd|abcdabcd **/
    public static final int BORDER_WRAP          = 5;
    
    
    
    public static void gaussianKernel( float sigma, float[] out ) {
        double w    = ( out.length - 1 ) * 0.5;
        double coef = -1.0 / ( 2.0 * sigma * sigma );
        double sum  = 0.0;
        
        for( int i = 0; i < out.length; i++ ) {
            double d = i - w;
            out[i] = (float)Math.exp( coef * d * d );
            sum += out[i];
        }
        
        for( int i = 0; i < out.length; i++ ) {
            out[i] /= sum;
        }
        
    }
    
    
    public static void binomialKernel( float[] out ) {
        final int order   = out.length - 1;
        final int halfLen = out.length / 2;
        out[0] = 1f;
        
        for( int i = 0; i < order; i++ ) {
            out[i+1] = out[i] * 0.5f;
            for( int j = i; j > 0; j-- ) {
                out[j] = ( out[j] + out[j-1] ) * 0.5f;
            }
            out[0] *= 0.5f;
        }
        
        // Mirror params.
        for( int i = 0; i <= halfLen; i++ ) {
            out[order-i] = out[i];
        }
    }

    /**
     * Performs 2-dimensional convolution with a 1-dimensional kernel.
     * 
     * @param src            Source mVal matrix.
     * @param srcOff         Offset into source mVal array
     * @param w              Width of source matrix.
     * @param h              Height of source matrix.
     * @param xStride        Number of array indices between a sample and the sample to the left.
     * @param yStride        Number of array indices between a sample and the sample above.
     * @param kernel         Convolution kernel. 
     * @param borderBehavior Specifies how to treat borders. See BORDER_* constants. 
     * @param borderConstant Meaning depends on borderBehavior. Currently, only BORDER_CONSTANT uses this mVal.
     * @param dst            Array to hold output matrix. Must use same xStride and yStride as src.
     * @param dstOff         Offset into dst array.
     */
    public static void convolve1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            int borderBehavior,
            float borderConstant,
            float[] dst,
            int dstOff
    ) {
        switch( borderBehavior ) {
        case BORDER_ZERO: 
            convolveFillZero1( src, srcOff, w, h, xStride, yStride, kernel, dst, dstOff );
            break;
        case BORDER_CONSTANT:
            convolveFillConstant1( src, srcOff, w, h, xStride, yStride, kernel, borderConstant, dst, dstOff );
            break;
        case BORDER_REPEAT:
            convolveRepeat1( src, srcOff, w, h, xStride, yStride, kernel, dst, dstOff );
            break;
        case BORDER_REFLECT_INNER:
            convolveReflectInner1( src, srcOff, w, h, xStride, yStride, kernel, dst, dstOff );
            break;
        case BORDER_REFLECT_OUTER:
            convolveReflectOuter1( src, srcOff, w, h, xStride, yStride, kernel, dst, dstOff );
            break;
        case BORDER_WRAP:
            convolveWrap1( src, srcOff, w, h, xStride, yStride, kernel, dst, dstOff );
            break;
        default:
            throw new IllegalArgumentException( "Unknown border behavior: " + borderBehavior );
        }
    }
    
    
    public static void convolveFillZero1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            float[] dst,
            int dstOff
    ) {
        final int kernLen   = kernel.length;
        final int kernLeft  = kernLen / 2;
        final int kernRight = kernLen - kernLeft;

        for( int dstRow = 0; dstRow < h; dstRow++ ) {
            final int srcRowInd = dstRow * yStride + srcOff;
            final int dstRowInd = dstRow * yStride + dstOff;

            for( int dstCol = 0; dstCol < w; dstCol++ ) {
                float sum = 0f;
                int i0 = kernLeft  <= dstCol     ? 0       : kernLeft - dstCol; 
                int i1 = kernRight <= w - dstCol ? kernLen : kernLeft - dstCol + w;

                for( int kernInd = i0; kernInd < i1; kernInd++ ) {
                    int srcCol = dstCol + kernInd - kernLeft;
                    sum += src[ srcCol * xStride + srcRowInd ] * kernel[ kernInd ];
                }

                dst[ dstCol * xStride + dstRowInd ] = sum;
            }
        }
    }


    public static void convolveFillConstant1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            float fillValue,
            float[] dst,
            int dstOff
    ) {
        final int kernLen   = kernel.length;
        final int kernLeft  = kernLen / 2;
        final int kernRight = kernLen - kernLeft;

        for( int dstRow = 0; dstRow < h; dstRow++ ) {
            final int srcRowInd = dstRow * yStride + srcOff;
            final int dstRowInd = dstRow * yStride + dstOff;

            for( int dstCol = 0; dstCol < w; dstCol++ ) {
                float sum = 0f;
                int i0 = 0;
                int i1 = kernLen;

                if( kernLeft > dstCol ) {
                    i0 = kernLeft - dstCol;
                    for( int i = 0; i < i0; i++ ) {
                        sum += kernel[i] * fillValue;
                    }
                }

                if( kernRight > w - dstCol ) {
                    i1 = kernLeft - dstCol + w;
                    for( int i = i1; i < kernLen; i++ ) {
                        sum += kernel[i] * fillValue;
                    }
                }

                for( int kernInd = i0; kernInd < i1; kernInd++ ) {
                    int srcCol = dstCol + kernInd - kernLeft;
                    sum += src[ srcCol * xStride + srcRowInd ] * kernel[ kernInd ];
                }

                dst[ dstCol * xStride + dstRowInd ] = sum;
            }
        }
    }

    
    public static void convolveRepeat1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            float[] dst,
            int dstOff
    ) {
        final int kernLen   = kernel.length;
        final int kernLeft  = kernLen / 2;
        final int kernRight = kernLen - kernLeft;

        for( int dstRow = 0; dstRow < h; dstRow++ ) {
            final int srcRowInd = dstRow * yStride + srcOff;
            final int dstRowInd = dstRow * yStride + dstOff;

            for( int dstCol = 0; dstCol < w; dstCol++ ) {
                float sum = 0f;
                int i0 = 0;
                int i1 = kernLen;

                if( kernLeft > dstCol ) {
                    i0 = kernLeft - dstCol;
                    float v = src[ srcRowInd ];
                    for( int i = 0; i < i0; i++ ) {
                        sum += kernel[i] * v;
                    }
                }

                if( kernRight > w - dstCol ) {
                    i1 = kernLeft - dstCol + w;
                    float v = src[ srcRowInd + xStride * ( w - 1 ) ];
                    for( int i = i1; i < kernLen; i++ ) {
                        sum += kernel[i] * v;
                    }
                }

                for( int kernInd = i0; kernInd < i1; kernInd++ ) {
                    int srcCol = dstCol + kernInd - kernLeft;
                    sum += src[ srcCol * xStride + srcRowInd ] * kernel[ kernInd ];
                }

                dst[ dstCol * xStride + dstRowInd ] = sum;
            }
        }
    }
    
    
    public static void convolveReflectInner1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            float[] dst,
            int dstOff
    ) {
        final int kernLen = kernel.length;
        
        // Amount to add to x coordinate so that we never try to
        // take modulo of a negative number. The most we will subtract from the 
        // index is kernDim / 2, so we find the least multiple of 2*(w-1) that is 
        // greater than kernDim / 2.
        final int cycleLen     = w - 1;
        final int kernToSrcCol = ( 2 * cycleLen ) * ( ( kernLen / 2 + 2 * cycleLen - 1 ) / ( 2 * cycleLen ) ) - kernLen / 2;
        
        for( int dstRow = 0; dstRow < h; dstRow++ ) {
            final int srcRowInd = dstRow * yStride + srcOff;
            final int dstRowInd = dstRow * yStride + dstOff;
            
            for( int dstCol = 0; dstCol < w; dstCol++ ) {
                float sum = 0f;
                
                for( int kernInd = 0; kernInd < kernLen; kernInd++ ) {
                    int srcCol = dstCol + kernInd + kernToSrcCol;  
                            
                    // Has it wrapped around an even or odd number of times?
                    int cycle = ( srcCol / cycleLen ) & 1;
                    
                    // Depending on whether even or odd cycle,
                    // even: srcX = srcX % cycleWidth
                    // odd:  srcX = w - srcX % cycleWidth
                    srcCol = cycle * cycleLen + ( 1 - 2 * cycle ) * ( srcCol % cycleLen );
                    
                    // Multiply by kernel and add.
                    sum += src[ srcCol * xStride + srcRowInd ] * kernel[ kernInd ];
                }
                
                dst[ dstCol * xStride + dstRowInd ] = sum;
            }
        }
    }
    

    public static void convolveReflectOuter1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            float[] dst,
            int dstOff
    ) {
        final int kernLen = kernel.length;
        
        // Amount to add to x coordinate so that we never try to
        // take modulo of a negative number. The most we will subtract from the 
        // index is kernDim / 2, so we find the least multiple of 2*w that is 
        // greater than kernDim / 2.
        final int kernToSrcCol = ( 2 * w ) * ( ( kernLen / 2 + 2 * w - 1 ) / ( 2 * w ) ) - kernLen / 2;
        
        for( int dstRow = 0; dstRow < h; dstRow++ ) {
            final int srcRowInd = dstRow * yStride + srcOff;
            final int dstRowInd = dstRow * yStride + dstOff;
            
            for( int dstCol = 0; dstCol < w; dstCol++ ) {
                float sum = 0f;
                
                for( int kernInd = 0; kernInd < kernLen; kernInd++ ) {
                    int srcCol = dstCol + kernInd + kernToSrcCol;  
                            
                    // Has it wrapped around an even or odd number of times?
                    int cycle = ( srcCol / w ) & 1;
                    
                    // Depending on whether even or odd cycle,
                    // even: srcX = srcX % w
                    // odd:  srcX = ( w - 1 ) - srcX % w;
                    srcCol = cycle * ( w - 1 ) + ( 1 - 2 * cycle ) * ( srcCol % w );
                    
                    // Multiply by kernel and add.
                    sum += src[ srcCol * xStride + srcRowInd ] * kernel[ kernInd ];
                }
                
                dst[ dstCol * xStride + dstRowInd ] = sum;
            }
        }
    }

    
    public static void convolveWrap1(
            float[] src,
            int srcOff,
            int w,
            int h,
            int xStride,
            int yStride,
            float[] kernel,
            float[] dst,
            int dstOff
    ) {
        final int kernLen = kernel.length;

        // Amount to add to x coordinate so that we never try to
        // take modulo of a negative number. The most we will subtract from the 
        // index is kernDim / 2, so we find the least multiple of w that is 
        // greater than kernDim / 2.
        final int kernToSrcCol =   ( ( kernLen / 2 + w - 1 ) / w ) * w - kernLen / 2;
        
        for( int dstRow = 0; dstRow < h; dstRow++ ) {
            final int srcRowInd = dstRow * yStride + srcOff;
            final int dstRowInd = dstRow * yStride + dstOff;

            for( int dstCol = 0; dstCol < w; dstCol++ ) {
                final int srcColOff = dstCol + kernToSrcCol;
                float sum = 0f;
                
                for( int kernInd = 0; kernInd < kernLen; kernInd++ ) {
                    int srcCol = ( srcColOff + kernInd ) % w;  
                    sum += src[ srcCol * xStride + srcRowInd ] * kernel[ kernInd ];
                }

                dst[ dstCol * xStride + dstRowInd ] = sum;
            }
        }
    }

    
}
