package cli.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;

public class UpdateCommand extends ElementCommand {

    public UpdateCommand(AbstractManager manager) {
        super("Update", "Update element of collection by ID", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        Checkers.checkLong(inlineParams.get(1));

        List<Integer> idList = new ArrayList<>();
        for (Map.Entry<Integer, StudyGroup> entry : manager.getCollection().entrySet()) {
            idList.add(entry.getValue().getId());
        }
        if (!idList.contains(Integer.parseInt(inlineParams.get(1)))) {
            output.writeLine("No element with such id" + "\n");
            return;
        }

        StudyGroup studyGroup = this.readElement(inlineParams.get(1), input, output);
        if (studyGroup != null) {
            manager.insert(Integer.parseInt(inlineParams.get(1)), studyGroup);
        } else {
            output.writeLine("Study group not found" + "\n");
            return;
        }
    }
}
