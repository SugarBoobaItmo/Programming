package serveradapter;

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

/**
 * 
 * The ServerAdapter class provides a way to connect to a server and send
 * requests to it.
 * 
 * It includes methods to send a request and set the user's login.
 */
public class ServerAdapter {
    private int port;
    private String host;
    private int maxTries = 3;
    private int numTries = 0;
    private boolean connected = false;
    private String login;

    /**
     * 
     * Constructor for the ServerAdapter class.
     * 
     * @param port The port to connect to.
     * @param host The host to connect to.
     */
    public ServerAdapter(int port, String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * 
     * Sends a request to the server.
     * 
     * @param command The command to send to the server.
     * 
     * @param data    The data to include in the request.
     * 
     * @return The response from the server.
     * 
     * @throws UnknownHostException If the host is not known.
     * 
     * @throws IOException          If there is an error connecting to the server.
     */
    public Response sendRequest(String command, HashMap<String, Object> data) throws UnknownHostException, IOException {
        Request request = new Request(command, data, login);
        Response response = null;
        Socket sock = null;
        // flag to check if the connection was successful
        connected = false;
        // data of connection tries
        numTries = 0;
        // try to connect to the server
        while (!connected && numTries < maxTries) {
            try {
                sock = new Socket(host, port);
                OutputStream output = sock.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(output);

                // write request to server
                oos.writeObject(request);
                oos.flush();

                InputStream input = sock.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(input);
                // read response from server
                response = (Response) ois.readObject();

                connected = true;
            } catch (IOException e) {
                // if there is an error connecting to the server, try again
                numTries++;
                System.out.println("Attempt " + numTries + " failed");
                // if the max number of tries is reached, exit the program
                if (numTries == maxTries) {
                    System.out.println("Ooops!! Could not connect to server");

                    System.exit(1);
                }
                // wait 1 second before trying again
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
            } catch (ClassNotFoundException e) {
                throw new IOException("Could not deserialize response");
            } finally {
                if (sock != null) {
                    sock.close();
                }
            }

        }
        return response;
    }

    /**
     * 
     * Sets the login for the user.
     * 
     * @param login The login to set for the user.
     */
    public void setLogin(String login) {
        this.login = login;
    }

}
