import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.StringTokenizer;

public class MaxTest
{
    public static void main( String [ ] args )
    {
        BufferedReader in = new BufferedReader( new
                             InputStreamReader( System.in ) );
        
        String oneLine;
        StringTokenizer str;
        int x;
        int y;

        System.out.println( "Enter 2 ints on one line: " );
        try
        {
            oneLine = in.readLine( );
            if( oneLine == null )
                return;
                
            str = new StringTokenizer( oneLine );
            if( str.countTokens( ) != 2 )
            {
                System.out.println( "Error: need two ints" );
                return;
            }
            x = Integer.parseInt( str.nextToken( ) );
            y = Integer.parseInt( str.nextToken( ) );
            System.out.println( "Max: " + Math.max( x, y ) );
        }
        catch( IOException e )
          { System.err.println( "Unexpected IO error" ); }
        catch( NumberFormatException e )
          { System.err.println( "Error: need two ints" ); }
    }
}
