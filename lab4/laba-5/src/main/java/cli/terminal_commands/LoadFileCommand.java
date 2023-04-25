package cli.terminal_commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import collection_manager.AbstractManager;

public class LoadFileCommand extends TerminalCommand{
    private final AbstractManager localManager;

    public LoadFileCommand(AbstractManager manager) {
        super("load", "Loads a file into the database");
        this.localManager = manager;
    }

    @Override
    public void execute(List<String> params) throws ExecuteError{
        Checkers.checkInlineParamsCount(1, params);
        localManager.setCollectionRecord(params.get(1));
        System.out.println("Loading file " + params.get(1));
    }
    
    
}
