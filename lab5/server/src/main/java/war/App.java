package war;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import durgaapi.Reciever;
import handlers.ClearHandler;
import handlers.CollectionStorage;
import handlers.ExitHandler;
import handlers.GetCollectionRecordHandler;
import handlers.InfoHandler;
import handlers.InsertHandler;
import handlers.RemoveGreaterHandler;
import handlers.RemoveKeyHandler;
import handlers.RemoveLowerHandler;
import handlers.ShowHandler;
import handlers.UpdateHandler;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import server.Server;
import server.exceptions.ServerException;

public class App {
    public static void main(String[] args) {

        Reciever reciever = new Reciever();
        reciever.registerHandler(new InsertHandler());
        reciever.registerHandler(new ClearHandler());
        reciever.registerHandler(new RemoveKeyHandler());
        reciever.registerHandler(new RemoveLowerHandler());
        reciever.registerHandler(new RemoveGreaterHandler());
        reciever.registerHandler(new UpdateHandler());
        reciever.registerHandler(new RemoveKeyHandler());
        reciever.registerHandler(new GetCollectionRecordHandler());
        reciever.registerHandler(new InfoHandler());
        reciever.registerHandler(new ExitHandler());
        reciever.registerHandler(new ShowHandler());

        Server server;
        try {
            server = new Server(8080, reciever);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            AtomicBoolean stopFlag = new AtomicBoolean(false);

            executor.execute(() -> {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input = null;

                try {
                    while (!stopFlag.get() && (input = br.readLine()) != null) {
                        if (input.equals("save")) {
                            System.out.println("Saving CollectionRecord to file...");
                            HashMap<String, CollectionRecord> con = CollectionStorage.getConnectionMap();
                            for (String key : con.keySet()) {
                                try {
                                    CollectionStorage.save(key, con.get(key));
                                } catch (ServerStorageException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                        } else if (input.equals("exit")) {
                            System.out.println("Exiting...");
                            System.exit(0);
                        } else {
                            System.out.println("Unknown command: " + input);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                }
            });

            while (!stopFlag.get()) {
                server.run();

                // Do other stuff in the main thread

                // Sleep for a short time to reduce CPU usage
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            stopFlag.set(true);
            executor.shutdown();

        } catch (ServerException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
