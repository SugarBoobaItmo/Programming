package cli.commands.checker;

public class Checker{
    public static boolean correctChecker(String value, boolean isNull, boolean isNumber, boolean biggerThanZero){
        // System.out.println("hy from checker");
        int correctnessFlag = 1;
        if (isNull){
            if (nullChecker(value)) correctnessFlag = 0;
        } else if (value.equals("")) {return true;}
        if (isNumber){
            if (!numbChecker(value)) {correctnessFlag = 0;}
        } 
        if (correctnessFlag<1){return false;} 
        
        if (biggerThanZero) {

        if (value.matches("-\\d+") || value.matches("0")) {
            // System.out.println(11111111);
            correctnessFlag = 0;
            }
        }

        if (correctnessFlag==1) return true;
        else return false;
    }

    public static boolean nullChecker(String value) {
        if (value.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean numbChecker(String value) {
        if (value.matches("\\d+") || value.matches("-\\d+")) {
            return true;
        }
        return false;
    }

    public static boolean strChecker(String value) {
        if (value.matches("[a-zA-Z]+")) {
            return true;
        }
        return false;
    }

    public static boolean intChecker(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            // System.out.println("Your number is too big");
            // Integer.parseInt(1);
            return false;
        }
    }

    public static boolean longChecker(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            // System.out.println("Your number is too big");
            return false;
        }
    }


}
