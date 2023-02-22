package cli.commands;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import cli.commands.checker.Checker;
import collection_manager.AbstractManager;
import models.Semester;
import models.Color;

import models.StudyGroup;

public class RemoveGreaterCommand extends ElementCommand {

    public RemoveGreaterCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {

        if ((inlineParams.size() == 1)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter id of element");
            StudyGroup studyGroup = readElement(scanner.nextLine());
            if (studyGroup != null) {
                manager.removeGreater(studyGroup);
                // manager.insert(Integer.parseInt(inlineParams.get(1)) ,studyGroup);
            } else {
                System.out.println("Incorrect command, please write it with correct parameters");
                return;
            }

        } else {
            System.out.println("Incorrect command, please write it with correct parameters");
            return;
        }

    }

}
