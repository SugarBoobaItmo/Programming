package collection_manager;


import java.util.LinkedHashSet;

import models.CollectionInfo;
import models.CollectionRecord;
import models.StudyGroup;


public abstract class AbstractManager {
    protected CollectionRecord collectionRecord;

    public CollectionInfo getInfo() {
        return this.collectionRecord.getInfo();
    }

    public LinkedHashSet<StudyGroup> getCollection() {
        return this.collectionRecord.getCollection();
    }

    public abstract void load();
    public abstract void add(StudyGroup group);
    public abstract void update();
    public abstract void removeById();
    public abstract void clear();
    public abstract void addIfMax();
    public abstract void removeGreater();

    // filter_starts_with_name
    // filter_contains_name
    // print_sorted_students_count
}
