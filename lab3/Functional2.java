public class Functional2{
    public int a = 1;
    public static int b = 1;
    // public Functional fu = new Functional(){
    //     // this.a = a;
    // };
    public static void say(){
        System.out.println(b);
    }

    public static class staticNested{
        
        public static int b = Functional2.b+1;
        public static void printBMinus(){System.out.println(b-1);}
        public void printB(){
            say();
        }
    }

    public class innerNested {
        int a = Functional2.this.a;

        public innerNested() {
            super();
            System.out.println("Hello");
        }

    }

    

    
}
