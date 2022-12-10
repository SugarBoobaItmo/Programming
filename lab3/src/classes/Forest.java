package src.classes;


import java.util.Random;

import src.abs.Location;
import src.abs.Positioned;
import src.interfaces.Descriptable;

public class Forest extends Location {
    private Tree[] trees;

    public Forest(Point pos, String name, Point pos2) {
        super(pos, name, pos2);
        Random random = new Random();
        this.trees = new Tree[random.nextInt(100)];
        for (int i = 0; i < this.trees.length; i++) {
            this.trees[i] = new Tree(random.nextInt(20));
        }
    }
    
    public Tree[] getTrees(){
        return trees;
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

    static class Tree implements Descriptable{
        private int height;

        public Tree(int height){
            this.height = height;
        }
        @Override
        public String describe() {
            return "высота этого дерева "+ height;
        } 
    }
}
