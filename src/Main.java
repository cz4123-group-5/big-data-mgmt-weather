package src;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("=== Singapore Weather Query System ===");

        // ask for and receive input (matric num)
        System.out.print("Enter the matriculation number as query: ");
        String matricNum = scan.nextLine();

        // process input
        Map<String, Object> query = Query.parseQueryInput(matricNum);

        System.out.println(query);

        // ingest data from data/*.csv 
                HashMap<String, Integer> dataTypes = new HashMap<>();
        dataTypes.put("id", ColumnStoreParent.INTEGER_DATATYPE);
        dataTypes.put("Timestamp", ColumnStoreParent.TIME_DATATYPE);
        dataTypes.put("Station", ColumnStoreParent.STRING_DATATYPE);
        dataTypes.put("Temperature", ColumnStoreParent.FLOAT_DATATYPE);
        dataTypes.put("Humidity", ColumnStoreParent.FLOAT_DATATYPE);


        ColumnStoreParent csMM = new ColumnStoreMM(dataTypes);
        ColumnStoreParent csDisk = new ColumnStoreDisk(dataTypes);
        ColumnStoreParent csDiskEnhanced = new ColumnStoreDiskEnhanced(dataTypes);
        List<ColumnStoreParent> columnStores = Arrays.asList(csMM, csDisk, csDiskEnhanced);
        
        

        // scan data

        // output result to file
    }
}
