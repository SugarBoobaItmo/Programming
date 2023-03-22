package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;

/**
 * 
 * The FilterStartsWithNameCommand class represents a command that filters the
 * collection by given name.
 * 
 */
public class FilterStartsWithNameCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructs a new FilterStartsWithNameCommand with the specified collection
     * manager.
     * 
     * @param manager the collection manager to be used
     */
    public FilterStartsWithNameCommand(AbstractManager manager) {
        super("FilterStartsWithName", "Filter by name -name", manager);
    }

    /**
     * 
     * Executes the FilterStartsWithNameCommand by filtering the collection by given
     * name.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws ExecuteError if an error occurs while executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);

        // given name
        String substring = inlineParams.get(1);
        // filter the collection by given name
        for (StudyGroup studyGroup : manager.getCollection().values()) {
            if (studyGroup.getName().startsWith(substring)) {
                output.writeLine("-" + studyGroup + "\n");
            }
        }
    }
}
