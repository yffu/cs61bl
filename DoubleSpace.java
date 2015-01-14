// Double space files specified on command line.

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class DoubleSpace
{
    public static void main( String [ ] args )
    {
        for( String fileName : args )
            doubleSpace( fileName );
    }
    
    public static void doubleSpace( String fileName )
    {
        PrintWriter   fileOut = null;
        BufferedReader fileIn = null;

        try
        {
            fileIn  = new BufferedReader( new FileReader( fileName ) );
            fileOut = new PrintWriter(  new FileWriter( fileName + ".ds" ) );

            String oneLine;

            while( ( oneLine = fileIn.readLine( ) ) != null )
                fileOut.println( oneLine + "\n" );
        }
        catch( IOException e )
          { e.printStackTrace( ); }

        finally
        {
            try
            {
                if( fileOut != null )
                    fileOut.close( );
                if( fileIn != null )
                    fileIn.close( );
            }
            catch( IOException e )
              { e.printStackTrace( ); }
        }
    }
}