package cli.commands;

import java.util.ArrayList;
import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.IncorrectInlineParamsCount;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import utils.ColorText;

/**
 * 
 * The HistoryCommand class represents a command that shows the history of
 * commands.
 * 
 */
public class HistoryCommand extends CLISupportedCommand {

    /**
     * 
     * Constructs a new HistoryCommand with the specified CLI client.
     * 
     * @param cli the CLI client to be used
     */
    public HistoryCommand(CLIClient cli) {
        super("History", "Show history of commands", new ArrayList<String>(), cli);
    }

    /**
     * 
     * Executes the HistoryCommand by showing the history of commands.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws IncorrectInlineParamsCount if the number of inline parameters is
     *                                    incorrect
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts)
            throws IncorrectInlineParamsCount {

        Checkers.checkInlineParamsCount(0, inlineParams);
        List<String> commandsHistory = cli.getCommandsHistory();

        // in case of empty history
        if (commandsHistory.size() == 0) {
            output.writeLine("History is empty" + "\n");
        } else if (commandsHistory.size() < 7) {
            output.writeLine(ColorText.colorText("History:", "yellow") + "\n");
            commandsHistory.forEach(v -> output.writeLine("  " + ColorText.colorText(v, "green") + "\n"));
        } else {
            // in case of history with more than 6 commands write only last 6 commands
            output.writeLine(ColorText.colorText("History:", "yellow") + "\n");
            List<String> sublist = commandsHistory.subList(commandsHistory.size() - 6, commandsHistory.size());
            sublist.forEach(v -> output.writeLine("  " + ColorText.colorText(v, "green") + "\n"));
        }
    }
}
