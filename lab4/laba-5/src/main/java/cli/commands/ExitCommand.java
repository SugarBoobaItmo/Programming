package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("Exit", "Exit from program");
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        output.writeLine("Goodbye!" + "\n");
    }
}
