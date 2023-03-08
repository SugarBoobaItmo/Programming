package cli.commands;

import java.time.LocalDateTime;
import java.util.Random;

import cli.commands.checker.Checker;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.Color;
import models.Semester;
import models.StudyGroup;

public abstract class ElementCommand extends AbstractCollectionCommand {
    public ElementCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    private String askField(String inputMessage, LineReader input, LineWriter output, boolean allowNull,
            Checker... checkers) {

        boolean errorFlag = true;
        while (true) {
            if (allowNull) {
                output.writeLine("(Enter \"\" to skip this field) ");
            }
            output.writeLine(inputMessage);

            String value = input.readLine();
            if (allowNull && value.equals(""))
                return "null";

            errorFlag = false;
            for (Checker checker : checkers) {
                try {
                    checker.check(value);
                } catch (ExecuteError e) {
                    output.writeLine(e.getMessage() + "\n");
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

    public StudyGroup readElement(LineReader input, LineWriter output) throws ExecuteError {
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
        if (adminName != "null" ) {
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

        StudyGroup group = new StudyGroup();
        group.serialize(
                id, name, x, y, creationDate,
                studentsCount, expelledStudents, transferredStudents, semester,
                adminName, adminBirthday, adminPassportID, adminHairColor);

        return group;
    }

    public StudyGroup readElement(String[] params, LineReader input, LineWriter output) throws ExecuteError {
        // name
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
        if (!studentsCount.equals("")){

            Checkers.checkPositive(studentsCount);
        } else if (studentsCount.equals("")) {
            studentsCount = "null";
        }

        String expelledStudents = params[4];
        Checkers.checkPositive(expelledStudents);

        String transferredStudents = params[5];
        Checkers.checkPositive(transferredStudents);

        String semester = params[6];
        if (!semester.equals("")){
            
            try {
                Semester.valueOf(semester);
            } catch (IllegalArgumentException e) {
                throw new ExecuteError("Incorrect semester");
            }
        } else if (semester.equals("")){
            semester = "null";
        }
        // System.out.println(semester);


        String adminName, adminPassportID, adminHairColor, adminBirthday;
        if (params[7].equals("")) {
            adminName = "null";
            adminPassportID = "null";
            adminHairColor = "null";
            adminBirthday = "null";
        } else {
            adminName = params[7];
            adminPassportID = params[8];
            Checkers.checkNull(adminPassportID);
            // Checkers.checkPassportID(adminPassportID);
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

        StudyGroup group = new StudyGroup();
        group.serialize(
                id, name, x, y, creationDate,
                studentsCount, expelledStudents, transferredStudents, semester,
                adminName, adminBirthday, adminPassportID, adminHairColor);

        return group;




    }
}
