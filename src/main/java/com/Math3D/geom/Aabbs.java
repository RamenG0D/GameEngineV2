/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import java.util.Arrays;


/**
 * Utility class for managing Axis-Aligned Bounding-Boxes. 
 * <p>
 * I guess this class is empty, right now. Eventually, I will put stuff in here.
 * Aabb arrays are meant to be a fast, mutable alternative to Manifold.Cube
 * objects, which are misnamed anyway.
 * 
 * @author decamp
 * @deprecated Use Box
 */
@Deprecated public final class Aabbs {

    public static Aabb arrayToAabb( double[] aabb ) {
        return Aabb.fromEdges( (float)aabb[0], (float)aabb[1], (float)aabb[2], (float)aabb[3], (float)aabb[4], (float)aabb[5] );
    }


    public static void aabbToArray( Aabb cube, double[] outAabb ) {
        outAabb[0] = cube.minX();
        outAabb[1] = cube.minY();
        outAabb[2] = cube.minZ();
        outAabb[3] = cube.maxX();
        outAabb[4] = cube.maxY();
        outAabb[5] = cube.maxZ();
    }


    public static void boundPoints( double[][] points, int off, int len, double[] out ) {
        if( len == 0 ) {
            Arrays.fill( out, Double.NaN );
            return;
        }

        double[] v = points[off];
        out[0] = out[3] = v[0];
        out[1] = out[4] = v[1];
        out[2] = out[5] = v[2];

        for( int i = 1; i < len; i++ ) {
            v = points[off + i];

            if( v[0] < out[0] ) {
                out[0] = v[0];
            } else if( v[0] > out[3] ) {
                out[3] = v[0];
            }

            if( v[1] < out[1] ) {
                out[1] = v[1];
            } else if( v[1] > out[4] ) {
                out[4] = v[1];
            }

            if( v[2] < out[2] ) {
                out[2] = v[2];
            } else if( v[2] > out[5] ) {
                out[5] = v[2];
            }
        }

    }


    public static boolean contains( double[] aabb, double x, double y, double z ) {
        if( x < aabb[0] || x > aabb[3] )
            return false;

        if( y < aabb[1] || y > aabb[4] )
            return false;

        if( z < aabb[2] || z > aabb[5] )
            return false;

        return true;
    }

    /**
     * Haha! A clipping method that works! Provided with an array of COPLANAR
     * vertices, and an AABB, this method will give you the geometric
     * intersection.
     * 
     * @param vertLoop  Array of length-3 vertices.
     * @param vertCount Number of vertices
     * @param clipAabb  Clipping bounds
     * @param out pre-allocated loop object to hold clipped results.
     * @return true iff intersection is found with non zero surface area.
     *         (Sorry; this statement isn't precisely true because I didn't
     *         formalize all the boundary conditions)
     */
    public static boolean clipPlanarToAabb( double[][] vertLoop, int vertOff, int vertCount, double[] clipAabb, Loop out ) {
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
                    vertCount = retainLoopSection( verts, vertCount, m0, m1, axis, min );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, m0, m1, axis, min );
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
                    vertCount = retainLoopSection( verts, vertCount, n0, n1, axis, max );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, n0, n1, axis, max );
                }
            }
        }

        out.mLength = vertCount;
        return true;
    }


    private static int removeLoopSection( double[][] v, int count, int start, int stop, int axis, double pos ) {

        // Make sure there's enough room between start and stop vertices if
        // they're adjacent.
        if( start + 1 == stop ) {
            double[] temp = v[v.length - 1];

            for( int i = v.length - 1; i >= stop; i-- ) {
                v[i] = v[i - 1];
            }

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

        if( gap == 0 )
            return count;

        count -= gap;

        for( int i = start + 1; i < count; i++ ) {
            double[] temp = v[i];
            v[i] = v[i + gap];
            v[i + gap] = temp;
        }

        return count;
    }


    private static int retainLoopSection( double[][] v, int count, int start, int stop, int axis, double pos ) {

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


    
    private Aabbs() {}

}
