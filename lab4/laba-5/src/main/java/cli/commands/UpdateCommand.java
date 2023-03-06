package cli.commands;

import java.util.Arrays;
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
        Checkers.checkInlineParamsCountGreater(1, inlineParams);
        Checkers.checkLong(inlineParams.get(1));

        for (Map.Entry<String, StudyGroup> entry : manager.getCollection().entrySet()) {
            if(Integer.parseInt(inlineParams.get(1))==entry.getValue().getId()){
                StudyGroup studyGroup;

                if (inlineParams.size() > 2){
                    studyGroup = this.readElement(Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 2, inlineParams.size()) ,input, output);
                    
                    } else
                    studyGroup = this.readElement(input, output);
                if (studyGroup != null) {
                    manager.update(entry.getKey(), studyGroup);
                }
                return;
            }
        }
        output.writeLine("Group with id: \"" + inlineParams.get(1)+ "\" not found" + "\n");
        return;

    }
}
