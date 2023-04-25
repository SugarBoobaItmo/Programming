package cli.commands.random_group;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


public class RandomGroup {
    public static String[] getRandomGroup() throws CsvValidationException {
        // File filePath = new File(".\\fields_of_groups.csv");
        File filePath = new File(".\\src\\main\\java\\cli\\commands\\random_group\\fields_of_groups.csv");
        String[] group = new String[10];
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
            CSVReader csvReader = new CSVReader(reader);
            for (int i = 0; i < 10; i++) {
                String[] row = csvReader.readNext();
                group[i] = row[(int) (Math.random() * row.length)];
            }

            csvReader.close();
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // return null;
        }
        return group;
    }
}
