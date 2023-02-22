package cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cli.commands.AbstractCommand;
import cli.commands.HelpCommand;
import cli.commands.HistoryCommand;

public class CLIClient {
    private HashMap<String, AbstractCommand> commands = new HashMap();
    private static List<String> commandsHistory = new ArrayList();

    public void registerCommand(String commandName, AbstractCommand command) {
        this.commands.put(commandName, command);
    }

    public void startCLI() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            this.acceptLine(line);
        }
    }

    public void acceptLine(String line) {
        String[] s = line.split(" ");
        List<String> params = Arrays.asList(s);
        this.resolveCommand(params);
    }

    public void resolveCommand(List<String> params) {
        AbstractCommand command = (AbstractCommand) this.commands.get(params.get(0));

        if (command == null) {
            System.out.println("Incorrect command");
            // params.forEach(v -> System.out.print(v+" "));
            // System.out.println();
            return;
        }

        if (command instanceof HistoryCommand) {
            command.execute(commandsHistory);
        } else if (command.getClass() == HelpCommand.class) {
            if (params.size() > 1) {
                System.out.println("Incorrect command, please write it without parameters");
            } else {
                List<String> helpParams = new ArrayList<>();

                commands.forEach((key, value) -> {
                    helpParams.add(key + " - " + value.getDescription());
                });
                command.execute(helpParams);
            }
        } else {
            command.execute(params);
        }

        commandsHistory.add(params.get(0));
    }
}
