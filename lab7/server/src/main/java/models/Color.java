package models;

/**
 * 
 * The Color enum represents a color.
 * 
 */
public enum Color{
    BLUE("blue"),
    YELLOW("yellow"),
    WHITE("white");

    // color
    private String color;

    /**
     * 
     * Constructs a Color object with a given color.
     * 
     * @param color the color.
     */
    Color(String color) {
        this.color = color;
    }

    /**
     * 
     * Returns the color.
     * 
     * @return the color.
     */
    public String getColor() {
        return color;
    }

}
