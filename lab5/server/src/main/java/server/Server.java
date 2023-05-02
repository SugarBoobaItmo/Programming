package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;

import durgaapi.Reciever;
import durgaapi.Request;
import durgaapi.Response;
import server.exceptions.ServerException;
import utils.logger.MyLogger;

/**
 * 
 * The Server class represents a server that listens to incoming client requests
 * and sends responses back to clients.
 * 
 * It uses a non-blocking I/O model and a selector to efficiently handle
 * multiple client connections.
 */
public class Server {
    private int port;
    private MyLogger logger = new MyLogger();
    private Selector selector;
    private ServerSocketChannel ssc;
    private Reciever reciever;

    /**
     * 
     * Constructs a Server object with the specified port number and Reciever
     * object.
     * 
     * @param port     the port number that the server listens to
     * 
     * @param reciever the Reciever object that handles incoming requests
     * 
     * @throws ServerException if there is an error starting the server
     */
    public Server(int port, Reciever reciever) throws ServerException {
        this.port = port;
        try {
            // create selector and server socket channel
            this.selector = Selector.open();
            this.ssc = ServerSocketChannel.open();

            // bind server socket channel to port
            ssc.bind(new InetSocketAddress(this.port));

            // set non-blocking mode for the listening socket
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            this.reciever = reciever;

        } catch (IOException e) {
            throw new ServerException("Failed to start server");
        }
    }

    /**
     * 
     * Starts the server and begins listening for incoming client requests.
     * 
     * @throws ServerException if there is an error running the server
     */

    public void run() throws ServerException {
        try {
            selector.select();
            try {
                // add a 100ms delay
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new ServerException("Failed to sleep thread");
            }
            // create a set of selected keys
            Set<SelectionKey> keys = selector.selectedKeys();
            // iterate over keys
            for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext();) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(key.selector(), SelectionKey.OP_READ);

                        logger.info("Client connected");
                    }
                    if (key.isReadable()) {
                        readRequest(key, reciever);

                    }

                }
            }

        } catch (IOException e) {
            throw new ServerException("Failed to run server");
        }

    }

    /**
     * 
     * Sends a response object to a client through the specified SocketChannel.
     * 
     * @param response the response object to be sent
     * 
     * @param sc       the SocketChannel to send the response through
     * 
     * @throws IOException if there is an error sending the response
     */
    public void sendResponse(Object response, SocketChannel sc) throws IOException {

        // serialize response object
        ByteBuffer buffer = encodeObject(response);
        buffer.flip();

        // send serialized response object
        while (buffer.hasRemaining()) {
            sc.write(buffer);
        }

        logger.info("Response sent");
        // close socket channel
        sc.close();

    }

    /**
     * 
     * Reads an incoming client request from a SelectionKey object and handles it
     * using the specified Reciever object.
     * 
     * @param key      the SelectionKey object that represents the client request
     * 
     * @param reciever the Reciever object that handles the request
     * 
     * @throws ServerException if there is an error reading or handling the request
     */
    public void readRequest(SelectionKey key, Reciever reciever) throws ServerException {

        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(16384);

        try {
            sc.read(buffer);
            buffer.flip();

            // creating request object from serialized data
            Request request = (Request) decodeObject(buffer);
            logger.info("Request: " + request.getCommand());

            // handling request
            Response response = reciever.handleRequest(request, request.getLogin());

            if (response.isOk()) {
                logger.info("Response: " + response.getMessage());
            } else {
                logger.warning("Response: " + response.getMessage());
            }

            // sending response
            sendResponse(response, sc);
        } catch (IOException e) {
            throw new ServerException("Failed to read request");
        } catch (Exception e) {
            throw new ServerException("Failed to handle request");
        }
    }

    /**
     * 
     * Serializes an object into a ByteBuffer.
     * 
     * @param obj the object to be serialized
     * 
     * @return a ByteBuffer containing the serialized object
     * 
     * @throws IOException if there is an error serializing the object
     */
    public static ByteBuffer encodeObject(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);

        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + baos.size());

        buffer.put(baos.toByteArray());
        return buffer;

    }

    /**
     * 
     * Deserializes an object from a ByteBuffer.
     * 
     * @param buffer the ByteBuffer containing the serialized object
     * 
     * @return the deserialized object
     * 
     * @throws IOException            if there is an error deserializing the object
     * 
     * @throws ClassNotFoundException if the class of the serialized object cannot
     *                                be found
     */
    public static Object decodeObject(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
                ObjectInputStream ois = new ObjectInputStream(bis)) {

            return ois.readObject();
        }
    }

}
