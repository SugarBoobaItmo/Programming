package models;

import java.time.LocalDateTime;

// import utils.ColorText;

/**
 * 
 * The Person class represents a person.
 * 
 */
public class Person implements Comparable<Person>, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String name; // Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; // Поле может быть null
    private String passportID; // Длина строки не должна быть больше 24, Поле не может быть null
    private Color hairColor; // Поле не может быть null

    /**
     * 
     * Constructs a Person object with a given name, birthday, passportID, and
     * hairColor.
     * 
     * @param name       the name of the person.
     * @param birthday   the birthday of the person.
     * @param passportID the passportID of the person.
     * @param hairColor  the hairColor of the person.
     */
    public Person(String name, LocalDateTime birthday, String passportID, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;

    }

    /**
     * 
     * Constructs a Person object without information about it.
     * 
     */
    public Person() {
        this.name = null;
        this.birthday = null;
        this.passportID = null;
        this.hairColor = null;

    }

    /**
     * 
     * Returns the name of the person.
     * 
     * @return the name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Returns the birthday of the person.
     * 
     * @return the birthday of the person.
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * 
     * Returns the passportID of the person.
     * 
     * @return the passportID of the person.
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * 
     * Returns the hairColor of the person.
     * 
     * @return the hairColor of the person.
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * 
     * Sets the name of the person.
     * 
     * @param name the name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * Sets the birthday of the person.
     * 
     * @param birthday the birthday of the person.
     */
    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * 
     * Sets the passportID of the person.
     * 
     * @param passportID the passportID of the person.
     */
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    /**
     * 
     * Sets the hairColor of the person.
     * 
     * @param hairColor the hairColor of the person.
     */
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * 
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object.
     */
    // @Override
    // public String toString() {
    //     return  
    //             ColorText.colorText("· name=", "yellow") + ColorText.colorText(name, "green") + "\n" +

    //             ColorText.colorText("· birthday=", "yellow") + ColorText.colorText(birthday, "green") + "\n" +
    //             ColorText.colorText("· passportID=", "yellow") + ColorText.colorText(passportID, "green") + "\n" +
    //             ColorText.colorText("· hairColor=", "yellow") + ColorText.colorText(hairColor.getColor(), "green") + "\n";
        
    // }

    @Override
    public String toString() {
        return "Person [birthday=" + birthday + ", hairColor=" + hairColor + ", name=" + name + ", passportID="
                + passportID + "]";
    }

    /**
     * 
     * Compares this person to the specified person. The result is true if and only
     * if the argument is not null and is a Person object that represents the same
     * person as this object.
     * 
     * @param groupAdmin the person to compare this Person against.
     * @return true if the given object represents a Person equivalent to this
     *         person, false otherwise.
     */
    @Override
    public int compareTo(Person groupAdmin) {
        int comparingResult = this.name.compareTo(groupAdmin.name);
        if (comparingResult == 0) {
            if (this.birthday == null) {
                if (groupAdmin.birthday == null)
                    comparingResult = 0;
                else
                    comparingResult = -1;
            } else
                comparingResult = this.birthday.compareTo(groupAdmin.birthday);
        }
        if (comparingResult == 0)
            comparingResult = this.passportID.compareTo(groupAdmin.passportID);
        if (comparingResult == 0)
            comparingResult = this.hairColor.compareTo(groupAdmin.hairColor);

        return comparingResult;
    }

    /**
     * 
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

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null)
            return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null)
            return false;
        if (passportID != null ? !passportID.equals(person.passportID) : person.passportID != null)
            return false;
        return hairColor == person.hairColor;
    }

    /**
     * 
     * Returns a hash code value for the object.
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (passportID != null ? passportID.hashCode() : 0);
        result = 31 * result + (hairColor != null ? hairColor.hashCode() : 0);
        return result;
    }

}
