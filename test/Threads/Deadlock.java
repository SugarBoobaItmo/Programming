import java.util.concurrent.atomic.AtomicBoolean;

public class Deadlock {
    // volatile static boolean flag = true;
    static AtomicBoolean flag = new AtomicBoolean(true);
    public static void main(String[] args){
        MyThread1 thread1 = new MyThread1();
        MyThread2 thread2 = new MyThread2();
        thread1.start();
        thread2.start();

    }
    public static class MyThread1 extends Thread{
        @Override
        public void run(){
            while (flag.get()){
                synchronized(flag){
                    System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "! (Thread1))");
                    flag.set(false);
                    flag.notify();

                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    public static class MyThread2 extends Thread{
        @Override
        public void run(){
            while(true){
                System.out.println(flag.get());
                if (!flag.get()){
                    synchronized(flag){
                        try {
                            flag.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "! (Thread2)");
                        flag.set(true);
                    }
                    
                }
            }
            }
        
    }
    
}
