package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingServer {

    private static Selector selector = null;

    public static void main(String[] args) {

        try {
            selector = Selector.open();
            // We have to set connection host, port and non-blocking mode
            ServerSocketChannel socket = ServerSocketChannel.open();
            ServerSocket serverSocket = socket.socket();
            serverSocket.bind(new InetSocketAddress("localhost", 8089));
            socket.configureBlocking(false);
            // int ops = socket.validOps();
            socket.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> i = selectedKeys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();

                    if (key.isAcceptable()) {
                        // New client has been accepted
                        doAccept(key);
                    } else if (key.isReadable()) {
                        // We can run non-blocking operation READ on our client
                        doRead(key);
                    } else if (key.isWritable()) {
                        // We can run non-blocking operation WRITE on our client
                        doWrite(key);
                    }
                    i.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private static void handleAccept(
    // SelectionKey key) throws IOException {

    // System.out.println("Connection Accepted...");

    // // Accept the connection and set non-blocking mode
    // ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
    // SocketChannel sc = ssc.accept();
    // sc.configureBlocking(false);

    // // Register that client is reading this channel
    // sc.register(selector, SelectionKey.OP_READ);
    // }

    // private static void handleRead(SelectionKey key)
    // throws IOException {
    // System.out.println("Reading...");
    // // create a ServerSocketChannel to read the request
    // SocketChannel sc = (SocketChannel) key.channel();

    // // Create buffer to read data
    // ByteBuffer buffer = ByteBuffer.allocate(1024);
    // sc.read(buffer);
    // // Parse data from buffer to String
    // String data = new String(buffer.array()).trim();
    // if (data.length() > 0) {
    // System.out.println("Received message: " + data);
    // if (data.equalsIgnoreCase("exit")) {
    // sc.close();
    // System.out.println("Connection closed...");
    // } else {

    // }

    // }
    // sc.register(key.selector(), SelectionKey.OP_WRITE);
    // }

    // private static void handleWrite(SelectionKey key) throws IOException
    // {
    // System.out.println("Writing...");
    // // create a ServerSocketChannel to read the request
    // SocketChannel sc = (SocketChannel) key.channel();

    // // Create buffer to read data
    // ByteBuffer buffer = ByteBuffer.allocate(1024);
    // buffer.put("Hello, client!".getBytes());
    // buffer.flip();
    // sc.write(buffer);

    // }

    private static void doAccept(
            SelectionKey key) throws IOException {

        System.out.println("Connection Accepted...");

        // Accept the connection and set non-blocking mode
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        // key.attach();

        // attach the client data to the key
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private static void doRead(SelectionKey key)

            throws IOException {
        System.out.println("Reading...");
        // create a ServerSocketChannel to read the request
        SocketChannel sc = (SocketChannel) key.channel();

        // Create buffer to read data
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        sc.read(buffer);
        // Parse data from buffer to String
        String data = new String(buffer.array()).trim();
        if (data.length() > 0) {
            System.out.println("Received message: " + data);
            if (data.equalsIgnoreCase("exit")) {
                sc.close();
                System.out.println("Connection closed...");
            } else {
                // Parse data from buffer to Cat object
                // String[] catData = data.split(",");
                // String name = catData[0];
                // int age = Integer.parseInt(catData[1]);
                // String color = catData[2];

                // Cat cat = new Cat(name, age, color);
                // // Modify the Cat object
                // cat.setAdditionalInfo("This cat loves playing with strings.");
                // key.attach(cat);
                sc.register(key.selector(), SelectionKey.OP_WRITE, buffer);

            }

        }
    }

    private static void doWrite(SelectionKey key) throws IOException {
        System.out.println("Writing...");
        // create a ServerSocketChannel to read the request
        SocketChannel sc = (SocketChannel) key.channel();
        // System.out.println("key.attachment() = " + key.attachment());
        // Cat cat = (Cat) key.attachment();
        // get cat from buffer
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        String data = new String(buffer.array()).trim();
        String[] catData = data.split(",");
        String name = catData[0];
        int age = Integer.parseInt(catData[1]);
        String color = catData[2];

        Cat cat = new Cat(name, age, color);
        // Modify the Cat object
        cat.setAdditionalInfo("This cat loves playing with strings.");

        System.out.println(cat.toString());


        // System.out.println(cat.toString());/
    
        sc.write(buffer);
    
        sc.register(key.selector(), SelectionKey.OP_READ);
    }    
}
