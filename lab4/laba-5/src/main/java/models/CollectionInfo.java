package models;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlElement;

public class CollectionInfo {
    public final ZonedDateTime creationTime;
    public final String owner;

    
    public CollectionInfo(ZonedDateTime creationTime, String owner) {

        this.creationTime = creationTime;
        this.owner = owner;


    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    
    public String getOwner() {
        return owner;
    }
}
