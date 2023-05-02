import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 8080)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            String request = "GET / HTTP/1.1\r\n" +
                             "Host: localhost\r\n" +
                             "Connection: close\r\n" +
                             "\r\n";
            outputStream.write(request.getBytes());
            outputStream.flush();
            System.out.println("Request sent");

            int data;
            while ((data = inputStream.read()) != -1) {
                System.out.print((char) data);
            }
            System.out.println("Response received");
        }
    

    }
}
