import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class DivideByTwo
{
    public static void main( String [ ] args )
    {
        BufferedReader in = new BufferedReader( new
                             InputStreamReader( System.in ) );
        int x;
        String oneLine;

        System.out.println( "Enter an integer: " );
        try
        {
            oneLine = in.readLine( );
            x = Integer.parseInt( oneLine );
            System.out.println( "Half of x is " + ( x / 2 ) );
        }
        catch( IOException e )
          { System.out.println( e ); }
        catch( NumberFormatException e )
          { System.out.println( e ); }
    }
}
