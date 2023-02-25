package cli.commands;

import java.util.Comparator;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

public class PrintDescendingCommand extends AbstractCollectionCommand {

    public PrintDescendingCommand(AbstractManager manager) {
        super("Print_descending", "Print collection in descending order", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        manager.getCollection()
                .values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .forEach((studyGroup) -> output.writeLine("-" + studyGroup + "\n"));
    }
}
