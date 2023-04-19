import java.io.*;
import java.util.*;

public class ReverseFile {
    /**
     * Reverses the order of the data rows in a CSV file.
     * This method is to create an unsorted file for testing purposes.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Read the CSV file and split it into header and data rows
            BufferedReader reader = new BufferedReader(new FileReader("SingaporeWeather.csv"));
            String header = reader.readLine();
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Reverse the order of the data rows
            Collections.reverse(lines);

            // Write the header row followed by the reversed data rows to the output file
            BufferedWriter writer = new BufferedWriter(new FileWriter("SingaporeWeatherReversed.csv"));
            writer.write(header);
            writer.newLine();
            for (String s : lines) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
