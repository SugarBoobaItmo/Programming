package models;

import java.time.LocalDateTime;

// @XmlRootElement(name = "Person")
public class Person implements Comparable<Person>{
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private String passportID; //Длина строки не должна быть больше 24, Поле не может быть null
    private Color hairColor; //Поле не может быть null

    public Person(String name, LocalDateTime birthday, String passportID, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;

    }


    public Person() {
        this.name = null;
        this.birthday = null;
        this.passportID = null;
        this.hairColor = null;

    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }
        
    public String getPassportID() {
        return passportID;
    }

    
    public Color getHairColor() {
        return hairColor;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }


    
    
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }
    
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;    }

    @Override
    public String toString() {
        return 
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", passportID='" + passportID + '\'' +
                ", hairColor=" + hairColor.getColor();
    }

    @Override
    public int compareTo(Person groupAdmin) {
        int comparingResult = this.name.compareTo(groupAdmin.name);
        if (comparingResult==0) {
            if (this.birthday==null){
                if (groupAdmin.birthday==null) comparingResult = 0;
                else comparingResult = -1;
            }
                else comparingResult = this.birthday.compareTo(groupAdmin.birthday);
        }
        if (comparingResult==0) comparingResult = this.passportID.compareTo(groupAdmin.passportID);
        if (comparingResult==0) comparingResult = this.hairColor.compareTo(groupAdmin.hairColor);

        return comparingResult;
    }

}
