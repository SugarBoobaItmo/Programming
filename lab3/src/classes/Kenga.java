package src.classes;

import java.util.Random;

import src.abs.Animal;
import src.abs.Container;
import src.abs.Creature;
import src.abs.Thing;
import src.enums.Liquids;
import src.enums.Meals;
import src.enums.Properties;
import src.interfaces.*;

public class Kenga extends Animal implements Pourable, Explainable {
    public Kenga(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "Выметайтесь из дома";
    }

    @Override
    public void relax(int duration) {
        this.setEnergy(this.getEnergy() + duration + 20);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration) > 0) {
            this.setEnergy(this.getEnergy() - duration + 2);
        } else {
            this.setEnergy(0);
        }

    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 30;
    }

    @Override
    public void eat(String[] food) {
        this.setEnergy(getEnergy() + 2);
        for (String i: food){
            System.out.println("съела "+i);
        }
    }

    @Override
    public String explain(Creature creature, String info) {
        return "объяснила " + creature.getName() + " " + info;
    }

    @Override
    public String pour(Liquids liquid, Container container) {
        container.setContent(liquid);
        return " налила " + liquid + " в " + container.getName();
    }
    
    @Override
    public String describe() {
        Random rand = new Random();
        Properties property = this.getProperties()[rand.nextInt(this.getProperties().length)];
        return property.translation + " " + this.getName() + " и её размер " + this.getCreatureSize();
    }

    public String[] cook(Meals meal) {
        class eatingDishes {
            private Meals meal;
            private String[] dish1 = new String[] {"яйцо", "хлеб", "круасан"};
            private String[] dish2 = new String[] {"суп", "салат", "пельмени" };
            private String[] dish3 = new String[] {"яблоко с корицей",
                    "хлеб с маслом", "круасан" };
            private String[] dish4 = new String[] {"макароны", "мясо" };

            public eatingDishes(Meals meal) {
                this.meal = meal;
            }

            public String[] getDish() {
                if (meal == Meals.BREAKFAST) {
                    return dish1;
                } else if (meal == Meals.LUNCH) {
                    return dish2;
                } else if (meal == Meals.DINNER) {
                    return dish3;
                } else if (meal == Meals.SUPPER) {
                    return dish4;
                }

                return new String[] {};
            }
        }
        eatingDishes dishes = new eatingDishes(meal);
        return dishes.getDish();
    }

}