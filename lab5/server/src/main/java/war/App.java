package war;

import core.collection.ServerAdapter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // System.out.println( "Hello World!" );
        ServerAdapter serverAdapter = new ServerAdapter(8080);
        // while (true) {
        serverAdapter.run();
        // }
    }
}
