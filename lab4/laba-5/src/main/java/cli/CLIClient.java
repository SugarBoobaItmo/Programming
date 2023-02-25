package cli;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cli.commands.AbstractCommand;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.commands.exceptions.IncorrectInlineParamsCount;
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
        this.registerCommand("help", new HelpCommand());
        this.registerCommand("history", new HistoryCommand());
        this.registerCommand("execute_script", new ExecuteScriptCommand());
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

    public List<String> parseParams(String line) {
        String[] s = line.split(" ");
        List<String> params = Arrays.asList(s);
        return params;
    }

    public AbstractCommand resolveCommand(List<String> params) throws CommandNotFound {
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

    public class HelpCommand extends AbstractCommand {
        public HelpCommand() {
            super("help", "Write commands info");
        }

        @Override
        public void execute(List<String> inlineParams, LineReader input, LineWriter output)
                throws IncorrectInlineParamsCount {

            Checkers.checkInlineParamsCount(0, inlineParams);

            commands.forEach((key, value) -> {
                output.writeLine(key + " - " + value.getDescription() + "\n");
            });
        }
    }

    public class HistoryCommand extends AbstractCommand {
        public HistoryCommand() {
            super("History", "Show history of commands");
        }

        @Override
        public void execute(List<String> inlineParams, LineReader input, LineWriter output)
                throws IncorrectInlineParamsCount {

            Checkers.checkInlineParamsCount(0, inlineParams);

            if (commandsHistory.size() == 0) {
                output.writeLine("History is empty" + "\n");
            } else if (commandsHistory.size() < 7) {
                output.writeLine("History:" + "\n");
                commandsHistory.forEach(v -> output.writeLine(v + "\n"));
            } else {
                output.writeLine("History:" + "\n");
                List<String> sublist = commandsHistory.subList(commandsHistory.size() - 6, commandsHistory.size());
                sublist.forEach(v -> output.writeLine(v + "\n"));
            }
        }
    }

    public class ExecuteScriptCommand extends AbstractCommand {
        public ExecuteScriptCommand() {
            super("ExecuteScript", "Execute script from file");
        }

        @Override
        public void execute(List<String> inlineParams, LineReader input, LineWriter output)
                throws IncorrectInlineParamsCount {
            Checkers.checkInlineParamsCount(1, inlineParams);
            String[] fileLines = scriptReader(inlineParams.get(1));
            if (fileLines != null) {
                for (String line : fileLines) {
                    List<String> params = parseParams(line);
                    try {
                        AbstractCommand command = resolveCommand(params);
                        executeCommand(params, command, scanner::nextLine, System.out::print);
                    } catch (CommandNotFound e) {
                        System.out.println("Command not found: " + e.getMessage());
                    }
                }
            }
        }

        public String[] scriptReader(String filePath) {

            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
                int t;
                StringBuilder sb = new StringBuilder();

                while ((t = reader.read()) != -1) {
                    char r = (char) t;
                    sb.append(r);

                }
                // String lines = sb.toString().replace("\n", "");

                return sb.toString().split(System.lineSeparator());

            } catch (IOException e) {
                System.out.println("File \"" + filePath + "\" not found");
                return null;
            }
        }

    }

}
