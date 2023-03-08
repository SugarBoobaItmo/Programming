package cli.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.exceptions.CommandNotFound;
import cli.exceptions.ScriptGroupIncorrectParams;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

public class ExecuteScriptCommand extends CLISupportedCommand {
    private int maxDepth = 5;
    private static HashMap<String, Integer> scriptExecuteDepth = new HashMap<String, Integer>();

    public ExecuteScriptCommand(CLIClient cli) {
        super("ExecuteScript", "Execute script from file", cli);

    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output)
            throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);

        String filePath = Paths.get(inlineParams.get(1)).normalize().toAbsolutePath().toString();
        if (scriptExecuteDepth.containsKey(filePath)) {
            int depth = scriptExecuteDepth.get(filePath);
            scriptExecuteDepth.put(filePath, depth + 1);
            if (depth + 1 >= maxDepth) {
                scriptExecuteDepth.remove(filePath);
                throw new ExecuteError("Max depth of script execution reached");
            }
        } else {
            scriptExecuteDepth.put(filePath, 1);
        }

        String[] fileLines = scriptReader(filePath);
        boolean skipDepthReset = false;
        if (fileLines != null) {
            for (int line = 0; line < fileLines.length; line++) {
                if (scriptExecuteDepth.get(filePath) >= maxDepth) {
                    skipDepthReset = true;
                    break;
                }
                ArrayList<String> params = cli.parseParams(fileLines[line]);
                try {
                    AbstractCommand command = cli.resolveCommand(params);

                    if ((command.getName().equals("Insert") 
                            || command.getName().equals("Update")
                            || command.getName().equals("RemoveGreater")
                            || command.getName().equals("RemoveLower")) 
                            && line + 1 < fileLines.length
                            && fileLines[line + 1].matches("\\s+.*")) {
                        try {
                            ArrayList<String> insertParams = checkInsertFields(fileLines, line);
                            insertParams.forEach(v -> params.add(v));
                            line += insertParams.size();
                        } catch (ScriptGroupIncorrectParams e) {
                            System.out.println(e.getMessage());
                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Script group incorrect params");
                            break;
                        }
                    }
                    cli.executeCommand(params, command, input, output);
                } catch (CommandNotFound e) {
                    System.out.println("Command not found: " + e.getMessage());
                }
            }
        }

        if (!skipDepthReset && scriptExecuteDepth.containsKey(filePath)) {
            scriptExecuteDepth.remove(filePath);
        }
    }

    public ArrayList<String> checkInsertFields(String[] fileLines, int line) throws ScriptGroupIncorrectParams {
        ArrayList<String> insertFields = new ArrayList<>();
        if (fileLines[line + 8].matches("\\s+")) {
            for (int elem = line + 1; elem < line + 9; elem++) {
                if (fileLines[elem].matches("\\s+.+")) {
                    insertFields.add(fileLines[elem].trim());
                } else
                    throw new ScriptGroupIncorrectParams();
            }
            return insertFields;
        }
        for (int i = line + 1; i < line + 11; i++) {

            if (fileLines[i].matches("\\s+.+")) {
                insertFields.add(fileLines[i].trim());
            } else {
                throw new ScriptGroupIncorrectParams();
            }
        }
        return insertFields;

    }

    public String[] scriptReader(String filePath) {

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
            int t;
            StringBuilder sb = new StringBuilder();

            while ((t = reader.read()) != -1) {
                char r = (char) t;
                sb.append(r);

            }

            return sb.toString().split(System.lineSeparator());

        } catch (IOException e) {
            System.out.println("File not found or no permission to read it");
            return null;
        }
    }

}