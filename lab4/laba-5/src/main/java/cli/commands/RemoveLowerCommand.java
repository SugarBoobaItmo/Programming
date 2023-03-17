package cli.commands;

import java.util.Arrays;
import java.util.List;

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
public class RemoveLowerCommand extends ElementCommand {
    /**
     * 
     * Constructs a RemoveLowerCommand object with the specified manager.
     * 
     * @param manager the AbstractManager object to manage the collection
     */
    public RemoveLowerCommand(AbstractManager manager) {
        super("RemoveLower", "Remove elements which are lower than given", manager);
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
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {

        StudyGroup studyGroup;
        // such logic is necessary for work with script
        if (inlineParams.size() > 2) {
            // create a new array with the first element removed
            studyGroup = this.readElement(
                    Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 1, inlineParams.size()),
                    input, output);

        } else
            studyGroup = this.readElement(input, output);

        if (studyGroup != null) {
            manager.removeLower(studyGroup);
        } else {
            output.writeLine("Incorrect command, please write it with correct parameters" + "\n");
            return;
        }
    }
}
