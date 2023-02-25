package collection_manager;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

import cli.commands.exceptions.GroupNotFound;
import csvutils.CsvUtils;
import models.CollectionRecord;
import models.StudyGroup;

public class LocalManager extends AbstractManager {

    public LocalManager() {
        super();

        String owner;
        try {
            owner = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            owner = "Unknown";
        }

        this.collectionRecord = new CollectionRecord(
                new TreeMap<Integer, StudyGroup>(),
                new models.CollectionInfo(LocalDateTime.now(), owner));
        this.collectionRecord.getInfo().setFilePath("file.csv");

    }

    public LocalManager(String filePath) {

        collectionRecord = CsvUtils.csvToRecord(filePath);
        collectionRecord.getInfo().setFilePath(filePath);

    }


    @Override
    public void insert(int index, StudyGroup group) {
        collectionRecord.getCollection().put(index, group);

    }

    @Override
    public void update(int index, StudyGroup group) {
        collectionRecord.getCollection().put(index, group);

    }

    @Override
    public void removeGreater(StudyGroup greater_group) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        collectionRecord.getCollection().forEach((k, v) -> {
            if (v.compareTo(greater_group) > 0)
                keys.add(k);
        });
        collectionRecord.getCollection().keySet().removeAll(keys);
    }

    @Override
    public void clear() {

        collectionRecord.getCollection().clear();

    }

    @Override
    public void removeLower(StudyGroup lower_group) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        collectionRecord.getCollection().forEach((k, v) -> {
            if (v.compareTo(lower_group) < 0)
                keys.add(k);
        });
        collectionRecord.getCollection().keySet().removeAll(keys);
    }

    @Override
    public void removeKey(int key) throws GroupNotFound {
        if (collectionRecord.getCollection().containsKey(key)) {
            collectionRecord.getCollection().remove(key);
        } else {
            throw new GroupNotFound();
        }
    }
}
