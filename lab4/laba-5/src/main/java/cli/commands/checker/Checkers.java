package cli.commands.checker;

import java.util.List;

import cli.commands.exceptions.IncorrectInlineParamsCount;
import cli.commands.exceptions.IncorrectInlineParamsCountGreater;
import cli.commands.exceptions.IncorrectInlineParamsCountLower;
import cli.commands.exceptions.IncorrectInteger;
import cli.commands.exceptions.IncorrectLong;
import cli.commands.exceptions.IncorrectWord;
import cli.commands.exceptions.NotPositive;
import cli.commands.exceptions.NullParam;
import cli.commands.exceptions.UnallowedSymbol;

/**
 * 
 * A class to check the validity of parameters.
 */
public class Checkers {
    /**
     * 
     * Checks if the number of inline parameters matches the expected count.
     * 
     * @param count        The expected count of inline parameters.
     * 
     * @param inlineParams A list of inline parameters.
     * 
     * @throws IncorrectInlineParamsCount If the number of inline parameters does
     *                                    not match the expected count.
     */
    public static void checkInlineParamsCount(int count, List<String> inlineParams)
            throws IncorrectInlineParamsCount {
        if (inlineParams.size() - 1 != count)
            throw new IncorrectInlineParamsCount(count, inlineParams.size() - 1);
    };

    /**
     * 
     * Checks if the number of inline parameters is greater than or equal to the
     * expected count.
     * 
     * @param count        The expected minimum count of inline parameters.
     * @param inlineParams A list of inline parameters.
     * @throws IncorrectInlineParamsCountGreater If the number of inline parameters
     *                                           is less than the expected count.
     */
    public static void checkInlineParamsCountGreater(int count, List<String> inlineParams)
            throws IncorrectInlineParamsCountGreater {
        if (inlineParams.size() - 1 < count)
            throw new IncorrectInlineParamsCountGreater(count, inlineParams.size() - 1);
    };

    /**
     * 
     * Checks if the number of inline parameters is lower than or equal to the
     * expected count.
     * 
     * @param count        The expected maximum count of inline parameters.
     * @param inlineParams A list of inline parameters.
     * @throws IncorrectInlineParamsCountLower If the number of inline parameters
     *                                           is more than the expected count.
     */
    public static void checkInlineParamsCountLower(int count, List<String> inlineParams)
            throws IncorrectInlineParamsCountLower {
        if (inlineParams.size() - 1 > count)
            throw new IncorrectInlineParamsCountLower(count, inlineParams.size() - 1);
    };
    /**
     * 
     * Checks if the given value is null or empty.
     * 
     * @param value The value to be checked.
     * @throws NullParam If the value is null or empty.
     */
    public static void checkNull(String value) throws NullParam {
        if (value == null || value.equals(""))
            throw new NullParam();
    }

    /**
     * 
     * Checks if the given value is a word.
     * 
     * @param value The value to be checked.
     * @throws IncorrectWord If the value is not a word.
     */
    public static void checkWord(String value) throws IncorrectWord {
        if (!value.matches("[a-zA-Z]+"))
            throw new IncorrectWord(value);
    }

    /**
     * 
     * Checks if the given value is an integer.
     * 
     * @param value The value to be checked.
     * @throws IncorrectInteger If the value is not an integer.
     */
    public static void checkInteger(String value) throws IncorrectInteger {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IncorrectInteger(value);
        }
    }

    /**
     * 
     * Checks if the given value is a long.
     * 
     * @param value The value to be checked.
     * @throws IncorrectLong If the value is not a long.
     */
    public static void checkLong(String value) throws IncorrectLong {
        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IncorrectLong(value);
        }
    }

    /**
     * 
     * Checks if the given value is a positive.
     * 
     * @param value The value to be checked.
     * @throws NotPositive   If the value is not a positive.
     * @throws IncorrectLong If the value is not a long.
     */
    public static void checkPositive(String value) throws NotPositive, IncorrectLong {
        try {
            long number = Long.parseLong(value);
            if (number <= 0)
                throw new NotPositive(value);
        } catch (NumberFormatException e) {
            throw new IncorrectLong(value);
        }
    }

    /**
     * 
     * Checks if the given value contains unallowed symbols.
     * 
     * @param value The value to be checked.
     * @throws UnallowedSymbol If the value contains unallowed symbols.
     */
    public static void checkSymbols(String value) throws UnallowedSymbol {
        if (value.contains("\"") || value.contains(","))
            throw new UnallowedSymbol(value);
    }
}
