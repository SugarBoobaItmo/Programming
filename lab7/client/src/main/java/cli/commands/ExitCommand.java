package cli.commands;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import serveradapter.ServerAdapter;


/**
 * 
 * The ExitCommand class represents a command that exits the program.
 * 
 */
public class ExitCommand extends CLISupportedCommand {
    private ServerAdapter serverAdapter;
    /**
     * 
     * Constructs a new ExitCommand.
     * 
     * @param manager the collection manager to be used
     */
    public ExitCommand(ServerAdapter serverAdapter, CLIClient cli) {
        super("Exit", "Exit from program", new ArrayList<String>(), cli);
        this.serverAdapter = serverAdapter;
        
    }

    /**
     * 
     * Executes the ExitCommand by exiting the program.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws ExecuteError if an error occurs while executing the command
     */

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        try {
            // send exit request to server to save user's collection
            serverAdapter.sendRequest("exit", null);
        } catch (UnknownHostException e) {
            System.out.println("Server is not available");
        } catch (IOException e) {
            output.writeLine("Server is not available" + "\n");
        }

        // output.writeLine("Goodbye!" + "\n");
        cli.exitProgram();
    }
}
