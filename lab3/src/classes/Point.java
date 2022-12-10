package src.classes;

import java.util.Objects;

public class Point {
    private final int x, y, z;

    public Point(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        } 
        Point otherPoint = (Point) other;
        return this.x == otherPoint.x && this.y == otherPoint.y && this.z == otherPoint.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return x;
    }
    public int getZ(){
        return x;
    }

}
