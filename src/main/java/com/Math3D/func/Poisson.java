/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;

import java.util.Random;


/**
 * Generates a Poisson process.
 */
public class Poisson {

    private final Random mRand;
    private double mTime;


    public Poisson() {
        this( new Random(), 0.0 );
    }


    public Poisson( Random optRand, double time ) {
        mRand = optRand == null ? new Random() : optRand;
        mTime = time;
    }


    public double time() {
        return mTime;
    }


    public void time( double time ) {
        mTime = time;
    }

    /**
     * @param intensity  Rate of event occurences.
     * @return Time of the next event
     */
    public double next( double intensity ) {
        mTime += sampleDuration( mRand.nextDouble(), intensity );
        return mTime;
    }


    public static double sampleDuration( double unitRandom, double intensity ) {
        return -Math.log( 1.0 - unitRandom ) / intensity;
    }

}
