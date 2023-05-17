/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import com.Math3D.*;

import java.io.Serializable;


/**
 * Axis-Aligned Bounding Box. Aabb objects are immutable.
 * 
 * @author Philip DeCamp
 */
@Deprecated public final class Aabb implements Cloneable, Serializable, Volume {

    private static final long serialVersionUID = 7863350136442702743L;


    /**
     * Create a new Aabb by specifying the min x,y,z point and size.
     * 
     * @return new Aabb object with the specified min positions and size.
     */
    public static Aabb fromBounds( float x0, float y0, float z0, float spanX, float spanY, float spanZ ) {
        return new Aabb( x0, y0, z0, x0 + spanX, y0 + spanY, z0 + spanZ );
    }

    /**
     * Create a new Aabb by specifying min and max values of each dim.
     *
     * @return an Aabb object with specified face positions.
     */
    public static Aabb fromEdges( float x0, float y0, float z0, float x1, float y1, float z1 ) {
        return new Aabb( x0, y0, z0, x1, y1, z1 );
    }

    /**
     * Creates a new Aabb by specifying center and size.
     *
     * @return a new Aabb
     */
    public static Aabb fromCenter( float centerX,
                                   float centerY,
                                   float centerZ,
                                   float spanX,
                                   float spanY,
                                   float spanZ )
    {
        centerX -= spanX * 0.5f;
        centerY -= spanY * 0.5f;
        centerZ -= spanZ * 0.5f;

        return new Aabb( centerX, centerY, centerZ, centerX + spanX, centerY + spanY, centerZ + spanZ );
    }


    public final Vector3 mMin = new Vector3();
    public final Vector3 mMax = new Vector3();

    private final float mX0;
    private final float mY0;
    private final float mZ0;
    private final float mX1;
    private final float mY1;
    private final float mZ1;


    private Aabb( float x0, float y0, float z0, float x1, float y1, float z1 ) {
        if( x0 <= x1 ) {
            mX0 = x0;
            mX1 = x1;
        } else {
            mX0 = x1;
            mX1 = x0;
        }

        if( y0 <= y1 ) {
            mY0 = y0;
            mY1 = y1;
        } else {
            mY0 = y1;
            mY1 = y0;
        }

        if( z0 <= z1 ) {
            mZ0 = z0;
            mZ1 = z1;
        } else {
            mZ0 = z1;
            mZ1 = z0;
        }
    }



    //////// FIELDS ////////

    public float minX() {
        return Math.min( mX0, mX1 );
    }

    public float minY() {
        return Math.min( mY0, mY1 );
    }

    public float minZ() {
        return Math.min( mZ0, mZ1 );
    }

    public float maxX() {
        return Math.max( mX0, mX1 );
    }

    public float maxY() {
        return Math.max( mY0, mY1 );
    }

    public float maxZ() {
        return Math.max( mZ0, mZ1 );
    }

    public float centerX() {
        return (mX0 + mX1) * 0.5f;
    }

    public float centerY() {
        return (mY0 + mY1) * 0.5f;
    }

    public float centerZ() {
        return (mZ0 + mZ1) * 0.5f;
    }

    public float spanX() {
        return mX1 - mX0;
    }

    public float spanY() {
        return mY1 - mY0;
    }

    public float spanZ() {
        return mZ1 - mZ0;
    }

    /**
     * @return the absolute area of this Aabb.
     */
    public float area() {
        float area = (mX1 - mX0) * (mY1 - mY0);
        area += (mX1 - mX0) * (mZ1 - mZ0);
        area += (mY1 - mY0) * (mZ1 - mZ0);

        return area * 2;
    }

    /**
     * @return the volume of this Aabb.
     */
    public float volume() {
        return (mX1 - mX0) * (mY1 - mY0) * (mZ1 - mY0);
    }

    /**
     * @return the largest dimension of the Aabb.
     */
    public float maxDim() {
        return Math.max( mX1 - mX0, Math.max( mY1 - mY0, mZ1 - mZ0 ) );
    }


    //////// TRANSFORMS ////////

    /**
     * Creates a new Aabb that has the size of <code>this</code> Aabb, but is
     * moved completely inside the argument Aabb. If the Aabb is too large to
     * fit inside, it is centered inside the argument Aabb, but its size is not
     * changed.
     *
     * @param bounds
     *            - Aabb in which to fit <code>this</code> Aabb.
     * @return a new Aabb.
     */
    public Aabb clamp( Aabb bounds ) {
        float left, right, top, bottom, front, back;


        if( mX0 < bounds.mX0 ) {
            left = bounds.mX0;
            right = left + mX1 - mX0;

            if( right > bounds.mX1 ) {
                left = (bounds.mX0 + bounds.mX1 + mX0 - mX1) * 0.5f;
                right = left + mX1 - mX0;
            }

        } else if( mX1 > bounds.mX1 ) {
            right = bounds.mX1;
            left = right + mX0 - mX1;

            if( left < bounds.mX0 ) {
                left = (bounds.mX0 + bounds.mX1 + mX0 - mX1) * 0.5f;
                right = left + mX1 - mX0;
            }

        } else {
            left = mX0;
            right = mX1;
        }

        if( mY0 < bounds.mY0 ) {
            top = bounds.mY0;
            bottom = top + mY1 - mY0;

            if( bottom > bounds.mY1 ) {
                top = (bounds.mY0 + bounds.mY1 + mY0 - mY1) * 0.5f;
                bottom = top + mY1 - mY0;
            }

        } else if( mY1 > bounds.mY1 ) {
            bottom = bounds.mY1;
            top = bottom + mY0 - mY1;

            if( top < bounds.mY0 ) {
                top = (bounds.mY0 + bounds.mY1 + mY0 - mY1) * 0.5f;
                bottom = top + mY1 - mY0;
            }

        } else {
            top = mY0;
            bottom = mY1;
        }


        if( mZ0 < bounds.mZ0 ) {
            front = bounds.mZ0;
            back = front + mZ1 - mZ0;

            if( back > bounds.mZ1 ) {
                front = (bounds.mZ0 + bounds.mZ1 + mZ0 - mZ1) * 0.5f;
                back = front + mZ1 - mZ0;
            }

        } else if( mZ1 > bounds.mZ1 ) {
            back = bounds.mZ1;
            front = back + mZ0 - mZ1;

            if( front < bounds.mZ0 ) {
                front = (bounds.mZ0 + bounds.mZ1 + mZ0 - mZ1) * 0.5f;
                back = front + mZ1 - mZ0;
            }

        } else {
            front = mZ0;
            back = mZ1;
        }


        return new Aabb( left, top, front, right, bottom, back );
    }

    /**
     * @return the floatersection between <code>this</code> Aabb and the
     *          Parameter Aabb. If the two Aabbs do not overlap, a Aabb with 0
     *          size is returned.
     */
    public Aabb clip( Aabb bounds ) {
        return new Aabb( Math.max( mX0, bounds.mX0 ),
                         Math.max( mY0, bounds.mY0 ),
                         Math.max( mZ0, bounds.mZ0 ),
                         Math.min( mX1, bounds.mX1 ),
                         Math.min( mY1, bounds.mY1 ),
                         Math.min( mZ1, bounds.mZ1 ) );
    }

    /**
     * Scales the size of the Aabb without changing the center point.
     *
     * @param scaleX Amount to scale width.
     * @param scaleY Amount to scale height.
     * @param scaleZ Amount to scale depth.
     * @return new Aabb with scaled width and height.
     */
    public Aabb inflate( float scaleX, float scaleY, float scaleZ ) {
        return Aabb.fromCenter( centerX(),
                                centerY(),
                                centerZ(),
                                (spanX() * scaleX),
                                (spanY() * scaleY),
                                (spanZ() * scaleZ) );
    }

    /**
     * @return the pointUnion between <code>this</code> Aabb and the parameter Aabb.
     *          The pointUnion may contain area not covered by either input Aabb.
     */
    public Aabb union( Aabb r ) {
        return new Aabb( Math.min( mX0, r.mX0 ),
                         Math.min( mY0, r.mY0 ),
                         Math.min( mZ0, r.mZ0 ),
                         Math.max( mX1, r.mX1 ),
                         Math.max( mY1, r.mY1 ),
                         Math.max( mZ1, r.mZ1 ) );
    }

    /**
     * Multiplies location and size.
     *
     * @param multX Amount to multiply the width and left face.
     * @param multY Amount to multiply the height and top face.
     * @param multZ Amount to multiply the depth and front face.
     * @return new Aabb object.
     */
    public Aabb scale( float multX, float multY, float multZ ) {
        return new Aabb( mX0 * multX, mY0 * multY, mZ0 * multZ,
                         mX1 * multX, mY1 * multY, mZ1 * multZ );
    }

    /**
     * Moves the Aabb.
     *
     * @param dx Amount to move the Aabb horizantally.
     * @param dy Amount to move the Aabb vertically.
     * @param dz Amount to move the Aabb depthally.
     * @return a new Aabb.
     */
    public Aabb translate( float dx, float dy, float dz ) {
        return new Aabb( mX0 + dx, mY0 + dy, mZ0 + dz, mX1 + dx, mY1 + dy, mZ1 + dz );
    }

    /**
     * Centers the Aabb on the given pofloat.
     *
     * @return new Aabb centered on the given pofloat.
     */
    public Aabb setCenter( float x, float y, float z ) {
        x -= (mX1 - mX0) * 0.5f;
        y -= (mY1 - mY0) * 0.5f;
        z -= (mZ1 - mZ0) * 0.5f;

        return new Aabb( x, y, z, x + mX1 - mX0, y + mY1 - mY0, z + mZ1 - mZ0 );
    }

    /**
     * Computes a new Aabb after an arbitrary homographic transform.
     */
    public Aabb transform( Matrix4 transformMat ) {
        float minX      = Float.POSITIVE_INFINITY;
        float maxX      = Float.NEGATIVE_INFINITY;
        float minY      = Float.POSITIVE_INFINITY;
        float maxY      = Float.NEGATIVE_INFINITY;
        float minZ      = Float.POSITIVE_INFINITY;
        float maxZ      = Float.NEGATIVE_INFINITY;
        Vector3 vert = new Vector3();

        for( int i = 0; i < 8; i++ ) {
            vert.x = (i % 2) < 1 ? mX0 : mX1;
            vert.y = (i % 4) < 2 ? mY0 : mY1;
            vert.z = (i    ) < 4 ? mZ0 : mZ1;

            Matrix.mult( transformMat, vert, vert );
            minX = Math.min( minX, vert.x );
            maxX = Math.max( maxX, vert.x );
            minY = Math.min( minY, vert.y );
            maxY = Math.max( maxY, vert.y );
            minZ = Math.min( minZ, vert.z );
            maxZ = Math.max( maxZ, vert.z );
        }

        return new Aabb( minX, minY, minZ, maxX, maxY, maxZ );
    }


    //////// QUERIES ////////

    /**
     * Tests if Aabb float intersects with a given point.
     *
     * @param x  The x coordinate of a point.
     * @param y  The y coordinate of a point.
     * @param z  The z coordinate of a point.
     * @return true if the float lies within the Aabb, otherwise false.
     */
    @Override
    public boolean contains( float x, float y, float z ) {
        return x >= mX0 &&
               x <  mX1 &&
               y >= mY0 &&
               y <  mY1 &&
               z >= mZ0 &&
               z <  mZ1;
    }

    /**
     * Tests if this Aabb has intersectio with other Aabb.
     *
     * @param aabb  An Aabb to check for overlap.
     * @return true if Aabbs have any floatersection.
     */
    public boolean intersects( Aabb aabb ) {
        return !( mX0 <  aabb.mX0 && mX1 <  aabb.mX0 ||
                  mX0 >= aabb.mX1 && mX1 >= aabb.mX1 ||
                  mY0 <  aabb.mY0 && mY1 <  aabb.mY0 ||
                  mY0 >= aabb.mY1 && mY1 >= aabb.mY1 ||
                  mZ0 <  aabb.mZ0 && mZ1 <  aabb.mZ0 ||
                  mZ0 >= aabb.mZ1 && mZ1 >= aabb.mZ1 );
    }


    //////// TYPE CONVERSIONS ////////

    /**
     * @param out6 On return, holds [x0, y0, z0, x1, y1, z1]
     */
    public void toArray( float[] out6 ) {
        out6[0] = mX0;
        out6[1] = mY0;
        out6[2] = mZ0;
        out6[3] = mX1;
        out6[4] = mY1;
        out6[5] = mZ1;
    }




    @Override
    public int hashCode() {
        return Float.floatToRawIntBits( mX0 + mY0 + mZ0 + mX1 + mY1 + mZ1 );
    }

    @Override
    public boolean equals( Object o ) {
        if( !(o instanceof Aabb) ) {
            return false;
        }

        Aabb r = (Aabb)o;
        return mX0 == r.mX0 &&
               mY0 == r.mY0 &&
               mX1 == r.mX1 &&
               mY1 == r.mY1 &&
               mZ0 == r.mZ0 &&
               mZ1 == r.mZ1;
    }

    @Override
    public Aabb bounds() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder( "Aabb [" );
        b.append( mX0 );
        b.append( ", " );
        b.append( mY0 );
        b.append( ", " );
        b.append( mZ0 );
        b.append( ", " );
        b.append( (mX1 - mX0) );
        b.append( ", " );
        b.append( (mY1 - mY0) );
        b.append( ", " );
        b.append( (mZ1 - mZ0) );
        b.append( "]" );
        return b.toString();
    }

}
