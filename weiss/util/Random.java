package weiss.util;

// Random class
//
// CONSTRUCTION: with (a) no initializer or (b) an integer
//     that specifies the initial state of the generator.
//     This random number generator is really only 31 bits,
//     so it is weaker than the one in java.util.
//
// ******************PUBLIC OPERATIONS*********************
//     Return a random number according to some distribution:
// int nextInt( )                          --> Uniform, [1 to 2^31-1]
// int nextDouble( )                       --> Uniform, (0 to 1)
// int nextInt( int high )                 --> Uniform [0..high)
// int nextInt( int low, int high )        --> Uniform [low..high]
// long nextLong( )                        --> Uniform [1 to 2^62-1]
// long nextLong( long low, long high )    --> Uniform [low..high]
// int nextPoisson( double expectedVal )   --> Poisson
// double nextNegExp( double expectedVal ) --> Negative exponential
// void permute( Object [ ] a )            --> Randomly permutate

/**
 * Random number class, using a 31-bit
 * linear congruential generator.
 * @author Mark Allen Weiss
 */
public class Random
{
    private static final int A = 48271;
    private static final int M = 2147483647;
    private static final int Q = M / A;
    private static final int R = M % A;

    /**
     * Construct this Random object with
     * initial state obtained from system clock.
     */
    public Random( )
    {
        this( (int) ( System.currentTimeMillis( ) % Integer.MAX_VALUE ) );
    }

    /**
     * Construct this Random object with
     * specified initial state.
     * @param initialValue the initial state.
     */
    public Random( int initialValue )
    {
        if( initialValue < 0 )
            initialValue += M;

        state = initialValue;
        if( state == 0 )
            state = 1;
    }

    /**
     * Return a pseudorandom int, and change the
     * internal state.
     * @return the pseudorandom int.
     */
    public int nextInt( )
    {
        int tmpState = A * ( state % Q ) - R * ( state / Q );
        if( tmpState >= 0 )
            state = tmpState;
        else
            state = tmpState + M;

        return state;
    }
    
    /**
     * Return a pseudorandom int in range [0..high),
     * and change the internal state.
     * @return the pseudorandom int.
     */
    public int nextInt( int high )
    {
        return nextInt( ) % high;
    }

    /**
     * Return a pseudorandom int, and change the
     * internal state. DOES NOT WORK.
     * @return the pseudorandom int.
     */
    public int nextIntWRONG( )
    {
        return state = ( A * state ) % M;
    }

    /**
     * Return a pseudorandom double in the open range 0..1
     * and change the internal state.
     * @return the pseudorandom double.
     */
    public double nextDouble( )
    {
        return (double) nextInt( ) / M;
    }

    /**
     * Return an int in the closed range [low,high], and
     * change the internal state.
     * @param low the minimum value returned.
     * @param high the maximum value returned.
     * @return the pseudorandom int.
     */
    public int nextInt( int low, int high )
    {
        double partitionSize = (double) M / ( high - low + 1 );

        return (int) ( nextInt( ) / partitionSize ) + low;
    }

    /**
     * Return a long in the range [0, 2^62-1], and
     * change the internal state.
     * @return the pseudorandom long.
     */
    public long nextLong( )
    {
        return  ( (long) nextInt( ) << 31 ) + nextInt( );
    }

    /**
     * Return a long in the closed range [low,high], and
     * change the internal state.
     * @param low the minimum value returned.
     * @param high the maximum value returned.
     * @return the pseudorandom long.
     */
    public long nextLong( long low, long high )
    {
        long longVal =  ( (long) nextInt( ) << 31 ) + nextInt( );
        long longM =  ( (long) M << 31 ) + M;

        double partitionSize = (double) longM / ( high - low + 1 );
        return (long) ( longVal / partitionSize ) + low;
    }

    /**
     * Return an int using a Poisson distribution, and
     * change the internal state.
     * @param expectedValue the mean of the distribution.
     * @return the pseudorandom int.
     */
    public int nextPoisson( double expectedValue )
    {
        double limit = -expectedValue;
        double product = Math.log( nextDouble( ) );
        int count;

        for( count = 0; product > limit; count++ )
            product += Math.log( nextDouble( ) );

        return count;
    }

    /**
     * Return a double using a negative exponential
     * distribution, and change the internal state.
     * @param expectedValue the mean of the distribution.
     * @return the pseudorandom double.
     */
    public double nextNegExp( double expectedValue )
    {
        return - expectedValue * Math.log( nextDouble( ) );
    }
    
    /**
     * Method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    private static final <AnyType> void swapReferences( AnyType [ ] a, int index1, int index2 )
    {
        AnyType tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }
        
    /**
     * Randomly rearrange an array.
     * The random numbers used depend on the time and day.
     * @param a the array.
     */
    public static final <AnyType> void permute( AnyType [ ] a )
    {
        Random r = new Random( );

        for( int j = 1; j < a.length; j++ )
            swapReferences( a, j, r.nextInt( 0, j ) );
    }

    private int state;

        // Test program
    public static void main( String [ ] args )
    {
        Random r = new Random( );

        for( int i = 0; i < 20; i++ )
            System.out.println( r.nextInt( ) );
            
        int [ ] dist = new int[ 10000 ];
        
        final int SAMPLES = 1000000;
        for( int i = 0; i < SAMPLES; i++ )
            dist[ r.nextPoisson( 2 ) ]++;
        for( int i = 0; i < 10; i++ )
            System.out.println( i + ": " +  dist[ i ] / (double) SAMPLES );    
    }
}
