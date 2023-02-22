package models;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StudyGroups")
public class CollectionRecord {
    private TreeMap<Integer ,StudyGroup> collection;
    private CollectionInfo info;

    public CollectionRecord(TreeMap<Integer, StudyGroup> collection, CollectionInfo info) {
        this.collection = collection;
        this.info = info;
        
    };

    
    public CollectionRecord() {
        this.collection = new TreeMap<Integer ,StudyGroup>();
        this.info = new CollectionInfo(null, null);
    }
    
    public TreeMap<Integer, StudyGroup> getCollection() {
        return this.collection;
    }
    
    public CollectionInfo getInfo() {
        return this.info;
    }

    // @XmlElement
    // public String getCurrentTime() {
    //     return info.getCreationTime().toString();
    // }
}
