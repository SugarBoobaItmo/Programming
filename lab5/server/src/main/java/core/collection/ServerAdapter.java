package core.collection;

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
import handlers.ClearHandler;

import handlers.GetCollectionRecordHandler;
import handlers.InsertHandler;
import handlers.RemoveGreaterHandler;
import handlers.RemoveKeyHandler;
import handlers.RemoveLowerHandler;
import handlers.UpdateHandler;
import utils.logger.MyLogger;

public class ServerAdapter {
    private int port;
    private MyLogger logger = new MyLogger();

    public ServerAdapter(int port) {
        this.port = port;
    }

    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // ssc.socket().bind(new InetSocketAddress(port));
            ssc.bind(new InetSocketAddress(port));
            
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            Reciever reciever = new Reciever();
            reciever.registerHandler(new InsertHandler());
            reciever.registerHandler(new ClearHandler());
            reciever.registerHandler(new RemoveKeyHandler());
            reciever.registerHandler(new RemoveLowerHandler());
            reciever.registerHandler(new RemoveGreaterHandler());
            reciever.registerHandler(new UpdateHandler());
            reciever.registerHandler(new RemoveKeyHandler());
            reciever.registerHandler(new GetCollectionRecordHandler());


            while (true) {
                selector.select();
                try {
                    Thread.sleep(100); // add a 100ms delay
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext();) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel sc = server.accept();
                            // SocketChannel sc = ssc.accept();
                            // System.out.println("Client connected: " + sc.getRemoteAddress());
                            logger.info("Client connected: " + sc.getRemoteAddress());
                            // add timeout to key
                            // ByteBuffer buffer = ByteBuffer.allocate(16384);
                            // key.attach(buffer);
                            
                            sc.configureBlocking(false);
                            sc.register(key.selector(), SelectionKey.OP_READ);
                            // key.attach(buffer);
                            // Object cliObject = new Object();
                            // key.attach(cliObject);
                            // Object cliObject = key.getPayload();

                        }
                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            readRequest(sc, key, reciever);

                        }

                    }
                }

                // keys.clear();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }

    }

    public void sendResponse(Object response, SocketChannel sc) throws IOException {
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = encodeObject(response);
        buffer.flip();

        while (buffer.hasRemaining()) {
            sc.write(buffer);
        }
        // System.out.println("Response sent");
        logger.info("Response sent");
        sc.close();

    }

    public void readRequest(SocketChannel sc, SelectionKey key, Reciever reciever) {
        // ByteBuffer buffer = (ByteBuffer) key.attachment();
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        try {
            sc.read(buffer);
            buffer.flip();
            Request request = (Request) decodeObject(buffer);
            // System.out.println("Request: " + request.getCommand());
            logger.info("Request: " + request.getCommand());

            Response response = reciever.handleRequest(request, request.getLogin());
            // System.out.println("Response: " + response.getMessage());
            if (response.isOk()){
                logger.info("Response: " + response.getMessage());
            } else {
                logger.warning("Response: " + response.getMessage());
            }
            sendResponse(response, sc);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ByteBuffer encodeObject(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + baos.size());
        // buffer.putInt(baos.size());
        // buffer.pu
        buffer.put(baos.toByteArray());
        return buffer;
        // return baos.toByteArray();

    }

    public static Object decodeObject(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
                ObjectInputStream ois = new ObjectInputStream(bis)) {

            return ois.readObject();
        }
    }
    

}
