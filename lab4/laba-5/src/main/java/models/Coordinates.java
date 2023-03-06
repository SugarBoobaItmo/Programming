package models;

import javax.xml.bind.annotation.XmlElement;

public class Coordinates implements Comparable<Coordinates> {
    private Integer x; // Значение поля должно быть больше -620
    private Integer y; // Максимальное значение поля: 762

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @XmlElement
    public void setX(Integer x) {
        this.x = x;
    }

    @XmlElement
    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x +
                "," + y + ")";

    }

    @Override
    public int compareTo(Coordinates coordinates) {
        // return 0;
        if (this.x > coordinates.x) {
            return 1;
        } else if (this.x < coordinates.x) {
            return -1;
        } else {
            if (this.y > coordinates.y) {
                return 1;
            } else if (this.y < coordinates.y) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
