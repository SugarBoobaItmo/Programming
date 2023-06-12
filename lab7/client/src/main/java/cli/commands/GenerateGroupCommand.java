package cli.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.opencsv.exceptions.CsvValidationException;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.commands.generate_group.RandomGroup;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;

/**
 * 
 * A command that generates a specified number of random study groups and adds
 * them to the collection managed by the
 * 
 * associated manager. The groups are generated by randomly choosing values from
 * a CSV file containing fields for each
 * 
 * group, and are created using the {@link RandomGroup#getRandomGroup()} method.
 * 
 */
public class GenerateGroupCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructs a new GenerateGroupCommand object with the specified manager.
     * 
     * @param manager the manager associated with this command
     */
    public GenerateGroupCommand(
            AbstractManager manager) {
        super("Generate group", "Randomly generate groups", new ArrayList<String>(Arrays.asList("{element}")), manager);

    }

    /**
     * 
     * Executes the command by generating the specified number of study groups and
     * adding them to the managed collection.
     * 
     * @param inlineParams    the parameters passed with the command
     * @param input           the reader for user input
     * @param output          the writer for program output
     * @param disableAttempts true if this command should not prompt for re-attempts
     *                        if user input is invalid
     * @throws ExecuteError if an error occurs while generating or adding a group
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts)
            throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        // get count of groups
        if (Integer.parseInt(inlineParams.get(1)) > 1000) {
            System.out.println("Too many groups");
            return;
        }
        // generate groups and add them to collection
        int count = Integer.parseInt(inlineParams.get(1));
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            try {
                String[] new_group = RandomGroup.getRandomGroup();
                StudyGroup studyGroup = ElementCommand.readArrayElement(new_group);

                // check if key already exists
                manager.loadCollectionRecord();
                int key = random.nextInt(10000) + 1;
                if (manager.getCollection().containsKey(key + "")) {
                    output.writeLine("Key already exists" + "\n");
                    output.writeLine("Generated " + count + " groups" + "\n");

                    return;
                }
                manager.insert(key + "", studyGroup);
                
            } catch (CsvValidationException e) {
                throw new ExecuteError("Error reading file for group generation");
            } catch (IOException e) {
                throw new ExecuteError("Error generating group");
            }

        }
        output.writeLine("Generated " + count + " groups" + "\n");
    }

}
