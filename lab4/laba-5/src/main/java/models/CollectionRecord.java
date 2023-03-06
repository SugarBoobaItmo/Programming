package models;

import java.util.TreeMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StudyGroups")
public class CollectionRecord {
    private TreeMap<String ,StudyGroup> collection;
    private CollectionInfo info;

    public CollectionRecord(TreeMap<String, StudyGroup> collection, CollectionInfo info) {
        this.collection = collection;
        this.info = info;
        
    };

    
    public CollectionRecord() {
        this.collection = new TreeMap<String ,StudyGroup>();
        this.info = new CollectionInfo(null, null);
    }
    
    public TreeMap<String, StudyGroup> getCollection() {
        return this.collection;
    }
    
    public CollectionInfo getInfo() {
        return this.info;
    }


}
