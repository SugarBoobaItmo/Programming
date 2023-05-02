package handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeMap;

import csvutils.CsvUtils;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.CollectionInfo;
import models.StudyGroup;

/**
 * 
 * The CollectionStorage class provides functionality to load and save a
 * collection record.
 * 
 */
public class CollectionStorage {
    // static map to store all the collections of all the users
    public static HashMap<String, CollectionRecord> connectionMap = new HashMap<>();

    /**
     * 
     * Loads the collection for the specified user from the HashMap. If the
     * collection does not exist in the HashMap, it is created and added to the
     * HashMap.
     * 
     * @param userAddress the address of the user whose collection to load
     * @return the CollectionRecord object associated with the user
     * @throws ServerStorageException if an error occurs while loading the
     *                                collection
     */
    public static CollectionRecord load(String userAdress) throws ServerStorageException {
        // if the collection is already exist in the map, return it
        if (connectionMap.containsKey(userAdress)) {
            return (CollectionRecord) connectionMap.get(userAdress);
        } else {
            // if the collection is not exist in the map, create it with the name of the
            // user login
            CollectionRecord collectionRecord;
            String filePath = normalizePath(userAdress);
            try {
                // if the file is not exist or empty, create a new collection
                if (!Files.exists(Paths.get(filePath)) || Files.isDirectory(Paths.get(filePath))
                        || Files.size(Paths.get(filePath)) == 0) {
                    collectionRecord = createNewCollectionRecord(userAdress);
                    // if the file is exist and not empty, load the collection from the file
                } else {
                    collectionRecord = CsvUtils.csvToRecord(filePath);
                }
            } catch (IOException e) {
                throw new ServerStorageException();
            }
            connectionMap.put(userAdress, collectionRecord);
            return collectionRecord;
        }

    }

    /**
     * 
     * Creates a new CollectionRecord object for the specified user and saves it to
     * a file.
     * 
     * @param userAddress the address of the user for whom to create a new
     *                    collection
     * @return the newly created CollectionRecord object
     * @throws ServerStorageException if an error occurs while creating or saving
     *                                the collection record
     */
    public static CollectionRecord createNewCollectionRecord(String userAdress) throws ServerStorageException {
        CollectionRecord collectionRecord;

        TreeMap<String, StudyGroup> collection = new TreeMap<String, StudyGroup>();
        CollectionInfo info = new CollectionInfo(LocalDateTime.now(), userAdress);
        // create a new collection record
        collectionRecord = new CollectionRecord(
                collection,
                info);
        // save the collection record to the file
        save(userAdress, collectionRecord);

        return collectionRecord;

    }

    /**
     * 
     * Saves the specified CollectionRecord object to a file.
     * 
     * @param userAddress      the address of the user whose collection to save
     * @param collectionRecord the CollectionRecord object to save
     * @throws ServerStorageException if an error occurs while saving the collection
     *                                record
     */
    public static void save(String userAdress, CollectionRecord collectionRecord) throws ServerStorageException {
        String filePath = normalizePath(userAdress);
        CsvUtils.recordToCsv(collectionRecord, filePath);
    }

    /**
     * 
     * Normalizes the specified path by removing the ".csv" extension and any
     * trailing slash or port number.
     * 
     * @param path the path to normalize
     * @return the normalized path
     */
    public static String normalizePath(String path) {
        path = path.replaceAll(".csv", "");
        return path.replaceAll("/|:\\d+", "") + ".csv";
    }

    /**
     * 
     * Returns the static HashMap that stores all the collections of all the users.
     * 
     * @return the static HashMap that stores all the collections of all the users
     */
    public static HashMap<String, CollectionRecord> getConnectionMap() {
        return connectionMap;
    }
}
