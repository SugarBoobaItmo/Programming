import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    static Lock lock = new ReentrantLock();
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
            lock.lock();
            while (flag.get()){
                    System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "! (Thread1))");
                    flag.set(false);

            }
            lock.unlock();
        }
        
    }
    public static class MyThread2 extends Thread{
        @Override
        public void run(){
            while(true){
                if (lock.tryLock()){
                        System.out.println(flag.get());
                        System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "! (Thread2)");
                        flag.set(true);
                        break;
                }
            }
            }
        
    }
    
}
