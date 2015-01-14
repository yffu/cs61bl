import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;

public class ReadStringsWithArrayList
{
    public static void main( String [ ] args )
    {
        ArrayList array = getStrings( );
        for( int i = 0; i < array.size( ); i++ )
            System.out.println( array.get( i ) );
    }

    // Read an unlimited number of String; return an ArrayList
    // The minimal I/O details used here are not important for
    // this example and are discussed in Section 2.6.
    public static ArrayList getStrings( )
    {
        BufferedReader in = new BufferedReader( new
                             InputStreamReader( System.in ) );
        ArrayList<String> array = new ArrayList<String>( );
        String oneLine;

        System.out.println( "Enter any number of strings, one per line; " );
        System.out.println( "Terminate with empty line: " );

        try
        {
            while( ( oneLine = in.readLine( ) ) != null &&
                    !oneLine.equals( "" ) )
                array.add( oneLine );
        }
        catch( IOException e )
        {
            System.out.println( "Unexpected IO Exception has shortened amount read" );
        }
        
        System.out.println( "Done reading" );
        return array;
    }
}