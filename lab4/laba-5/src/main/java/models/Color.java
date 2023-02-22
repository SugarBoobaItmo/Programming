package models;

public enum Color {
    BLUE("blue"),
    YELLOW("yellow"),
    WHITE("white");

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    // @Override
    // public String toString() {
    //     return color;
    // }
}
