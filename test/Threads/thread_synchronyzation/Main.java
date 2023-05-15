package thread_synchronyzation;

import java.util.concurrent.atomic.AtomicInteger;

public class Main{
    // volatile static int counter = 0;
    static AtomicInteger counter = new AtomicInteger(0);
    public static void main(String[] args){
        
        MyThreadWriter threadWriter = new MyThreadWriter();
        MyThreadReader threadReader = new MyThreadReader();
        threadWriter.start();
        threadReader.start();
        
        // try {
        //     threadWriter.join();
        //     threadReader.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // System.out.println(counter);
        // MyThread thread = new MyThread();
        // thread.start();
        // MyThread thread2 = new MyThread();
        // thread2.start();
        
    }
    public static class MyThread extends Thread{
        @Override
        public void run(){
            // System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "!");
            // System.out.println();
            incrementCounter();
        }
        public synchronized void incrementCounter(){
            // counter++;
            counter.incrementAndGet();
            System.out.println("increment counter for synchronyzed: " + counter+ " " + Thread.currentThread().getName());
        }
    }

    public static class MyThreadWriter extends Thread{
        @Override
        public void run(){
            while (counter.get() < 5){
                synchronized(Main.class){

                    System.out.println("increment counter: " + counter.getAndIncrement());
                }
                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }
    public static class MyThreadReader extends Thread{
        int localCounter = counter.get();
        @Override
        public void run(){
            while (counter.get() < 5){
                synchronized(Main.class){
                    if (localCounter != counter.get()){
                        System.out.println("counter: " + counter.get());
                        localCounter = counter.get();
                    }
                }
            }
        }
    }
}
