package src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a row in the resulting csv output file
 */
public class Output {
//    public static final int MAX_HUMIDITY = 0;
//    public static final int MAX_TEMP = 1;
//    public static final int MIN_HUMIDITY = 2;
//    public static final int MIN_TEMP = 3;

    LocalDateTime dateTime;
    String station;
    String type;
    float value;

    public Output(LocalDateTime dateTime, String station, String type, float value) {
        this.dateTime = dateTime;
        this.station = station;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
//        String typeString = "";
//        switch (type) {
//            case MAX_HUMIDITY: typeString = "Max Humidity";
//            case MAX_TEMP: typeString = "Max Temperature";
//            case MIN_HUMIDITY: typeString = "Min Humidity";
//            case MIN_TEMP: typeString = "Min Temperature";
//            default: typeString = "unknown";
//        }

        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                "," + station +
                "," + type +
                "," + value;
    }
    
    static void writeOutput(String filePath, List<Output> outputData) throws IOException {
         File outputFile = new File(filePath);
         
         // delete file if it exists, else output will be added on to existing file resulting in duplicates
         if (outputFile.exists()) {
         	outputFile.delete();
         }
         
         System.out.println(outputFile.getAbsolutePath());
         outputFile.createNewFile();
         
         if(!outputFile.setReadable(true) || !outputFile.setWritable(true)) {
             System.out.println("Could not set permissions for this file.");
             return;
         }
         
         FileOutputStream outputStream = new FileOutputStream(outputFile, true);
         outputStream.write("Date,Station,Category,Value\n".getBytes(StandardCharsets.UTF_8));
         
         for (Output data: outputData) {
//        	 System.out.println(data);
             outputStream.write(data.toString().getBytes(StandardCharsets.UTF_8));
             outputStream.write('\n');
         }
    }
}