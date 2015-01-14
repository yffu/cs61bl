import java.io.File;

// Program that lists files in a directory,
// recursively processing subdirectories.
// It then outputs the total number of bytes used to
// store the directory.

public class FileSystem extends File
{
        // Constructor
    public FileSystem( String name )
    {
        super( name );
    }

        // Output file name with indentation
    public void printName( int depth )
    {
        for( int i = 0; i < depth; i++ )
            System.out.print( "\t" );
        System.out.println( getName( ) );
    }

        // Public driver to list all files in directory
    public void listAll( )
    {
        listAll( 0 );
    }

        // Recursive method to list all files in directory
    private void listAll( int depth )
    {
        printName( depth );

        if( isDirectory( ) )
        {
            String [ ] entries = list( );

            for( int i = 0; i < entries.length; i++ )
            {
                FileSystem child = new FileSystem( getPath( )
                             + separatorChar + entries[ i ] );
                child.listAll( depth + 1 );
            }
        }
    }

    public long size( )
    {
        long totalSize = length( );

        if( isDirectory( ) )
        {
            String [ ] entries = list( );
            for( int i = 0; i < entries.length; i++ )
            {
                FileSystem  child = new FileSystem( getPath( )
                              + separatorChar + entries[ i ] );
                totalSize += child.size( );
            }
        }

        return totalSize;
    }

        // Simple main to list all files in current directory
    static public void main( String [ ] args )
    {
        FileSystem f;

        if( args.length == 0 )
            f = new FileSystem( "." );
        else
            f = new FileSystem( args[ 0 ] );

        f.listAll( );
        System.out.println( "Total bytes: " + f.size( ) );
    }
}