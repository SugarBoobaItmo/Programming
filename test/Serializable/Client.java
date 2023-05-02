import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Client {
    public static void main(String[] args){
        try (Socket socket = new Socket("localhost", 8080)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            Person person = new Person("John", 30, true);
            objectOutputStream.writeObject(person);
            objectOutputStream.flush();

            System.out.println("Request sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}