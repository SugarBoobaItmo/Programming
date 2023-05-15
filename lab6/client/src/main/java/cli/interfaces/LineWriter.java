package cli.interfaces;

/**
 * A functional interface for writing lines to the console.
 */
@FunctionalInterface
public interface LineWriter {
    /**
     * Writes a line to the console.
     * 
     * @param line the line to write
     */
    public void writeLine(String line);
}
