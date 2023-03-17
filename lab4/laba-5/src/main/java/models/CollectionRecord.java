package models;

import java.util.TreeMap;

/**
 * 
 * The CollectionRecord class represents a collection record.
 * 
 * It contains the collection itself and information about the collection.
 * 
 */
public class CollectionRecord {
    // collection itself
    private TreeMap<String, StudyGroup> collection;
    // collection info
    private CollectionInfo info;

    /**
     * 
     * Constructs a CollectionRecord object with a given collection and collection
     * info.
     * 
     * @param collection the collection.
     * @param info       the collection info.
     */
    public CollectionRecord(TreeMap<String, StudyGroup> collection, CollectionInfo info) {
        this.collection = collection;
        this.info = info;

    };

    /**
     * 
     * Constructs a CollectionRecord object without information about it.
     * 
     */
    public CollectionRecord() {
        this.collection = new TreeMap<String, StudyGroup>();
        this.info = new CollectionInfo(null, null);
    }

    /**
     * 
     * Returns the collection.
     * 
     * @return the collection.
     */
    public TreeMap<String, StudyGroup> getCollection() {
        return this.collection;
    }

    /**
     * 
     * Returns the collection info.
     * 
     * @return the collection info.
     */
    public CollectionInfo getInfo() {
        return this.info;
    }

}
