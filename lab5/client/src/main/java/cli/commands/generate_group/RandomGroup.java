package cli.commands.generate_group;

import java.io.IOException;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 * 
 * A utility class for generating a random group of fields from a CSV file.
 */
public class RandomGroup {
    /**
     * 
     * Generates a random group of fields by reading from a CSV file and randomly
     * choosing
     * 
     * one value from each row.
     * 
     * @return a String array of 10 fields representing the random group
     * 
     * @throws CsvValidationException if there is an error validating the CSV file
     * 
     * @throws IOException            if there is an error reading the CSV file
     */
    public static String[] getRandomGroup() throws CsvValidationException, IOException {
        // read fields from file and randomly choose one from each row to create a group
        String[] group = new String[10];
        try (InputStreamReader reader = new InputStreamReader(
                RandomGroup.class.getClassLoader().getResourceAsStream("fields_of_groups.csv"))) {
            CSVReader csvReader = new CSVReader(reader);
            for (int i = 0; i < 10; i++) {
                String[] row = csvReader.readNext();
                group[i] = row[(int) (Math.random() * row.length)];
            }

            csvReader.close();

        }
        return group;
    }
}
