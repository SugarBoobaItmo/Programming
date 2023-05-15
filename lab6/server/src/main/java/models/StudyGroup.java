package models;

import java.time.LocalDateTime;
import java.util.Objects;

// import utils.ColorText;

/**
 * 
 * The StudyGroup class represents a study group.
 * 
 */
public class StudyGroup implements Comparable<StudyGroup>, java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                        // поля должно быть уникальным, Значение этого поля должно генерироваться
                        // автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.time.LocalDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
                                                  // автоматически
    private Long studentsCount; // Значение поля должно быть больше 0, Поле может быть null
    private Long expelledStudents; // Значение поля должно быть больше 0, Поле не может быть null
    private Long transferredStudents; // Значение поля должно быть больше 0, Поле не может быть null
    private Semester semesterEnum; // Поле может быть null
    private Person groupAdmin; // Поле может быть null
    private String owner;

    /**
     * 
     * Constructs a StudyGroup object with a given id, name, coordinates,
     * creationDate,
     * studentsCount, expelledStudents, transferredStudents, semesterEnum, and
     * groupAdmin.
     * 
     * @param id                  the id of the study group.
     * @param name                the name of the study group.
     * @param coordinates         the coordinates of the study group.
     * @param creationDate        the creationDate of the study group.
     * @param studentsCount       the studentsCount of the study group.
     * @param expelledStudents    the expelledStudents of the study group.
     * @param transferredStudents the transferredStudents of the study group.
     * @param semesterEnum        the semesterEnum of the study group.
     * @param groupAdmin          the groupAdmin of the study group.
     */
    public StudyGroup(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate,
            Long studentsCount, Long expelledStudents, Long transferredStudents, Semester semesterEnum,
            Person groupAdmin) {

        if (id <= 0)
            throw new IllegalArgumentException("Id must be greater than 0.");
        this.id = id;

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name;

        if (coordinates == null)
            throw new IllegalArgumentException("Coordinates cannot be null.");
        this.coordinates = coordinates;

        if (creationDate == null)
            throw new IllegalArgumentException("Creation date cannot be null.");
        this.creationDate = creationDate;

        if (studentsCount != null && studentsCount <= 0)
            throw new IllegalArgumentException("Students count must be greater than 0.");
        this.studentsCount = studentsCount;

        if (expelledStudents <= 0)
            throw new IllegalArgumentException("Expelled students must be greater than 0.");
        this.expelledStudents = expelledStudents;

        if (transferredStudents <= 0)
            throw new IllegalArgumentException("Transferred students must be greater than 0.");
        this.transferredStudents = transferredStudents;

        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    /**
     * 
     * Constructs a StudyGroup object without information about it.
     * 
     */
    private StudyGroup() {
        this.id = null;
        this.name = null;
        this.coordinates = null;
        this.creationDate = null;
        this.studentsCount = 0l;
        this.expelledStudents = 0l;
        this.transferredStudents = 0l;
        this.semesterEnum = null;
        this.groupAdmin = null;
    }

    /**
     * 
     * Returns the id of the study group.
     * 
     * @return the id of the study group.
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * Returns the name of the study group.
     * 
     * @return the name of the study group.
     */
    public String getName() {
        return name;

    }

    /**
     * 
     * Returns the coordinates of the study group.
     * 
     * @return the coordinates of the study group.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * Returns the creationDate of the study group.
     * 
     * @return the creationDate of the study group.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * 
     * Returns the studentsCount of the study group.
     * 
     * @return the studentsCount of the study group.
     */
    public Long getStudentsCount() {
        return studentsCount;
    }

    /**
     * 
     * Returns the expelledStudents of the study group.
     * 
     * @return the expelledStudents of the study group.
     */
    public Long getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * 
     * Returns the transferredStudents of the study group.
     * 
     * @return the transferredStudents of the study group.
     */
    public Long getTransferredStudents() {
        return transferredStudents;
    }

    /**
     * 
     * Returns the semesterEnum of the study group.
     * 
     * @return the semesterEnum of the study group.
     */
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    /**
     * 
     * Returns the groupAdmin of the study group.
     * 
     * @return the groupAdmin of the study group.
     */
    public Person getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * 
     * Sets the id of the study group.
     * 
     * @param id the id of the study group.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * Sets the name of the study group.
     * 
     * @param name the name of the study group.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * Sets the coordinates of the study group.
     * 
     * @param coordinates the coordinates of the study group.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * 
     * Sets the creationDate of the study group.
     * 
     * @param creationDate the creationDate of the study group.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * 
     * Sets the studentsCount of the study group.
     * 
     * @param studentsCount the studentsCount of the study group.
     */
    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    /**
     * 
     * Sets the expelledStudents of the study group.
     * 
     * @param expelledStudents the expelledStudents of the study group.
     */
    public void setExpelledStudents(long expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    /**
     * 
     * Sets the transferredStudents of the study group.
     * 
     * @param transferredStudents the transferredStudents of the study group.
     */
    public void setTransferredStudents(long transferredStudents) {
        this.transferredStudents = transferredStudents;
    }

    /**
     * 
     * Sets the semesterEnum of the study group.
     * 
     * @param semesterEnum the semesterEnum of the study group.
     */
    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    /**
     * 
     * Sets the groupAdmin of the study group.
     * 
     * @param groupAdmin the groupAdmin of the study group.
     */
    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public void setOwner (String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    /**
     * 
     * Static method that returns a study group from input values.
     * 
     * @param o the input values.
     * @return a study group.
     */
    public static StudyGroup deserialize(String... o) {

        int id = Integer.parseInt(o[0]);
        String name = o[1];
        Coordinates coordinates = new Coordinates(Integer.parseInt(o[2]), Integer.parseInt(o[3]));
        LocalDateTime creationDate = LocalDateTime.parse(o[4]);
        Long studentsCount;
        if (o[5].equals("null"))
            studentsCount = null;
        else
            studentsCount = Long.parseLong(o[5]);

        Long expelledStudents = Long.parseLong(o[6]);
        Long transferredStudents = Long.parseLong(o[7]);
        Semester semesterEnum;
        if (o[8].equals("null"))
            semesterEnum = null;
        else
            semesterEnum = Semester.valueOf(o[8]);
        Person groupAdmin;
        if (o[9].equals("null") && o[10].equals("null") && o[11].equals("null") && o[12].equals("null"))
            groupAdmin = null;
        else
            groupAdmin = new Person(o[9], LocalDateTime.parse(o[10]), o[11], Color.valueOf(o[12]));

        return new StudyGroup(id, name, coordinates, creationDate, studentsCount, expelledStudents, transferredStudents,
                semesterEnum, groupAdmin);
    }

    /**
     * 
     * Returns a string array representation of the study group.
     * 
     * @return a string array representation of the study group.
     */
    public String[] serialize() {
        String[] o = new String[13];
        o[0] = this.id.toString();
        o[1] = this.name;
        o[2] = this.coordinates.getX().toString();
        o[3] = this.coordinates.getY().toString();
        o[4] = this.creationDate.toString();
        if (o[5] == null)
            o[5] = "null";
        else
            o[5] = this.studentsCount.toString();
        o[6] = this.expelledStudents.toString();
        o[7] = this.transferredStudents.toString();
        if (this.semesterEnum == null)
            o[8] = "null";
        else
            o[8] = this.semesterEnum.toString();
        if (this.groupAdmin == null) {
            o[9] = "null";
            o[10] = "null";
            o[11] = "null";
            o[12] = "null";
            return o;
        }
        o[9] = this.groupAdmin.getName().toString();
        o[10] = this.groupAdmin.getBirthday().toString();
        o[11] = this.groupAdmin.getPassportID();
        o[12] = this.groupAdmin.getHairColor().toString();
        return o;
    }

    /**
     * 
     * Returns a string representation of the study group.
     * 
     * @return a string representation of the study group.
     */
    // @Override
    // public String toString() {
    // return "StudyGroup " +
    // "id="+ id + "\n" +
    // ColorText.colorText("· name=", "yellow") + ColorText.colorText(name, "green")
    // + "\n" +
    // ColorText.colorText("· coordinates=", "yellow") +
    // ColorText.colorText(coordinates, "green") + "\n" +
    // ColorText.colorText("· creationDate=", "yellow") +
    // ColorText.colorText(creationDate, "green") + "\n" +
    // ColorText.colorText("· studentsCount=", "yellow") +
    // ColorText.colorText(studentsCount, "green") + "\n" +
    // ColorText.colorText("· expelledStudents=", "yellow") +
    // ColorText.colorText(expelledStudents, "green") + "\n" +
    // ColorText.colorText("· transferredStudents=", "yellow") +
    // ColorText.colorText(transferredStudents, "green") + "\n" +
    // ColorText.colorText("· semesterEnum=", "yellow") +
    // ColorText.colorText((semesterEnum != null ? semesterEnum.getSemester():
    // null), "green") + "\n" +
    // ColorText.colorText("· groupAdmin=", "yellow") +
    // ColorText.colorText(groupAdmin, "green");

    // }

    @Override
    public String toString() {
        return "StudyGroup " +
                "id=" + id + "\n" +
                "· name=" + name + "\n" +
                "· coordinates=" + coordinates + "\n" +
                "· creationDate=" + creationDate + "\n" +
                "· studentsCount=" + studentsCount + "\n" +
                "· expelledStudents=" + expelledStudents + "\n" +
                "· transferredStudents=" + transferredStudents + "\n" +
                "· semesterEnum=" + (semesterEnum != null ? semesterEnum.getSemester() : null) + "\n" +
                "· groupAdmin=" + groupAdmin;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudyGroup that = (StudyGroup) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                coordinates.equals(that.coordinates) &&
                creationDate.equals(that.creationDate) &&
                studentsCount.equals(that.studentsCount) &&
                expelledStudents.equals(that.expelledStudents) &&
                transferredStudents.equals(that.transferredStudents) &&
                semesterEnum == that.semesterEnum &&
                groupAdmin.equals(that.groupAdmin);
    }

    /**
     * Returns a hash code value for the object.
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, transferredStudents,
                semesterEnum, groupAdmin);
    }

    /**
     * Compares this object with the specified object for order.
     * 
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(StudyGroup o) {
        // compare all fields
        if (o.getStudentsCount() == null && this.getStudentsCount() == null)
            return 0;

        if (o.getStudentsCount() == null)
            return 1;

        if (this.getStudentsCount() == null)
            return -1;

        return this.getStudentsCount().compareTo(o.getStudentsCount());
    }

}
