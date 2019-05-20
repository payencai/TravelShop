package com.tec.travelagency.utils;

import java.util.Random;

/**
 * yangqiangyu on 14/11/2016 13:11
 */
/**
 * <p><code>JVMRandom</code> is a wrapper that supports all possible
 * Random methods via the {@link Math#random()} method
 * and its system-wide {@link Random} object.</p>
 * <p>
 * It does this to allow for a Random class in which the seed is
 * shared between all members of the class - a better name would
 * have been SharedSeedRandom.
 * <p>
 * <b>N.B.</b> the current implementation overrides the methods
 * {@link Random#nextInt(int)} and {@link Random#nextLong()}
 * to produce positive numbers ranging from 0 (inclusive)
 * to MAX_VALUE (exclusive).
 *
 * @since 2.0
 * @version $Id: JVMRandom.java 911986 2010-02-19 21:19:05Z niallp $
 */
public final class JVMRandom extends Random {

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1L;

    private static final Random SHARED_RANDOM = new Random();

    /**
     * Ensures that only the parent constructor can call reseed.
     */
    private boolean constructed = false;

    /**
     * Constructs a new instance.
     */
    public JVMRandom() {
        this.constructed = true;
    }

    /**
     * Unsupported in 2.0.
     *
     * @param seed ignored
     * @throws UnsupportedOperationException
     */
    public synchronized void setSeed(long seed) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Unsupported in 2.0.
     *
     * @return Nothing, this method always throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     */
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported in 2.0.
     *
     * @param byteArray ignored
     * @throws UnsupportedOperationException
     */
    public void nextBytes(byte[] byteArray) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed int value
     * from the Math.random() sequence.</p>
     * Identical to <code>nextInt(Integer.MAX_VALUE)</code>
     * <p>
     * <b>N.B. All values are >= 0.<b>
     * </p>
     * @return the random int
     */
    public int nextInt() {
        return nextInt(Integer.MAX_VALUE);
    }

    /**
     * <p>Returns a pseudorandom, uniformly distributed int value between
     * <code>0</code> (inclusive) and the specified value (exclusive), from
     * the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random int
     * @throws IllegalArgumentException when <code>n &lt;= 0</code>
     */
    public int nextInt(int n) {
        return SHARED_RANDOM.nextInt(n);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed long value
     * from the Math.random() sequence.</p>
     * Identical to <code>nextLong(Long.MAX_VALUE)</code>
     * <p>
     * <b>N.B. All values are >= 0.<b>
     * </p>
     * @return the random long
     */
    public long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }


    /**
     * <p>Returns a pseudorandom, uniformly distributed long value between
     * <code>0</code> (inclusive) and the specified value (exclusive), from
     * the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random long
     * @throws IllegalArgumentException when <code>n &lt;= 0</code>
     */
    public static long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Upper bound for nextInt must be positive"
            );
        }
        // Code adapted from Harmony Random#nextInt(int)
        if ((n & -n) == n) { // n is power of 2
            // dropping lower order bits improves behaviour for low values of n
            return next63bits() >> 63 // drop all the bits
                    - bitsRequired(n-1); // except the ones we need
        }
        // Not a power of two
        long val;
        long bits;
        do { // reject some values to improve distribution
            bits = next63bits();
            val = bits % n;
        } while (bits - val + (n - 1) < 0);
        return val;
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed boolean value
     * from the Math.random() sequence.</p>
     *
     * @return the random boolean
     */
    public boolean nextBoolean() {
        return SHARED_RANDOM.nextBoolean();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the Math.random()
     * sequence.</p>
     *
     * @return the random float
     */
    public float nextFloat() {
        return SHARED_RANDOM.nextFloat();
    }

    /**
     * <p>Synonymous to the Math.random() call.</p>
     *
     * @return the random double
     */
    public double nextDouble() {
        return SHARED_RANDOM.nextDouble();
    }

    /**
     * Get the next unsigned random long
     * @return unsigned random long
     */
    private static long next63bits(){
        // drop the sign bit to leave 63 random bits
        return SHARED_RANDOM.nextLong() & 0x7fffffffffffffffL;
    }

    /**
     * Count the number of bits required to represent a long number.
     *
     * @param num long number
     * @return number of bits required
     */
    private static int bitsRequired(long num){
        // Derived from Hacker's Delight, Figure 5-9
        long y=num; // for checking right bits
        int n=0; // number of leading zeros found
        while(true){
            // 64 = number of bits in a long
            if (num < 0) {
                return 64-n; // no leading zeroes left
            }
            if (y == 0) {
                return n; // no bits left to check
            }
            n++;
            num=num << 1; // check leading bits
            y=y >> 1; // check trailing bits
        }
    }
}