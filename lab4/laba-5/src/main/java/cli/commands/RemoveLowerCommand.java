package cli.commands;

import java.util.Arrays;
import java.util.List;

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
        // Checkers.checkInlineParamsCount(0, inlineParams);


        StudyGroup studyGroup;
        
        if (inlineParams.size() > 1){
            studyGroup = this.readElement(Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 1, inlineParams.size()) ,input, output);
            
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
