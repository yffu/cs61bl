import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ListFiles
{
    public static void main( String [ ] args )
    {
        if( args.length == 0 )
            System.out.println( "No files specified" );
        for( String fileName : args )
            listFile( fileName );
    }

    public static void listFile( String fileName )
    {
        FileReader theFile;
        BufferedReader fileIn = null;
        String oneLine;

        System.out.println( "FILE: " + fileName );
        try
        {
            theFile = new FileReader( fileName );
            fileIn  = new BufferedReader( theFile );
            while( ( oneLine = fileIn.readLine( ) ) != null )
                System.out.println( oneLine );
        }
        catch( IOException e )
          {  System.out.println( e ); }
        finally
        {
            // Close the stream
            try
            {
                if(fileIn != null )
                    fileIn.close( );
            }
            catch( IOException e )
              { }
        }
    }
}
