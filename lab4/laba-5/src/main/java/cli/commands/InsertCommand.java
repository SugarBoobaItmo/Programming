package cli.commands;

import java.util.Arrays;
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
        Checkers.checkInlineParamsCountGreater(1, inlineParams);

        if(manager.getCollection().containsKey(inlineParams.get(1))) {
            output.writeLine("Key already exists" + "\n");
            return;
        }
        StudyGroup studyGroup;
        if (inlineParams.size() > 2){
        studyGroup = this.readElement(Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 2, inlineParams.size()) ,input, output);
        
        } else
        studyGroup = this.readElement(input, output);
        
        if (studyGroup != null) {
            manager.insert(inlineParams.get(1), studyGroup);
            // System.out.println(studyGroup);
        } else {
            output.writeLine("Study group not found" + "\n");
            return;
        }
    }
    
}
