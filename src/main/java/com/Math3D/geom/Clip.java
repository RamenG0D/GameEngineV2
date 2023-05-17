/*
 * Copyright (c) 2015. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import com.Math3D.*;

/**
 * @author Philip DeCamp
 */
public class Clip {


    /**
     * Clips 2-planar polygon with 2-plane, inclusive. This method computes the part of polygon that
     * lies in the non-negative space defined by a plane, and removes the polygon part in the negative space.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param plane     Plane to use for clipping, of the form {@code plane.x * x + plane.y * y + plane.z * z + plane.w == 0 }
     * @param out       Holds clipped polygon on return.
     *
     * @return true iff clipped polygon is non-null.
     */
    public static boolean clipPlanarWithPlane( Vector3[] vertLoop,
                                               int vertOff,
                                               int vertCount,
                                               Vector4 plane,
                                               PolyLine out )
    {
        if( plane.z == 0 ) {
            if( plane.y == 0 ) {
                if( plane.x < 0 ) {
                    return clipPlanarUnderAxisPlane( vertLoop, vertOff, vertCount, 0, -plane.w / plane.x, out );
                } else if( plane.x > 0 ) {
                    return clipPlanarOverAxisPlane( vertLoop, vertOff, vertCount, 0, -plane.w / plane.x, out );
                } else {
                    return false;
                }
            } else if( plane.x == 0 ) {
                if( plane.y < 0 ) {
                    return clipPlanarUnderAxisPlane( vertLoop, vertOff, vertCount, 1, -plane.w / plane.y, out );
                } else {
                    return clipPlanarOverAxisPlane( vertLoop, vertOff, vertCount, 1,  -plane.w / plane.y, out );
                }
            }
        } else if( plane.y == 0 && plane.x == 0 ) {
            if( plane.z < 0 ) {
                return clipPlanarUnderAxisPlane( vertLoop, vertOff, vertCount, 2, -plane.w / plane.z, out );
            } else {
                return clipPlanarOverAxisPlane( vertLoop, vertOff, vertCount, 2,  -plane.w / plane.z, out );
            }
        }

        return clipPlanarFast( vertLoop, vertOff, vertCount, plane, out );
    }


    /**
     * Clips 2-planar polygon with axis-aligned 2-plane, inclusive.
     * This method computes the part of polygon that lies in the non-positive space defined by a plane,
     * and removes the polygon part in the positive space.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param axis      Axis of clipping plane. {@code 0 == x, 1 == y, 2 == z }
     * @param max       Where plane intersects axis.
     * @param out       Holds clipped polygon on return.
     *
     * @return true iff clipped polygon is non-null.
     *
     * @see #clipPlanarWithPlane
     */
    public static boolean clipPlanarUnderAxisPlane( Vector3[] vertLoop,
                                                    int vertOff,
                                                    int vertCount,
                                                    int axis,
                                                    float max,
                                                    PolyLine out )
    {
        int outSize = 0;
        out.ensureCapacity( vertCount * 3 / 2 );

        Vector3 va = vertLoop[vertCount - 1 + vertOff];
        float aDist = max - va.getElement( axis );

        for( int b = 0; b < vertCount; b++ ) {
            Vector3 vb = vertLoop[ b + vertOff ];
            float bDist = max - vb.getElement( axis );

            if( aDist < 0 ) {
                if( bDist > 0 ) {
                    // Cross event
                    lerp( va, vb, -aDist / (bDist - aDist), out.mVerts[outSize] );
                    // Correct for rounding errors on clipping plane.
                    out.mVerts[outSize++].setElement( axis, max );
                    Vector.put( vb, out.mVerts[outSize++] );
                } else if( bDist == 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            } else if( aDist > 0 ) {
                if( bDist >= 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                } else {
                    // Cross event.
                    lerp( va, vb, aDist / (aDist - bDist), out.mVerts[outSize] );
                    // Correct for rounding errors on clipping plane.
                    out.mVerts[outSize++].setElement( axis, max );
                }
            } else {
                if( bDist >= 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            }

            va    = vb;
            aDist = bDist;
        }

        out.mSize = outSize ;
        return outSize >= 3;
    }

    /**
     * Clips 2-planar polygon with axis-aligned 2-plane, inclusive.
     * This method computes the part of polygon that lies in the non-negative space defined by a plane,
     * and removes the polygon part in the negative space.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param axis      Axis of clipping plane. {@code 0 == x, 1 == y, 2 == z }
     * @param min       Where plane intersects axis.
     * @param out       Holds clipped polygon on return.
     *
     * @return true iff clipped polygon is non-null.
     *
     * @see #clipPlanarWithPlane
     */
    public static boolean clipPlanarOverAxisPlane( Vector3[] vertLoop,
                                                   int vertOff,
                                                   int vertCount,
                                                   int axis,
                                                   float min,
                                                   PolyLine out )
    {
        out.ensureCapacity( vertCount * 3 / 2 );
        int outSize = 0;

        Vector3 va = vertLoop[vertCount - 1 + vertOff];
        float aDist = va.getElement( axis ) - min;

        for( int b = 0; b < vertCount; b++ ) {
            Vector3 vb = vertLoop[ b + vertOff ];
            float bDist = vb.getElement( axis ) - min;

            if( aDist < 0 ) {
                if( bDist > 0 ) {
                    // Cross event
                    lerp( va, vb, -aDist / (bDist - aDist), out.mVerts[outSize] );
                    // Correct for rounding errors on clipping plane.
                    out.mVerts[outSize++].setElement( axis, min );
                    Vector.put( vb, out.mVerts[outSize++] );
                } else if( bDist == 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            } else if( aDist > 0 ) {
                if( bDist >= 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                } else {
                    // Cross event.
                    lerp( va, vb, aDist / (aDist - bDist), out.mVerts[outSize] );
                    // Correct for rounding errors on clipping plane.
                    out.mVerts[outSize++].setElement( axis, min );
                }
            } else {
                if( bDist >= 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            }

            va    = vb;
            aDist = bDist;
        }

        out.mSize = outSize ;
        return outSize >= 3;
    }

    /**
     * Clips 2-planar polygon with 2-plane inclusively WITHOUT making accuracy optimizations for special cases.
     *
     * This method computes the part of polygon that lies in the non-positive space defined by a plane,
     * and removes the polygon part in the positive space.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param plane     Plane to use for clipping, of the form {@code plane.x * x + plane.y * y + plane.z * z + plane.w == 0 }
     * @param out       Holds clipped polygon on return.
     *
     * @return true iff clipped polygon is non-null.
     *
     * @see #clipPlanarWithPlane
     */
    public static boolean clipPlanarFast( Vector3[] vertLoop,
                                          int vertOff,
                                          int vertCount,
                                          Vector4 plane,
                                          PolyLine out )
    {
        int outSize = 0;
        out.ensureCapacity( vertCount * 3 / 2 );

        Vector3 va = vertLoop[vertCount - 1 + vertOff];
        float aDist = Vector.dot( va, plane ) + plane.w;

        for( int b = 0; b < vertCount; b++ ) {
            Vector3 vb = vertLoop[b + vertOff];
            float bDist = Vector.dot( vb, plane ) + plane.w;

            if( aDist < 0 ) {
                if( bDist > 0 ) {
                    // Cross event.
                    Vector.lerp( va, vb, -aDist / (bDist - aDist), out.mVerts[outSize++] );
                    Vector.put( vb, out.mVerts[outSize++] );
                } else if( bDist == 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            } else if( aDist > 0 ) {
                out.ensureCapacity( outSize + 1 );
                if( bDist < 0 ) {
                    Vector.lerp( va, vb, aDist / (aDist - bDist), out.mVerts[outSize++] );
                } else {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            } else {
                if( bDist >= 0 ) {
                    Vector.put( vb, out.mVerts[outSize++] );
                }
            }

            va    = vb;
            aDist = bDist;
        }

        out.mSize = outSize;
        return outSize >= 3;
    }

    /**
     * Provided with an array of COPLANAR vertices and an AABB, this method
     * will provide the inclusive geometric intersection.
     *
     * @param vertLoop Array of coplanar length-3 vertices.
     * @param vertOff  Index of first vert.
     * @param vertNum  Number of verts.
     * @param clip     Clipping box
     * @param workPoly Loop used as working space.
     * @param out      pre-allocated loop object to hold clipped results.
     * @return true iff intersection is found with non zero surface area.
     * (This isn't precisely true because I haven't formalized all the boundary conditions)
     */
    public static boolean clipPlanarWithBox( Vector3[] vertLoop,
                                             int vertOff,
                                             int vertNum,
                                             Box3 clip,
                                             PolyLine workPoly,
                                             PolyLine out )
    {
        // Check for any intersection.
        return clipPlanarOverAxisPlane(  vertLoop,        vertOff, vertNum,       0, clip.x0, workPoly ) &&
               clipPlanarUnderAxisPlane( workPoly.mVerts, 0,       workPoly.mSize,  0, clip.x1, out      ) &&
               clipPlanarOverAxisPlane(  out.mVerts,      0,       out.mSize,       1, clip.y0, workPoly ) &&
               clipPlanarUnderAxisPlane( workPoly.mVerts, 0,       workPoly.mSize,  1, clip.y1, out      ) &&
               clipPlanarOverAxisPlane(  out.mVerts,      0,       out.mSize,       2, clip.z0, workPoly ) &&
               clipPlanarUnderAxisPlane( workPoly.mVerts, 0,       workPoly.mSize,  2, clip.z1, out );
    }


    /**
     * Splits 2-planar polygon with 2-plane.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param plane     Plane to use for splitting, of the form {@code plane.x * x + plane.y * y + plane.z * z + plane.w == 0 }
     * @param outNeg   Holds part of polygon that lies in plane's non-positive space.
     * @param outPos  Holds part of polygon that lies in plane's positive space.
     *
     * @return -1 if polygon lies entirely in negative domain, <br>
     *          0 if polygon spans negative and non-negative domain, <br>
     *          1 if polygon lies entirely in non-negative domain.
     */
    public static int splitPlanarWithPlane( Vector3[] vertLoop,
                                            int vertOff,
                                            int vertCount,
                                            Vector4 plane,
                                            PolyLine outNeg,
                                            PolyLine outPos )
    {
        if( plane.z == 0 ) {
            if( plane.y == 0 ) {
                if( plane.x < 0 ) {
                    return -splitPlanarWithAxisPlaneExclusive( vertLoop, vertOff, vertCount, 0, -plane.w / plane.x, outPos, outNeg );
                } else if( plane.x > 0 ) {
                    return splitPlanarWithAxisPlane( vertLoop, vertOff, vertCount, 0, -plane.w / plane.x, outNeg, outPos );
                } else {
                    return 0;
                }
            } else if( plane.x == 0 ) {
                if( plane.y < 0 ) {
                    return -splitPlanarWithAxisPlaneExclusive( vertLoop, vertOff, vertCount, 1, -plane.w / plane.y, outPos, outNeg );
                } else {
                    return splitPlanarWithAxisPlane( vertLoop, vertOff, vertCount, 1, -plane.w / plane.y, outNeg, outPos );
                }
            }
        } else if( plane.y == 0 && plane.x == 0 ) {
            if( plane.z < 0 ) {
                return -splitPlanarWithAxisPlaneExclusive( vertLoop, vertOff, vertCount, 2, -plane.w / plane.z, outPos, outNeg );
            } else {
                return splitPlanarWithAxisPlane( vertLoop, vertOff, vertCount, 2, -plane.w / plane.z, outNeg, outPos );
            }
        }

        return splitPlanarWithPlaneFast( vertLoop, vertOff, vertCount, plane, outNeg, outPos );
    }

    /**
     * Splits 2-planar polygon with axis-aligned 2-plane.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param axis      Axis of splitting plane. {@code 0 == x, 1 == y, 2 == z }
     * @param plane     Where plane intersects axis.
     * @param outNeg   Receives part of polygon that lies in plane's negative space.
     * @param outPos  Receives part of polygon that lies in plane's positive space or on plane.
     *
     * @return -1 if polygon lies entirely in negative domain, <br>
     *          0 if polygon spans negative and non-negative domains, <br>
     *          1 if polygon lies entirely in non-negative domaoin.
     *
     * @see #splitPlanarWithPlane
     */
    public static int splitPlanarWithAxisPlane( Vector3[] vertLoop,
                                                int vertOff,
                                                int vertCount,
                                                int axis,
                                                float plane,
                                                PolyLine outNeg,
                                                PolyLine outPos )
    {
        int sizeNeg = 0;
        int sizePos = 0;
        int cap = vertCount * 3 / 2;
        outNeg.ensureCapacity( cap );
        outPos.ensureCapacity( cap );

        Vector3 va = vertLoop[vertCount - 1 + vertOff];
        float aDist = va.getElement( axis ) - plane;

        for( int b = 0; b < vertCount; b++ ) {
            Vector3 vb = vertLoop[ b + vertOff ];
            float bDist = vb.getElement( axis ) - plane;

            if( aDist < 0 ) {
                if( bDist < 0 ) {
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else if( bDist > 0 ) {
                    // Plane cross event.
                    // Interpolate point and add to both neg and pos polygons.
                    Vector3 mid = outNeg.mVerts[sizeNeg++];
                    lerp( va, vb, -aDist / (bDist - aDist), mid );
                    // Correct for rounding errors so that vert has exact coord is clipping plane.
                    mid.setElement( axis, plane );
                    Vector.put( mid, outPos.mVerts[sizePos++] );
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                } else {
                    // On the line. Add to both polygons.
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                    Vector.put( vb, outPos.mVerts[sizeNeg++] );
                }
            } else if( aDist > 0 ) {
                if( bDist >= 0 ) {
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                } else {
                    // Cross event.
                    // Interpolate point and add to pos and neg polygons.
                    Vector3 mid = outPos.mVerts[sizePos++];
                    lerp( va, vb, aDist / (aDist - bDist), mid );
                    // Correct for rounding errors so that vert has exact coord as clipping plane.
                    mid.setElement( axis, plane );
                    Vector.put( mid, outNeg.mVerts[sizeNeg++] );
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                }
            } else {
                // aDist == 0.0, meaning va is on the line
                if( bDist >= 0 ) {
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                } else {
                    // va is on line, vb is on right.
                    // Because line verts normally go to the right,
                    // check if va needs to be added also to the neg polygon.
                    if( sizeNeg == 0 || outNeg.mVerts[sizeNeg-1].equals( va ) ) {
                        Vector.put( va, outNeg.mVerts[sizeNeg++] );
                    }
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                }
            }

            va    = vb;
            aDist = bDist;
        }

        outNeg.mSize = sizeNeg;
        outPos.mSize = sizePos;
        return sizeNeg == 0 ? ( sizePos == 0 ? 0 : 1 ) : ( sizePos == 0 ? -1 : 0 );
    }


    /**
     * Splits 2-planar polygon with axis-aligned 2-plane. Unlike {@link #splitPlanarWithAxisPlane}, this
     * method places verts that lie on splitting plane into {@code outNeg} instead of {@code outPos}, as
     * if the positive space was exclusive of the splitting plane.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param axis      Axis of splitting plane. {@code 0 == x, 1 == y, 2 == z }
     * @param plane     Where plane intersects axis.
     * @param outNeg   Receives part of polygon that lies in plane's negative space or on plane.
     * @param outPos  Receives part of polygon that lies in plane's positive space.
     *
     * @return -1 if polygon lies entirely in negative domain, <br>
     *          0 if polygon spans negative and non-negative domains, <br>
     *          1 if polygon lies entirely in non-negative domaoin.
     *
     * @see #splitPlanarWithPlane
     */
    public static int splitPlanarWithAxisPlaneExclusive( Vector3[] vertLoop,
                                                         int vertOff,
                                                         int vertCount,
                                                         int axis,
                                                         float plane,
                                                         PolyLine outNeg,
                                                         PolyLine outPos )
    {
        int sizeNeg = 0;
        int sizePos = 0;
        int cap = vertCount * 3 / 2;
        outNeg.ensureCapacity( cap );
        outPos.ensureCapacity( cap );

        Vector3 va = vertLoop[vertCount - 1 + vertOff];
        float aDist = va.getElement( axis ) - plane;

        for( int b = 0; b < vertCount; b++ ) {
            Vector3 vb = vertLoop[ b + vertOff ];
            float bDist = vb.getElement( axis ) - plane;

            if( aDist < 0 ) {
                if( bDist <= 0 ) {
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else {
                    // Plane cross event.
                    // Interpolate point and add to both neg and pos polygons.
                    Vector3 mid = outNeg.mVerts[sizeNeg++];
                    lerp( va, vb, -aDist / (bDist - aDist), mid );
                    // Correct for rounding errors so that vert has exact coord is clipping plane.
                    mid.setElement( axis, plane );
                    Vector.put( mid, outPos.mVerts[sizePos++] );
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                }
            } else if( aDist > 0 ) {
                if( bDist > 0 ) {
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                } else if( bDist < 0 ) {
                    // Cross event.
                    // Interpolate point and add to pos and neg polygons.
                    Vector3 mid = outPos.mVerts[sizePos++];
                    lerp( va, vb, aDist / (aDist - bDist), mid );
                    // Correct for rounding errors so that vert has exact coord as clipping plane.
                    mid.setElement( axis, plane );
                    Vector.put( mid, outNeg.mVerts[sizeNeg++] );
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else {
                    // VA positive, VB on plane. Add vert ot both.
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                    Vector.put( va, outNeg.mVerts[sizeNeg++] );
                }
            } else {
                // aDist == 0.0, meaning va is on the line
                if( bDist <= 0 ) {
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else {
                    // va is on plane, vb is positive.
                    // Because va would normally be included in negative polygon,
                    // check if va needs to be added also to the positive polygon.
                    if( sizePos == 0 || outPos.mVerts[sizePos-1].equals( va ) ) {
                        Vector.put( va, outPos.mVerts[sizePos++] );
                    }
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                }
            }
            va    = vb;
            aDist = bDist;
        }

        outNeg.mSize = sizeNeg;
        outPos.mSize = sizePos;
        return sizeNeg == 0 ? ( sizePos == 0 ? 0 : 1 ) : ( sizePos == 0 ? -1 : 0 );
    }


    /**
     * Splits 2-planar polygon with axis-aligned 2-plane.
     *
     * @param vertLoop  Array of coplanar length-3 verts.
     * @param vertOff   Index of first vert
     * @param vertCount Number of verts
     * @param plane     Plane to use for splitting, of the form {@code plane.x * x + plane.y * y + plane.z * z + plane.w == 0 }
     * @param outNeg   Receives part of polygon that lies in plane's non-positive space.
     * @param outPos  Receives part of polygon that lies in plane's positive space.
     *
     * @return -1 if polygon lies entirely in non-positive space, <br>
     *          0 if polygon lies in non-positive and positive space, <br>
     *          1 if polygon lies entirely in positive space.
     */
    public static int splitPlanarWithPlaneFast( Vector3[] vertLoop,
                                                int vertOff,
                                                int vertCount,
                                                Vector4 plane,
                                                PolyLine outNeg,
                                                PolyLine outPos )
    {
        int sizeNeg = 0;
        int sizePos = 0;
        int cap = vertCount * 3 / 2;
        outNeg.ensureCapacity( cap );
        outPos.ensureCapacity( cap );

        Vector3 va = vertLoop[vertCount - 1 + vertOff];
        float aDist = Vector.dot( va, plane ) + plane.w;

        for( int b = 0; b < vertCount; b++ ) {
            Vector3 vb = vertLoop[ b + vertOff ];
            float bDist = Vector.dot( vb, plane ) + plane.w;

            if( aDist < 0 ) {
                if( bDist <= 0 ) {
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else {
                    // Cross event.
                    // Interpolated point and add to neg and pos sides.
                    Vector3 mid = outNeg.mVerts[sizeNeg++];
                    lerp( va, vb, -aDist / (bDist - aDist), mid );
                    Vector.put( mid, outPos.mVerts[sizePos++] );
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                }
            } else if( aDist > 0 ) {
                if( bDist > 0 ) {
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                } else if( bDist < 0 ) {
                    // Cross event.
                    // Interpolate point and add to neg and pos sides.
                    Vector3 mid = outPos.mVerts[sizePos++];
                    lerp( va, vb, aDist / (aDist - bDist), mid );
                    Vector.put( mid, outNeg.mVerts[sizeNeg++] );
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else {
                    // On the line. Add to both.
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                }
            } else {
                // aDist == 0.0, meaning va is on the line
                if( bDist <= 0 ) {
                    Vector.put( vb, outNeg.mVerts[sizeNeg++] );
                } else {
                    // va is on line, vb is on right.
                    // Because line verts normally go to the neg space, check
                    // whether outRight contains va.
                    if( sizePos == 0 || outPos.mVerts[sizePos-1].equals( va ) ) {
                        Vector.put (va, outPos.mVerts[sizePos++] );
                    }
                    Vector.put( vb, outPos.mVerts[sizePos++] );
                }
            }

            va    = vb;
            aDist = bDist;
        }

        outNeg.mSize = sizeNeg;
        outPos.mSize = sizePos;

        return sizeNeg == 0 ? ( sizePos == 0 ? 0 : 1 ) : ( sizePos == 0 ? -1 : 0 );
    }


    /**
     * Provided with an array of COPLANAR vertices and an AABB, this method
     * will provide the geometric intersection.
     *
     * @param vertLoop  Array of length-3 vertices.
     * @param vertCount Number of vertices
     * @param clip      Clipping box
     * @param out       pre-allocated loop object to hold clipped results.
     * @return true iff intersection is found with non zero surface area.
     * (This isn't precisely true because I haven't formalized all the boundary conditions)
     */
    // TODO: This should be cleaned up, and could be faster.
    // Also, it's broken for concave loops.
    // It would be better to iterate through verts then clip planes, not vice versa.
    @Deprecated
    public static boolean clipPlanarOld( Vector3[] vertLoop, int vertOff, int vertCount, Box3 clip, PolyLine out ) {
        final int cap = vertCount + 6;
        out.ensureCapacity( cap );
        Vector3[] verts = out.mVerts;

        // Copy data into output.
        for( int i = 0; i < vertCount; i++ ) {
            Vector.put( vertLoop[i + vertOff], verts[i] );
        }

        // Iterate through clip planes.
        for( int axis = 0; axis < 3; axis++ ) {
            // Iterate through vertices.
            float min = clip.min( axis );
            float max = clip.max( axis );
            float v0  = verts[0].getElement( axis );

            int m0 = -1;
            int m1 = -1;
            int n0 = -1;
            int n1 = -1;

            // Find plane crossings.
            for( int i = 0; i < vertCount; i++ ) {
                int j = ( i + 1 ) % vertCount;
                float a = verts[i].getElement( axis );
                float b = verts[j].getElement( axis );

                if( (a < min) != (b < min) ) {
                    if( m0 == -1 ) {
                        m0 = i;
                    } else {
                        m1 = i;
                    }
                }

                if( (a > max) != (b > max) ) {
                    if( n0 == -1 ) {
                        n0 = i;
                    } else {
                        n1 = i;
                    }
                }
            }

            if( m1 == -1 ) {
                // No intersection with min plane.
                if( v0 < min ) {
                    // No intersection with volume.
                    out.mSize = 0;
                    return false;
                }
            } else {
                // Split loop at min plane.
                if( v0 < min ) {
                    vertCount = retainLoopSection( verts, vertCount, m0, m1, axis, min );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, m0, m1, axis, min );
                }

                // Recompute max crossings, if necessary.
                if( n1 != -1 ) {
                    n0 = -1;
                    n1 = -1;
                    for( int i = 0; i < vertCount; i++ ) {
                        int j = ( i + 1 ) % vertCount;
                        float a = verts[i].getElement( axis );
                        float b = verts[j].getElement( axis );
                        if( (a > max) != (b > max) ) {
                            if( n0 == -1 ) {
                                n0 = i;
                            } else {
                                n1 = i;
                            }
                        }
                    }
                }
            }

            if( n1 == -1 ) {
                // No intersection with max plane.
                if( v0 > max ) {
                    // No intersection with volume.
                    out.mSize = 0;
                    return false;
                }
            } else {
                // Split loop at max plane.
                if( v0 > max ) {
                    vertCount = retainLoopSection( verts, vertCount, n0, n1, axis, max );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, n0, n1, axis, max );
                }
            }
        }

        out.mSize = vertCount;
        return true;
    }

    /**
     * Provided with an array of COPLANAR vertices and an AABB, this method
     * will provide the geometric intersection.
     *
     * @param vertLoop  Array of length-3 vertices.
     * @param vertCount Number of vertices
     * @param clipAabb  Clipping box
     * @param out       pre-allocated loop object to hold clipped results.
     * @return true iff intersection is found with non zero surface area.
     * (This isn't precisely true because I haven't formalized all the boundary conditions)
     */
    @Deprecated
    public static boolean clipPlanar3( double[][] vertLoop, int vertOff, int vertCount, double[] clipAabb, Loop out ) {
        double[][] verts = out.mVerts;

        if( verts == null || verts.length < vertCount + 6 ) {
            verts = new double[vertLoop.length + 6][3];
            out.mVerts = verts;
        }

        // Copy data into output.
        for( int i = 0; i < vertCount; i++ ) {
            System.arraycopy( vertLoop[i + vertOff], 0, verts[i], 0, 3 );
        }

        // Iterate through clip planes.
        // Iterate through clip planes.
        for( int axis = 0; axis < 3; axis++ ) {

            // Iterate through vertices.
            double min = clipAabb[axis];
            double max = clipAabb[axis + 3];
            int m0 = -1;
            int m1 = -1;
            int n0 = -1;
            int n1 = -1;

            // Find plane crossings.
            for( int i = 0; i < vertCount; i++ ) {
                double a = verts[i][axis];
                double b = verts[(i + 1) % vertCount][axis];

                if( (a < min) != (b < min) ) {
                    if( m0 == -1 ) {
                        m0 = i;
                    } else {
                        m1 = i;
                    }
                }

                if( (a > max) != (b > max) ) {
                    if( n0 == -1 ) {
                        n0 = i;
                    } else {
                        n1 = i;
                    }
                }
            }

            if( m1 == -1 ) {
                // No intersection with min plane.
                if( verts[0][axis] < min ) {
                    // No intersection with volume.
                    out.mLength = 0;
                    return false;
                }
            } else {
                // Split loop at min plane.
                if( verts[0][axis] < min ) {
                    vertCount = retainLoopSection3( verts, vertCount, m0, m1, axis, min );
                } else {
                    vertCount = removeLoopSection3( verts, vertCount, m0, m1, axis, min );
                }

                // Recompute max crossings, if necessary.
                if( n1 != -1 ) {
                    n0 = -1;
                    n1 = -1;

                    for( int i = 0; i < vertCount; i++ ) {
                        double a = verts[i][axis];
                        double b = verts[(i + 1) % vertCount][axis];

                        if( (a > max) != (b > max) ) {
                            if( n0 == -1 ) {
                                n0 = i;
                            } else {
                                n1 = i;
                            }
                        }
                    }
                }
            }

            if( n1 == -1 ) {
                // No intersection with max plane.
                if( verts[0][axis] > max ) {
                    // No intersection with volume.
                    out.mLength = 0;
                    return false;
                }
            } else {
                // Split loop at max plane.
                if( verts[0][axis] > max ) {
                    vertCount = retainLoopSection3( verts, vertCount, n0, n1, axis, max );
                } else {
                    vertCount = removeLoopSection3( verts, vertCount, n0, n1, axis, max );
                }
            }
        }

        out.mLength = vertCount;
        return true;
    }


    private static int removeLoopSection( Vector3[] v, int count, int start, int stop, int axis, float pos ) {
        // Make sure there is enough room between start and stop vertices if they're adjacent.
        if( start + 1 == stop ) {
            Vector3 temp = v[count];
            System.arraycopy( v, stop - 1, v, stop, count + 1 - stop );
            v[stop++] = temp;
            Vector.put( v[stop], temp );
            count++;
        }

        // Cache start values.
        Vector3 va = v[start];
        Vector3 vb = v[start + 1];
        float a = va.getElement( axis );
        float b = vb.getElement( axis );

        // Check if va is precisely on edge.
        if( a != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                vb.x = r * vb.x + (1f - r) * va.x;
            }
            if( va.y != vb.y ) {
                vb.y = r * vb.y + (1f - r) * va.y;
            }
            if( va.z != vb.z ) {
                vb.z = r * vb.z + (1f - r) * va.z;
            }
            vb.setElement( axis, pos ); // Avoid rounding errors.
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[(stop + 1) % count];
        a = va.getElement( axis );
        b = vb.getElement( axis );

        // Check if vb is precisely on edge.
        if( b != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                va.x = r * vb.x + (1.0f - r) * va.x;
            }
            if( va.y != vb.y ) {
                va.y = r * vb.y + (1.0f - r) * va.y;
            }
            if( va.z != vb.z ) {
                va.z = r * vb.z + (1.0f - r) * va.z;
            }
            va.setElement( axis, pos ); // Avoid rounding errors.
        } else {
            stop++;
        }

        // Check if there's a gap to fill.
        int gap = stop - start - 1;
        if( gap == 0 ) {
            return count;
        }
        count -= gap;
        for( int i = start + 1; i < count; i++ ) {
            Vector3 temp = v[i];
            v[i] = v[i + gap];
            v[i + gap] = temp;
        }

        return count;
    }


    private static int retainLoopSection( Vector3[] v, int count, int start, int stop, int axis, float pos ) {
        // Make sure there's enough space if vertices are adjacent.
        if( stop + 1 == count ) {
            Vector3 temp = v[count++];
            Vector.put( v[0], temp );
        }

        // Cache start values.
        Vector3 va = v[start  ];
        Vector3 vb = v[start+1];
        float a = va.getElement( axis );
        float b = vb.getElement( axis );

        // Check if vb is precisely on edge.
        if( b != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                va.x = r * vb.x + (1.0f - r) * va.x;
            }
            if( va.y != vb.y ) {
                va.y = r * vb.y + (1.0f - r) * va.y;
            }
            if( va.z != vb.z ) {
                va.z = r * vb.z + (1.0f - r) * va.z;
            }
            va.setElement( axis, pos ); // To avoid rounding errors.
        } else {
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[stop + 1];
        a = va.getElement( axis );
        b = vb.getElement( axis );

        // Check if vb is precisely on edge.
        if( b != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                vb.x = r * vb.x + (1 - r) * va.x;
            }
            if( va.y != vb.y ) {
                vb.y = r * vb.y + (1 - r) * va.y;
            }
            if( va.z != vb.z ) {
                vb.z = r * vb.z + (1 - r) * va.z;
            }
            vb.setElement( axis, pos );
            stop++;
        }

        // Shift elements back.
        count = stop - start + 1;
        if( start == 0 ) {
            return count;
        }
        for( int i = 0; i < count; i++ ) {
            Vector3 temp = v[i];
            v[i] = v[i + start];
            v[i + start] = temp;
        }

        return count;
    }


    private static int removeLoopSection3( double[][] v, int count, int start, int stop, int axis, double pos ) {
        // Make sure there's enough room between start and stop vertices if
        // they're adjacent.
        if( start + 1 == stop ) {
            double[] temp = v[count];
            System.arraycopy( v, stop - 1, v, stop, count + 1 - stop );
            v[stop++] = temp;
            temp[0] = v[stop][0];
            temp[1] = v[stop][1];
            temp[2] = v[stop][2];
            count++;
        }

        // Cache start values.
        double[] va = v[start];
        double[] vb = v[start + 1];

        double a = va[axis];
        double b = vb[axis];

        // Check if va is precisely on edge.
        if( a != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                vb[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                vb[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                vb[2] = r * vb[2] + (1.0 - r) * va[2];
            vb[axis] = pos; // To avoid rounding errors.
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[(stop + 1) % count];
        a = va[axis];
        b = vb[axis];

        // Check if vb is precisely on edge.
        if( b != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                va[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                va[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                va[2] = r * vb[2] + (1.0 - r) * va[2];
            va[axis] = pos; // To avoid rounding errors.
        } else {
            stop++;
        }

        // Check if there's a gap to fill.
        int gap = stop - start - 1;
        if( gap == 0 ) {
            return count;
        }

        count -= gap;
        for( int i = start + 1; i < count; i++ ) {
            double[] temp = v[i];
            v[i] = v[i + gap];
            v[i + gap] = temp;
        }

        return count;
    }


    private static int retainLoopSection3( double[][] v, int count, int start, int stop, int axis, double pos ) {

        // Make sure there's enough space if vertices are adjacent.
        if( stop + 1 == count ) {
            double[] temp = v[count++];
            temp[0] = v[0][0];
            temp[1] = v[0][1];
            temp[2] = v[0][2];
        }

        // Cache start values.
        double[] va = v[start];
        double[] vb = v[start + 1];

        double a = va[axis];
        double b = vb[axis];

        // Check if vb is precisely on edge.
        if( b != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                va[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                va[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                va[2] = r * vb[2] + (1.0 - r) * va[2];
            va[axis] = pos; // To avoid rounding errors.
        } else {
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[stop + 1];
        a = va[axis];
        b = vb[axis];

        // Check if vb is precisely on edge.
        if( b != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                vb[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                vb[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                vb[2] = r * vb[2] + (1.0 - r) * va[2];
            vb[axis] = pos;
            stop++;
        }

        // Shift elements back.
        count = stop - start + 1;

        if( start == 0 )
            return count;

        for( int i = 0; i < count; i++ ) {
            double[] temp = v[i];
            v[i] = v[i + start];
            v[i + start] = temp;
        }

        return count;
    }


    private static void clean( PolyLine line ) {
        int cap = line.mVerts.length;

        for( int i = 0; i < line.mSize && line.mSize > 1; i++ ) {
            int prev = ( i + line.mSize - 1 ) % line.mSize;
            Vector3 a = line.mVerts[i   ];
            Vector3 b = line.mVerts[prev];
            if( near( a, b ) ) {
                System.arraycopy( line.mVerts, prev+1, line.mVerts, prev, cap-prev-1 );
                line.mVerts[cap-1] = b;
                line.mSize--;
                i--;
            }
        }

    }


    private static boolean near( Vector3 a, Vector3 b ) {
        return Tolerence.approxEqual( a.x, b.x ) &&
               Tolerence.approxEqual( a.y, b.y ) &&
               Tolerence.approxEqual( a.z, b.z );
    }


    private static boolean lessNear( Vector3 a, double x, double y, double z ) {
        return Tolerence.approxEqual( a.x, x, 0.01, 0.01 ) &&
               Tolerence.approxEqual( a.y, y, 0.01, 0.01 ) &&
               Tolerence.approxEqual( a.z, z, 0.01, 0.01 );
    }

    /**
     * DO NOT CHANGE unless you understand the rounding behavior.
     * Implementation details on this were important.
     */
    private static void lerp( Vector3 a, Vector3 b, float p, Vector3 out ) {
        out.x = a.x + ( p * ( b.x - a.x ) );
        out.y = a.y + ( p * ( b.y - a.y ) );
        out.z = a.z + ( p * ( b.z - a.z ) );
    }

}
