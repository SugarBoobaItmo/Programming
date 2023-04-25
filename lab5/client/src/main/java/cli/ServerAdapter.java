package cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import durgaapi.Request;
import durgaapi.Response;

public class ServerAdapter {
    private int port;
    private String host;
    private int maxTries = 3;
    private int numTries = 0;
    private boolean connected = false;
    private String login;
    

    public ServerAdapter(int port, String host) {
        this.port = port;
        this.host = host;
    }


    public Response sendRequest(String command, HashMap<String, Object> data) throws UnknownHostException, IOException {
        Request request = new Request(command, data, login);
        Response response = null;
        Socket sock = null;
        connected = false;
        numTries = 0;
        while (!connected && numTries < maxTries) {
            try {
                sock = new Socket(host, port);
                OutputStream output = sock.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(output);
                oos.writeObject(request);
                oos.flush();

                InputStream input = sock.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(input);
                response = (Response) ois.readObject();
                // System.out.println("Received message: " + response);
                connected = true;
            } catch (IOException e) {
                numTries++;
                System.out.println("Attempt " + numTries + " failed");
                if (numTries == maxTries) {
                    // throw e;
                    System.out.println("Ooops!! Could not connect to server");
                    
                    System.exit(1);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
                throw new IOException("Could not deserialize response");
            } finally {
                if (sock != null) {
                    sock.close();
                }
            }

            // System.out.println("Received message: " + response);

        }
        return response;

    }

    public void setLogin(String login) {
        this.login = login;
    }

}
