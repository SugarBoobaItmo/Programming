package cli.commands.checker;

import java.util.List;

import cli.commands.exceptions.IncorrectInlineParamsCount;
import cli.commands.exceptions.IncorrectInlineParamsCountGreater;
import cli.commands.exceptions.IncorrectInteger;
import cli.commands.exceptions.IncorrectLong;
import cli.commands.exceptions.IncorrectWord;
import cli.commands.exceptions.NotPositive;
import cli.commands.exceptions.NullParam;

public class Checkers {
    public static void checkInlineParamsCount(int count, List<String> inlineParams)
            throws IncorrectInlineParamsCount {
        if (inlineParams.size() - 1 != count)
            throw new IncorrectInlineParamsCount(count, inlineParams.size() - 1);
    };
    public static void checkInlineParamsCountGreater(int count, List<String> inlineParams)
            throws IncorrectInlineParamsCountGreater {
        if (inlineParams.size() - 1 < count)
            throw new IncorrectInlineParamsCountGreater(count, inlineParams.size() - 1);
    };

    public static void checkNull(String value) throws NullParam {
        if (value == null || value.equals(""))
            throw new NullParam();
    }

    public static void checkWord(String value) throws IncorrectWord {
        if (!value.matches("[a-zA-Z]+"))
            throw new IncorrectWord(value);
    }

    public static void checkInteger(String value) throws IncorrectInteger {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IncorrectInteger(value);
        }
    }

    public static void checkLong(String value) throws IncorrectLong {
        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IncorrectLong(value);
        }
    }

    public static void checkPositive(String value) throws NotPositive, IncorrectLong {
        try {
            long number = Long.parseLong(value);
            if (number <= 0)
                throw new NotPositive(value);
        } catch (NumberFormatException e) {
            throw new IncorrectLong(value);
        }
    }
}
