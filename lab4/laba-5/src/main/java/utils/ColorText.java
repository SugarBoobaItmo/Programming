package utils;

public class ColorText {
    public static String colorText(Object text, String color) {
        

        String[] colors = { "black", "red", "green", "yellow", "blue", "magenta", "cyan", "white" };
        String[] colorCodes = { "30", "31", "32", "33", "34", "35", "36", "37" };
        String colorCode = "0";

        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(color)) {
                colorCode = colorCodes[i];
                break;
            }
        }
        return ("\u001B[" + colorCode + "m" + text + "\u001B[0m");
    }
}
