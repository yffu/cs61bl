
/**
 * Example of Comparator, with rectangles.
 */
public class SimpleRectangle
{
    public SimpleRectangle( int l, int w )
    {
        length = l; width = w;
    }

    public int getLength( )
    {
        return length;
    }
    
    public int getWidth( )
    {
        return width;
    }
    
    public String toString( )
    {
        return "Rectangle " + getLength( ) + " by " + getWidth( );
    }
    
    private int length;
    private int width;
}
