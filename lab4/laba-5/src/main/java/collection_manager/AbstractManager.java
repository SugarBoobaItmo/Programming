package collection_manager;

import java.util.TreeMap;

import cli.commands.exceptions.GroupNotFound;
import models.CollectionInfo;
import models.CollectionRecord;
import models.StudyGroup;

public abstract class AbstractManager {
    protected CollectionRecord collectionRecord;

    public CollectionInfo getInfo() {
        return this.collectionRecord.getInfo();
    }

    public TreeMap<Integer, StudyGroup> getCollection() {
        return this.collectionRecord.getCollection();
    }

    public CollectionRecord getCollectionRecord() {
        return this.collectionRecord;
    }

    public abstract void add(Integer index, StudyGroup group);

    public abstract void update(int index, StudyGroup group);

    public abstract void removeGreater(StudyGroup greaterGroup);

    public abstract void clear();

    public abstract void removeLower(StudyGroup lowerGroup);

    public abstract void removeKey(int key) throws GroupNotFound;

    public abstract void insert(int index, StudyGroup group);

}
