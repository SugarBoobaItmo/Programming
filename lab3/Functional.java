import java.util.Arrays;
import java.util.function.Consumer;
import java.lang.*;
import java.lang.reflect.*;

public class Functional {
    int y = 0; 
    int y2 = 0;
    public void loop(){
        Functional2 fu = new Functional2(){
            // y = this.y;
            int y2 = Functional.this.y2;
        };
        for (int i=0;i<10;i++){
            y++;
            Functional2 fu2 = new Functional2(){
                int y = Functional.this.y;
                public void printY(){
                    System.out.println(y);
                }
            };
            // fu2.printY();
        } 
    }
    static int c = 10; 
    static Functional2 func = new Functional2();
    static Functional2.innerNested inn = func.new innerNested();
    static Functional2.staticNested stat = new Functional2.staticNested();
    
    interface FuncInterface {
        void execute(int a);
    }

    public static void main(String... args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InstantiationException, InvocationTargetException {
        FuncInterface fi1 = (int a) -> {
            System.out.println(a);
        };

        FuncInterface fi2 = System.out::println;

        fi2.execute(10);
        FuncInterface fi3 = new FuncInterface(){
            @Override
            public void execute(int a){
                System.out.println(a);
            }
        };
        fi3.execute(10);

        Functional2.staticNested.printBMinus();
        class Local {

            static final int a = 10;

        } 
        // Class clazz = Inner.class;
        // Inner b = new Inner();
        Class clazz = StaticNested.class;
        StaticNested b = new StaticNested();

    // Field fields[] = clazz.getDeclaredFields();
        Class clazz2 = b.getClass();

        System.out.println("Name of class");
        System.out.println(clazz2);
        System.out.println(b);
        System.out.println();

        
        System.out.println("analyzing fields");
        Field[] fields = clazz2.getDeclaredFields();
        // System.out.println(fields);
        for (java.lang.reflect.Field i: fields){
            System.out.print(i.getName()+" "+i.get(b)+" ");
            System.out.println(i.getType()+" ");
            // System.out.println(i.getModifiers());
            

        }
        // System.out.println(Arrays.toString(clazz2.getFields()));
        System.out.println("Second type of analyzing fields");
        System.out.println(Arrays.toString(clazz2.getDeclaredFields()));
        System.out.println();

        System.out.println();
        // System.out.println(Arrays.toString(clazz.getDeclaredFields()));

        System.out.println(Arrays.toString(clazz2.getDeclaredMethods()));
        System.out.println(Arrays.toString(clazz2.getDeclaredConstructors()));
        clazz2.getDeclaredField("b").setAccessible(true);
        clazz2.getDeclaredField("b").set(b, 25);
        System.out.println(b.b);
        System.out.println(clazz2.getDeclaredField("b").get(b));
        Constructor[] cons = clazz2.getDeclaredConstructors();
        for (Constructor i: cons){
            System.out.println(i);
        }
        System.out.println(cons[0].newInstance().toString());
    }

    public int timeIt(Runnable func) {
        int startTime = 10;
        func.run();
        int endTime = 20;
        return endTime - startTime;
    }

    static class StaticNested{
        static int a = 20;
        int c = 2;
        int b = Inner.a;

        public StaticNested(){
            c = 1;
        }
        @Override
        public String toString(){
            return "StaticNested"+b;
        }
    // Functional2.staticNested.printBMinus();

    }

    class Inner extends StaticNested {
        public static final int a = 1;
    }

    Inner iner = new Inner(){
        
    };
    Functional2 func2 = new Functional2(){
        public static final int c = 1;
    };
}

