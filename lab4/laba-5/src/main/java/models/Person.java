package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.Date;

// @XmlRootElement(name = "Person")
@XmlType(propOrder = { "name", "birthday", "height", "weight", "passportID" })
public class Person {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Date birthday; // Поле не может быть null
    private int height; // Значение поля должно быть больше 0
    private long weight; // Значение поля должно быть больше 0
    private String passportID; // Значение этого поля должно быть уникальным, Длина строки должна быть не
                               // меньше 5, Длина строки не должна быть больше 20, Поле может быть null

    public Person(String name, Date birthday, int height, long weight, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;


    }


    public Person() {
        this.name = null;
        this.birthday = null;
        this.height = 0;
        this.weight = 0;
        this.passportID = null;

    }

    public String getName() {
        return name;
    }

    public java.util.Date getBirthday() {
        return birthday;
    }
    
    public int getHeight() {
        return height;
    }
    
    public long getWeight() {
        return weight;
    }
    
    public String getPassportID() {
        return passportID;
    }

    @XmlElement    
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @XmlElement
    public void setHeight(int height) {
        this.height = height;
    }

    @XmlElement
    public void setWeight(long weight) {
        this.weight = weight;
    }

    @XmlElement
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                '}';
    }

}
