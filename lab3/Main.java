
import java.util.Arrays;
import java.util.Random;

import src.abs.Creature;
import src.classes.Fog;
import src.classes.Pit;
import src.classes.Point;
import src.classes.Puh;
import src.classes.Pyatachok;
import src.classes.Rabbit;
import src.enums.Properties;
public class Main {
    public static void timer(Runnable action) {
        int startTime = 0;
        action.run();
        int endTime = 10;
        System.out.println(endTime - startTime);
    }

    public static void main(String[] args) {
        Pit pit = new Pit(new Point(11, 10, 3), "Pit", new Point(11, 11, 11));
        System.out.println(pit.describe() + " сгенерирована");

        

        Fog fog = new Fog(new Point(0, 0, 0), "Fog", new Point(10, 11, 11));
        System.out.println(fog.describe()+ " сгенерирована");

        Puh bear = new Puh(new Point(0, 0, 0), "Puhi", 10, 100, new Properties[] { Properties.SCARED, Properties.SAD });
        System.out.println("На свет появился " + bear.describe());
        Rabbit rabbit = new Rabbit(new Point(10, 9, 3), "Rabbi", 10, 100,
                new Properties[] { Properties.CLEVER, Properties.SAD });
        System.out.println("На свет появился " + rabbit.describe());
        Pyatachok pig = new Pyatachok(new Point(11, 10, 3), "Piggi", 5, 60,
                new Properties[] { Properties.SCARED, Properties.SMALL, Properties.STUPID });
        System.out.println("На свет появился " + pig.describe());

        int x, y;
        Random random = new Random();
        if (fog.checkConsist(rabbit)) {
            if (!pit.checkConsist(bear)){
                rabbit.snort();
                System.out.println(rabbit.getName() +" фыркнул и теперь его энергия "+ rabbit.getEnergy());
            }
        while (!pit.checkConsist(rabbit) && !rabbit.isTired()) {
            System.out.println("Этот " + rabbit.getName() + " пока не догнал яму");
            x = random.nextInt(10) - 5;
            y = random.nextInt(10) - 5;
            rabbit.run(x, y);
            x = random.nextInt(10) - 5;
            y = random.nextInt(10) - 5;
            pit.run(x, y);
            System.out.println(rabbit.say());
            
                rabbit.work(20);
                System.out.println(rabbit.getName() + " поработал. Его энгергия " + rabbit.getEnergy());
                System.out
                        .println(rabbit.getName() + " похудел от работы и теперь его вес " + rabbit.getCreatureSize());
            }
        }
        if (rabbit.isTired()) {
            System.out.println(rabbit.getName() + " слишком сильно устал");
        }
        if (pit.checkConsist(rabbit)) {
            System.out.println(rabbit.getName() + " наконец-то догнал яму и отдохнул");
        }

        if (fog.checkConsist(bear)) {
            while (!bear.isTired()) {
                bear.work(15);
                System.out.println(bear.getName() + " поработал. Его энгергия " + bear.getEnergy());
                System.out.println(bear.getName() + " похудел от работы и теперь его вес " + bear.getCreatureSize());
            }
            System.out.println(bear.getName() + " истощен и возможно при смерти");
        } else if (pit.checkConsist(bear)) {
            bear.relax(10);
            System.out.println(bear.getName() + " отдохнул");
        }

        if (fog.checkConsist(pig)) {
            System.out.println(pig.say());
            while (!pig.isTired()) {
                pig.work(10);
                System.out.println(pig.getName() + " поработал. Его энгергия " + pig.getEnergy());
                System.out.println(pig.getName() + " похудел от работы и теперь его вес " + pig.getCreatureSize());
            }
            System.out.println(pig.getName() + " истощен и не может работать");
        } else if (pit.checkConsist(pig)) {
            if (pit.checkConsist(rabbit)){
                System.out.println(pig.waiting(10, rabbit));
            }
            pig.relax(10);
            System.out.println(pig.getName() + " отдохнул");
        }
        
    }
}