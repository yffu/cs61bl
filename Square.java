public class Square extends Rectangle
{
    public Square( double side )
    {
        super( side, side );
    }
    
    public String toString( )
    {
        return "Square: " + getLength( );
    }
}