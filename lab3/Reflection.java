
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketImpl;
import java.util.Arrays;

import javafx.scene.control.TableColumn.SortType;
import src.abs.Creature;
import src.classes.Point;
import src.classes.Puh;
import src.enums.*;

public class Reflection {
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
        Creature creature = new Creature(new Point(15, 20, 3), "MamaKenga", 10, 100, new Properties[] { Properties.STRICT }){
            public int aaaaaaaaaaaaa;
            private int bbbbbbbbbb;

            @Override
            public void relax(int duration) {
                // TODO Auto-generated method stub
                    
            }

            @Override
            public void work(int duration) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public boolean isTired() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public String say() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void eat(String[] food) {
                // TODO Auto-generated method stub
                
            }
            private void bar() {}
            public void foo() {}
        };

        Class clazz = creature.getClass();
        System.out.println("Имя класса: "+clazz.getName()); 
        
        System.out.println("declared Поля класса");
        Field[] dfields = clazz.getDeclaredFields();
        for (Field i: dfields){
            System.out.println(i.getName());
        }
        System.out.println();
        
        System.out.println("declared Методы класса");
        Method[] dmethods = clazz.getDeclaredMethods();
        for (Method i: dmethods){
            System.out.println(i.getName()+ Arrays.toString(i.getParameters()));
        }
        System.out.println();

        System.out.println("Поля класса");
        Field[] fields = clazz.getFields();
        for (Field i: fields){
            System.out.println(i.getName());
        }
        System.out.println();

        System.out.println("Методы класса");
        Method[] methods = clazz.getMethods();
        for (Method i: methods){
            System.out.println(i.getName()+ Arrays.toString(i.getParameters()));
        }

        System.out.println("Переопределение приватного метода");
        Field name = clazz.getSuperclass().getDeclaredField("name");

        name.setAccessible(true);
        // System.out.println(name);
        name.set(creature, "BigPuha");
        System.out.println(creature.getName());

        Constructor[] constructor = clazz.getSuperclass().getConstructors();
        for (Constructor i: constructor){
            System.out.println(Arrays.toString(i.getParameterTypes()));
        }

        Class puhClass = Puh.class;
        Constructor crClass = puhClass.getConstructor(Point.class, String.class, int.class, int.class, Properties[].class);
        Puh puh = (Puh) crClass.newInstance(
            new Point(10, 20, 30),
            "eueueueueueueueueu",
            10,
            20,
            new Properties[]{}
        );
        System.out.println(puh);


        Field[] puhFields = puhClass.getSuperclass().getSuperclass().getFields();
        System.out.println(Arrays.toString(puhFields));

        Field[] puhdFields = puhClass.getSuperclass().getSuperclass().getDeclaredFields();
        System.out.println(Arrays.toString(puhdFields));

        Field crName = puhClass.getSuperclass().getSuperclass().getDeclaredField("name");
        crName.setAccessible(true);

        crName.set(puh, "alalalalalalalasflokdsgjmeds");

        System.out.println(crName.get(puh));

        Class integer = int.class;
        System.out.println(integer);
        System.out.println(integer.getName());
        System.out.println(integer.getClassLoader());
        System.out.println(integer.getSuperclass());
        System.out.println(integer.getTypeName());
        System.out.println(integer.isPrimitive());

    
    
    }

}
 

