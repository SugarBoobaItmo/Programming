package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

public class ShowCommand extends AbstractCollectionCommand {
    public ShowCommand(AbstractManager manager) {
        super("Show", "Show collection", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        if (manager.getCollection().size() == 0) {
            output.writeLine("Collection is empty" + "\n");
        } else {
            manager.getCollection().forEach((k, v) -> output.writeLine("-" + v  + "\n"));
        }
    }
}
