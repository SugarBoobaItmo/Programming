package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

/**
 * 
 * The ClearCommand class represents a command to clear a collection managed by
 * a given manager.
 * 
 */
public class ClearCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructs a ClearCommand object with a given manager.
     * 
     * @param manager the manager that manages the collection to be cleared
     */
    public ClearCommand(AbstractManager manager) {
        super("Clear", "Clear collection", manager);
    }

    /**
     * 
     * Executes the ClearCommand, which clears the collection managed by the given
     * manager.
     * 
     * @param inlineParams a list of command parameters passed inline
     * @param input        the input LineReader object
     * @param output       the output LineWriter object
     * @throws ExecuteError if the command execution fails
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        this.manager.clear();
    }
}
