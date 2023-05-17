/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;


/**
 * Power-Of-Two utilities.
 * 
 * @author Philip DeCamp
 */
public class Pots {

    /**
     * @return true iff val is a power-of-two.
     */
    public static boolean isPot( int val ) {
        return val > 0 && ( val & ( val - 1 ) ) == 0;
    }

    /**
     * @return the smallest power-of-two that is greater than <tt>val</tt>.
     */
    public static int higherPot( int val ) {
        if( val <= 0 ) {
            return 1;
        }
        val = (val >>  1) | val;
        val = (val >>  2) | val;
        val = (val >>  4) | val;
        val = (val >>  8) | val;
        val = (val >> 16) | val;
        return val + 1;
    }

    /**
     * @return the smallest power-of-two that is greater-than-or-equal-to <tt>val</tt>.
     */
    public static int ceilPot( int val ) {
        if( val <= 0 ) {
            return 1;
        }
        return higherPot( val - 1 );
    }

    /**
     * @return the largest power-of-two that is less than <tt>val</tt>.
     */
    public static int lowerPot( int val ) {
        if( val <= 1 ) {
            return 1;
        }
        return higherPot( val - 1 ) >> 1;
    }

    /**
     * @return the largest power-of-two that is less-than-or-equal-to <tt>val</tt>.
     */
    public static int floorPot( int val ) {
        if( val <= 1 ) {
            return 1;
        }
        return higherPot( val ) >> 1;
    }

    /**
     * Binary log function that rounds down.
     *
     * @return {@code val > 0}: the integer less-than-or-equal-to log_2( val ) <br>
     *         otherwise : 0
     */
    public static int floorLog2( int val ) {
        if( val <= 0 ) {
            return 0;
        }
        return 31 - Integer.numberOfLeadingZeros( val );
    }

    /**
     * Binary log function that rounds up.
     *
     * @return {@code val > 0}: the integer less-than-or-equal-to log_2( val ) <br>
     *         otherwise : 0
     */
    public static int ceilLog2( int val ) {
        if( val <= 0 ) {
            return 0;
        }
        return 32 - Integer.numberOfLeadingZeros( val - 1 );
    }



    /**
     * @return true iff val is a power-of-two.
     */
    public static boolean isPot( long val ) {
        return val > 0 && ( val & ( val - 1 ) ) == 0;
    }

    /**
     * @return the smallest power-of-two that is greater than <tt>val</tt>.
     */
    public static long higherPot( long val ) {
        if( val <= 0 ) {
            return 1;
        }
        val = (val >>  1) | val;
        val = (val >>  2) | val;
        val = (val >>  4) | val;
        val = (val >>  8) | val;
        val = (val >> 16) | val;
        val = (val >> 32) | val;
        return val + 1;
    }

    /**
     * @return the smallest power-of-two that is greater-than-or-equal-to <tt>val</tt>.
     */
    public static long ceilPot( long val ) {
        if( val <= 0 ) {
            return 1;
        }
        return higherPot( val - 1 );
    }

    /**
     * @return the largest power-of-two that is less than <tt>val</tt>.
     */
    public static long lowerPot( long val ) {
        if( val <= 1 ) {
            return 1;
        }
        return higherPot( val - 1 ) >> 1;
    }

    /**
     * @return the largest power-of-two that is less-than-or-equal-to <tt>val</tt>.
     */
    public static long floorPot( long val ) {
        if( val <= 1 ) {
            return 1;
        }
        return higherPot( val ) >> 1;
    }

    /**
     * Binary log function that rounds answer down.
     *
     * @return {@code val > 0}: the integer less-than-or-equal-to log_2( val ) <br>
     *         otherwise : 0
     */
    public static long floorLog2( long val ) {
        if( val <= 0 ) {
            return 0;
        }
        return 63 - Long.numberOfLeadingZeros( val );
    }

    /**
     * Binary log function that rounds answer up.
     *
     * @return {@code val > 0}: the integer less-than-or-equal-to log_2( val ) <br>
     *         otherwise : 0
     */
    public static long ceilLog2( long val ) {
        if( val < 0 ) {
            return 0;
        }
        return 64 - Long.numberOfLeadingZeros( val - 1 );
    }

}
