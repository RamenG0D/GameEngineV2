/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;


/**
 * EaseFuncs provides many kinds of easing curves, which are used in interpolated animation,
 * or "tweening." 
 * <p>
 * Easing functions follow f(0) -> 0 and f(1) -> 1. This class 
 * provides three types of easing functions for most equations: <ul>
 * <li>An "ease" or "ease in/out" function that begins and ends with zero speed (zero first derivative at 0.0 and 1.0 ).
 * <li>An "ease in" function that begins with zero speed and end with positive speed. <br>
 * <li>An "ease out" function that begins with positive speed and end with zero speed. <br>
 * </ul>
 * Easing functions are not necessarily monotonic, continuous, or restricted to 
 * the range [0,1], although most are.
 * <p>
 * Outside of the domain [0,1], return values of these functions are NOT DEFINED. These functions
 * MAY NOT clamp parameters. 
 *  
 * @author decamp
 */
public final class EaseFuncs {
    
    public static final Function11 LINEAR = new Function11() {
        public double apply( double t ) {
            return t;
        }
    };
        
    public static final Function11 COS = new Function11() {
        public double apply( double t ) {
            return cos( t );
        }
    };
    
    public static final Function11 COS_IN = new Function11() {
        public double apply( double t ) {
            return cosIn( t );
        }
    };
    
    public static final Function11 COS_OUT = new Function11() {
        public double apply( double t ) {
            return cosOut( t );
        }
    };
    
    public static final Function11 SMOOTH = new Function11() {
        public double apply( double t ) {
            return smooth( t );
        }
    };
    
    public static final Function11 SMOOTH_IN = new Function11() {
        public double apply( double t ) {
            return smoothIn( t );
        }
    };
    
    public static final Function11 SMOOTH_OUT = new Function11() {
        public double apply( double t ) {
            return smoothOut( t );
        }
    };
    
    public static final Function11 SMOOTHER = new Function11() {
        public double apply( double t ) {
            return smoother( t );
        }
    };
    
    public static final Function11 SMOOTHER_IN = new Function11() {
        public double apply( double t ) {
            return smootherIn( t );
        }
    };
    
    public static final Function11 SMOOTHER_OUT = new Function11() {
        public double apply( double t ) {
            return smootherOut( t );
        }
    };
    
    public static final Function11 POW2 = new Function11() {
        public double apply( double t ) {
            return pow2( t );
        }
    };
    
    public static final Function11 POW2_IN = new Function11() {
        public double apply( double t ) {
            return pow2In( t );
        }
    };
    
    public static final Function11 POW2_OUT = new Function11() {
        public double apply( double t ) {
            return pow2Out( t );
        }
    };
    
    public static final Function11 POW3 = new Function11() {
        public double apply( double t ) {
            return pow3( t );
        }
    };
    
    public static final Function11 POW3_IN = new Function11() {
        public double apply( double t ) {
            return pow3In( t );
        }
    };
    
    public static final Function11 POW3_OUT = new Function11() {
        public double apply( double t ) {
            return pow3Out( t );
        }
    };
    
    public static final Function11 CIRC = new Function11() {
        public double apply( double t ) {
            return circ( t );
        }
    };
    
    public static final Function11 CIRC_IN = new Function11() {
        public double apply( double t ) {
            return circIn( t );
        }
    };
    
    public static final Function11 CIRC_OUT = new Function11() {
        public double apply( double t ) {
            return circOut( t );
        }
    };
    
    public static Function11 newExp( final double coef ) {
        final double scaleIn  = 0.5 / ( Math.exp(  coef ) - 1.0 );
        final double scaleOut = 0.5 / ( Math.exp( -coef ) - 1.0 );
        
        return new Function11() {
            public double apply( double t ) {
                if( t <= 0.5 ) {
                    return scaleIn * ( Math.exp( t * coef * 2.0 ) - 1.0 );
                } else {
                    //TODO : Should only need single scale factor, here.
                    t = 2.0 * t - 1.0;
                    return scaleOut * ( Math.exp( t * -coef ) - 1.0 ) + 0.5;
                }
            }
        };
    }
    
    public static Function11 newExpIn( final double coef ) {
        final double scale = 1.0 / ( Math.exp( coef ) - 1.0 );
        
        return new Function11() {
            public double apply( double t ) {
                return scale * ( Math.exp( t * coef ) - 1.0 );
            }
        };
    }
    
    public static Function11 newExpOut( double coef ) {
        return newExpIn( -coef );
    }
        
    public static Function11 newPow( final double exp ) {
        return new Function11() {
            public double apply( double t ) {
                return pow( exp, t );
            }
        };
    }
    
    public static Function11 newPowIn( final double exp ) {
        return new Function11() {
            public double apply( double t ) {
                return powIn( exp, t );
            }
        };
    }
    
    public static Function11 newPowOut( final double exp ) {
        return new Function11() {
            public double apply( double t ) {
                return powOut( exp, t );
            }
        };
    }
    
    public static Function11 newSpring( double cycles, final double decayPow ) {
        final double w = TWO_PI * cycles;
        
        return new Function11() {
            public double apply( double t ) {
                if( t < 0.5 ) {
                    t *= 2.0;
                    double m = Math.pow( t, decayPow );
                    return m * Math.cos( w * ( 1 - t ) ) * 0.5;
                } else {
                    t = t * 2.0 - 1.0;
                    double m = Math.pow( (1.0 - t ), decayPow );
                    return 1.0 - 0.5 * m * Math.cos( w * t );
                }
            }
        };
    }
    
    public static Function11 newSpringIn( double cycles, final double decayPow ) {
        final double w = TWO_PI * cycles;
        
        return new Function11() {
            public double apply( double t ) {
                double m = Math.pow( t, decayPow );
                return m * Math.cos( w * ( 1 - t ) );
            }
        };
    }
    
    public static Function11 newSpringOut( double cycles, final double decayPow ) {
        final double w = TWO_PI * cycles;
        
        return new Function11() {
            public double apply( double t ) {
                double m = Math.pow( (1.0 - t), decayPow );
                return 1.0 - m * Math.cos( w * t );
            }
        };
    }
    
    
    
    /**
     * "smoothStep" function <code>t1 = t0 * t0 * ( 3 - 2 * t0 )</code>.
     * "smoothStep" is a polynomial approximation of the cosine curve, but may be computed
     * ten times faster. 
     */
    public static double smooth( double t ) {
        return t * t * ( 3.0 - 2.0 * t );
    }
    
    
    public static double smoothIn( double t ) {
        return 0.5 * t * t * ( 3.0 - t );
    }
    
    
    public static double smoothOut( double t ) {
        t += 1.0;
        return 0.5 * t * t * ( 3.0 - t ) - 1.0;
    }
    
    /**
     * "smootherStep" function <code>t1 = t0 * t0 * t0 * ( t0 * ( t0 * 6 - 15 ) + 10 )</code>.
     * While "smootherStep" is not as close to cosine as "smoothStep," 
     * it provides zero acceleration at t0 = 0 and t0 = 1, making it a better choice when performing
     * back-to-back tweens.
     */
    public static double smoother( double t ) {
        return t * t * t * ( t * ( t * 6.0 - 15.0 ) + 10.0 );
    }
    
    
    public static double smootherIn( double t ) {
        return 0.0625 * t * t * t * ( t * ( t * 6.0 - 30.0 ) + 40.0 );
    }
    
    
    public static double smootherOut( double t ) {
        t += 1.0;
        return 0.0625 * t * t * t * ( t * ( t * 6.0 - 30.0 ) + 40.0 ) - 1.0;
    }
    
    /**
     * Cosine easing function. Derivative is zero at 0.0 and 1.0.
     * This is generally computationally intensive, and in most cases,
     * <code>smooth()</code> should be used instead.
     */
    public static double cos( double t ) {
        return ( 1.0 - Math.cos( t * Math.PI ) ) * 0.5;
    }
    
    
    public static double cosIn( double t ) {
        return 1.0 - Math.cos( t * Math.PI * 0.5 );
    }
    
    
    public static double cosOut( double t ) {
        return -Math.cos( ( t * 0.5 + 0.5 ) * Math.PI );
    }
    
    
    public static double pow2( double t ) {
        if( t <= 0.5 ) {
            return 2.0 * t * t;
        } else {
            return 2.0 * t * ( 2.0 - t ) - 1.0; 
        }
    }
    
    
    public static double pow2In( double t ) {
        return t * t;
    }
    
    
    public static double pow2Out( double t ) {
        return t * ( 2.0 - t );
    }
    
    
    public static double pow3( double t ) {
        if( t <= 0.5 ) {
            return 4.0 * t * t * t;
        } else {
            t = 1.0 - t;
            return 1.0 - 4.0 * t * t * t;
        }
    }
    
    
    public static double pow3In( double t ) {
        return t * t * t;
    }
    
    
    public static double pow3Out( double t ) {
        t = 1.0 - t;
        return 1.0 - t * t * t;
    }

    
    public static double pow( double exp, double t ) {
        if( t <= 0.5 ) {
            return 0.5 * Math.pow( 2.0 * t, exp );
        } else {
            return 1.0 - 0.5 * Math.pow( 2.0 - 2.0 * t, exp );
        }
    }
    
    
    public static double powIn( double exp, double t ) {
        return Math.pow( t, exp );
    }
    
    
    public static double powOut( double exp, double t ) {
        t = 1.0 - t;
        return 1.0 - Math.pow( t, exp );
    }
    
    
    public static double circ( double t ) {
        if( t <= 0.5 ) {
            return 0.5 - Math.sqrt( 0.25 - t * t );
        } else {
            return 0.5 + Math.sqrt( ( t - 1.5) * ( 0.5 - t ) );
        }
    }
    
    
    public static double circIn( double t ) {
        return 1.0 - Math.sqrt( 1.0 - t * t );
    }

    
    public static double circOut( double t ) {
        return Math.sqrt( t * ( 2.0 - t ) ); 
    }
    
    
    public static double exp( double coef, double t ) {
        if( t <= 0.5 ) {
            return 0.5 * ( Math.exp( t * coef * 2.0 ) - 1.0 ) / ( Math.exp( coef ) - 1.0 );
        } else {
            t = 2.0 * t - 1.0;
            return 0.5 * ( Math.exp( t * -coef ) - 1.0 ) / ( Math.exp( -coef ) - 1.0 ) + 0.5;
        }
    }
    
    
    public static double expIn( double coef, double t ) {
        return ( Math.exp( t * coef ) - 1.0 ) / ( Math.exp( coef ) - 1.0 );
    }
    
    
    public static double expOut( double coef, double t ) {
        return expIn( -coef, t );
    }
    

    public static double spring( double cycles, double decayPow, double t ) {
        if( t < 0.5 ) {
            t *= 2.0;
            double m = Math.pow( t, decayPow );
            return m * Math.cos( cycles * TWO_PI * ( 1 - t ) ) * 0.5;
        } else {
            t = t * 2.0 - 1.0;
            double m = Math.pow( (1.0 - t ), decayPow );
            return 1.0 - 0.5 * m * Math.cos( cycles * TWO_PI * t );
        }
    }
    
    
    public static double springIn( double cycles, double decayPow, double t ) {
        double m = Math.pow( t, decayPow );
        return m * Math.cos( cycles * TWO_PI * ( 1 - t ) );
    }
    
    
    public static double springOut( double cycles, double decayPow, double t ) {
        double m = Math.pow( (1.0 - t), decayPow );
        return 1.0 - m * Math.cos( cycles * TWO_PI * t );
    }
    

    
    private static final double TWO_PI = Math.PI * 2.0;
    
    
    private EaseFuncs() {}
    
}
