package cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

import models.StudyGroup;

/**
 * 
 * The RemoveLowerCommand class represents a command to remove all elements in
 * the collection that are lower than the
 * 
 * specified element.
 */
public class RemoveLowerCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructs a RemoveLowerCommand object with the specified manager.
     * 
     * @param manager the AbstractManager object to manage the collection
     */
    public RemoveLowerCommand(AbstractManager manager) {
        super("RemoveLower", "Remove elements which are lower than given", new ArrayList<String>(Arrays.asList("{element}")), manager);
    }

    /**
     * 
     * Executes the command to remove all elements in the collection that are lower
     * than the specified element.
     * 
     * @param inlineParams the list of inline parameters provided with the command
     * 
     * @param input        the LineReader object to read input from the console
     * 
     * @param output       the LineWriter object to write output to the console
     * 
     * @throws ExecuteError if there was an error executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);

        StudyGroup studyGroup;

        if (disableAttempts) {
            studyGroup = ElementCommand.readScriptElement(input, output);
        } else {
            studyGroup = ElementCommand.readElement(input, output);
        }

        if (studyGroup != null) {
            manager.removeLower(studyGroup);
            output.writeLine("Lower elements were removed");

        } else {
            output.writeLine("Incorrect command, please write it with correct parameters" + "\n");
            return;
        }
    }
}
