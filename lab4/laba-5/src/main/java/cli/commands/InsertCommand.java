package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

import models.StudyGroup;

public class InsertCommand extends ElementCommand {

    public InsertCommand(AbstractManager manager) {
        super("Insert", "Insert element to collection", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        Checkers.checkLong(inlineParams.get(1));

        if(manager.getCollection().containsKey(Integer.parseInt(inlineParams.get(1)))) {
            output.writeLine("Key already exists" + "\n");
            return;
        }
        StudyGroup studyGroup;
        
        studyGroup = this.readElement(inlineParams.get(1), input, output);
        
        if (studyGroup != null) {
            manager.insert(Integer.parseInt(inlineParams.get(1)), studyGroup);
        } else {
            output.writeLine("Study group not found" + "\n");
            return;
        }
    }
}
