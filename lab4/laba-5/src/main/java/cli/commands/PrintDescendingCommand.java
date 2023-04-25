package cli.commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

/**
 * The PrintDescendingCommand class represents a command to print the collection
 * in descending order.
 */
public class PrintDescendingCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructor for the PrintDescendingCommand class.
     * 
     * @param manager An instance of the AbstractManager class.
     */
    public PrintDescendingCommand(AbstractManager manager) {
        super("Print_descending", "Print collection in descending order", new ArrayList<String>(), manager);
    }

    /**
     * 
     * Executes the command to print the collection in descending order.
     * 
     * @param inlineParams The list of parameters provided with the command.
     * @param input        The LineReader to read input from.
     * @param output       The LineWriter to write output to.
     * @throws ExecuteError If there is an error executing the command.
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        manager.getCollection()
                .values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .forEach((studyGroup) -> output.writeLine("-" + studyGroup + "\n"));
    }
}
