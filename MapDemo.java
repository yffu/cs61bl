import weiss.util.Iterator;
import weiss.util.Map;
import weiss.util.TreeMap;
import weiss.util.HashMap;
import weiss.util.Set;
import weiss.util.Collection;
import weiss.util.Comparator;
import weiss.util.Collections;

public class MapDemo
{
    // Do some inserts and printing (done in printMap).
    public static void main( String [ ] args )
    {
        demo( new TreeMap<String,String>( Collections.reverseOrder( ) ) );
        demo( new HashMap<String,String>( ) );
    }
    
    public static <KeyType,ValueType> void printMap( String msg, Map<KeyType,ValueType> m )
    {
        System.out.println( msg + ":" );
        Set<Map.Entry<KeyType,ValueType>> entries = m.entrySet( );
        
        for( Map.Entry<KeyType,ValueType> thisPair : entries )
        {
            System.out.print( thisPair.getKey( ) + ": " );
            System.out.println( thisPair.getValue( ) );
        }
    }

    public static void demo( Map<String,String> phone1 )
    {		
        phone1.put( "John Doe", "212-555-1212" );
        phone1.put( "Jane Doe", "312-555-1212" );
        phone1.put( "Holly Doe", "213-555-1212" ); 
        phone1.put( "Susan Doe", "617-555-1212" ); 
        phone1.put( "Jane Doe", "unlisted" );

        System.out.println( "phone1.get(\"Jane Doe\"): " + phone1.get( "Jane Doe" ) );
        System.out.println( );
        
        System.out.println( "The " + phone1.getClass( ).getName( ) + ": " );
        printMap( "phone1", phone1 );
        
        System.out.println( "\nThe keys are: " );
        Set<String> keys = phone1.keySet( );
        for( String s : keys )
            System.out.println( "\t" + s );
        System.out.println( );   
        
        System.out.println( "\nThe values are: " );
        Collection<String> values = phone1.values( );
        for( String s : values )
            System.out.println( "\t" + s );
        System.out.println( );   
        
        keys.remove( "John Doe" );
        values.remove( "unlisted" );
        System.out.println( "After John Doe and 1 unlisted are removed, the map is" );
        printMap( "phone1", phone1 );
        
        System.out.println( phone1 );
    }
}
