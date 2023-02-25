package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;

public class FilterStartsWithNameCommand extends AbstractCollectionCommand {

    public FilterStartsWithNameCommand(AbstractManager manager) {
        super("FilterStartsWithName", "Filter by name", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        
        String substring = inlineParams.get(1);
        for (StudyGroup studyGroup : manager.getCollection().values()) {
            if (studyGroup.getName().startsWith(substring)) {
                output.writeLine("-" + studyGroup + "\n");
            }
        }
    }
}
