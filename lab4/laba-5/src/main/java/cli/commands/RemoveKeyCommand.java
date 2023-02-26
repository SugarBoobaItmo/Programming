package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

public class RemoveKeyCommand extends AbstractCollectionCommand {

    public RemoveKeyCommand(AbstractManager manager) {
        super("RemoveKey", "Remove element by key", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        manager.removeKey(inlineParams.get(1));
    }
}
