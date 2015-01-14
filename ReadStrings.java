import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadStrings
{
    public static void main( String [ ] args )
    {
        String [ ] array = getStrings( );
        for( int i = 0; i < array.length; i++ )
            System.out.println( array[ i ] );
    }

    // Read an unlimited number of String; return a String [ ]
    public static String [ ] getStrings( )
    {
        BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
        String [ ] array = new String[ 5 ];
        int itemsRead = 0;
        String oneLine;

        System.out.println( "Enter any number of strings, one per line; " );
        System.out.println( "Terminate with empty line: " );

        try
        {
            while( ( oneLine = in.readLine( ) ) != null && !oneLine.equals( "" ) )
            {
                if( itemsRead == array.length )
                    array = resize( array, array.length * 2 );
                array[ itemsRead++ ] = oneLine;
            }
        }
        catch( IOException e )
        {
            System.out.println( "Unexpected IO Exception has shortened amount read" );
        }
        
        System.out.println( "Done reading" );
        return resize( array, itemsRead );
    }

    // Resize a String[ ] array; return new array
    public static String [ ] resize( String [ ] array, int newSize )
    {
        String [ ] original = array;
        int numToCopy = Math.min( original.length, newSize );

        array = new String[ newSize ];
        for( int i = 0; i < numToCopy; i++ )
            array[ i ] = original[ i ];
        return array;
    }
}
