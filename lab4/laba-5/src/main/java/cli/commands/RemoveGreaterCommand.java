package cli.commands;

import java.util.Arrays;
import java.util.List;

import cli.commands.exceptions.ExecuteError;
import cli.commands.exceptions.GroupNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

import models.StudyGroup;

public class RemoveGreaterCommand extends ElementCommand {
    public RemoveGreaterCommand(AbstractManager manager) {
        super("RemoveGreater", "Remove elements which are greater than given", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        // Checkers.checkInlineParamsCount(0, inlineParams);

        
        StudyGroup studyGroup;
        
        if (inlineParams.size() > 1){
            studyGroup = this.readElement(Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 1, inlineParams.size()) ,input, output);
            
            } else
            studyGroup = this.readElement(input, output);
        
        if (studyGroup != null) {
            manager.removeGreater(studyGroup);
        } else {
            throw new GroupNotFound();
        }
    }
}
