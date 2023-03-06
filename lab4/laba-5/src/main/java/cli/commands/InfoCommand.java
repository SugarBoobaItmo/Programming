package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

public class InfoCommand extends AbstractCollectionCommand {
    public InfoCommand(AbstractManager manager) {
        super("Info", "Write info about collection", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        output.writeLine(manager.getInfo().toString() + "\n");
    }
}
