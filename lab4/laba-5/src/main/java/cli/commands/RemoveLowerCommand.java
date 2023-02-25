package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

import models.StudyGroup;

public class RemoveLowerCommand extends ElementCommand {
    public RemoveLowerCommand(AbstractManager manager) {
        super("RemoveLower", "Remove elements which are lower than given", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);

        output.writeLine("Enter id of element" + "\n");

        StudyGroup studyGroup = this.readElement(input.readLine(), input, output);
        if (studyGroup != null) {
            manager.removeLower(studyGroup);
        } else {
            output.writeLine("Incorrect command, please write it with correct parameters" + "\n");
            return;
        }
    }
}
