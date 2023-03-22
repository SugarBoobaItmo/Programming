package cli.commands.checker;

import cli.commands.exceptions.ExecuteError;

/**
 * 
 * A functional interface to represent a checker.
 */
@FunctionalInterface
public interface Checker {
    /**
     * 
     * Checks if the given value is valid.
     * 
     * @param value The value to check.
     * @throws ExecuteError If the value is not valid.
     */
    public void check(String value) throws ExecuteError;
}
