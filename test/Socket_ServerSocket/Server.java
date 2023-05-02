import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Listening on port 8080");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connection established");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                System.out.println(line);
            }
            System.out.println("Request received");

            String response = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/html\r\n" +
                              "Connection: close\r\n" +
                              "\r\n" +
                              "<html><body><h1>Hello World</h1></body></html>";
            outputStream.write(response.getBytes());
            outputStream.flush();
            System.out.println("Response sent");

            // socket.close();
        }
    }
}
