package csvutils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.TreeMap;

import models.CollectionInfo;
import models.CollectionRecord;
import models.StudyGroup;

public class CsvUtils {
    public static void recordToCsv(CollectionRecord collectionRecord, String filePath){
    // convert record to csv
    ArrayList<String[]> values4converting = new ArrayList<String[]>();
    // ArrayList<String> valuesKeys = new ArrayList<String>();

    for (StudyGroup i : collectionRecord.getCollection().values()) {
        values4converting.add(i.deserialize());
    }
    
    
    try  {
        FileOutputStream file = new FileOutputStream(filePath);
        BufferedOutputStream writer = new BufferedOutputStream(file);
        
        for(int line = 0; line<values4converting.size(); line++){
 
            writer.write("\"".getBytes());
            writer.write(values4converting.get(line)[0].getBytes());
            writer.write("\"".getBytes());
            

            for(int i = 1; i < values4converting.get(line).length; i++){
                writer.write(",".getBytes());  
                writer.write("\"".getBytes());
                writer.write(values4converting.get(line)[i].getBytes());
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

        writer.close();
      } catch (IOException ex) {
        // ex.printStackTrace(System.err);
        System.out.println("Incorrect file path");
      }


    }

    public static CollectionRecord csvToRecord(String filePath){
        TreeMap<Integer, StudyGroup> collection = new TreeMap<>();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))){
            
           
    
            // ArrayList<String[]> elems = new ArrayList<String[]>();
    
            int t;
            StringBuilder sb = new StringBuilder();
            while((t=reader.read())!= -1)
            {
                char r = (char)t;
                sb.append(r);
    
            }

            if (sb.length() == 0) {
                return new CollectionRecord(collection, new CollectionInfo(LocalDateTime.now(), "Unknown"));
            }

            String elemsString = sb.toString();
            elemsString = elemsString.replaceAll("\"", "");
            String[] lines = elemsString.split("\n");
    
            for(int i = 0; i < lines.length-1; i++){
                StudyGroup studyGroup = new StudyGroup();
                String[] values = lines[i].split(",");
                
                studyGroup.serialize(values);

                collection.put(studyGroup.getId(), studyGroup);
            }

            reader.close();
            return new CollectionRecord(collection, new CollectionInfo(LocalDateTime.parse(lines[lines.length-1].split(",")[1]), lines[lines.length-1].split(",")[0]));

        } 
        catch (Exception e) {
            
            System.out.println("Incorrect file");

        }
        return null;

    }


}
