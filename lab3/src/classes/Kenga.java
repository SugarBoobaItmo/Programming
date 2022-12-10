package src.classes;

import src.abs.Animal;
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
        return "М-м-м-м, я не говорю";
    }


    @Override
    public void relax(int duration) {
        this.setEnergy(this.getEnergy()+duration+20);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration) > 0) {
            this.setEnergy(this.getEnergy()-duration+2);
        } else {
            this.setEnergy(0);
        }

    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 30; 
    }
    
    @Override 
    public void eat(){
        this.setEnergy(getEnergy()+2);
    }

    @Override
    public String explain(Creature creature, String info) {
        return "объяснила "+ creature+ " "+ info;
    }

    @Override
    public String pour(Liquids liquid, Thing thing) {

        return "Налила "+liquid+" в "+thing.getName();
    }

    public String[] cook(Meals meal){
        class eatingDishes{
            private Meals meal;
            private String[] dish1 = new String[]{"каша", "яйцо", "хлеб", "круасан", Liquids.MILK.getTranslation()};
            private String[] dish2 = new String[]{"суп", "салат", "греча", "пельмени"}; 
            private String[] dish3 = new String[]{Liquids.KOMPOT.getTranslation(), "запеканка с повидлом", "хлеб с маслом", "круасан"}; 
            private String[] dish4 = new String[]{"макароны", Liquids.WINE.getTranslation(), "рыба"}; 

            public eatingDishes(Meals meal){
                this.meal = meal;
            }
            public String[] getDish(){
                if (meal == Meals.BREAKFAST){
                    return dish1;
                }else if (meal == Meals.LUNCH){
                    return dish2;
                }else if (meal == Meals.DINNER){
                    return dish3;
                }else if (meal == Meals.SUPPER){
                    return dish4;
                }

                return new String[]{};
            }   
        }
        eatingDishes dishes = new eatingDishes(meal);
        return dishes.getDish();
    }


}