package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


// @XmlRootElement(name = "Coordinates")
// @XmlType(propOrder = { "x", "y" })
public class Coordinates {
    private float x; // Значение поля должно быть больше -620
    private float y; // Максимальное значение поля: 762

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @XmlElement
    public void setX(float x) {
        this.x = x;
    }

    @XmlElement
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
