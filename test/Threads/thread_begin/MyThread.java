package thread_begin;
public class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println("Hello from a thread, "+ Thread.currentThread().getName() + "!");
    }
    
}
