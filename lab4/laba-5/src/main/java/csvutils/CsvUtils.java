package csvutils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import models.CollectionInfo;
import models.CollectionRecord;
import models.StudyGroup;

public class CsvUtils {
    public static void recordToCsv(CollectionRecord collectionRecord, String filePath) {
        // convert record to csv
        LinkedHashMap<String, String[]> values4converting = new LinkedHashMap<String, String[]>();

        collectionRecord.getCollection().forEach((k, v) -> {
            values4converting.put(k, v.deserialize());
        });

        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath))) {

            for (String key : values4converting.keySet()) {

                writer.write("\"".getBytes());
                writer.write(key.getBytes());
                writer.write("\"".getBytes());
                for (int i = 0; i < values4converting.get(key).length; i++) {
                    writer.write(",".getBytes());
                    writer.write("\"".getBytes());
                    writer.write(values4converting.get(key)[i].getBytes());
                    writer.write("\"".getBytes());
                }
                writer.write("\n".getBytes());

            }

            writer.write("\"".getBytes());
            writer.write(collectionRecord.getInfo().getOwner().getBytes());
            writer.write("\"".getBytes());
            writer.write(",".getBytes());
            writer.write("\"".getBytes());
            writer.write(collectionRecord.getInfo().getCreationTime().toString().getBytes());
            writer.write("\"".getBytes());

        } catch (IOException ex) {
            System.out.println("Incorrect file path");
        }

    }

    public static CollectionRecord csvToRecord(String filePath) {
        TreeMap<String, StudyGroup> collection = new TreeMap<>();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {

            int t;
            StringBuilder sb = new StringBuilder();
            while ((t = reader.read()) != -1) {
                char r = (char) t;
                sb.append(r);
            }

            if (sb.length() == 0) {
                return new CollectionRecord(collection, new CollectionInfo(LocalDateTime.now(), "Unknown"));
            }

            String elemsString = sb.toString();
            elemsString = elemsString.replaceAll("\"", "");
            String[] lines = elemsString.split("\n");

            for (int i = 0; i < lines.length - 1; i++) {
                StudyGroup studyGroup = new StudyGroup();
                String[] values = lines[i].split(",");

                studyGroup.serialize(Arrays.copyOfRange(values, 1, values.length));

                collection.put(values[0], studyGroup);
            }

            return new CollectionRecord(collection, new CollectionInfo(
                    LocalDateTime.parse(lines[lines.length - 1].split(",")[1]), lines[lines.length - 1].split(",")[0]));

        } catch (Exception e) {
            System.out.println("Incorrect file");
        }
        return null;

    }

}
