package models;

/**
 * 
 * The Coordinates class represents coordinates (x,y).
 * 
 */
public class Coordinates implements Comparable<Coordinates> {
    private Integer x; // Значение поля должно быть больше -620
    private Integer y; // Максимальное значение поля: 762

    /**
     * 
     * Constructs a Coordinates object with a given x and y.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * Constructs a Coordinates object without information about it.
     * 
     */
    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * 
     * Returns the x coordinate.
     * 
     * @return the x coordinate.
     */
    public Integer getX() {
        return x;
    }

    /**
     * 
     * Returns the y coordinate.
     * 
     * @return the y coordinate.
     */
    public Integer getY() {
        return y;
    }

    /**
     * 
     * Sets the x coordinate.
     * 
     * @param x the x coordinate.
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * 
     * Sets the y coordinate.
     * 
     * @param y the y coordinate.
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * 
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "(" + x +
                "," + y + ")";

    }

    /**
     * 
     * Compares this object with the specified object for order.
     * 
     * @param coordinates the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less
     *         than, equal to, or greater than the specified object.
     */
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

    /**
     * 
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Coordinates coordinates = (Coordinates) obj;
        return x == coordinates.x && y == coordinates.y;
    }

    /**
     * 
     * Returns a hash code value for the object.
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

}
