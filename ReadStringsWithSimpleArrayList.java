import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadStringsWithSimpleArrayList
{
    public static void main( String [ ] args )
    {
        SimpleArrayList array = getStrings( );
        for( int i = 0; i < array.size( ); i++ )
            System.out.println( array.get( i ) );
    }

    // Read an unlimited number of String; return an ArrayList
    public static SimpleArrayList getStrings( )
    {
        BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
        SimpleArrayList array = new SimpleArrayList( );
        String oneLine;

        System.out.println( "Enter any number of strings, one per line; " );
        System.out.println( "Terminate with empty line: " );

        try
        {
            while( ( oneLine = in.readLine( ) ) != null && !oneLine.equals( "" ) )
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
