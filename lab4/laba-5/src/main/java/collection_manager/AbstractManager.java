package collection_manager;

import java.util.TreeMap;

import cli.commands.exceptions.GroupNotFound;
import models.CollectionInfo;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * 
 * The AbstractManager class represents an abstract collection manager.
 * 
 * It provides functionality to get the collection record and the collection
 * itself.
 * 
 * It also provides functionality to update, remove, clear, insert and remove
 * greater and lower elements.
 * 
 */
public abstract class AbstractManager {
    // collection record instance with collection info and collection itself
    protected CollectionRecord collectionRecord;

    /**
     * 
     * Returns information about the collection.
     * 
     * @return information about the collection.
     */
    public CollectionInfo getInfo() {
        return this.collectionRecord.getInfo();
    }

    /**
     * 
     * Returns the collection.
     * 
     * @return the collection.
     */
    public TreeMap<String, StudyGroup> getCollection() {
        return this.collectionRecord.getCollection();
    }

    /**
     * 
     * Returns the collection record.
     * 
     * @return the collection record.
     */
    public CollectionRecord getCollectionRecord() {
        return this.collectionRecord;
    }

    /**
     * 
     * Updates a study group in the collection.
     * 
     * @param index the index of the study group to update.
     * @param group the updated study group.
     */
    public abstract void update(String index, StudyGroup group);

    /**
     * 
     * Removes a study group from the collection.
     * 
     * @param greaterGroup the study group to compare to.
     */
    public abstract void removeGreater(StudyGroup greaterGroup);

    /**
     * 
     * Clears the collection.
     * 
     */
    public abstract void clear();

    /**
     * 
     * Removes all study groups that are lower than the given study group.
     * 
     * @param lowerGroup the study group to compare to.
     */
    public abstract void removeLower(StudyGroup lowerGroup);

    /**
     * 
     * Removes a study group from the collection.
     * 
     * @param key the key of the study group to remove.
     * @throws GroupNotFound if the study group is not found.
     */
    public abstract void removeKey(String key) throws GroupNotFound;

    /**
     * 
     * Inserts a study group into the collection.
     * 
     * @param index the index of the study group to insert.
     * @param group the study group to insert.
     */
    public abstract void insert(String index, StudyGroup group);

    /**
     * 
     * Saves the collection to a file.
     */
    public abstract void save();

    /**
     * 
     * Sets the collection record.
     * 
     * @param filePath the file path of the collection record.
     */
    public abstract void setCollectionRecord(String filePath);

}
