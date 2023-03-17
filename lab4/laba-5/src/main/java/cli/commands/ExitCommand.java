package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

/**
 * 
 * The ExitCommand class represents a command that exits the program.
 * 
 */
public class ExitCommand extends AbstractCommand {
    /**
     * 
     * Constructs a new ExitCommand.
     * 
     */
    public ExitCommand() {
        super("Exit", "Exit from program");
    }

    /**
     * 
     * Executes the ExitCommand by exiting the program.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws ExecuteError if an error occurs while executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        output.writeLine("Goodbye!" + "\n");
        System.exit(0);
    }
}
