/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D;

import java.util.Arrays;
import java.util.NoSuchElementException;


/**
 * Methods for manipulating 2 and 3 dimensional Axis-Aligned-Bounding-Boxes.
 *
 * <p>All 2D Array boxes are stored as [x0, y0, x1, y1].
 *
 * <p>All 3D array boxes are stored as [x0, y0, z0, x1, y1, z2].
 * 
 * @author decamp
 */
public final class Box {

    //== BOX2 Functions ===========================

    public static void put( Box2 box, Box2 out ) {
        out.x0 = box.x0;
        out.y0 = box.y0;
        out.x1 = box.x1;
        out.y1 = box.y1;
    }


    public static float min( Box2 box, int dim ) {
        switch( dim ) {
        case 0: return box.x0;
        case 1: return box.y0;
        default: throw new NoSuchElementException();
        }
    }


    public static float max( Box2 box, int dim ) {
        switch( dim ) {
        case 0: return box.x1;
        case 1: return box.y1;
        default: throw new NoSuchElementException();
        }
    }


    public static float centX( Box2 box ) {
        return 0.5f * ( box.x0 + box.x1 );
    }


    public static float centY( Box2 box ) {
        return 0.5f * ( box.x0 + box.x1 );
    }


    public static float spanX( Box2 box ) {
        return box.x1 - box.x0;
    }


    public static float spanY( Box2 box ) {
        return box.y1 - box.y0;
    }

    /**
     * Translates box.
     */
    public static void translate( Box2 box, float tx, float ty, Box2 out ) {
        out.x0 = box.x0 + tx;
        out.y0 = box.y0 + ty;
        out.x1 = box.x1 + tx;
        out.y1 = box.y1 + ty;
    }

    /**
     * Translates box so that the center is at a specified point.
     */
    public static void setCenter( Box2 box, float cx, float cy, Box2 out ) {
        float dx = cx - centX( box );
        float dy = cy - centY( box );
        out.x0 = box.x0 + cx;
        out.y0 = box.y0 + cy;
        out.x1 = box.x1 + cx;
        out.y1 = box.y1 + cy;
    }

    /**
     * Scales all box values.
     */
    public static void scale( Box2 box, float sx, float sy, Box2 out ) {
        out.x0 = box.x0 * sx;
        out.y0 = box.y0 * sy;
        out.x1 = box.x1 * sx;
        out.y1 = box.y1 * sy;
    }

    /**
     * Scales size of box without changing center point.
     *
     * @param a    Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as {@code a}.
     */
    public static void inflate( Box2 a, float sx, float sy, Box2 out ) {
        float c;
        float d;
        c = 0.5f * ( a.x0 + a.x1 );
        d = ( a.x1 - a.x0 ) * sx * 0.5f;
        out.x0 = c - d;
        out.x1 = c + d;
        c = 0.5f * ( a.y0 + a.y1 );
        d = ( a.y1 - a.y0 ) * sy * 0.5f;
        out.y0 = c - d;
        out.y1 = c + d;
    }

    /**
     * Computes intersection between {@code a} and {@code b}.
     * If boxes do not overlap on a given dimension, then the output will
     * be located entirely within {@code a} on the side nearest to
     * {@code b} and will have zero size. For example, the calling
     * {@code intersect( { 0, 0, 1, 1 }, { 2, 0, 3, 0.5f }, out )}
     * will result in
     * {@code out = { 1, 0, 1, 0.5f }}.
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     * @return true iff boxes contain non-zero overlap.
     */
    public static boolean intersect( Box2 a, Box2 b, Box2 out ) {
        boolean nonZero = true;
        out.x0 = a.x0 >= b.x0 ? a.x0 :
                a.x1 >= b.x0 ? a.x1 : b.x0;
        out.x1 = a.x1 <= b.x1 ? a.x1 : b.x1;
        if( out.x0 >= out.x1 ) {
            out.x1 = out.x0;
            nonZero = false;
        }

        out.y0 = a.y0 >= b.y0 ? a.y0 :
                a.y1 >= b.y0 ? a.y1 : b.y0;
        out.y1 = a.y1 <= b.y1 ? a.y1 : b.y1;
        if( out.y0 >= out.y1 ) {
            out.y1 = out.y0;
            nonZero = false;
        }

        return nonZero;
    }

    /**
     * Computes pointUnion between {@code a} and {@code b}.
     * The pointUnion is also a box and may contain additional area,
     * but will be the smallest box that contains all of a and b.
     *
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     */
    public static void union( Box2 a, Box2 b, Box2 out ) {
        out.x0 = a.x0 <= b.x0 ? a.x0 : b.x0;
        out.y0 = a.y0 <= b.y0 ? a.y0 : b.y0;
        out.x1 = a.x1 >= b.x1 ? a.x1 : b.x1;
        out.y1 = a.y1 >= b.y1 ? a.y1 : b.y1;
    }

    /**
     * Computes pointUnion between a point {@code [x,y]} and {@code box}
     * and returns the smallest box that contains both.
     *
     * @param x   X-Coordinate of point.
     * @param y   Y-Coordinate of point.
     * @param box Box
     * @param out Output box. May be same object as {@code box}.
     */
    public static void union( float x, float y, Box2 box, Box2 out ) {
        out.x0 = x <= box.x0 ? x : box.x0;
        out.x1 = x >= box.x1 ? x : box.x1;
        out.y0 = y <= box.y0 ? y : box.y0;
        out.y1 = y >= box.y1 ? y : box.y1;
    }

    /**
     * Centers box {@code in} inside box {@code ref} and scales
     * uniformly (without affecting aspect) until the boxes share at least
     * two borders.
     *
     * @param in   Box to fit
     * @param ref  Reference box.
     * @param out  Receives output box. May be same object as {@code in} or {@code ref}.
     */
    public static void fit( Box2 in, Box2 ref, Box2 out ) {
        float w0 = spanX( in );
        float w1 = spanX( ref );
        float h0 = spanY( in );
        float h1 = spanY( ref );
        if( w0 * h1 > w1 * h0 ) {
            float h = w1 * h0 / w0;
            float m = (h1 - h) * 0.5f;
            out.y1 = ref.y0 + m + h;
            out.x1 = ref.x1;
            out.y0 = ref.y0 + m;
            out.x0 = ref.x0;
        } else {
            float w = h1 * w0 / h0;
            float m = (w1 - w) * 0.5f;
            out.y1 = ref.y1;
            out.x1 = ref.x0 + m + w;
            out.y0 = ref.y0;
            out.x0 = ref.x0 + m;
        }
    }

    /**
     * Creates box that has same size as {@code box}, but positioned to
     * be inside {@code ref}. If {@code box} is larger than
     * {@code ref} for a given dimension, it will be centered inside
     * {@code ref} for that dimension. If {@code box} is already
     * completely inside of {@code ref}, it will not be changed.
     *
     * @param box  Box to reposition.
     * @param ref  Box defining target space for {@code in}
     * @param out  Holds output. May be same object as in or ref.
     */
    public static void clamp( Box2 box, Box2 ref, Box2 out ) {
        float d0;
        float d1;
        d0 = spanX( box );
        d1 = spanX( ref );
        if( d0 > d1 ) {
            float cx = centX( ref );
            out.x0 = cx - 0.5f * d0;
            out.x1 = cx + 0.5f * d0;
        } else if( box.x0 < ref.x0 ) {
            out.x0 = ref.x0;
            out.x1 = ref.x0 + d0;
        } else if( box.x1 > ref.x1 ) {
            out.x0 = ref.x1 - d0;
            out.x1 = ref.x1;
        } else {
            out.x0 = box.x0;
            out.x1 = box.x1;
        }

        d0 = spanY( box );
        d1 = spanY( ref );
        if( d0 > d1 ) {
            float cy = centY( ref );
            out.y0 = cy - 0.5f * d0;
            out.y1 = cy + 0.5f * d0;
        } else if( box.y0 < ref.y0 ) {
            out.y0 = ref.y0;
            out.y1 = ref.y0 + d0;
        } else if( box.y1 > ref.y1 ) {
            out.y0 = ref.y1 - d0;
            out.y1 = ref.y1;
        } else {
            out.y0 = box.y0;
            out.y1 = box.y1;
        }
    }

    /**
     * Tests if a point is inside given box.
     * Each dimension of box is treated as half-open interval.
     *
     * @return true iff x and y are contained by box.
     */
    public static boolean contains( Box2 box, float x, float y ) {
        return x >= box.x0 &&
               y >= box.y0 &&
               x <  box.x1 &&
               y <  box.y1;
    }

    /**
     * Maps a point in model coordinates to box coordinates,
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     */
    public static void modelToBox( float x, float y, Box2 box, Vector2 out ) {
        out.x = ( x - box.x0 ) / ( box.x1 - box.x0 );
        out.y = ( y - box.y0 ) / ( box.y1 - box.y0 );
    }

    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel( float x, float y, Box2 box, Vector2 out ) {
        out.x = x * ( box.x1 - box.x0 ) + box.x0;
        out.y = y * ( box.y1 - box.y0 ) + box.y0;
    }

    /**
     * Performs linear mapping of some coordinate in a space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     */
    public static void mapPoint( float x, float y, float z, Box2 srcDomain, Box2 dstDomain, Vector2 out ) {
        out.x = ( x - srcDomain.x0 ) / ( srcDomain.x1 - srcDomain.x0 ) * ( dstDomain.x1 - dstDomain.x0 ) + dstDomain.x0;
        out.y = ( y - srcDomain.y0 ) / ( srcDomain.y1 - srcDomain.y0 ) * ( dstDomain.y1 - dstDomain.y0 ) + dstDomain.y0;
    }

    /**
     * Performs linear mapping of a Box in a space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     *
     * @param in        Input box
     * @param srcDomain Box defining domain of input coordinate.
     * @param dstDomain Box defining destination domain (codomain).
     * @param out       On return, holds mapped box. May be same is in.
     */
    public static void mapBox( Box2 in, Box2 srcDomain, Box2 dstDomain, Box2 out ) {
        float sx = ( dstDomain.x1 - dstDomain.x0 ) / ( srcDomain.x1 - srcDomain.x0 );
        float sy = ( dstDomain.y1 - dstDomain.y0 ) / ( srcDomain.y1 - srcDomain.y0 );
        float tx = dstDomain.x0 - srcDomain.x0 * sx;
        float ty = dstDomain.y0 - srcDomain.y0 * sy;
        out.x0 = sx * in.x0 + tx;
        out.y0 = sy * in.y0 + ty;
        out.x1 = sx * in.x1 + tx;
        out.y1 = sy * in.y1 + ty;
    }

    /**
     * Fixes box coordinates so that min values are {@code <=} max values.
     */
    public static void fix( Box2 box ) {
        if( box.x0 > box.x1 ) {
            float f = box.x0;
            box.x0 = box.x1;
            box.x1 = f;
        }
        if( box.y0 > box.y1 ) {
            float f = box.y0;
            box.y0 = box.y1;
            box.y1 = f;
        }
    }

    /**
     * @param points  array of length-3 vertices.
     * @param off     mOffset into points
     * @param len     number of points
     * @param out     length-6 array that to hold the pointUnion of the points on return.
     */
    public static void pointUnion( Vector2[] points, int off, int len, Box2 out ) {
        if( len == 0 ) {
            out.x0 = out.y0 = Float.NaN;
            out.x1 = out.y1 = Float.NaN;
            return;
        }

        Vector2 v = points[off];
        float minX = v.x, maxX = v.x;
        float minY = v.y, maxY = v.y;

        for( int i = 1; i < len; i++ ) {
            v = points[off + i];

            if( v.x < minX ) {
                minX = v.x;
            } else if( v.x > maxX ) {
                maxX = v.x;
            }

            if( v.y < minY ) {
                minY = v.y;
            } else if( v.y > maxY) {
                maxY = v.y;
            }
        }

        out.x0 = minX;
        out.y0 = minY;
        out.x1 = maxX;
        out.y1 = maxY;
    }


    public static String format( Box2 b ) {
        return Box3.class.getSimpleName() +
               String.format( Vector.FORMAT2, b.x0, b.y0 ) +
               String.format( Vector.FORMAT2, b.x1, b.y1 );
    }



    //== BOX3 Functions ===========================

    public static void put( Box3 box, Box3 out ) {
        out.x0 = box.x0;
        out.y0 = box.y0;
        out.z0 = box.z0;
        out.x1 = box.x1;
        out.y1 = box.y1;
        out.z1 = box.z1;
    }


    public static float min( Box3 box, int dim ) {
        switch( dim ) {
        case 0: return box.x0;
        case 1: return box.y0;
        case 2: return box.z0;
        default: throw new NoSuchElementException();
        }
    }


    public static float max( Box3 box, int dim ) {
        switch( dim ) {
        case 0: return box.x1;
        case 1: return box.y1;
        case 2: return box.z1;
        default: throw new NoSuchElementException();
        }
    }


    public static float centX( Box3 box ) {
        return 0.5f * ( box.x0 + box.x1 );
    }


    public static float centY( Box3 box ) {
        return 0.5f * ( box.x0 + box.x1 );
    }


    public static float centZ( Box3 box ) {
        return 0.5f * ( box.z0 + box.z1 );
    }


    public static float spanX( Box3 box ) {
        return box.x1 - box.x0;
    }


    public static float spanY( Box3 box ) {
        return box.y1 - box.y0;
    }


    public static float spanZ( Box3 box ) {
        return box.z1 - box.z0;
    }

    /**
     * Translates box.
     */
    public static void translate( Box3 box, float tx, float ty, float tz, Box3 out ) {
        out.x0 = box.x0 + tx;
        out.y0 = box.y0 + ty;
        out.z0 = box.z0 + tz;
        out.x1 = box.x1 + tx;
        out.y1 = box.y1 + ty;
        out.z1 = box.z1 + tz;
    }

    /**
     * Translates box so that the center is at a specified point.
     */
    public static void setCenter( Box3 box, float cx, float cy, float cz, Box3 out ) {
        float dx = cx - centX( box );
        float dy = cy - centY( box );
        float dz = cz - centZ( box );
        out.x0 = box.x0 + cx;
        out.y0 = box.y0 + cy;
        out.z0 = box.z0 + cz;
        out.x1 = box.x1 + cx;
        out.y1 = box.y1 + cy;
        out.z1 = box.z1 + cz;
    }

    /**
     * Scales all box values.
     */
    public static void scale( Box3 box, float sx, float sy, float sz, Box3 out ) {
        out.x0 = box.x0 * sx;
        out.y0 = box.y0 * sy;
        out.z0 = box.z0 * sz;
        out.x1 = box.x1 * sx;
        out.y1 = box.y1 * sy;
        out.z1 = box.z1 * sz;
    }

    /**
     * Scales size of box without changing center point.
     * 
     * @param a    Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as {@code a}.
     */
    public static void inflate( Box3 a, float sx, float sy, float sz, Box3 out ) {
        float c;
        float d;
        c = 0.5f * ( a.x0 + a.x1 );
        d = ( a.x1 - a.x0 ) * sx * 0.5f;
        out.x0 = c - d;
        out.x1 = c + d;
        c = 0.5f * ( a.y0 + a.y1 );
        d = ( a.y1 - a.y0 ) * sy * 0.5f;
        out.y0 = c - d;
        out.y1 = c + d;
        c = 0.5f * ( a.z0 + a.z1 );
        d = ( a.z1 - a.z0 ) * sz * 0.5f;
        out.z0 = c - d;
        out.z1 = c + d;
    }

    /**
     * Computes intersection between {@code a} and {@code b}.
     * If boxes do not overlap on a given dimension, then the output will
     * be located entirely within {@code a} on the side nearest to 
     * {@code b} and will have zero size. For example, the calling
     * {@code intersect( { 0, 0, 1, 1 }, { 2, 0, 3, 0.5f }, out )}
     * will result in
     * {@code out = { 1, 0, 1, 0.5f }}.
     * 
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b. 
     * @return true iff boxes contain non-zero overlap.
     */
    public static boolean intersect( Box3 a, Box3 b, Box3 out ) {
        boolean nonZero = true;
        out.x0 = a.x0 >= b.x0 ? a.x0 :
                    a.x1 >= b.x0 ? a.x1 : b.x0;
        out.x1 = a.x1 <= b.x1 ? a.x1 : b.x1;
        if( out.x0 >= out.x1 ) {
            out.x1 = out.x0;
            nonZero = false;
        }

        out.y0 = a.y0 >= b.y0 ? a.y0 :
                    a.y1 >= b.y0 ? a.y1 : b.y0;
        out.y1 = a.y1 <= b.y1 ? a.y1 : b.y1;
        if( out.y0 >= out.y1 ) {
            out.y1 = out.y0;
            nonZero = false;
        }

        out.z0 = a.z0 >= b.z0 ? a.z0 :
                    a.z1 >= b.z0 ? a.z1 : b.z0;
        out.z1 = a.z1 <= b.z1 ? a.z1 : b.z1;
        if( out.z0 >= out.z1 ) {
            out.z1 = out.z0;
            nonZero = false;
        }

        return nonZero;
    }

    /**
     * Computes pointUnion between {@code a} and {@code b}.
     * The pointUnion is also a box and may contain additional area,
     * but will be the smallest box that contains all of a and b.
     *
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     */
    public static void union( Box3 a, Box3 b, Box3 out ) {
        out.x0 = a.x0 <= b.x0 ? a.x0 : b.x0;
        out.y0 = a.y0 <= b.y0 ? a.y0 : b.y0;
        out.z0 = a.z0 <= b.z0 ? a.z0 : b.z0;
        out.x1 = a.x1 >= b.x1 ? a.x1 : b.x1;
        out.y1 = a.y1 >= b.y1 ? a.y1 : b.y1;
        out.z1 = a.z1 >= b.z1 ? a.z1 : b.z1;
    }

    /**
     * Computes pointUnion between a point {@code [x,y,z]} and {@code box}
     * and returns the smallest box that contains both.
     *
     * @param x   X-Coordinate of point.
     * @param y   Y-Coordinate of point.
     * @param z   Z-Coordinate of point.
     * @param box Box
     * @param out Output box. May be same object as {@code box}.
     */
    public static void union( float x, float y, float z, Box3 box, Box3 out ) {
        out.x0 = x <= box.x0 ? x : box.x0;
        out.x1 = x >= box.x1 ? x : box.x1;
        out.y0 = y <= box.y0 ? y : box.y0;
        out.y1 = y >= box.y1 ? y : box.y1;
        out.z0 = z <= box.z0 ? z : box.z0;
        out.z1 = z >= box.z1 ? z : box.z1;
    }

    /**
     * Creates box that has same size as {@code box}, but positioned to
     * be inside {@code ref}. If {@code box} is larger than
     * {@code ref} for a given dimension, it will be centered inside
     * {@code ref} for that dimension. If {@code box} is already
     * completely inside of {@code ref}, it will not be changed.
     *
     * @param box  Box to reposition.
     * @param ref  Box defining target space for {@code in}
     * @param out  Holds output. May be same object as in or ref.
     */
    public static void clamp( Box3 box, Box3 ref, Box3 out ) {
        float d0;
        float d1;
        d0 = spanX( box );
        d1 = spanX( ref );
        if( d0 > d1 ) {
            float cx = centX( ref );
            out.x0 = cx - 0.5f * d0;
            out.x1 = cx + 0.5f * d0;
        } else if( box.x0 < ref.x0 ) {
            out.x0 = ref.x0;
            out.x1 = ref.x0 + d0;
        } else if( box.x1 > ref.x1 ) {
            out.x0 = ref.x1 - d0;
            out.x1 = ref.x1;
        } else {
            out.x0 = box.x0;
            out.x1 = box.x1;
        }

        d0 = spanY( box );
        d1 = spanY( ref );
        if( d0 > d1 ) {
            float cy = centY( ref );
            out.y0 = cy - 0.5f * d0;
            out.y1 = cy + 0.5f * d0;
        } else if( box.y0 < ref.y0 ) {
            out.y0 = ref.y0;
            out.y1 = ref.y0 + d0;
        } else if( box.y1 > ref.y1 ) {
            out.y0 = ref.y1 - d0;
            out.y1 = ref.y1;
        } else {
            out.y0 = box.y0;
            out.y1 = box.y1;
        }

        d0 = spanZ( box );
        d1 = spanZ( ref );
        if( d0 > d1 ) {
            float cz = centZ( ref );
            out.z0 = cz - 0.5f * d0;
            out.z1 = cz + 0.5f * d0;
        } else if( box.z0 < ref.z0 ) {
            out.z0 = ref.z0;
            out.z1 = ref.z0 + d0;
        } else if( box.z1 > ref.z1 ) {
            out.z0 = ref.z1 - d0;
            out.z1 = ref.z1;
        } else {
            out.z0 = box.z0;
            out.z1 = box.z1;
        }
    }

    /**
     * Tests if a point is inside given box. 
     * Each dimension of box is treated as half-open interval.
     * 
     * @return true iff x and y are contained by box.
     */
    public static boolean contains( Box3 box, float x, float y, float z ) {
        return x >= box.x0 &&
               y >= box.y0 &&
               z >= box.z0 &&
               x <  box.x1 &&
               y <  box.y1 &&
               z <  box.z1;
    }

    /**
     * Maps a point in model coordinates to box coordinates, 
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     */
    public static void modelToBox( float x, float y, float z, Box3 box, Vector3 out ) {
        out.x = ( x - box.x0 ) / ( box.x1 - box.x0 );
        out.y = ( y - box.y0 ) / ( box.y1 - box.y0 );
        out.z = ( z - box.z0 ) / ( box.z1 - box.z0 );
    }

    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel( float x, float y, float z, Box3 box, Vector3 out ) {
        out.x = x * ( box.x1 - box.x0 ) + box.x0;
        out.y = y * ( box.y1 - box.y0 ) + box.y0;
        out.z = z * ( box.z1 - box.z0 ) + box.z0;
    }

    /**
     * Performs linear mapping of some coordinate in a space defined by 
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     */
    public static void mapPoint( float x, float y, float z, Box3 srcDomain, Box3 dstDomain, Vector3 out ) {
        out.x = ( x - srcDomain.x0 ) / ( srcDomain.x1 - srcDomain.x0 ) * ( dstDomain.x1 - dstDomain.x0 ) + dstDomain.x0;
        out.y = ( y - srcDomain.y0 ) / ( srcDomain.y1 - srcDomain.y0 ) * ( dstDomain.y1 - dstDomain.y0 ) + dstDomain.y0;
        out.z = ( z - srcDomain.z0 ) / ( srcDomain.z1 - srcDomain.z0 ) * ( dstDomain.z1 - dstDomain.z0 ) + dstDomain.z0;
    }

    /**
     * Performs linear mapping of a Box in a space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     * 
     * @param in        Input box
     * @param srcDomain Box defining domain of input coordinate. 
     * @param dstDomain Box defining destination domain (codomain). 
     * @param out       On return, holds mapped box. May be same is in.
     */
    public static void mapBox( Box3 in, Box3 srcDomain, Box3 dstDomain, Box3 out ) {
        float sx = ( dstDomain.x1 - dstDomain.x0 ) / ( srcDomain.x1 - srcDomain.x0 );
        float sy = ( dstDomain.y1 - dstDomain.y0 ) / ( srcDomain.y1 - srcDomain.y0 );
        float sz = ( dstDomain.z1 - dstDomain.z0 ) / ( srcDomain.z1 - srcDomain.z0 );
        float tx = dstDomain.x0 - srcDomain.x0 * sx;
        float ty = dstDomain.y0 - srcDomain.y0 * sy;
        float tz = dstDomain.z0 - srcDomain.z0 * sz;
        out.x0 = sx * in.x0 + tx;
        out.y0 = sy * in.y0 + ty;
        out.z0 = sz * in.z0 + tz;
        out.x1 = sx * in.x1 + tx;
        out.y1 = sy * in.y1 + ty;
        out.z1 = tz * in.z1 + tz;
    }

    /**
     * Fixes box coordinates so that min values are {@code <=} max values.
     */
    public static void fix( Box3 box ) {
        if( box.x0 > box.x1 ) {
            float f = box.x0;
            box.x0 = box.x1;
            box.x1 = f;
        }
        if( box.y0 > box.y1 ) {
            float f = box.y0;
            box.y0 = box.y1;
            box.y1 = f;
        }
        if( box.z0 > box.z1 ) {
            float f = box.z0;
            box.z0 = box.z1;
            box.z1 = f;
        }
    }

    /**
     * @param points  array of length-3 vertices.
     * @param off     mOffset into points
     * @param len     number of points
     * @param out     length-6 array that to hold the pointUnion of the points on return.
     */
    public static void pointUnion( Vector3[] points, int off, int len, Box3 out ) {
        if( len == 0 ) {
            out.x0 = out.y0 = out.z0 = Float.NaN;
            out.x1 = out.y1 = out.z1 = Float.NaN;
            return;
        }

        Vector3 v = points[off];
        float minX = v.x, maxX = v.x;
        float minY = v.y, maxY = v.y;
        float minZ = v.z, maxZ = v.z;

        for( int i = 1; i < len; i++ ) {
            v = points[off + i];

            if( v.x < minX ) {
                minX = v.x;
            } else if( v.x > maxX ) {
                maxX = v.x;
            }

            if( v.y < minY ) {
                minY = v.y;
            } else if( v.y > maxY) {
                maxY = v.y;
            }

            if( v.z < minZ ) {
                minZ = v.z;
            } else if( v.z > maxZ ) {
                maxZ = v.z;
            }
        }

        out.x0 = minX;
        out.y0 = minY;
        out.z0 = minZ;
        out.x1 = maxX;
        out.y1 = maxY;
        out.z1 = maxZ;
    }


    public static String format( Box3 b ) {
        return Box3.class.getSimpleName() +
               String.format( Vector.FORMAT3, b.x0, b.y0, b.z0 ) +
               String.format( Vector.FORMAT3, b.x1, b.y1, b.z1 );
    }



    //== DOUBLE[2] Functions ===========================

    /**
     * Translates box.
     */
    public static void translate2( double[] box, double tx, double ty, double[] out ) {
        out[0] = box[0] + tx;
        out[1] = box[1] + ty;
        out[2] = box[2] + tx;
        out[3] = box[3] + ty;
    }

    /**
     * Translates box so that the center is at a specified point.
     */
    public static void setCenter2( double[] box, double cx, double cy, double[] out ) {
        double dx = cx - ( box[0] + box[2] ) * 0.5f;
        double dy = cy - ( box[1] + box[3] ) * 0.5f;
        out[0] = box[0] + dx;
        out[1] = box[1] + dy;
        out[2] = box[2] + dx;
        out[3] = box[3] + dy;
    }

    /**
     * Scales all box values.
     */
    public static void scale2( double[] box, double sx, double sy, double[] out ) {
        out[0] = box[0] * sx;
        out[1] = box[1] * sy;
        out[2] = box[2] * sx;
        out[3] = box[3] * sy;
    }

    /**
     * Scales size of box without changing center point.
     *
     * @param a    Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as {@code a}.
     */
    public static void inflate2( double[] a, double sx, double sy, double[] out ) {
        double c, d;
        c = ( a[0] + a[2] ) * 0.5f;
        d = ( a[2] - a[0] ) * 0.5f * sx;
        out[0] = c - d;
        out[2] = c + d;
        c = ( a[1] + a[3] ) * 0.5f;
        d = ( a[3] - a[1] ) * 0.5f * sy;
        out[1] = c - d;
        out[3] = c + d;
    }

    /**
     * Computes intersection between {@code a} and {@code b}.
     * If boxes do not overlap on a given dimension, then the output will
     * be located entirely within {@code a} on the side nearest to
     * {@code b} and will have zero size. For example, the calling
     * {@code  intersect( { 0, 0, 1, 1 }, { 2, 0, 3, 0.5f }, out )}
     * will result in
     * {@code out = { 1, 0, 1, 0.5f }}.
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     * @return true iff boxes contain non-zero overlap.
     */
    public static boolean intersect2( double[] a, double[] b, double[] out ) {
        boolean nonzero = true;

        for( int i = 0; i < 2; i++ ) {
            final int j = i + 2;
            out[i] = a[i] >= b[i] ? a[i] :
                    a[j] >= b[i] ? a[j] : b[i];
            out[j] = a[j] <= b[j] ? a[j] : b[j];

            if( out[j] <= out[i] ) {
                out[j] = out[i];
                nonzero = false;
            }
        }

        return nonzero;
    }

    /**
     * Computes pointUnion between {@code a} and {@code b}.
     * The pointUnion is also a box and may contain additional area,
     * but will be the smallest box that contains all of a and b.
     *
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     */
    public static void union2( double[] a, double[] b, double[] out ) {
        out[0] = a[0] < b[0] ? a[0] : b[0];
        out[1] = a[1] < b[1] ? a[1] : b[1];
        out[2] = a[2] > b[2] ? a[2] : b[2];
        out[3] = a[3] > b[3] ? a[3] : b[3];
    }

    /**
     * Centers box {@code in} inside box {@code ref} and scales
     * uniformly (without affecting aspect) until the boxes share at least
     * two borders.
     *
     * @param in   Box to fit
     * @param ref  Reference box.
     * @param out  Receives output box. May be same object as {@code in} or {@code ref}.
     */
    public static void fit2( double[] in, double[] ref, double[] out ) {
        double w0 = ( in[2]  - in[0]  );
        double w1 = ( ref[2] - ref[0] );
        double h0 = ( in[3]  - in[1]  );
        double h1 = ( ref[3] - ref[1] );
        if( w0 * h1 > w1 * h0 ) {
            double h = w1 * h0 / w0;
            double m = (h1 - h) * 0.5f;
            out[3] = ref[1] + m + h;
            out[2] = ref[2];
            out[1] = ref[1] + m;
            out[0] = ref[0];
        } else {
            double w = h1 * w0 / h0;
            double m = (w1 - w) * 0.5f;
            out[3] = ref[3];
            out[2] = ref[0] + m + w;
            out[1] = ref[1];
            out[0] = ref[0] + m;
        }
    }

    /**
     * Creates box that has same size as {@code box}, but positioned to
     * be inside {@code ref}. If {@code box} is larger than
     * {@code ref} for a given dimension, it will be centered inside
     * {@code ref} for that dimension. If {@code box} is already
     * completely inside of {@code ref}, it will not be changed.
     *
     * @param box  Box to reposition.
     * @param ref  Box defining target space for {@code in}
     * @param out  Holds output. May be same object as in or ref.
     */
    public static void clamp2( double[] box, double[] ref, double[] out ) {
        for( int i = 0; i < 2; i++ ) {
            final int j = i + 2;
            final double d0 = box[j] - box[i];
            final double d1 = ref[j] - ref[i];
            if( d0 >= d1 ) {
                double x = ( ref[i] + ref[j] );
                out[i] = 0.5 * ( x - d0 );
                out[j] = 0.5 * ( x + d0 );
            } else if( box[i] <= ref[i] ) {
                out[i] = ref[i];
                out[j] = ref[i] + d0;
            } else if( box[j] >= ref[j] ) {
                out[i] = ref[j] - d0;
                out[j] = ref[j];
            } else {
                out[i] = box[i];
                out[j] = box[j];
            }
        }
    }

    /**
     * Tests if a point is inside given box.
     * Each dimension of box is treated as half-open interval.
     *
     * @return true iff x and y are contained by box.
     */
    public static boolean contains2( double[] box, double x, double y ) {
        return x >= box[0] && y >= box[1] && x < box[2] && y < box[3];
    }

    /**
     * Maps a point in model coordinates to box coordinates,
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     */
    public static void worldToBox2( double x, double y, double[] box, double[] outXY, int outOff ) {
        outXY[outOff  ] = ( x - box[0] ) / ( box[2] - box[0] );
        outXY[outOff+1] = ( y - box[1] ) / ( box[3] - box[1] );
    }

    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToWorld2( double x, double y, double[] box, double[] outXY, int outOff ) {
        outXY[outOff  ] = x * ( box[2] - box[0] ) + box[0];
        outXY[outOff+1] = y * ( box[3] - box[1] ) + box[1];
    }

    /**
     * Performs linear mapping of a coordinate in a space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     *
     * @param x         Input x-coordinate
     * @param y         Input y-coordinate
     * @param srcDomain Box defining domain of input coordinate.
     * @param dstDomain Box defining destination domain (codomain).
     * @param outXY     Array to hold resulting xy coordinates.
     * @param outOff    Offset into output array.
     */
    public static void mapPoint2( double x,
                                  double y,
                                  double[] srcDomain,
                                  double[] dstDomain,
                                  double[] outXY,
                                  int outOff )
    {
        outXY[outOff  ] = ( x - srcDomain[0] ) / ( srcDomain[2] - srcDomain[0] ) * ( dstDomain[2] - dstDomain[0] ) + dstDomain[0];
        outXY[outOff+1] = ( y - srcDomain[1] ) / ( srcDomain[3] - srcDomain[1] ) * ( dstDomain[3] - dstDomain[1] ) + dstDomain[1];
    }

    /**
     * Performs linear mapping of a Box2 in a space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     *
     * @param in        Input box
     * @param srcDomain Box defining domain of input coordinate.
     * @param dstDomain Box defining destination domain (codomain).
     * @param out       On return, holds mapped box. May be same as {@code in}.
     */
    public static void mapBox2( double[] in,
                                double[] srcDomain,
                                double[] dstDomain,
                                double[] out )
    {
        double sx = ( dstDomain[2] - dstDomain[0] ) / ( srcDomain[2] - srcDomain[0] );
        double sy = ( dstDomain[3] - dstDomain[1] ) / ( srcDomain[3] - srcDomain[1] );
        double tx = dstDomain[0] - srcDomain[0] * sx;
        double ty = dstDomain[1] - srcDomain[1] * sy;

        out[0] = sx * in[0] + tx;
        out[1] = sy * in[1] + ty;
        out[2] = sx * in[2] + tx;
        out[3] = sy * in[3] + ty;
    }

    /**
     * Fixes box coordinates so that min values are {@code <=} max values.
     */
    public static void fix2( double[] box ) {
        for( int i = 0; i < 2; i++ ) {
            final int j = i + 2;
            if( box[i] > box[j] ) {
                double f = box[i];
                box[i] = box[j];
                box[j] = f;
            }
        }
    }


    public static String format2( double[] v ) {
        return "Box2" +
               String.format( Vector.FORMAT2, v[0], v[1] ) +
               String.format( Vector.FORMAT2, v[2], v[3] );
    }



    //== DOUBLE[3] Functions ===========================

    /**
     * Translates box.
     */
    public static void translate3( double[] box, double tx, double ty, double tz, double[] out ) {
        out[0] = box[0] + tx;
        out[1] = box[1] + ty;
        out[2] = box[2] + tz;
        out[3] = box[3] + tx;
        out[4] = box[4] + ty;
        out[5] = box[5] + tz;
    }

    /**
     * Translates box so that the center is at a specified point.
     */
    public static void setCenter3( double[] box, double cx, double cy, double cz, double[] out ) {
        double dx = cx - 0.5 * ( box[0] + box[3] );
        double dy = cy - 0.5 * ( box[1] + box[4] );
        double dz = cz - 0.5 * ( box[2] + box[5] );
        out[0] = box[0] + dx;
        out[1] = box[1] + dy;
        out[2] = box[2] + dz;
        out[3] = box[3] + dx;
        out[4] = box[4] + dy;
        out[5] = box[5] + dz;
    }

    /**
     * Scales all box values.
     */
    public static void scale3( double[] box, double sx, double sy, double sz, double[] out ) {
        out[0] = box[0] * sx;
        out[1] = box[1] * sy;
        out[2] = box[2] * sz;
        out[3] = box[3] * sx;
        out[4] = box[4] * sy;
        out[5] = box[5] * sz;
    }

    /**
     * Scales size of box without changing center point.
     *
     * @param box  Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as {@code a}.
     */
    public static void inflate3( double[] box, double sx, double sy, double sz, double[] out ) {
        double c, d;
        c = ( box[0] + box[3] ) * 0.5;
        d = ( box[3] - box[0] ) * 0.5 * sx;
        out[0] = c - d;
        out[3] = c + d;
        c = ( box[1] + box[4] ) * 0.5;
        d = ( box[4] - box[1] ) * 0.5 * sy;
        out[1] = c - d;
        out[4] = c + d;
        c = ( box[2] + box[5] ) * 0.5;
        d = ( box[5] - box[2] ) * 0.5 * sz;
        out[2] = c - d;
        out[5] = c + d;
    }

    /**
     * Computes intersection between {@code a} and {@code b}.
     * If boxes do not overlap on a given dimension, then the output will
     * be located entirely within {@code a} on the side nearest to
     * {@code b} and will have zero size. For example, the calling
     * {@code clip( { 0, 0, 1, 1 }, { 2, 0, 3, 0.5f }, out )}
     * will result in
     * {@code out = { 1, 0, 1, 0.5f }}.
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     * @return true iff boxes contain non-zero overlap.
     */
    public static boolean intersect3( double[] a, double[] b, double[] out ) {
        boolean nonZero = true;
        for( int i = 0, j = 2; i < 3; j = i++ ) {
            out[i] = a[i] >= b[i] ? a[i] :
                     a[j] >= b[i] ? a[j] : b[i];
            out[j] = a[j] <= b[j] ? a[j] : b[j];
            if( out[j] <= out[i] ) {
                out[j] = out[i];
                nonZero = false;
            }
        }
        return nonZero;
    }

    /**
     * Computes pointUnion between {@code a} and {@code b}.
     * The pointUnion is also a box and may contain additional area,
     * but will be the smallest box that contains all of a and b.
     *
     *
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b.
     */
    public static void union3( double[] a, double[] b, double[] out ) {
        out[0] = a[0] <= b[0] ? a[0] : b[0];
        out[1] = a[1] <= b[1] ? a[1] : b[1];
        out[2] = a[2] <= b[2] ? a[2] : b[2];
        out[3] = a[3] >= b[3] ? a[3] : b[3];
        out[4] = a[4] >= b[4] ? a[4] : b[4];
        out[5] = a[5] >= b[5] ? a[5] : b[5];
    }

    /**
     * Creates box that has same size as {@code box}, but positioned to
     * be inside {@code ref}. If {@code box} is larger than
     * {@code ref} for a given dimension, it will be centered inside
     * {@code ref} for that dimension. If {@code box} is already
     * completely inside of {@code ref}, it will not be changed.
     *
     * @param box  Box to reposition.
     * @param ref  Box defining target space for {@code in}
     * @param out  Holds output. May be same object as in or ref.
     */
    public static void clamp3( double[] box, double[] ref, double[] out ) {
        for( int i = 0; i < 2; i++ ) {
            final int j = i + 3;
            final double d0 = box[j] - box[i];
            final double d1 = ref[j] - ref[i];
            if( d0 >= d1 ) {
                double x = ( ref[i] + ref[j] );
                out[i] = 0.5 * ( x - d0 );
                out[j] = 0.5 * ( x + d0 );
            } else if( box[i] <= ref[i] ) {
                out[i] = ref[i];
                out[j] = ref[i] + d0;
            } else if( box[j] >= ref[j] ) {
                out[i] = ref[j] - d0;
                out[j] = ref[j];
            } else {
                out[i] = box[i];
                out[j] = box[j];
            }
        }
    }

    /**
     * Tests if a point is inside box.
     * Each dimension of box is treated as half-open interval.
     *
     * @return true iff [x,y,z] lies inside box.
     */
    public static boolean contains3( double[] box, double x, double y, double z ) {
        return x >= box[0] && y >= box[1] && z >= box[2] &&
               x <  box[3] && y <  box[4] && z <  box[5];
    }

    /**
     * Maps a point in model coordinates to box coordinates,
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     */
    public static void modelToBox3( double x, double y, double z, double[] box, double[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = ( x - box[0] ) / ( box[3] - box[0] );
        outXYZ[1+outOff] = ( y - box[1] ) / ( box[4] - box[1] );
        outXYZ[2+outOff] = ( z - box[2] ) / ( box[5] - box[2] );
    }

    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel3( double x, double y, double z, double[] box, double[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = x * ( box[3] - box[0] ) + box[0];
        outXYZ[1+outOff] = y * ( box[4] - box[1] ) + box[1];
        outXYZ[2+outOff] = z * ( box[5] - box[2] ) + box[2];
    }

    /**
     * Performs linear mapping of a point in space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     * @param x         X-coordinate of input point.
     * @param y         Y-coordinate of input point.
     * @param z         Z-coordinate of input point.
     * @param srcDomain Box that defines domain of input coordinate.
     * @param dstDomain Box that defines destination domain (codomain).
     * @param outXYZ    Receives output point [x,y,z].
     * @param outOff    Offset into {@code outXYZ} to be used.
     */
    public static void mapPoint3( double x, double y, double z, double[] srcDomain, double[] dstDomain, double[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = ( x - srcDomain[0] ) / ( srcDomain[3] - srcDomain[0] ) * ( dstDomain[3] - dstDomain[0] ) + dstDomain[0];
        outXYZ[1+outOff] = ( y - srcDomain[1] ) / ( srcDomain[4] - srcDomain[1] ) * ( dstDomain[4] - dstDomain[1] ) + dstDomain[1];
        outXYZ[2+outOff] = ( z - srcDomain[2] ) / ( srcDomain[5] - srcDomain[2] ) * ( dstDomain[5] - dstDomain[2] ) + dstDomain[2];
    }

    /**
     * Performs linear mapping of a Box3 in a space defined by
     * {@code srcDomain} into the coordinate space defined by {@code dstDomain}.
     *
     * @param in        Input box
     * @param srcDomain Box defining domain of input coordinate.
     * @param dstDomain Box defining destination domain (codomain).
     * @param out       On return, holds mapped box. May be same is in.
     */
    public static void mapBox3( double[] in, double[] srcDomain, double[] dstDomain, double[] out ) {
        double sx = ( dstDomain[3] - dstDomain[0] ) / ( srcDomain[3] - srcDomain[0] );
        double sy = ( dstDomain[4] - dstDomain[1] ) / ( srcDomain[4] - srcDomain[1] );
        double sz = ( dstDomain[5] - dstDomain[2] ) / ( srcDomain[5] - srcDomain[2] );
        double tx = dstDomain[0] - srcDomain[0] * sx;
        double ty = dstDomain[1] - srcDomain[1] * sy;
        double tz = dstDomain[2] - srcDomain[2] * sz;
        out[0] = sx * in[0] + tx;
        out[1] = sy * in[1] + ty;
        out[2] = sz * in[2] + tz;
        out[3] = sx * in[3] + tx;
        out[4] = sy * in[4] + ty;
        out[5] = tz * in[5] + tz;
    }

    /**
     * Fixes box coordinates so that min values are {@code <=} max values.
     */
    public static void fix3( double[] box ) {
        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            if( box[i] > box[j] ) {
                double f = box[i];
                box[i] = box[j];
                box[j] = f;
            }
        }
    }


    public static String format3( double[] v ) {
        return String.format( "box3[% 7.4f, % 7.4f, % 7.4f][% 7.4f,% 7.4f, % 7.4f]",
                              v[0], v[1], v[2], v[3], v[4], v[5] );
    }

    /**
     * @param points  array of length-3 vertices.
     * @param off     mOffset into points
     * @param len     number of points
     * @param out     length-6 array that to hold the pointUnion of the points on return.
     */
    public static void boundPoints3( double[][] points, int off, int len, double[] out ) {
        if( len == 0 ) {
            Arrays.fill( out, 0, 6, 0.0 );
            return;
        }

        double[] v = points[off];
        double minX = v[0], maxX = v[0];
        double minY = v[1], maxY = v[1];
        double minZ = v[2], maxZ = v[2];

        for( int i = 1; i < len; i++ ) {
            v = points[off + i];

            if( v[0] < minX ) {
                minX = v[0];
            } else if( v[0] > maxX ) {
                maxX = v[0];
            }

            if( v[1] < minY ) {
                minY = v[1];
            } else if( v[1] > maxY) {
                maxY = v[1];
            }

            if( v[2] < minZ ) {
                minZ = v[2];
            } else if( v[2] > maxZ ) {
                maxZ = v[2];
            }
        }

        out[0] = minX;
        out[1] = minY;
        out[2] = minZ;
        out[3] = maxX;
        out[4] = maxY;
        out[5] = maxZ;
    }



    private Box() {}

}
