import java.util.*;

public class InstanceOfTest
{
    public static void main( String [ ] args )
    {
        List<Integer> list1 = new ArrayList<Integer>( );
        list1.add( 4 );
        Object list = list1;
        List<String> list2 = (List<String>) list;
        String s = list2.get( 0 );
    }
}
