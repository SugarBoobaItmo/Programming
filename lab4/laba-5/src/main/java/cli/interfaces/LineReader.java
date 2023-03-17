package cli.interfaces;

/**
 * A functional interface for reading lines from the console.
 */
@FunctionalInterface
public interface LineReader{
    /**
     * Reads a line from the console.
     * 
     * @return the line read
     */
    public String readLine();
}
