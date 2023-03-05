package cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cli.commands.AbstractCommand;
import cli.commands.exceptions.ExecuteError;
import cli.exceptions.CommandNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

public class CLIClient {
    private boolean debugMode;
    private Scanner scanner = new Scanner(System.in);
    private HashMap<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();
    private List<String> commandsHistory = new ArrayList<String>();

    public CLIClient(boolean debugMode) {
        this.debugMode = debugMode;

    }

    public void registerCommand(String commandName, AbstractCommand command) {
        this.commands.put(commandName, command);
    }

    public void startCLI() {
        while (true) {
            System.out.print(">> ");
            String line = scanner.nextLine();
            List<String> params = parseParams(line);
            try {
                AbstractCommand command = resolveCommand(params);

                executeCommand(params, command, scanner::nextLine, System.out::print);
            } catch (CommandNotFound e) {
                System.out.println("Command not found: " + e.getMessage());
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

    public List<String> getCommandsHistory() {
        return commandsHistory;
    }

}
