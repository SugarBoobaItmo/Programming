package cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.commands.random_group.RandomGroup;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;

public class GenerateGroupCommand extends AbstractCollectionCommand {

    public GenerateGroupCommand(
            AbstractManager manager) {
        super("Generate group", "Randomly generate groups", new ArrayList<String>(Arrays.asList("{element}")), manager);

    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts)
            throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        if (Integer.parseInt(inlineParams.get(1))>1000) {
            System.out.println("Too many groups");
            return;
        }
        int count = Integer.parseInt(inlineParams.get(1));
        for (int i = 0; i < count; i++) {
            try {
                String[] new_group = RandomGroup.getRandomGroup();
                StudyGroup studyGroup = ElementCommand.readArrayElement(new_group);
                manager.insert(i + "", studyGroup);
                ;
            } catch (CsvValidationException e) {
            }

        }
        System.out.println("Generated " + count + " groups");
    }

}
