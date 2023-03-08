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

public class CLIClient {
    private boolean debugMode;
    private Scanner scanner = new Scanner(System.in);

    private HashMap<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();
    private HashMap<String, TerminalCommand> terminal_commands = new HashMap<String, TerminalCommand>();

    private List<String> commandsHistory = new ArrayList<String>();

    public CLIClient(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void registerCommand(String commandName, AbstractCommand command) {
        this.commands.put(commandName, command);
    }

    public void registerTerminalCommand(String commandName, TerminalCommand command) {
        this.terminal_commands.put(commandName, command);
    }

    public void startCLI() {
        System.out.println("Press Ctrl+D and enter to skip command execution.");
        while (true) {
            System.out.print(">> ");
            if (!scanner.hasNextLine()) {
                scanner = new Scanner(System.in);
                continue;
            }
            String line = scanner.nextLine();
            List<String> params = parseParams(line);
            try {
                AbstractCommand command = resolveCommand(params);
                try {
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

    public ArrayList<String> parseParams(String line) {
        String[] s = line.split(" ");
        ArrayList<String> params = new ArrayList<>(Arrays.asList(s));
        return params;
    }

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

    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
    public HashMap<String, TerminalCommand> getTerminalCommands() {
        return terminal_commands;
    }
    public List<String> getCommandsHistory() {
        return commandsHistory;
    }


}
