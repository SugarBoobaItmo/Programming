package models;

public enum Semester {
    FIFTH("fifth"),
    SIXTH("sixth"),
    SEVENTH("seventh"),
    EIGHTH("eighth");

    private String semester;

    Semester(String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    // @Override
    // public String toString() {
    //     return semester;
    // }
}