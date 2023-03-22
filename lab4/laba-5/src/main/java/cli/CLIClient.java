package cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cli.commands.AbstractCommand;
import cli.commands.exceptions.ExecuteError;
import cli.exceptions.CommandNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import cli.terminal_commands.TerminalCommand;

/**
 * 
 * The CLIClient class represents a Command Line Interface (CLI) client.
 * 
 * It provides functionality to register commands and execute them via a command
 * prompt.
 */
public class CLIClient {

    // debug mode flag shows exceptions via catching an Exception and printing it
    private boolean debugMode;
    // scanner instance for reading user input
    private Scanner scanner = new Scanner(System.in);

    // commands and terminal commands are stored in HashMaps for fast access
    private HashMap<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();
    private HashMap<String, TerminalCommand> terminal_commands = new HashMap<String, TerminalCommand>();

    // commands history is stored in a private List
    private List<String> commandsHistory = new ArrayList<String>();

    /**
     * 
     * Constructs a new CLIClient instance with the specified debug mode.
     * 
     * @param debugMode the debug mode flag to set
     */

    public CLIClient(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * 
     * Registers a new command with the specified command name and instance.
     * 
     * @param commandName the name of the command to register
     * @param command     the instance of the command to register
     */
    public void registerCommand(String commandName, AbstractCommand command) {
        this.commands.put(commandName, command);
    }

    /**
     * 
     * Registers a new terminal command with the specified command name and
     * instance.
     * 
     * @param commandName the name of the terminal command to register
     * @param command     the instance of the terminal command to register
     */
    public void registerTerminalCommand(String commandName, TerminalCommand command) {
        this.terminal_commands.put(commandName, command);
    }

    /**
     * 
     * Starts the CLI client and waits for input from the user.
     * The user input is parsed and resolved to a command, which is then executed.
     * If the user enters Ctrl+D, the command execution is skipped.
     */

    public void startCLI() {
        System.out.println("Press Ctrl+D and enter to skip command execution.");
        //
        while (true) {
            // Catching an exception is necessary to skip command execution
            System.out.print(">> ");
            if (!scanner.hasNextLine()) {
                scanner = new Scanner(System.in);
                continue;
            }

            String line = scanner.nextLine();
            List<String> params = parseParams(line);
            // getting command and executing it
            try {
                AbstractCommand command = resolveCommand(params);
                try {
                    if (line.matches(params.get(0) + "\\s+.*\\s+.*")) {
                        System.out.println("Incorrect command syntax.");
                        continue;}
                    executeCommand(params, command, scanner::nextLine, System.out::print);
                } catch (NoSuchElementException e) {
                    scanner = new Scanner(System.in);
                    System.out.println("Command execution skipped.");
                }
            } catch (CommandNotFound e) {
                try {
                    TerminalCommand command = resolveTerminalCommand(params);
                    command.execute();
                } catch (CommandNotFound e1) {
                    System.out.println("Command not found: " + e1.getMessage());
                }
            }
        }
    }

    /**
     * 
     * Parses the specified line into a list of parameters.
     * 
     * @param line the line to parse
     * @return the list of parameters
     */
    public ArrayList<String> parseParams(String line) {
        String[] s = line.split(" ");
        ArrayList<String> params = new ArrayList<>(Arrays.asList(s));
        return params;
    }

    /**
     * 
     * Resolves the command with the specified name.
     * @param params the name of the command to resolve
     * @return the resolved command
     * @throws CommandNotFound if the command with the specified name is not found
     */
    public AbstractCommand resolveCommand(List<String> params) throws CommandNotFound {
        if (params.size() == 0) {
            throw new CommandNotFound("");
        }
        String commandName = params.get(0);
        AbstractCommand command = this.commands.get(commandName);

        if (command == null) {
            throw new CommandNotFound(commandName);
        }
        return command;
    }

    /**
     * 
     * Resolves the terminal command with the specified name.
     * 
     * @param params the name of the terminal command to resolve
     * @return the resolved terminal command
     * @throws CommandNotFound if the terminal command with the specified name is not found
     */
    public TerminalCommand resolveTerminalCommand(List<String> params) throws CommandNotFound {
        if (params.size() == 0) {
            throw new CommandNotFound("");
        }
        String commandName = params.get(0);
        TerminalCommand command = this.terminal_commands.get(commandName);

        if (command == null) {
            throw new CommandNotFound(commandName);
        }
        return command;
    }

    /**
     * 
     * Executes the specified command with the specified parameters.
     * 
     * @param inlineParams  the parameters to pass to the command
     * @param command the command to execute
     * @param input   the input function to use
     * @param output  the output function to use
     */
    public void executeCommand(List<String> inlineParams, AbstractCommand command, LineReader input,
            LineWriter output) {
        try {
            command.execute(inlineParams, input, output);
        } catch (ExecuteError e) {
            output.writeLine("Command execution error:\n\t" + e.getMessage() + "\n");
        } catch (Exception e) {
            if (debugMode) {
                throw e;
            }
            output.writeLine("Unexpected error:\n\t" + e.toString() + "\n");
        }
        commandsHistory.add(inlineParams.get(0));
    }

    /**
     * 
     * @return the commands
     */
    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }

    /**
     * 
     * @return the terminal commands
     */
    public HashMap<String, TerminalCommand> getTerminalCommands() {
        return terminal_commands;
    }

    /**
     * 
     * @return the commands history
     */
    public List<String> getCommandsHistory() {
        return commandsHistory;
    }

}
