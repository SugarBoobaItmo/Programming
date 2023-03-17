package models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 
 * The StudyGroup class represents a study group.
 * 
 */
public class StudyGroup implements Comparable<StudyGroup> {

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
            long studentsCount, Long expelledStudents, long transferredStudents, Semester semesterEnum,
            Person groupAdmin) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.transferredStudents = transferredStudents;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    /**
     * 
     * Constructs a StudyGroup object without information about it.
     * 
     */
    public StudyGroup() {
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
    public long getStudentsCount() {
        return studentsCount;
    }

    /**
     * 
     * Returns the expelledStudents of the study group.
     * 
     * @return the expelledStudents of the study group.
     */
    public long getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * 
     * Returns the transferredStudents of the study group.
     * 
     * @return the transferredStudents of the study group.
     */
    public long getTransferredStudents() {
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

    /**
     * 
     * Returns a string representation of the study group.
     * 
     * @return a string representation of the study group.
     */
    public void serialize(String... o) {

        this.id = Integer.parseInt(o[0]);
        this.name = o[1];
        this.coordinates = new Coordinates(Integer.parseInt(o[2]), Integer.parseInt(o[3]));
        this.creationDate = LocalDateTime.parse(o[4]);
        if (o[5].equals("null"))
            this.studentsCount = null;
        else
            this.studentsCount = Long.parseLong(o[5]);
        this.expelledStudents = Long.parseLong(o[6]);
        this.transferredStudents = Long.parseLong(o[7]);
        if (o[8].equals("null"))
            this.semesterEnum = null;
        else
            this.semesterEnum = Semester.valueOf(o[8]);
        if (o[9].equals("null") && o[10].equals("null") && o[11].equals("null") && o[12].equals("null"))
            this.groupAdmin = null;
        else
            this.groupAdmin = new Person(o[9], LocalDateTime.parse(o[10]), o[11], Color.valueOf(o[12]));

    }

    /**
     * 
     * Returns a string array representation of the study group.
     * 
     * @return a string array representation of the study group.
     */
    public String[] deserialize() {
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
    @Override
    public String toString() {
        return "StudyGroup " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", expelledStudents=" + expelledStudents +
                ", transferredStudents=" + transferredStudents +
                ", semesterEnum=" + (semesterEnum != null ? semesterEnum.getSemester() : semesterEnum) +
                ", groupAdmin=" + groupAdmin;
    }

    /** 
     * Indicates whether some other object is "equal to" this one.
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
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, transferredStudents,
                semesterEnum, groupAdmin);
    }

    /**
     * Compares this object with the specified object for order.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(StudyGroup o) {
        int comparingResult = Integer.compare(this.id, o.id);

        if (comparingResult == 0)
            comparingResult = this.name.compareTo(o.name);
        if (comparingResult == 0)
            comparingResult = this.coordinates.compareTo(o.coordinates);
        if (comparingResult == 0)
            comparingResult = this.creationDate.compareTo(o.creationDate);
        if (comparingResult == 0) {
            if (this.studentsCount == null) {
                if (o.studentsCount == null)
                    comparingResult = 0;
                else
                    comparingResult = -1;
            } else
                comparingResult = Long.compare(this.studentsCount, o.studentsCount);
        }
        if (comparingResult == 0)
            comparingResult = Long.compare(this.expelledStudents, o.expelledStudents);
        if (comparingResult == 0)
            comparingResult = Long.compare(this.transferredStudents, o.transferredStudents);
        if (comparingResult == 0) {
            if (this.semesterEnum == null) {
                if (o.semesterEnum == null)
                    comparingResult = 0;
                else
                    comparingResult = -1;
            } else
                comparingResult = this.semesterEnum.compareTo(o.semesterEnum);
        }

        if (comparingResult == 0) {
            if (this.groupAdmin == null) {
                if (o.groupAdmin == null)
                    comparingResult = 0;
                else
                    comparingResult = -1;
            } else
                comparingResult = this.groupAdmin.compareTo(o.groupAdmin);
        }

        return comparingResult;
    }

}
