package collection_manager;

import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import models.CollectionRecord;
import models.StudyGroup;
import xmlutils.XmlUtils;


public class LocalManager extends AbstractManager {
    private String filePath;

    public LocalManager(String filePath) {
        this.filePath = filePath;    
    }

    @Override
    public void load() {
        
            XmlUtils.recordToXml(collectionRecord, filePath);
            System.out.println("Collection loaded");
            // collectionRecord = XmlUtils.xmlToRecord(filePath);
            
        
      
        
    }

    @Override

    @XmlElement(name = "StudyGroup")
    public void add(StudyGroup group) {
        // System.out.println(group);
         
        collectionRecord = new CollectionRecord(new LinkedHashSet<StudyGroup>(), new models.CollectionInfo(ZonedDateTime.now(), filePath));
        collectionRecord.getCollection().add(group);
        
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeById() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addIfMax() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeGreater() {
        // TODO Auto-generated method stub
        
    }
}
