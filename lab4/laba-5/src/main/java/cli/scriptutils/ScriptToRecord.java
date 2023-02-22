package cli.scriptutils;

import java.io.FileInputStream;

import java.io.InputStreamReader;
import java.util.Arrays;


public class ScriptToRecord {
    public static String[] readLines(String filePath){
        try {
            FileInputStream file = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(file);

            
            int t;
            StringBuilder sb = new StringBuilder();
            while((t=reader.read())!= -1)
            {
                char r = (char)t;
                sb.append(r);
    
            }
            String lines = sb.toString().replace("\n", "");
            // System.out.println(lines.toString());
            // System.out.println(Arrays.toString(sb.toString().split(System.lineSeparator())));
            return sb.toString().split(System.lineSeparator());
            
        } catch (Exception e) {
            System.out.println("File not found");
            return null;
        }
        
    }
}
