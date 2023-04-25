package collection_manager;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import cli.commands.exceptions.GroupNotFound;
import csvutils.CsvUtils;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * 
 * The LocalManager class represents a local collection manager.
 * 
 * It provides functionality to create a new collection, load a collection from
 * a
 * file, save a collection to a file, insert, update, remove, clear, remove
 * greater and remove lower elements.
 * 
 */
public class LocalManager extends AbstractManager {

    private String filePath;
    /**
     * 
     * Constructs a new LocalManager instance with the specified arguments.
     * If there is a file path argument, the collection is loaded from the file.
     * If the file is not found, the user is prompted to enter another file path or
     * skip to create a new collection.
     * If there is no file path argument, a new collection is created.
     * 
     * @param args the arguments to construct the LocalManager instance.
     */
    public LocalManager(String[] args) {
        // if there is a file path argument, load the collection from the file
        if (args.length > 0) {
            this.filePath = args[0];
            this.filePath = checkFilePath(this.filePath);

            collectionRecord = CsvUtils.csvToRecord(this.filePath);
            // if the file is not found, ask the user to enter another file path
            while (collectionRecord == null) {
                System.out.print("Please choose another file or skip to create new collection: ");
                this.filePath = System.console().readLine();
                // if the user skips, create a new collection
                if (this.filePath.equals("")) {
                    createNewCollection();
                    return;
                }
                collectionRecord = CsvUtils.csvToRecord(this.filePath);
            }
            // if the file is found, set the file path to the collection info
            collectionRecord.getInfo().setFilePath(this.filePath);

        } else {
            // if there is no file path argument, create a new collection
            createNewCollection();
        }

    }

    /**
     * Checks if the specified file path has a .csv file format.
     * If not, prompts the user to enter another file path.
     * 
     * @param filePath the file path to check.
     * @return the validated file path with .csv file format.
     */
    public String checkFilePath(String filePath) {
        while (!(filePath.matches(".*\\.csv"))) {
            System.out.println("Incorrect file format, please enter path to .csv file");
            System.out.print(">> ");
            filePath = System.console().readLine();
        }
        return filePath;
    }

    /**
     * Creates a new empty collection with the current time stamp and the owner's IP
     * address as the collection information.
     * Sets the file path of the collection information to a generated file name.
     */
    public void createNewCollection() {
        String owner;
        try {
            owner = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            owner = "Unknown";
        }

        this.collectionRecord = new CollectionRecord(
                new TreeMap<String, StudyGroup>(),
                new models.CollectionInfo(LocalDateTime.now(), owner));
        this.filePath = "file" + new Random().nextInt(1000) + ".csv";
        this.collectionRecord.getInfo().setFilePath(this.filePath);

        System.out.println("New collection was created in file: " + this.filePath);
    }

    /**
     * 
     * Inserts a StudyGroup object into the collection at the specified index.
     * 
     * @param index the index to insert the StudyGroup object at.
     * @param group the StudyGroup object to insert into the collection.
     */
    @Override
    public void insert(String index, StudyGroup group) {
        collectionRecord.getCollection().put(index, group);

    }

    /**
     * 
     * Updates a StudyGroup object in the collection at the specified index.
     * 
     * @param index the index to update the StudyGroup object at.
     * @param group the StudyGroup object to update in the collection.
     */
    @Override
    public void update(String index, StudyGroup group) {
        collectionRecord.getCollection().put(index, group);

    }

    /**
     * 
     * Removes all StudyGroup objects in the collection that are greater than the
     * specified StudyGroup object.
     * 
     * @param greater_group the StudyGroup object to compare against.
     */
    @Override
    public void removeGreater(StudyGroup greater_group) {
        ArrayList<String> keys = new ArrayList<String>();
        collectionRecord.getCollection().forEach((k, v) -> {
            if (v.compareTo(greater_group) > 0)
                keys.add(k);
        });
        collectionRecord.getCollection().keySet().removeAll(keys);
    }

    /**
     * Removes all StudyGroup objects in the collection.
     */
    @Override
    public void clear() {

        collectionRecord.getCollection().clear();

    }

    /**
     * 
     * Removes all StudyGroup objects in the collection that are less than the
     * specified StudyGroup object.
     * 
     * @param lower_group the StudyGroup object to compare against.
     */
    @Override
    public void removeLower(StudyGroup lower_group) {
        ArrayList<String> keys = new ArrayList<String>();
        collectionRecord.getCollection().forEach((k, v) -> {
            if (v.compareTo(lower_group) < 0)
                keys.add(k);
        });
        collectionRecord.getCollection().keySet().removeAll(keys);
    }

    /**
     * 
     * Removes the StudyGroup object at the specified index in the collection.
     * 
     * @param key the index of the StudyGroup object to remove.
     * @throws GroupNotFound if the specified index is not found in the collection.
     */
    @Override
    public void removeKey(String key) throws GroupNotFound {
        if (collectionRecord.getCollection().containsKey(key)) {
            collectionRecord.getCollection().remove(key);
        } else {
            throw new GroupNotFound();
        }
    }

    /**
     * 
     * Saves the collection to the file path specified in the collection information.
     * 
     */
    @Override
    public void save() {
        CsvUtils.recordToCsv(collectionRecord, this.filePath);
    }

    /**
     * 
     * Saves the collection from the specified file path.
     * 
     * @param filePath the file path to save from the collection.
     */
    @Override
    public void setCollectionRecord(String filePath) {
        
        CollectionRecord new_record = CsvUtils.csvToRecord(filePath);
        if (new_record != null) {
            this.collectionRecord = new_record;
            this.filePath = filePath;
            this.collectionRecord.getInfo().setFilePath(this.filePath);
        }
    }




}
