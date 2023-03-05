package collection_manager;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import cli.commands.exceptions.GroupNotFound;
import csvutils.CsvUtils;
import models.CollectionRecord;
import models.StudyGroup;

public class LocalManager extends AbstractManager {

    public LocalManager(String[] args) {
        if (args.length > 0) {
            String filePath = args[0];
            while (!(filePath.matches(".*\\.csv"))) {
                System.out.println("Incorrect file format, please enter path to .csv file");
                System.out.print(">> ");
                filePath = System.console().readLine();
            }

            collectionRecord = CsvUtils.csvToRecord(filePath);
            collectionRecord.getInfo().setFilePath(filePath);

        } else {

            String owner;
            try {
                owner = Inet4Address.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                owner = "Unknown";
            }

            this.collectionRecord = new CollectionRecord(
                    new TreeMap<String, StudyGroup>(),
                    new models.CollectionInfo(LocalDateTime.now(), owner));
            String filePath = "file" + new Random().nextInt(1000) + ".csv";
            this.collectionRecord.getInfo().setFilePath(filePath);

            System.out.println("New collection was created in file: " + filePath);

        }

    }

    @Override
    public void insert(String index, StudyGroup group) {
        collectionRecord.getCollection().put(index, group);

    }

    @Override
    public void update(String index, StudyGroup group) {
        collectionRecord.getCollection().put(index, group);

    }

    @Override
    public void removeGreater(StudyGroup greater_group) {
        ArrayList<String> keys = new ArrayList<String>();
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
        ArrayList<String> keys = new ArrayList<String>();
        collectionRecord.getCollection().forEach((k, v) -> {
            if (v.compareTo(lower_group) < 0)
                keys.add(k);
        });
        collectionRecord.getCollection().keySet().removeAll(keys);
    }

    @Override
    public void removeKey(String key) throws GroupNotFound {
        if (collectionRecord.getCollection().containsKey(key)) {
            collectionRecord.getCollection().remove(key);
        } else {
            throw new GroupNotFound();
        }
    }
}
