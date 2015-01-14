package weiss.nonstandard;

public class Exiting
{
    // Suspend current program for a long time
    public static void longPause( )
    {
        try
          { Thread.sleep( 1000000000 ); }
        catch( InterruptedException e ) { }
    }
}
