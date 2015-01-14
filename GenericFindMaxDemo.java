public class GenericFindMaxDemo
{
    /**
     * Return max item in a.
     * Precondition: a.length > 0
     */
    public static <AnyType extends Comparable<? super AnyType>> AnyType findMax( AnyType [ ] a )
    {
        int maxIndex = 0;
        
        for( int i = 1; i < a.length; i++ )
            if( a[ i ].compareTo( a[ maxIndex ] ) > 0 )
                maxIndex = i;
            
        return a[ maxIndex ];    
    }
    
    /**
     * Test findMax on Shape and String objects.
     * (Integer is discussed in Chapter 4).
     */
    public static void main( String [ ] args )
    {
        Shape [ ] sh1 = { new Circle(  2.0 ),
                        new Square(  3.0 ),
                        new Rectangle( 3.0, 4.0 ) };
        
        Circle [ ] c1 = { new Circle(  2.0 ),
                        new Circle(  3.0 ),
                        new Circle( 1.0 ) };
						
        String [ ] st1 = { "Joe", "Bob", "Bill", "Zeke" };
        
        Shape maxShape = findMax( sh1 );
        String maxString = findMax( st1 );
        Circle maxCircle = findMax( c1 );
		
        System.out.println( maxShape );
        System.out.println( maxString );
        System.out.println( maxCircle );
    }
}
