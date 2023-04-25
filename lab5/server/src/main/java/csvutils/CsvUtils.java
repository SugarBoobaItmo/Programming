package csvutils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvMalformedLineException;

import handlers.exceptions.ServerStorageException;
import models.CollectionInfo;
import models.CollectionRecord;
import models.StudyGroup;
// import utils.ColorText;

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
     * @param collectionRecord The CollectionRecord to write to the CSV file.
     * @param filePath         The path of the file to write the CSV data to.
     * @throws ServerStorageException
     */
    public static void recordToCsv(CollectionRecord collectionRecord, String filePath) throws ServerStorageException {
        // convert record to csv
        LinkedHashMap<String, String[]> values4converting = new LinkedHashMap<String, String[]>();

        // put all values in a vaCollectionRecord collectionRecord, String filePath map
        collectionRecord.getCollection().forEach((k, v) -> {
            values4converting.put(k, v.serialize());
        });

        // write to file using a buffered output stream
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath))) {

            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(writer));

            csvWriter.writeNext(new String[] { "key", "groupId", "groupName", "coordinateX", "coordinateY",
                    "creationDate", "studentsCount", "expelledStudents", "transferredStudents",
                    "semesterEnum", "groupAdminName", "birthDay", "passportId", "hairColor" });

            values4converting.forEach((k, v) -> {
                String[] row = new String[v.length + 1];
                row[0] = k;
                for (int i = 0; i < v.length; i++) {
                    row[i + 1] = v[i];
                }
                csvWriter.writeNext(row);
            });

            csvWriter.writeNext(new String[] { "owner", "creationTime" });
            csvWriter.writeNext(new String[] { collectionRecord.getInfo().getOwner(),
                    collectionRecord.getInfo().getCreationTime().toString() });
            csvWriter.close();
        } catch (IOException ex) {
            // System.out.println(ColorText.colorText("No permission to write in file",
            // "red"));
            System.out.println("No permission to write in file");
            throw new ServerStorageException("No permission to write in file");
        }

    }

    /**
     * 
     * Reads a CollectionRecord from a CSV file.
     * 
     * @param file The path of the file to read the CSV data from.
     * @return The CollectionRecord read from the CSV file.
     * @throws ServerStorageException
     */
    public static CollectionRecord csvToRecord(String file) throws ServerStorageException {

        TreeMap<String, StudyGroup> collection = new TreeMap<>();
        CollectionInfo info = new CollectionInfo(LocalDateTime.now(), "Unknown");
        int rowNumber = 0;

        // read fString filePathrom file using an input stream reader
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {


            CSVReader csvReader = new CSVReader(reader);
            while (true) {
                rowNumber++;
                String[] row = csvReader.readNext();

                if (row.length == 1) {
                    continue;
                }
                if (row[0].equals("key")) {
                    continue;
                }
                if (row[0].equals("owner")) {
                    row = csvReader.readNext();
                    info = new CollectionInfo(LocalDateTime.parse(row[1]), row[0]);
                    break;
                }
                StudyGroup studyGroup = StudyGroup.deserialize(Arrays.copyOfRange(row, 1, row.length));
                collection.put(row[0], studyGroup);
            }   

            csvReader.close();
            return new CollectionRecord(collection, info);
        } catch (CsvMalformedLineException e) {
            // System.out.println(ColorText.colorText("Incorrect csv format for reading in line: "+rowNumber, "red"));
            System.out.println("Incorrect csv format for reading in line: "+rowNumber);
            throw new ServerStorageException();

        } catch (SecurityException e) {
            // System.out.println(ColorText.colorText("No permission to read file", "red"));
            System.out.println("No permission to read file");
        } catch (IOException e) {
            // System.out.println(ColorText.colorText("No such file or no permission to read it", "red"));
            // System.out.println("No such file or no permission to read it");
            throw new ServerStorageException();
        } catch (IllegalArgumentException e) {
            // System.out.println(ColorText.colorText("Incorrect line: "+rowNumber, "red"));
            System.out.println("Incorrect line: "+rowNumber);
            throw new ServerStorageException();
        } catch (Exception e) {
            // System.out.println(ColorText.colorText("Incorrect file", "red"));
            System.out.println("Incorrect file");
            throw new ServerStorageException();
        }

        // collectionManager.initialize();
        return null;

    }

}
