package com.demos;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicLong;
import com.utils.App;

public class TvStatic extends App {

    public TvStatic() {super();}

    public static void main(String[] args) {
        new TvStatic().run();
    }

    @Override
    public void update(float delta) {}

    @Override
    public void render() {
        Random rand = new Random();
        //
        for (int r = 0; r < getScreen().getWidth(); r++) {
            for (int c = 0; c < getScreen().getHeight(); c++) {
                getScreen().setRGB(r, c, new Color(rand.nextInt(0, 255), rand.nextInt(0, 255), rand.nextInt(0, 255)).getRGB());
            }
        }
    }

    @Override
    public void input() {}
    //
    private static class Random {
        private final AtomicLong seed;
        private static final long multiplier = 0x5DEECE66DL;
        private static final long addend = 0xBL;
        private static final long mask = (1L << 48) - 1;
        private static final String BAD_BOUND = "bound must be positive";
        //
        private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
        /**
         * Creates a new random number generator. This constructor sets
         * the seed of the random number generator to a value very likely
         * to be distinct from any other invocation of this constructor.
         */
        public Random() {
            this(seedUniquifier() ^ System.nanoTime());
        }
        //
        public Random(long seed) {
            if (getClass() == Random.class)
                this.seed = new AtomicLong(initialScramble(seed));
            else {
                // subclass might have overridden setSeed
                this.seed = new AtomicLong();
                setSeed(seed);
            }
        }
        //
        public synchronized void setSeed(long seed) {
            this.seed.set(initialScramble(seed));
        }
        //
        private static long initialScramble(long seed) {
            return (seed ^ multiplier) & mask;
        }
        //
        private static long seedUniquifier() {
            for (;;) {
                long current = seedUniquifier.get();
                long next = current * 1181783497276652981L;
                if (seedUniquifier.compareAndSet(current, next))
                    return next;
            }
        }
        //
        protected int next(int bits) {
            long oldseed, nextseed;
            AtomicLong seed = this.seed;
            do {
                oldseed = seed.get();
                nextseed = (oldseed * multiplier + addend) & mask;
            } while (!seed.compareAndSet(oldseed, nextseed));
            return (int)(nextseed >>> (48 - bits));
        }
        //
        public int nextInt() {
            return next(32);
        }
        //
        int nextInt(int origin, int bound) {
            int n = bound - origin;
            if (n > 0) {
                return nextInt(n) + origin;
            }
            else {  // range not representable as int
                int r;
            do {
                r = nextInt();
            } while (r < origin || r >= bound);
                return r;
            }
        }
        //
        public int nextInt(int bound) {
            if (bound <= 0)
                throw new IllegalArgumentException(BAD_BOUND);
            int r = next(31);
            int m = bound - 1;
            if ((bound & m) == 0)  // i.e., bound is a power of 2
                r = (int)((bound * (long)r) >> 31);
            else { // reject over-represented candidates
                for (int u = r;
                     u - (r = u % bound) + m < 0;
                     u = next(31))
                    ;
            }
            return r;
        }
    }

    @Override
    public void deprecatedGraphics(float delta) {
        //
    }
}
