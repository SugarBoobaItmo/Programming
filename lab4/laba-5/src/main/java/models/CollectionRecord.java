package models;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StudyGroups")
public class CollectionRecord {
    private LinkedHashSet<StudyGroup> collection;
    private CollectionInfo info;

    public CollectionRecord(LinkedHashSet<StudyGroup> collection, CollectionInfo info) {
        this.collection = collection;
        this.info = info;
    };

    
    public CollectionRecord() {
        this.collection = new LinkedHashSet<StudyGroup>();
        this.info = new CollectionInfo(null, null);
    }
    
    @XmlElement(name = "StudyGroup")
    public LinkedHashSet<StudyGroup> getCollection() {
        return this.collection;
    }
    
    @XmlElement
    public CollectionInfo getInfo() {
        return this.info;
    }

    // @XmlElement
    // public String getCurrentTime() {
    //     return info.getCreationTime().toString();
    // }
}
