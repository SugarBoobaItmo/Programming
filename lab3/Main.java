
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import src.abs.*;

import src.classes.*;
import src.enums.*;
import src.exceptions.IncorrectSizeException;

public class Main {
    
    public static void main(String[] args) throws IncorrectSizeException {
        try {
            throw new IOException();
        } catch (IOException e) {

        } catch (Exception e) {

        }

        Creature.CreatureDispatcher dispatcher = new Creature.CreatureDispatcher();
        // dispatcher.killAll();
        System.out.println("Загрузка истории ");
        System.out.println();
        House house = new House(new Point(15, 20, 3), "Kenga'sHouse", new Point(20, 25, 3), "дерево");
        System.out.println(house.describe() + " сгенерирована");

        Kenga kangaroo = new Kenga(new Point(15, 20, 3), "MamaKenga", 10, 100, new Properties[] { Properties.STRICT });
        System.out.println("На свет появилась " + kangaroo.describe());
        Ru babyKang = new Ru(new Point(16, 21, 3), "KrohaRu", 5, 120,
                new Properties[] { Properties.FRIENDLY, Properties.SMALL });
        System.out.println("На свет появился " + babyKang.describe());
        Tigra tiger = new Tigra(new Point(16, 23, 3), "Tiger", 145, 80, new Properties[] { Properties.FRIENDLY });
        System.out.println("На свет появился " + tiger.describe());
        System.out.println();

        Random random = new Random();
        boolean mealIsReady;
        Cup cup = new Cup(kangaroo.getPosition(), kangaroo, "cup", null);

        if (random.nextInt(2) == 0) {
            System.out.println(kangaroo.getName() + " успела приготовить: ");
            String[] meal = kangaroo.cook(Meals.BREAKFAST);
            for (int i = 0; i < meal.length; i++) {
                System.out.println(i + 1 + ". " + meal[i]);
            }
            mealIsReady = true;

        } else {
            System.out.println(kangaroo.getName() + kangaroo.pour(Liquids.MILK, cup));
            mealIsReady = false;
        }
        System.out.println();
        if (mealIsReady) {
            System.out.println(babyKang.getName() + " спокойно поел");
            babyKang.eat(kangaroo.cook(Meals.BREAKFAST));
            System.out.println(tiger.getName() + " спокойно поел");
            tiger.eat(kangaroo.cook(Meals.BREAKFAST));
        } else {
            System.out.println(babyKang.getName() + " : " + babyKang.say());
            System.out.println(tiger.getName() + " " + tiger.swallow(cup.getContent()));
            Chair chair = new Chair(babyKang.getPosition(), babyKang, "chair");
            System.out.println(babyKang.getName() + babyKang.flip(chair, 1));
            babyKang.push(tiger);
            tiger.push(babyKang);

        }
        Forest forest = new Forest(new Point(23, 26, 3), "Forest", new Point(27, 30, 3));

        System.out.println(kangaroo.getName() + " : " + kangaroo.say());
        System.out.println();
        babyKang.setPosition(forest.getPosition());
        System.out.println(babyKang.getName() + " ушел в " + forest.getName());

        tiger.setPosition(forest.getPosition());
        System.out.println(tiger.getName() + " ушел в " + forest.getName());

        Basket basket = new Basket(tiger.getPosition(), tiger, "Basket");

        int duration = 0;
        while (true) {
            duration += 10;

            Cone cone = new Cone((new Point(23 + random.nextInt(10), 26 + random.nextInt(10), 3 + random.nextInt(4))),
                    tiger, "Cone");
            if (forest.checkConsist(cone)) {
                System.out.println("шишки есть");

                babyKang.throwing(cone, tiger);

                tiger.throwing(cone, babyKang);

                babyKang.setPosition(house.getPosition());
                System.out.println(babyKang.getName() + " ушел в " + house.getName());

                tiger.setPosition(house.getPosition());
                System.out.println(tiger.getName() + " ушел в " + house.getName());
                Forest.Tree tree = forest.new Tree(random.nextInt(10));
                System.out.println(tiger.getName() + " забыл " + basket + " под деревом, " + tree);
                tree.getCoordinates();
                break;
            } else {
                System.out.println("нет шишек");

                tiger.work(duration);
                babyKang.work(duration);

                babyKang.setPosition(house.getPosition());
                System.out.println(babyKang.getName() + " ушел в " + house.getName());

                tiger.setPosition(house.getPosition());
                System.out.println(tiger.getName() + " ушел в " + house.getName());
                System.out.println(kangaroo.getName() + " : " + kangaroo.say());

                babyKang.setPosition(forest.getPosition());
                System.out.println(babyKang.getName() + " снова ушел в " + forest.getName());

                tiger.setPosition(forest.getPosition());
                System.out.println(tiger.getName() + " снова ушел в " + forest.getName());

            }
            ;
        }
        // dispatcher.killAll();

        System.out.println();

        KrisRobin boy = new KrisRobin(null, "Robbi", 10, 100, new Properties[] { Properties.SMALL, Properties.CLEVER });
        System.out.println("На свет появился " + boy.describe());

        // kangaroo.cook(Meals.DINNER);
        if (random.nextInt(2) == 0) {
            System.out.println(
                    boy.getName() + " внезапно появился в локации " + house.getName());
            boy.setPosition(house.getPosition());

            System.out.println(tiger.getName() + " уселся есть");
            tiger.eat(kangaroo.cook(Meals.DINNER));

            // System.out.println();
            System.out
                    .println(babyKang.getName() + " " + babyKang.explain(tiger, "что не так с его бисквитным Кашлем"));
            System.out.println(tiger.getName() + " " + tiger.explain(babyKang, "что-то интересное"));
            System.out.println(
                    kangaroo.getName() + " " + kangaroo.explain(tiger, "не говорить вместе с " + babyKang.getName()));
            System.out.println(
                    kangaroo.getName() + " " + kangaroo.explain(babyKang, "не говорить вместе с " + tiger.getName()));

            System.out.println();

        } else {
            Location nowhere = new Location(new Point(10000, 10000, 10000), "Nowhere", new Point(10000, 10000, 10000)) {

                @Override
                public String describe() {

                    return "Локация " + this.getName();
                }

                @Override
                public boolean checkConsist(Positioned obj) {
                    boolean diapasonX = (this.getPosition2().getX() >= obj.getPosition().getX())
                            && (obj.getPosition().getX() >= this.getPosition().getX());

                    boolean diapasonY = (this.getPosition2().getY() >= obj.getPosition().getY())
                            && (obj.getPosition().getY() >= this.getPosition().getY());

                    return diapasonX && diapasonY;
                }

            };
            System.out.println(boy.getName() + " в локации " + nowhere.getName());
            boy.setPosition(nowhere.getPosition());
        }

        System.out.println();
        Pit pit = new Pit(new Point(11, 10, 3), "Pit", new Point(11, 11, 11));
        System.out.println(pit.describe() + " сгенерирована");

        Fog fog = new Fog(new Point(0, 0, 0), "Fog", new Point(10, 11, 11));
        System.out.println(fog.describe() + " сгенерирована");

        Puh bear = new Puh(new Point(11, 10, 3), "Puhi", 10, 100,
                new Properties[] { Properties.SCARED, Properties.SAD });
        System.out.println("На свет появился " + bear.describe());
        Rabbit rabbit = new Rabbit(new Point(10, 9, 3), "Rabbi", 10, 100,
                new Properties[] { Properties.CLEVER, Properties.SAD });

        System.out.println("На свет появился " + rabbit.describe());
        Pyatachok pig = new Pyatachok(new Point(11, 10, 3), "Piggi", 5, 60,
                new Properties[] { Properties.SCARED, Properties.SMALL, Properties.STUPID });
        System.out.println("На свет появился " + pig.describe());

        int x, y;
        // Random random = new Random();
        if (fog.checkConsist(rabbit)) {
            if (!pit.checkConsist(bear)) {
                rabbit.snort();
                System.out.println(rabbit.getName() + " фыркнул и теперь его энергия " + rabbit.getEnergy());
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
            if (pit.checkConsist(rabbit)) {
                System.out.println(pig.waiting(10, rabbit));
            }
            pig.relax(10);
            System.out.println(pig.getName() + " отдохнул");
        }

        System.out.println();
        if ((house.checkConsist(boy)) && (!house.checkConsist(pig)) && (!house.checkConsist(rabbit))
                && (!house.checkConsist(bear))) {
            System.out.println(boy.getName() + " " + boy.understood("что не все дома!!!"));

            if ((pit.checkConsist(pig) && pit.checkConsist(bear))
                    || (fog.checkConsist(pig) && fog.checkConsist(bear))) {
                pig.run(10, 10);
                bear.run(10, 10);
                System.out.println(bear.getName() + " " + bear.stand());

                if (random.nextInt(2) == 1) {
                    System.out.println(pig.getName() + " и " + bear.getName() + " начали свое движение");
                    Pot pot1 = new Pot(pig.getPosition(), bear, "Pot1", Liquids.HONNEY);
                    Pot pot2 = new Pot(pig.getPosition(), bear, "Pot2", Liquids.HONNEY);
                    pot1.push(pot2);
                    System.out.println(pig.getName() + " " + pig.sayNothing());
                } else {
                    pig.beep();
                    pig.understood("где находится");
                    boy.setPosition(pig.getPosition());

                }
            } else
                System.out.println("герои до сих пор бродят по " + forest.getName());
            if (!house.checkConsist(boy)) {

                System.out.println(boy.getName() + " нашел " + pig.getName());

            } else
                System.out.println(boy.getName() + " не нашел " + pig.getName());

            System.out.println();
            tiger.setPosition(forest.getPosition());
            for (int i = 0; i < random.nextInt(4); i++) {
                // System.out.println(i);
                System.out.println("Этот " + tiger.getName() + " бегает кругами");
                x = random.nextInt(10) - 5;
                y = random.nextInt(10) - 5;
                tiger.run(x, y);
                System.out.println(tiger.grow());
                tiger.work(10);
            }
            // System.out.println(tiger.getEnergy());
            if (tiger.getEnergy() > 50) {
                Creature voice = new Creature(tiger.getPosition(), "voice", 0, 0, new Properties[] {}) {

                    @Override
                    public void relax(int duration) {

                    }

                    @Override
                    public void work(int duration) {

                    }

                    @Override
                    public boolean isTired() {
                        return false;
                    }

                    @Override
                    public String say() {
                        return null;
                    }

                    @Override
                    public void eat(String[] food) {

                    }

                };

                System.out.println(rabbit.hear(voice.getName()));
                rabbit.setPosition(voice.getPosition());
                System.out.println(rabbit.getName() + " побежал на голос");

                tiger.convert(
                        new Properties[] { Properties.KIND, Properties.BIG, Properties.SAVING, Properties.BESTOFALL });

                System.out.println(
                        rabbit.getName() + " настолько обрадовался видеть " + tiger.getName() + " что теперь тот: ");
                for (int i = 0; i < tiger.getProperties().length; i++) {
                    if (i != tiger.getProperties().length - 1) {

                        System.out.print(tiger.getProperties()[i] + ", ");
                    } else
                        System.out.print(tiger.getProperties()[i]);

                }

                System.out.println();

            }

        } else {
            System.out.println(boy.getName() + " не дошел до " + house.getName() + " поэтому герои " + pig.getName()
                    + ", " + bear.getName() + ", " + rabbit.getName() + " потерялись навсегда");

        }
        System.out.println();
        dispatcher.showAllCreatures();
    }
}