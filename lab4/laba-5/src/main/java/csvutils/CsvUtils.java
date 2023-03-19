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

/**
 * 
 * The CsvUtils class provides functionality to convert a collection record to
 * csv and vice versa.
 * 
 */
public class CsvUtils {
    /**
     * 
     * Writes a CollectionRecord to a CSV file.
     * 
     * @param collectionRecord The CollectionRecord to be converted to CSV.
     * @param filePath         The path of the file to write the CSV data to.
     * @throws IOException if an I/O error occurs.
     */
    public static void recordToCsv(CollectionRecord collectionRecord, String filePath) {
        // convert record to csv
        LinkedHashMap<String, String[]> values4converting = new LinkedHashMap<String, String[]>();

        // put all values in a values4converting map
        collectionRecord.getCollection().forEach((k, v) -> {
            values4converting.put(k, v.serialize());
        });

        // write to file using a buffered output stream
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath))) {

            for (String key : values4converting.keySet()) {
                // write key of a value
                writer.write("\"".getBytes());
                writer.write(key.getBytes());
                writer.write("\"".getBytes());
                // write values of a value
                for (int i = 0; i < values4converting.get(key).length; i++) {
                    writer.write(",".getBytes());
                    writer.write("\"".getBytes());
                    writer.write(values4converting.get(key)[i].getBytes());
                    writer.write("\"".getBytes());
                }
                writer.write("\n".getBytes());

            }
            // write collection info
            writer.write("\"".getBytes());
            writer.write(collectionRecord.getInfo().getOwner().getBytes());
            writer.write("\"".getBytes());
            writer.write(",".getBytes());
            writer.write("\"".getBytes());
            writer.write(collectionRecord.getInfo().getCreationTime().toString().getBytes());
            writer.write("\"".getBytes());

        } catch (IOException ex) {
            System.out.println("No permission to write in file");
        }

    }

    /**
     * 
     * Converts a CSV file to a CollectionRecord object.
     * 
     * @param filePath the file path of the CSV file to be converted to a
     *                 CollectionRecord
     * 
     * @return the CollectionRecord object representing the CSV data, or null if the
     *         file could not be read or parsed correctly
     * @throws IOException if an I/O error occurs.
     */
    public static CollectionRecord csvToRecord(String filePath) {

        TreeMap<String, StudyGroup> collection = new TreeMap<>();
        // read from file using an input stream reader
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
            // read file and convert to string
            int t;
            StringBuilder sb = new StringBuilder();
            while ((t = reader.read()) != -1) {
                char r = (char) t;
                sb.append(r);
            }
            // if file is empty return only collection info
            if (sb.length() == 0) {
                return new CollectionRecord(collection, new CollectionInfo(LocalDateTime.now(), "Unknown"));
            }
            // split string by new line and convert to string array
            String elemsString = sb.toString();
            elemsString = elemsString.replaceAll("\"", "");
            String[] lines = elemsString.split("\n");

            // convert string array to collection record
            for (int i = 0; i < lines.length - 1; i++) {
                StudyGroup studyGroup;
                String[] values = lines[i].split(",");

                studyGroup = StudyGroup.deserialize(Arrays.copyOfRange(values, 1, values.length));

                collection.put(values[0], studyGroup);
            }

            return new CollectionRecord(collection, new CollectionInfo(
                    LocalDateTime.parse(lines[lines.length - 1].split(",")[1]), lines[lines.length - 1].split(",")[0]));

        } catch (IOException ex) {
            System.out.println("No such file or no permission to read it");
        } catch (Exception e) {
            System.out.println("Incorrect file");

        }
        return null;

    }

}
