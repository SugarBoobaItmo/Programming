package models;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// @XmlRootElement(name = "StudyGroup")
@XmlType(propOrder = { "id", "name", "coordinates", "creationDate", "studentsCount", "expelledStudents",
        "transferredStudents", "semesterEnum", "groupAdmin" })
public class StudyGroup {

    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                     // поля должно быть уникальным, Значение этого поля должно генерироваться
                     // автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
                                                  // автоматически
    private long studentsCount; // Значение поля должно быть больше 0
    private long expelledStudents; // Значение поля должно быть больше 0
    private long transferredStudents; // Значение поля должно быть больше 0
    private Semester semesterEnum; // Поле может быть null
    private Person groupAdmin; // Поле не может быть null

    public StudyGroup(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate,
            long studentsCount, long transferredStudents, Semester semesterEnum, Person groupAdmin) {
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

    public StudyGroup() {
        this.id = null;
        this.name = null;
        this.coordinates = null;
        this.creationDate = null;
        this.studentsCount = 0;
        this.expelledStudents = 0;
        this.transferredStudents = 0;
        this.semesterEnum = null;
        this.groupAdmin = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;

    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public long getExpelledStudents() {
        return expelledStudents;
    }

    public long getTransferredStudents() {
        return transferredStudents;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @XmlElement
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement
    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    @XmlElement
    public void setExpelledStudents(long expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    @XmlElement
    public void setTransferredStudents(long transferredStudents) {
        this.transferredStudents = transferredStudents;
    }

    @XmlElement
    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    @XmlElement
    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", expelledStudents=" + expelledStudents +
                ", transferredStudents=" + transferredStudents +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin +
                '}';
    }

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
                studentsCount == that.studentsCount &&
                expelledStudents == that.expelledStudents &&
                transferredStudents == that.transferredStudents &&
                semesterEnum == that.semesterEnum &&
                groupAdmin.equals(that.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, transferredStudents,
                semesterEnum, groupAdmin);
    }
}
