package src.classes;

import src.abs.Location;
import src.abs.Positioned;
import src.interfaces.Descriptable;

public class House extends Location {

    private String material;
    private Door door = new Door(material);

    public House(Point pos, String name, Point pos2, String material) {
        super(pos, name, pos2);
        this.material = material;
    }

    public String describe() {
        return "Локация " + this.getName();
    }

    public boolean checkConsist(Positioned obj) {
        boolean diapasonX = (this.getPosition2().getX() >= obj.getPosition().getX())
                && (obj.getPosition().getX() >= this.getPosition().getX());

        boolean diapasonY = (this.getPosition2().getY() >= obj.getPosition().getY())
                && (obj.getPosition().getY() >= this.getPosition().getY());

        return diapasonX && diapasonY;
    }

    public String getMaterial() {
        return material;
    }

    public Door getDoor() {
        return door;
    }

    class Door implements Descriptable {
        private String material;

        public Door(String material) {
            this.material = material;
        }

        @Override
        public String describe() {
            return "изготовлено из материала " + material;
        }

        public String getMaterial() {
            return material;
        }
    }

}
