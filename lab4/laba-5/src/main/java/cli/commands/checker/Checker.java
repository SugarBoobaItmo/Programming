package cli.commands.checker;

import cli.commands.exceptions.ExecuteError;

@FunctionalInterface
public interface Checker {
    public void check(String value) throws ExecuteError;
}
