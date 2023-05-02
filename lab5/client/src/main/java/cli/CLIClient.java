package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cli.commands.AbstractCommand;
import cli.commands.exceptions.ExecuteError;
import cli.exceptions.CommandNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import cli.terminal_commands.TerminalCommand;
import utils.ColorText;
import utils.LevenshteinDistance;

/**
 * 
 * The CLIClient class represents a Command Line Interface (CLI) client.
 * 
 * It provides functionality to register commands and execute them via a command
 * prompt.
 */
public class CLIClient {
    private boolean fuzzyMode = false;

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

    public void deleteCommand(String commandName) {
        if (this.commands.containsKey(commandName))
            this.commands.remove(commandName);
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
        // randomly open files from folder "resources" and print them to console

        try {
            String[] listOfFiles = {"first_picture.txt", "second_picture.txt", "third_picture.txt", "fourth_picture.txt", "fifth_picture.txt"};
            int randomFileIndex = (int) (Math.random() * listOfFiles.length);
            String fileName = listOfFiles[randomFileIndex];
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ascii_picts/" + fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            System.out.println(); 
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
            reader.close();
        } catch (IOException e) {
            System.out.println(ColorText.colorText("File not found.", "red"));
        }

        while (true) {
            // Catching an exception is necessary to skip command execution (for Ctrl+D)
            // write file from folder "resources" to folder "output"
            System.out.print(">> ");
            if (!scanner.hasNextLine()) {
                scanner = new Scanner(System.in);
                // continue;
                System.out.println("Goodbye!");
                break;

            }

            String line = scanner.nextLine();
            List<String> params = parseParams(line);
            // getting command and executing it
            try {
                AbstractCommand command = resolveCommand(params);
                try {

                    executeCommand(params, command, scanner::nextLine, System.out::print, false);
                } catch (NoSuchElementException e) {
                    scanner = new Scanner(System.in);
                    System.out.println(ColorText.colorText("Command execution skipped.", "red"));
                }
            } catch (CommandNotFound e) {
                try {
                    TerminalCommand command = resolveTerminalCommand(params);
                    command.execute(params);
                } catch (CommandNotFound e1) {
                    System.out.println(ColorText.colorText("Command not found: " + e1.getMessage(), "red"));

                } catch (ExecuteError e1) {
                    System.out.println(ColorText.colorText("Error executing command: " + e1.getMessage(), "red"));
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
     * 
     * @param params the name of the command to resolve
     * @return the resolved command
     * @throws CommandNotFound if the command with the specified name is not found
     */
    public AbstractCommand resolveCommand(List<String> params) throws CommandNotFound {
        if (params.size() == 0) {
            throw new CommandNotFound("");
        }
        String commandName = params.get(0);

        if (fuzzyMode) {
            // commandName = commandName.toLowerCase();
            Map<String, Integer> distances = new HashMap<>();
            ArrayList<String> commands_array = new ArrayList<>(commands.keySet());
            terminal_commands.forEach((k, v) -> commands_array.add(k));

            for (String command : commands_array) {

                int distance = LevenshteinDistance.levenshteinDistance(commandName, command);
                distances.put(command, distance);
            }

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(distances.entrySet());
            Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            // System.out.println(Arrays.toString(sortedEntries.toArray()));
            commandName = sortedEntries.get(0).getKey();

        }
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
     * @throws CommandNotFound if the terminal command with the specified name is
     *                         not found
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
     * @param inlineParams the parameters to pass to the command
     * @param command      the command to execute
     * @param input        the input function to use
     * @param output       the output function to use
     */
    public void executeCommand(List<String> inlineParams, AbstractCommand command, LineReader input,
            LineWriter output, boolean disableAttempts) {
        try {
            command.execute(inlineParams, input, output, disableAttempts);
        } catch (ExecuteError e) {
            output.writeLine(ColorText.colorText("Command execution error:\n\t" + e.getMessage() + "\n", "red"));
        } catch (Exception e) {
            if (debugMode) {
                throw e;
            }
            output.writeLine(ColorText.colorText("Unexpected error:\n\t" + e.toString() + "\n", "red"));
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

    /**
     * 
     * @return the fuzzyMode
     */
    public boolean getFuzzyMode() {
        return fuzzyMode;
    }

    /**
     * 
     * @param fuzzyMode the fuzzyMode to set
     */
    public void setFuzzyMode(boolean fuzzyMode) {
        this.fuzzyMode = fuzzyMode;
    }

    /**
     * method for exit from program
     */
    public void exitProgram() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

}
