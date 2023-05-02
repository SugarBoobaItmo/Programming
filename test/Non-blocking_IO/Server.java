import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
    public static void main(String[] args) {
        try (// System.out.println("Server started");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(8080));

            Selector selector = Selector.open();

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                selector.select();
                if (selector.selectedKeys().isEmpty()) {
                    continue;
                }

                Iterator iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    if (key.isAcceptable()) {
                        System.out.println("Connection accepted");
                        SocketChannel client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        System.out.println("Reading from client");
                        SocketChannel client = (SocketChannel) key.channel();
                        client.read(buffer);
                        buffer.flip();
                        System.out.println("Data received: " + new String(buffer.array()));
                        buffer.clear();
                        client.register(selector, SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) {
                        System.out.println("Writing to client");
                        SocketChannel client = (SocketChannel) key.channel();
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "\r\n" +
                                "Hello world";
                        ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                        client.write(responseBuffer);
                        client.close();
                    }
                    iterator.remove();
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
