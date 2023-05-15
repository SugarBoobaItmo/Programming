package thread_begin;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();
        // join method is used to wait for the thread to finish
        thread.join();
        MyThread thread2 = new MyThread();
        thread2.start();
        
        MyThread thread3 = new MyThread();
        thread3.start();

        // creating a thread using Runnable interface
        MyRunnable runnable = new MyRunnable();
        Thread thread4 = new Thread(runnable);
        thread4.start();
        
        // yield method is used to pause the current thread and let other threads to execute 
        Thread.yield();
        System.out.println("Hello from main thread, "+ Thread.currentThread().getName() + "!");
    }
}
