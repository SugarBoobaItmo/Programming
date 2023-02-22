package cli.commands;

import java.io.FileInputStream;
import java.io.InputStreamReader;
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

public abstract class ElementCommand extends AbstractCollectionCommand {

    public ElementCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    public StudyGroup readElement(String id) {
        if (!Checker.correctChecker(id, true, true, true) || !Checker.intChecker(id)) {
            // System.out.println("Incorrect id, please try again");
            return null;
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String[] sererializingFields = new String[13];

        sererializingFields[0] = id;

        while (true) {

            System.out.print("Enter group name: ");
            sererializingFields[1] = scanner.nextLine();
            if (Checker.nullChecker(sererializingFields[1])) {
                System.out.println("Incorrect name, please try again");
            } else
                break;
        }

        while (true) {

            System.out.print("Enter coordinates x: ");
            sererializingFields[2] = scanner.nextLine();

            if (!Checker.correctChecker(sererializingFields[2], true, true, false)
                    || !Checker.intChecker(sererializingFields[2])) {
                System.out.println("Incorrect x, please try again");
            } else
                break;
        }

        while (true) {

            System.out.print("Enter coordinates y: ");
            sererializingFields[3] = scanner.nextLine();

            if (!Checker.correctChecker(sererializingFields[3], true, true, false)
                    || !Checker.intChecker(sererializingFields[3])) {
                System.out.println("Incorrect y, please try again");
            } else
                break;
        }

        sererializingFields[4] = LocalDateTime.now().plusDays(random.nextInt(10 - 5 + 1) + 5).toString();

        while (true) {

            System.out.print("Enter group students count: ");
            sererializingFields[5] = scanner.nextLine();

            if (!Checker.correctChecker(sererializingFields[5], false, true, true)) {
                System.out.println("Incorrect students count, please try again");
            } else
                break;
        }

        while (true) {

            System.out.print("Enter number of expelled students: ");
            sererializingFields[6] = scanner.nextLine();

            if (!Checker.correctChecker(sererializingFields[6], true, true, true)) {
                System.out.println("Incorrect number of expelled students, please try again");
            } else
                break;
        }

        while (true) {

            System.out.print("Enter number of transfered students: ");
            sererializingFields[7] = scanner.nextLine();

            if (!Checker.correctChecker(sererializingFields[7], true, true, true)) {
                System.out.println("Incorrect number of transfered students, please try again");
            } else
                break;
        }

        System.out.println("Enter semester: ");
        ArrayList<String> semesters = new ArrayList<>();

        for (Semester semester : Semester.values()) {
            System.out.println("- " + semester);
            semesters.add(semester.toString());
        }
        while (true) {
            System.out.print("Enter semester: ");
            sererializingFields[8] = scanner.nextLine();
            if (!Checker.nullChecker(sererializingFields[8])) {

                if (!semesters.contains(sererializingFields[8])) {
                    System.out.println("Incorrect semester, please try again");
                } else
                    break;
            } else
                break;
        }

        System.out.println("If your group doesn't have admin, please enter");
        if (Checker.nullChecker(scanner.nextLine())) {
            sererializingFields[9] = "null";
            sererializingFields[10] = "null";
            sererializingFields[11] = "null";
            sererializingFields[12] = "null";
            StudyGroup group = new StudyGroup();
            group.serialize(sererializingFields);
            return group;
            // manager.insert(Integer.parseInt(inlineParams.get(1)), group);
            // return;
        }

        while (true) {
            System.out.print("Enter group admin name: ");
            sererializingFields[9] = scanner.nextLine();
            if (Checker.nullChecker(sererializingFields[9])) {
                System.out.println("Incorrect name, please try again");
            } else
                break;
        }

        sererializingFields[10] = LocalDateTime.now().plusDays(random.nextInt(10 - 5 + 1) + 5).toString();

        while (true) {

            System.out.print("Enter group admin passport id: ");
            sererializingFields[11] = scanner.nextLine();
            if (Checker.nullChecker(sererializingFields[11]) || sererializingFields[11].length() > 24) {
                System.out.println("Incorrect passport id, please try again");
            } else
                break;
        }

        System.out.println("Enter hair color: ");
        ArrayList<String> hairColor = new ArrayList<>();

        for (Color color : Color.values()) {
            System.out.println("- " + color);
            hairColor.add(color.toString());
        }

        while (true) {
            System.out.print("Enter group admin hair color: ");
            sererializingFields[12] = scanner.nextLine();
            if (!hairColor.contains(sererializingFields[12])) {
                System.out.println("Incorrect hair color, please try again");
            } else
                break;
        }

        StudyGroup group = new StudyGroup();
        group.serialize(sererializingFields);
        return group;
        // manager.insert(Integer.parseInt(inlineParams.get(1)), group);
    }

    public StudyGroup readFileElement(String id, String filePath) {
        if (!Checker.correctChecker(id, true, true, true) || !Checker.intChecker(id)) {
            return null;
        }

        try {
            
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
    
            // FileInputStream file = new FileInputStream(filePath);
            // InputStreamReader reader = new InputStreamReader(file);

            // ArrayList<String[]> elems = new ArrayList<String[]>();
    
            // int t;
            // StringBuilder sb = new StringBuilder();
            // while((t=reader.read())!= -1)
            // {
            //     char r = (char)t;
            //     sb.append(r);
    
            // }

            // if (sb.length() == 0) {

            // }

            // String elemsString = sb.toString();
            // elemsString = elemsString.replaceAll("\"", "");
            // String[] lines = elemsString.split("\n");
            

            String[] sererializingFields = new String[13];
    
            sererializingFields[0] = id;
    
            while (true) {
    
                System.out.print("Enter group name: ");
                sererializingFields[1] = scanner.nextLine();
                if (Checker.nullChecker(sererializingFields[1])) {
                    System.out.println("Incorrect name, please try again");
                } else
                    break;
            }
    
            while (true) {
    
                System.out.print("Enter coordinates x: ");
                sererializingFields[2] = scanner.nextLine();
    
                if (!Checker.correctChecker(sererializingFields[2], true, true, false)
                        || !Checker.intChecker(sererializingFields[2])) {
                    System.out.println("Incorrect x, please try again");
                } else
                    break;
            }
    
            while (true) {
    
                System.out.print("Enter coordinates y: ");
                sererializingFields[3] = scanner.nextLine();
    
                if (!Checker.correctChecker(sererializingFields[3], true, true, false)
                        || !Checker.intChecker(sererializingFields[3])) {
                    System.out.println("Incorrect y, please try again");
                } else
                    break;
            }
    
            sererializingFields[4] = LocalDateTime.now().plusDays(random.nextInt(10 - 5 + 1) + 5).toString();
    
            while (true) {
    
                System.out.print("Enter group students count: ");
                sererializingFields[5] = scanner.nextLine();
    
                if (!Checker.correctChecker(sererializingFields[5], false, true, true)) {
                    System.out.println("Incorrect students count, please try again");
                } else
                    break;
            }
    
            while (true) {
    
                System.out.print("Enter number of expelled students: ");
                sererializingFields[6] = scanner.nextLine();
    
                if (!Checker.correctChecker(sererializingFields[6], true, true, true)) {
                    System.out.println("Incorrect number of expelled students, please try again");
                } else
                    break;
            }
    
            while (true) {
    
                System.out.print("Enter number of transfered students: ");
                sererializingFields[7] = scanner.nextLine();
    
                if (!Checker.correctChecker(sererializingFields[7], true, true, true)) {
                    System.out.println("Incorrect number of transfered students, please try again");
                } else
                    break;
            }
    
            System.out.println("Enter semester: ");
            ArrayList<String> semesters = new ArrayList<>();
    
            for (Semester semester : Semester.values()) {
                System.out.println("- " + semester);
                semesters.add(semester.toString());
            }
            while (true) {
                System.out.print("Enter semester: ");
                sererializingFields[8] = scanner.nextLine();
                if (!Checker.nullChecker(sererializingFields[8])) {
    
                    if (!semesters.contains(sererializingFields[8])) {
                        System.out.println("Incorrect semester, please try again");
                    } else
                        break;
                } else
                    break;
            }
    
            System.out.println("If your group doesn't have admin, please enter");
            if (Checker.nullChecker(scanner.nextLine())) {
                sererializingFields[9] = "null";
                sererializingFields[10] = "null";
                sererializingFields[11] = "null";
                sererializingFields[12] = "null";
                StudyGroup group = new StudyGroup();
                group.serialize(sererializingFields);
                return group;
                // manager.insert(Integer.parseInt(inlineParams.get(1)), group);
                // return;
            }
    
            while (true) {
                System.out.print("Enter group admin name: ");
                sererializingFields[9] = scanner.nextLine();
                if (Checker.nullChecker(sererializingFields[9])) {
                    System.out.println("Incorrect name, please try again");
                } else
                    break;
            }
    
            sererializingFields[10] = LocalDateTime.now().plusDays(random.nextInt(10 - 5 + 1) + 5).toString();
    
            while (true) {
    
                System.out.print("Enter group admin passport id: ");
                sererializingFields[11] = scanner.nextLine();
                if (Checker.nullChecker(sererializingFields[11]) || sererializingFields[11].length() > 24) {
                    System.out.println("Incorrect passport id, please try again");
                } else
                    break;
            }
    
            System.out.println("Enter hair color: ");
            ArrayList<String> hairColor = new ArrayList<>();
    
            for (Color color : Color.values()) {
                System.out.println("- " + color);
                hairColor.add(color.toString());
            }
    
            while (true) {
                System.out.print("Enter group admin hair color: ");
                sererializingFields[12] = scanner.nextLine();
                if (!hairColor.contains(sererializingFields[12])) {
                    System.out.println("Incorrect hair color, please try again");
                } else
                    break;
            }
    
            StudyGroup group = new StudyGroup();
            group.serialize(sererializingFields);
            return group;
        } catch (Exception e) {
            System.out.println("Incorrect file");

        }
        return null;
        // manager.insert(Integer.parseInt(inlineParams.get(1)), group);
    }
}
