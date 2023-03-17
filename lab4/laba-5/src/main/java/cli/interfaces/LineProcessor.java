package cli.interfaces;

/**
 * A functional interface for processing lines.
 */
@FunctionalInterface
public interface LineProcessor {
    /**
     * Processes a line.
     * 
     * @param line   the line to process
     * @param input  the input line reader
     * @param output the output line writer
     */
    public void processLine(String line, LineReader input, LineWriter output);
}
