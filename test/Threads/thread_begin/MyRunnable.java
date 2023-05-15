package thread_begin;
public class MyRunnable implements Runnable{
    @Override
    public void run(){
        System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "!");
    }
}
