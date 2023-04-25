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
 * The HelpCommand class represents a command that shows the list of commands.
 * 
 */
public class HelpCommand extends CLISupportedCommand {

    /**
     * 
     * Constructs a new HelpCommand with the specified CLI client.
     * 
     * @param cli the CLI client to be used
     */
    public HelpCommand(CLIClient cli) {
        super("help", "Write commands info", new ArrayList<String>(), cli);
    }

    /**
     * 
     * Executes the HelpCommand by showing the list of commands.
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

        output.writeLine("Commands:" + "\n");
        cli.getCommands().forEach((key, value) -> {
            key = ColorText.colorText(key, "yellow");
            String params = ColorText.colorText(String.join(", ", value.getInlineParams()), "blue");
            String descr = ColorText.colorText(value.getDescription(), "green");

            output.writeLine(String.format("\t%s\n\t\t%s\n\n", key + " " + params, descr));
        });
    }

}
