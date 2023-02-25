package models;

import java.time.LocalDateTime;


public class CollectionInfo {
    public final LocalDateTime creationTime;
    public final String owner;
    public String filePath;
    
    public CollectionInfo(LocalDateTime creationTime, String owner) {

        this.creationTime = creationTime;
        this.owner = owner;

    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    
    public String getOwner() {
        return owner;
    }

    public String[] deserialize() {
        return null;       

    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
    
    @Override
    public String toString() {
        return "CollectionInfo : creationTime=" + creationTime + ", owner=" + owner;
    }
}
