package models;

import java.time.LocalDateTime;

/**
 * 
 * The CollectionInfo class represents information about a collection.
 * 
 * It contains the creation time and the owner of the collection.
 * 
 */
public class CollectionInfo {
    // creation time of the collection
    public final LocalDateTime creationTime;
    // owner of the collection
    public final String owner;
    // path to the file with the collection
    public String filePath;

    /**
     * 
     * Constructs a CollectionInfo object with a given creation time and owner.
     * 
     * @param creationTime the creation time of the collection.
     * @param owner        the owner of the collection.
     */
    public CollectionInfo(LocalDateTime creationTime, String owner) {

        this.creationTime = creationTime;
        this.owner = owner;

    }

    /**
     * 
     * Returns the creation time of the collection.
     * 
     * @return the creation time of the collection.
     */
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * 
     * Returns the owner of the collection.
     * 
     * @return the owner of the collection.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 
     * Deserializes the collection into an array of strings.
     * 
     * @return an array of strings representing the collection.
     */

    /**
     * 
     * Sets the path to the file with the collection.
     * 
     * @param filePath the path to the file with the collection.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 
     * Returns the path to the file with the collection.
     * 
     * @return the path to the file with the collection.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 
     * Returns a string representation of the collection information.
     * 
     * @return a string representation of the collection information.
     */
    @Override
    public String toString() {
        return "CollectionInfo : creationTime=" + creationTime + ", owner=" + owner;
    }
}
