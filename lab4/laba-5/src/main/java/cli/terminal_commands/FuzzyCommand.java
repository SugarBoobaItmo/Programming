package cli.terminal_commands;

import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;

public class FuzzyCommand extends TerminalCommand{
    private CLIClient cli;

    public FuzzyCommand(CLIClient cli) {
        super("/fuzzyCommand", "set fuzzy mode");
        this.cli = cli;
    }

    @Override
    public void execute(List<String> params) throws ExecuteError{
        
        Checkers.checkInlineParamsCount(0, params);
        cli.setFuzzyMode(!cli.getFuzzyMode());
        System.out.println("Fuzzy mode is " + (cli.getFuzzyMode()==true?"ON":"OFF"));
    }
    
}
