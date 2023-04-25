package handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.TreeMap;

import csvutils.CsvUtils;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.CollectionInfo;
import models.StudyGroup;

public class CollectionStorage {
    public static CollectionRecord load(String userAdress) throws ServerStorageException {
        CollectionRecord collectionRecord = null;
        String filePath = normalizePath(userAdress) + ".csv";
        try {
            if (!Files.exists(Paths.get(filePath)) || Files.isDirectory(Paths.get(filePath))
                    || Files.size(Paths.get(filePath)) == 0) {
                collectionRecord = createNewCollectionRecord(filePath);
            } else {
                collectionRecord = CsvUtils.csvToRecord(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return collectionRecord;

    }

    public static void save(String userAdress, CollectionRecord collectionRecord) throws ServerStorageException {
        String filePath = normalizePath(userAdress) + ".csv";
        CsvUtils.recordToCsv(collectionRecord, filePath);
    }

    public static CollectionRecord createNewCollectionRecord(String userAdress) throws ServerStorageException {
        CollectionRecord collectionRecord = null;

        TreeMap<String, StudyGroup> collection = new TreeMap<String, StudyGroup>();
        CollectionInfo info = new CollectionInfo(LocalDateTime.now(), normalizePath(userAdress));

        collectionRecord = new CollectionRecord(
                collection,
                info);
        // collectionRecord.getInfo().setFilePath(normalizePath(userAdress));
        System.out.println(collectionRecord.getInfo().getFilePath());
        save(userAdress, collectionRecord);

        return collectionRecord;

    }

    public static String normalizePath(String path) {
        path = path.replaceAll(".csv", "");
        return path.replaceAll("/|:\\d+", "");
    }
}
