package cli.commands;

import java.time.LocalDateTime;
import java.util.Random;

import cli.commands.checker.Checker;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import models.Color;
import models.Semester;
import models.StudyGroup;
import utils.ColorText;

/**
 * 
 * This abstract is abstract class for all commands that need to fetch Group
 * object fields data from user.
 * 
 * It contains method that asks user for field data, checks it and then build
 * Group object.
 */
public class ElementCommand{

    /**
     * 
     * Constructs a new ElementCommand with the specified collection manager.
     * 
     * @param name        the name of the command
     * @param description the description of the command
     * @param manager     the collection manager to be used
     */
    // public ElementCommand(String name, String description, ArrayList<String> inlineParams, AbstractManager manager) {
    //     super(name, description, inlineParams, manager);
    // }

    /**
     * 
     * Asks for field data, checks it.
     * 
     * @param input  the LineReader used to read input from the console
     * @param output the LineWriter used to write output to the console
     * @return checked value
     * @throws ExecuteError if an error occurs while executing the command
     */
    private static String askField(String inputMessage, LineReader input, LineWriter output, boolean allowNull,
            Checker... checkers) {
        // for stopping asking for field data if it is correct
        boolean errorFlag = true;
        while (true) {
            // if field is not required and user wants to skip it
            if (allowNull) {
                output.writeLine("(Enter \"\" to skip this field) ");
            }
            output.writeLine(inputMessage);

            String value = input.readLine();
            if (allowNull && value.equals(""))
                return "null";

            errorFlag = false;
            // check field data
            for (Checker checker : checkers) {
                try {
                    checker.check(value);
                } catch (ExecuteError e) {
                    output.writeLine(ColorText.colorText(e.getMessage() + "\n", "red"));
                    errorFlag = true;
                    break;
                }
            }
            if (errorFlag) {
                continue;
            }
            return value;
        }
    }

    /**
     * 
     * Asks for field data, checks it and then build Group object.
     * 
     * @param input  the LineReader used to read input from the console
     * @param output the LineWriter used to write output to the console
     * @return StudyGroup object
     * @throws ExecuteError if an error occurs while executing the command
     */
    public static StudyGroup readElement(LineReader input, LineWriter output) throws ExecuteError {
        Random random = new Random();
        String id = String.valueOf(random.nextInt(1000000));

        String name = askField(
                "Enter group name: ", input, output,
                false, Checkers::checkNull, Checkers::checkSymbols);

        String x = askField(
                "Enter coordinates x: ", input, output,
                false, Checkers::checkNull, Checkers::checkInteger);

        String y = askField(
                "Enter coordinates y: ", input, output,
                false, Checkers::checkNull, Checkers::checkInteger);

        String studentsCount = askField(
                "Enter group students count: ", input, output,
                true, Checkers::checkPositive);

        String expelledStudents = askField(
                "Enter number of expelled students: ", input, output,
                false, Checkers::checkPositive);

        String transferredStudents = askField(
                "Enter number of transferred students: ", input, output,
                false, Checkers::checkPositive);

        String semester = askField(
                "Enter semester (FIFTH, SIXTH, SEVENTH, EIGHTH): ", input, output, true,
                (value) -> {
                    try {
                        Semester.valueOf(value);
                    } catch (IllegalArgumentException e) {
                        throw new ExecuteError("Incorrect semester");
                    }
                });

        String adminName = askField("Enter admin name: ", input, output, true, Checkers::checkSymbols);

        String adminPassportID = "null";
        String adminHairColor = "null";
        String adminBirthday = "null";
        // if admin name is not null then ask for other admin fields
        if (adminName != "null") {
            adminPassportID = askField(
                    "Enter admin passport ID: ", input, output, false,
                    Checkers::checkNull,
                    (value) -> {
                        if (value.length() <= 24)
                            throw new ExecuteError("Passport ID must be longer than 24 characters");
                    }, Checkers::checkSymbols);

            adminHairColor = askField(
                    "Enter admin hair color (BLUE, YELLOW, WHITE): ", input, output, false,
                    Checkers::checkNull,
                    (value) -> {
                        try {
                            Color.valueOf(value);
                        } catch (IllegalArgumentException e) {
                            throw new ExecuteError("Incorrect hair color");
                        }
                    });
            adminBirthday = LocalDateTime.now().minusYears(random.nextInt(3) - 17).toString();
        }
        String creationDate = LocalDateTime.now().toString();

        StudyGroup group;
        group = StudyGroup.deserialize(
                id, name, x, y, creationDate,
                studentsCount, expelledStudents, transferredStudents, semester,
                adminName, adminBirthday, adminPassportID, adminHairColor);

        return group;
    }

    /**
     * 
     * Asks for script field data, checks it and then build Group object.
     * 
     * @param params the array of parameters
     * @param input  the LineReader used to read input from the console
     * @param output the LineWriter used to write output to the console
     * @return StudyGroup object
     * @throws ExecuteError if an error occurs while executing the command
     */
    public static StudyGroup readScriptElement(LineReader input, LineWriter output) throws ExecuteError {
        // name
        Random random = new Random();
        String id = String.valueOf(random.nextInt(1000000));

        String name = input.readLine();
        output.writeLine("Enter group name: " + name + "\n");
        Checkers.checkNull(name);

        // x
        String x = input.readLine();
        output.writeLine("Enter coordinates x: " + x + "\n");
        Checkers.checkNull(x);
        Checkers.checkInteger(x);

        String y = input.readLine();
        output.writeLine("Enter coordinates y: " + y + "\n");
        Checkers.checkNull(y);
        Checkers.checkInteger(y);

        String studentsCount = input.readLine();
        output.writeLine("Enter group students count: " + studentsCount + "\n");
        if (!studentsCount.equals("")) {

            Checkers.checkPositive(studentsCount);
        } else if (studentsCount.equals("")) {
            studentsCount = "null";
        }

        String expelledStudents = input.readLine();
        output.writeLine("Enter number of expelled students: " + expelledStudents + "\n");
        Checkers.checkPositive(expelledStudents);

        String transferredStudents = input.readLine();
        output.writeLine("Enter number of transferred students: " + transferredStudents + "\n");
        Checkers.checkPositive(transferredStudents);

        String semester = input.readLine();
        output.writeLine("Enter semester (FIFTH, SIXTH, SEVENTH, EIGHTH): " + semester + "\n");
        if (!semester.equals("")) {

            try {
                Semester.valueOf(semester);
            } catch (IllegalArgumentException e) {
                throw new ExecuteError("Incorrect semester");
            }
        } else if (semester.equals("")) {
            semester = "null";
        }

        String admin_field = input.readLine();
        output.writeLine("Enter admin name: " + admin_field + "\n");
        String adminName, adminPassportID, adminHairColor, adminBirthday;
        if (admin_field.equals("")) {
            adminName = "null";
            adminPassportID = "null";
            adminHairColor = "null";
            adminBirthday = "null";
        } else {
            adminName = admin_field;
            adminPassportID = input.readLine();
            output.writeLine("Enter admin passport ID: " + adminPassportID + "\n");
            Checkers.checkNull(adminPassportID);
            if (adminPassportID.length() <= 24)
                throw new ExecuteError("Passport ID must be longer than 24 characters");

            adminHairColor = input.readLine();
            output.writeLine("Enter admin hair color (BLUE, YELLOW, WHITE): " + adminHairColor + "\n");
            try {
                Color.valueOf(adminHairColor);
            } catch (IllegalArgumentException e) {
                throw new ExecuteError("Incorrect hair color");
            }
            adminBirthday = LocalDateTime.now().minusYears(random.nextInt(3) - 17).toString();

        }
        String creationDate = LocalDateTime.now().toString();

        StudyGroup group;
        group = StudyGroup.deserialize(
                id, name, x, y, creationDate,
                studentsCount, expelledStudents, transferredStudents, semester,
                adminName, adminBirthday, adminPassportID, adminHairColor);

        return group;

    }

    public static StudyGroup readArrayElement(String[] params) throws ExecuteError {
        if (params.length != 10) {
            throw new ExecuteError("Incorrect number of parameters");
        }
        Random random = new Random();
        String id = String.valueOf(random.nextInt(1000000));

        String name = params[0];
        Checkers.checkNull(name);

        // x
        String x = params[1];
        Checkers.checkNull(x);
        Checkers.checkInteger(x);

        String y = params[2];
        Checkers.checkNull(y);
        Checkers.checkInteger(y);

        String studentsCount = params[3];
        if (!studentsCount.equals("")) {

            Checkers.checkPositive(studentsCount);
        } else if (studentsCount.equals("")) {
            studentsCount = "null";
        }

        String expelledStudents = params[4];
        Checkers.checkPositive(expelledStudents);

        String transferredStudents = params[5];
        Checkers.checkPositive(transferredStudents);

        String semester = params[6];
        if (!semester.equals("")) {

            try {
                Semester.valueOf(semester);
            } catch (IllegalArgumentException e) {
                throw new ExecuteError("Incorrect semester");
            }
        } else if (semester.equals("")) {
            semester = "null";
        }

        String admin_field = params[7];
        String adminName, adminPassportID, adminHairColor, adminBirthday;
        if (admin_field.equals("")) {
            adminName = "null";
            adminPassportID = "null";
            adminHairColor = "null";
            adminBirthday = "null";
        } else {
            adminName = admin_field;
            adminPassportID = params[8];
            Checkers.checkNull(adminPassportID);
            if (adminPassportID.length() <= 24)
                throw new ExecuteError("Passport ID must be longer than 24 characters");

            adminHairColor = params[9];
            try {
                Color.valueOf(adminHairColor);
            } catch (IllegalArgumentException e) {
                throw new ExecuteError("Incorrect hair color");
            }
            adminBirthday = LocalDateTime.now().minusYears(random.nextInt(3) - 17).toString();

        }
        String creationDate = LocalDateTime.now().toString();

        StudyGroup group;
        group = StudyGroup.deserialize(
                id, name, x, y, creationDate,
                studentsCount, expelledStudents, transferredStudents, semester,
                adminName, adminBirthday, adminPassportID, adminHairColor);

        return group;

    }
}
