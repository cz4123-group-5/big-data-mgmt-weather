package src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("=== Singapore Weather Query System ===");

        // ask for and receive input (matric num)
        System.out.print("Enter the matriculation number as query: ");
        String matricNum = scan.nextLine();

        // process input
        Map<String, Object> query = Query.parseQueryInput(matricNum);

        System.out.println(query);

        // ingest data from data/*.csv 
        

        // scan data

        // output result to file
        
       List<Output> list = new ArrayList<>();
    // test write to csv file using dummy output
       Output output1 = new Output(LocalDateTime.of(2018, 10, 01, 13, 30), "Changi", "Max Humidity", (float) 100);
       Output output2 = new Output(LocalDateTime.of(2018, 11, 01, 12, 00), "Changi", "Max Temperature", (float) 32.2);
       Output output3 = new Output(LocalDateTime.of(2018, 11, 05, 12, 15), "Changi", "Min Temperature", (float) 26.5);
       list.add(output1);
       list.add(output2);
       list.add(output3);
       
       Output.writeOutput("ScanResult_<" + matricNum + ">.csv", list);
    }
}
