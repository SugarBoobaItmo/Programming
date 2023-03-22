package cli.commands;

import java.util.Arrays;
import java.util.List;

import cli.commands.exceptions.ExecuteError;
import cli.commands.exceptions.GroupNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

import models.StudyGroup;

/**
 * 
 * The RemoveGreaterCommand class represents a command to remove all elements in
 * the collection that are greater than the
 * 
 * specified element.
 */
public class RemoveGreaterCommand extends ElementCommand {
    /**
     * 
     * Constructs a RemoveGreaterCommand object with the specified manager.
     * 
     * @param manager the AbstractManager object to manage the collection
     */
    public RemoveGreaterCommand(AbstractManager manager) {
        super("RemoveGreater", "Remove elements which are greater than given -{element}", manager);
    }

    /**
     * 
     * Executes the command to remove all elements in the collection that are
     * greater
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
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {

        StudyGroup studyGroup;

        if (inlineParams.size() > 2) {
            studyGroup = this.readElement(
                    Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 1, inlineParams.size()),
                    input, output);

        } else
            studyGroup = this.readElement(input, output);

        if (studyGroup != null) {
            manager.removeGreater(studyGroup);
        } else {
            throw new GroupNotFound();
        }
    }
}
