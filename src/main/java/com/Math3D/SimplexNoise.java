package com.Math3D;

/**
 * Simplex Noise in 2D, 3D and 4D. Based on the example code of this paper:
 * http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf
 *
 * @author Stefan Gustavson, Linkping University, Sweden (stegu at itn dot liu dot se)
 *
 *         Slight optimizations and restructuring by
 * @author Karsten Schmidt (info at toxi dot co dot uk)
 *
 */
public class SimplexNoise {

    /**
     * Computes 2D Simplex Noise.
     *
     * @param x coordinate
     * @param y coordinate
     * @return noise mVal in range -1 ... +1.
     */
    public static double noise( double x, double y ) {
        // Noise contributions from the three corners.
        double n0 = 0, n1 = 0, n2 = 0;

        // Skew the input space to determine which simplex cell we're in
        double s = (x + y) * F2; // Hairy factor for 2D
        int i = fastfloor( x + s );
        int j = fastfloor( y + s );
        double t = (i + j) * G2;
        double x0 = x - ( i - t ); // The x,y distances from the cell origin
        double y0 = y - ( j - t );

        // For the 2D case, the SIMPLEX shape is an equilateral triangle.
        // Determine which simplex we are in.

        // Offsets for second (middle) corner of simplex in (i,j)
        int i1, j1;
        if( x0 > y0 ) {
            // lower triangle, XY order: (0,0)->(1,0)->(1,1)
            i1 = 1;
            j1 = 0;
        } else {
            // upper triangle, YX order: (0,0)->(0,1)->(1,1)
            i1 = 0;
            j1 = 1;
        }

        // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
        // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
        // c = (3-sqrt(3))/6

        // Offsets for middle corner in (x,y) unskewed
        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;

        // Offsets for last corner in (x,y) unskewed
        double x2 = x0 + G22;
        double y2 = y0 + G22;

        // Work out the hashed gradient indices of the three simplex corners
        int ii = i & 0xff;
        int jj = j & 0xff;

        // Calculate the contribution from the three corners
        double t0 = 0.5 - x0 * x0 - y0 * y0;
        if( t0 > 0 ) {
            int gi0;
            gi0 = PERM[jj];
            gi0 = PERM[ ( ii + gi0 ) & 0xFF ] % 12;
            t0 *= t0;
            n0 = t0 * t0 * dot( GRADIENTS3[gi0], x0, y0 );
            // (x,y) of GRADIENTS3 used for 2D gradient
        }

        double t1 = 0.5 - x1 * x1 - y1 * y1;
        if( t1 > 0 ) {
            int gi1;
            gi1 = PERM[ ( jj + j1 )       & 0xFF ];
            gi1 = PERM[ ( ii + i1 + gi1 ) & 0xFF ] % 12;
            t1 *= t1;
            n1 = t1 * t1 * dot( GRADIENTS3[gi1], x1, y1 );
        }

        double t2 = 0.5 - x2 * x2 - y2 * y2;
        if( t2 > 0 ) {
            int gi2;
            gi2 = PERM[ ( jj + 1 ) & 0xFF ];
            gi2 = PERM[ ( ii + 1 + gi2 ) & 0xFF ] % 12;
            t2 *= t2;
            n2 = t2 * t2 * dot( GRADIENTS3[gi2], x2, y2 );
        }

        // Add contributions from each corner to get the final noise mVal.
        // The result is scaled to return values in the interval [-1,1].
        return 70.0 * ( n0 + n1 + n2 );
    }

    /**
     * Computes 3D Simplex Noise.
     *
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @return noise mVal in range -1 ... +1
     */
    public static double noise( double x, double y, double z ) {
        // Noise contributions from the four corners.
        double n0 = 0, n1 = 0, n2 = 0, n3 = 0;

        // Skew the input space to determine which simplex cell we're in
        double s = ( x + y + z ) * F3;

        int i = fastfloor( x + s );
        int j = fastfloor( y + s );
        int k = fastfloor( z + s );

        // Very nice and simple unskew factor.
        double t  = ( i + j + k ) * G3;
        double x0 = x - ( i - t ); // The x,y,z distances from the cell origin
        double y0 = y - ( j - t );
        double z0 = z - ( k - t );
        // For the 3D case, the SIMPLEX shape is a slightly irregular tetrahedron.
        // Determine which simplex we are in.

        // Offsets for second corner of simplex in (i,j,k) coords
        int i1, j1, k1;
        // Offsets for third corner of simplex in (i,j,k) coords
        int i2, j2, k2;

        if( x0 >= y0 ) {
            if( y0 >= z0 ) {
                // X Y Z order
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } else if( x0 >= z0 ) {
                // X Z Y order
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } else {
                // Z X Y order
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            }
        } else {
            if( y0 < z0 ) {
                // Z Y X order
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } else if ( x0 < z0 ) {
                // Y Z X order
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } else {
                // Y X Z order
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            }
        }
        // A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
        // a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z),
        // and
        // a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z),
        // where
        // c = 1/6.
        double x1 = x0 - i1 + G3; // Offsets for second corner in (x,y,z) coords
        double y1 = y0 - j1 + G3;
        double z1 = z0 - k1 + G3;

        double x2 = x0 - i2 + F3; // Offsets for third corner in (x,y,z)
        double y2 = y0 - j2 + F3;
        double z2 = z0 - k2 + F3;

        double x3 = x0 - 0.5; // Offsets for last corner in (x,y,z)
        double y3 = y0 - 0.5;
        double z3 = z0 - 0.5;
        // Work out the hashed gradient indices of the four simplex corners
        int ii = i & 0xff;
        int jj = j & 0xff;
        int kk = k & 0xff;

        // Calculate the contribution from the four corners
        double t0 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
        if( t0 > 0 ) {
            int gi0;
            gi0 = PERM[ kk              ];
            gi0 = PERM[(jj + gi0) & 0xFF];
            gi0 = PERM[(ii + gi0) & 0xFF] % 12;
            t0 *= t0;
            n0 = t0 * t0 * dot( GRADIENTS3[gi0], x0, y0, z0);
        }

        double t1 = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
        if( t1 > 0 ) {
            int gi1;
            gi1 = PERM[ (kk + k1      ) & 0xFF ];
            gi1 = PERM[ (jj + j1 + gi1) & 0xFF ];
            gi1 = PERM[ (ii + i1 + gi1) & 0xFF ] % 12;
            t1 *= t1;
            n1 = t1 * t1 * dot( GRADIENTS3[gi1], x1, y1, z1 );
        }
        double t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
        if( t2 > 0 ) {
            int gi2;
            gi2 = PERM[(kk + k2)       & 0xFF];
            gi2 = PERM[(jj + j2 + gi2) & 0xFF];
            gi2 = PERM[(ii + i2 + gi2) & 0xFF] % 12;
            t2 *= t2;
            n2 = t2 * t2 * dot( GRADIENTS3[gi2], x2, y2, z2 );
        }
        double t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
        if( t3 > 0 ) {
            int gi3;
            gi3 = PERM[(kk + 1)       & 0xFF];
            gi3 = PERM[(jj + 1 + gi3) & 0xFF];
            gi3 = PERM[(ii + 1 + gi3) & 0xFF] % 12;
            t3 *= t3;
            n3 = t3 * t3 * dot( GRADIENTS3[gi3], x3, y3, z3 );
        }
        // Add contributions from each corner to get the final noise mVal.
        // The result is scaled to stay just inside [-1,1]
        return 32.0 * ( n0 + n1 + n2 + n3 );
    }

    /**
     * Computes 4D Simplex Noise.
     *
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @param w coordinate
     * @return noise mVal in range -1 ... +1
     */
    public static double noise( double x, double y, double z, double w ) {
        // The skewing and unskewing factors are hairy again for the 4D case

        // Noise contributions from five corners.
        double n0 = 0;
        double n1 = 0;
        double n2 = 0;
        double n3 = 0;
        double n4 = 0;

        // Skew the (x,y,z,w) space to determine which cell of 24 simplices
        double s = ( x + y + z + w ) * F4; // Factor for 4D skewing
        int i = fastfloor( x + s );
        int j = fastfloor( y + s );
        int k = fastfloor( z + s );
        int l = fastfloor( w + s );
        double t = (i + j + k + l) * G4; // Factor for 4D unskewing
        double x0 = x - ( i - t ); // The x,y,z,w distances from the cell origin
        double y0 = y - ( j - t );
        double z0 = z - ( k - t );
        double w0 = w - ( l - t );

        // For the 4D case, the SIMPLEX is a 4D shape.
        // To find which of the 24 possible simplices we're in, we need to
        // determine the magnitude ordering of x0, y0, z0 and w0.
        // The method below is a good way of finding the ordering of x,y,z,w and
        // then find the correct traversal order for the simplex were in.
        // First, six pair-wise comparisons are performed between each possible
        // pair of the four coordinates, and the results are used to add up
        // binary bits for an integer index.
        int c = 0;
        if( x0 > y0 ) {
            c = 0x20;
        }
        if( x0 > z0 ) {
            c |= 0x10;
        }
        if( y0 > z0 ) {
            c |= 0x08;
        }
        if( x0 > w0 ) {
            c |= 0x04;
        }
        if( y0 > w0 ) {
            c |= 0x02;
        }
        if( z0 > w0 ) {
            c |= 0x01;
        }
        int i1, j1, k1, l1; // The integer offsets for the second simplex corner
        int i2, j2, k2, l2; // The integer offsets for the third simplex corner
        int i3, j3, k3, l3; // The integer offsets for the fourth simplex corner

        // SIMPLEX[c] is a 4-vector with the numbers 0, 1, 2 and 3 in some
        // order. Many values of c will never occur, since e.g. x>y>z>w makes
        // x<z, y<w and x<w impossible. Only the 24 indices which have non-zero
        // entries make any sense. We use a thresholding to set the coordinates
        // in turn from the largest magnitude. The number 3 in the "simplex"
        // array is at the position of the largest coordinate.
        int[] sc = SIMPLEX[c];
        i1 = sc[0] >> 2;
        j1 = sc[1] >> 2;
        k1 = sc[2] >> 2;
        l1 = sc[3] >> 2;
        // The number 2 in the "SIMPLEX" array is at the second largest coordinate.
        i2 = sc[0] >> 1 & 1;
        j2 = sc[1] >> 1 & 1;
        k2 = sc[2] >> 1 & 1;
        l2 = sc[3] >> 1 & 1;
        // The number 1 in the "SIMPLEX" array is at the second smallest coordinate.
        i3 = sc[0] & 1;
        j3 = sc[1] & 1;
        k3 = sc[2] & 1;
        l3 = sc[3] & 1;
        // The fifth corner has all coordinate offsets = 1, so no need to look
        // that up.

        // Offsets for second corner in (x,y,z,w)
        double x1 = x0 - i1 + G4;
        double y1 = y0 - j1 + G4;
        double z1 = z0 - k1 + G4;
        double w1 = w0 - l1 + G4;

        double x2 = x0 - i2 + G42; // Offsets for third corner in (x,y,z,w)
        double y2 = y0 - j2 + G42;
        double z2 = z0 - k2 + G42;
        double w2 = w0 - l2 + G42;

        double x3 = x0 - i3 + G43; // Offsets for fourth corner in (x,y,z,w)
        double y3 = y0 - j3 + G43;
        double z3 = z0 - k3 + G43;
        double w3 = w0 - l3 + G43;

        double x4 = x0 + G44; // Offsets for last corner in (x,y,z,w)
        double y4 = y0 + G44;
        double z4 = z0 + G44;
        double w4 = w0 + G44;

        // Work out the hashed gradient indices of the five simplex corners
        int ii = i & 0xff;
        int jj = j & 0xff;
        int kk = k & 0xff;
        int ll = l & 0xff;

        // Calculate the contribution from the five corners
        double t0 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0 - w0 * w0;
        if( t0 > 0 ) {
            t0 *= t0;
            int gi0;
            gi0 = PERM[ ll              ];
            gi0 = PERM[(kk + gi0) & 0xFF];
            gi0 = PERM[(jj + gi0) & 0xFF];
            gi0 = PERM[(ii + gi0) & 0xFF] % 32;
            n0 = t0 * t0 * dot( GRADIENTS4[gi0], x0, y0, z0, w0 );
        }

        double t1 = 0.6 - x1 * x1 - y1 * y1 - z1 * z1 - w1 * w1;
        if( t1 > 0 ) {
            t1 *= t1;
            int gi1;
            gi1 = PERM[(ll + l1      ) & 0xFF];
            gi1 = PERM[(kk + k1 + gi1) & 0xFF];
            gi1 = PERM[(jj + j1 + gi1) & 0xFF];
            gi1 = PERM[(ii + i1 + gi1) & 0xFF] % 32;
            n1 = t1 * t1 * dot( GRADIENTS4[gi1], x1, y1, z1, w1);
        }

        double t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
        if( t2 > 0 ) {
            t2 *= t2;
            int gi2;
            gi2 = PERM[(ll + l2      ) & 0xFF];
            gi2 = PERM[(kk + k2 + gi2) & 0xFF];
            gi2 = PERM[(jj + j2 + gi2) & 0xFF];
            gi2 = PERM[(ii + i2 + gi2) & 0xFF] % 32;
            n2 = t2 * t2 * dot( GRADIENTS4[gi2], x2, y2, z2, w2 );
        }
        double t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
        if( t3 > 0 ) {
            t3 *= t3;
            int gi3;
            gi3 = PERM[(ll + l3      ) & 0xFF];
            gi3 = PERM[(kk + k3 + gi3) & 0xFF];
            gi3 = PERM[(jj + j3 + gi3) & 0xFF];
            gi3 = PERM[(ii + i3 + gi3) & 0xFF] % 32;
            n3 = t3 * t3 * dot( GRADIENTS4[gi3], x3, y3, z3, w3);
        }
        double t4 = 0.6 - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
        if (t4 > 0) {
            t4 *= t4;
            int gi4;
            gi4 = PERM[(ll + 1      ) & 0xFF];
            gi4 = PERM[(kk + 1 + gi4) & 0xFF];
            gi4 = PERM[(jj + 1 + gi4) & 0xFF];
            gi4 = PERM[(ii + 1 + gi4) & 0xFF] % 32;
            n4 = t4 * t4 * dot( GRADIENTS4[gi4], x4, y4, z4, w4);
        }
        // Sum up and scale the result to cover the range [-1,1]
        return 27.0 * ( n0 + n1 + n2 + n3 + n4 );
    }


    private static final double SQRT3 = Math.sqrt( 3.0 );
    private static final double SQRT5 = Math.sqrt( 5.0 );

    /**
     * Skewing and unskewing factors for 2D, 3D and 4D, some of them
     * pre-multiplied.
     */
    private static final double F2  = 0.5 * (SQRT3 - 1.0);
    private static final double G2  = (3.0 - SQRT3) / 6.0;
    private static final double G22 = G2 * 2.0 - 1;

    private static final double F3 = 1.0 / 3.0;
    private static final double G3 = 1.0 / 6.0;

    private static final double F4  = (SQRT5 - 1.0) / 4.0;
    private static final double G4  = (5.0 - SQRT5) / 20.0;
    private static final double G42 = G4 * 2.0;
    private static final double G43 = G4 * 3.0;
    private static final double G44 = G4 * 4.0 - 1.0;

    /**
     * Gradient vectors for 3D (pointing to mid points of all edges of a unit cube)
     */
    private static final double[][] GRADIENTS3 = { {  1,  1,  0 }, { -1,  1,  0 }, {  1, -1,  0 },
                                                   { -1, -1,  0 }, {  1,  0,  1 }, { -1,  0,  1 },
                                                   {  1,  0, -1 }, { -1,  0, -1 }, {  0,  1,  1 },
                                                   {  0, -1,  1 }, {  0,  1, -1 }, {  0, -1, -1 } };

    /**
     * Gradient vectors for 4D (pointing to mid points of all edges of a unit 4D hypercube)
     */
    private static final double[][] GRADIENTS4 = { {  0,  1,  1,  1 }, {  0,  1,  1, -1 }, {  0,  1, -1,  1 },
                                                   {  0,  1, -1, -1 }, {  0, -1,  1,  1 }, {  0, -1,  1, -1 },
                                                   {  0, -1, -1,  1 }, {  0, -1, -1, -1 }, {  1,  0,  1,  1 },
                                                   {  1,  0,  1, -1 }, {  1,  0, -1,  1 }, {  1,  0, -1, -1 },
                                                   { -1,  0,  1,  1 }, { -1,  0,  1, -1 }, { -1,  0, -1,  1 },
                                                   { -1,  0, -1, -1 }, {  1,  1,  0,  1 }, {  1,  1,  0, -1 },
                                                   {  1, -1,  0,  1 }, {  1, -1,  0, -1 }, { -1,  1,  0,  1 },
                                                   { -1,  1,  0, -1 }, { -1, -1,  0,  1 }, { -1, -1,  0, -1 },
                                                   {  1,  1,  1,  0 }, {  1,  1, -1,  0 }, {  1, -1,  1,  0 },
                                                   {  1, -1, -1,  0 }, { -1,  1,  1,  0 }, { -1,  1, -1,  0 },
                                                   { -1, -1,  1,  0 }, { -1, -1, -1,  0 } };

    /**
     * Permutation table
     */
    private static final int[] PERM = { 151, 160, 137, 91, 90, 15, 131, 13, 201,
                                        95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37,
                                        240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62,
                                        94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56,
                                        87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139,
                                        48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133,
                                        230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25,
                                        63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200,
                                        196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3,
                                        64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255,
                                        82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
                                        223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153,
                                        101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79,
                                        113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242,
                                        193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249,
                                        14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204,
                                        176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222,
                                        114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };

    /**
     * A lookup table to traverse the simplex around a given point in 4D.
     * Details can be found where this table is used, in the 4D noise method.
     */
    private static final int[][] SIMPLEX = { { 0, 1, 3, 7 }, { 0, 1, 7, 3 }, { 0, 0, 0, 0 }, { 0, 3, 7, 1 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 3, 7, 0 },
                                             { 0, 3, 1, 7 }, { 0, 0, 0, 0 }, { 0, 7, 1, 3 }, { 0, 7, 3, 1 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 7, 3, 0 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 1, 3, 0, 7 }, { 0, 0, 0, 0 }, { 1, 7, 0, 3 }, { 0, 0, 0, 0 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 7, 0, 1 }, { 3, 7, 1, 0 },
                                             { 1, 0, 3, 7 }, { 1, 0, 7, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 0, 0, 0, 0 }, { 3, 0, 7, 1 }, { 0, 0, 0, 0 }, { 3, 1, 7, 0 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 3, 0, 1, 7 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 7, 0, 1, 3 }, { 7, 0, 3, 1 }, { 0, 0, 0, 0 }, { 7, 1, 3, 0 },
                                             { 3, 1, 0, 7 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                                             { 7, 1, 0, 3 }, { 0, 0, 0, 0 }, { 7, 3, 0, 1 }, { 7, 3, 1, 0 } };


    /**
     * Computes dot product in 2D.
     *
     * @param g 2-vector (grid mOffset)
     * @return dot product
     */
    private static double dot( double g[], double x, double y ) {
        return g[0] * x + g[1] * y;
    }

    /**
     * Computes dot product in 3D.
     *
     * @param g 3-vector (grid mOffset)
     * @return dot product
     */
    private static double dot( double g[], double x, double y, double z ) {
        return g[0] * x + g[1] * y + g[2] * z;
    }

    /**
     * Computes dot product in 4D.
     *
     * @param g 4-vector (grid mOffset)
     * @return dot product
     */
    private static double dot( double g[], double x, double y, double z, double w ) {
        return g[0] * x + g[1] * y + g[2] * z + g[3] * w;
    }

    /**
     * This method is a *lot* faster than using (int)Math.floor(x).
     *
     * @param x mVal to be floored
     */
    private static int fastfloor( double x ) {
        return x >= 0 ? (int)x : (int)x - 1;
    }

}

