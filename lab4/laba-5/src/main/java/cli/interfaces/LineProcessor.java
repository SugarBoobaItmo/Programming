package cli.interfaces;

@FunctionalInterface
public interface LineProcessor {
    public void processLine(String line, LineReader input, LineWriter output);
}
