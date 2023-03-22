package models;

/**
 * 
 * The Semester enum represents a semester.
 * 
 */
public enum Semester {
    FIFTH("fifth"),
    SIXTH("sixth"),
    SEVENTH("seventh"),
    EIGHTH("eighth");

    // semester
    private String semester;

    /**
     * 
     * Constructs a Semester object with a given semester.
     * 
     * @param semester the semester.
     */
    Semester(String semester) {
        this.semester = semester;
    }

    /**
     * 
     * Returns the semester.
     * 
     * @return the semester.
     */
    public String getSemester() {
        return semester;
    }

}