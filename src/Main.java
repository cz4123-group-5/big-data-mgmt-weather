package src;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("=== Singapore Weather Query System ===");

        // ask for and receive input (matric num)
        System.out.print("Enter the matriculation number as query: ");
        String matricNum = scan.nextLine();

        // process input
        Map<String, String> query = Query.parseQueryInput(matricNum);

        System.out.println(query);

        // ingest data from data/*.csv (data needs to be sorted!)
//        ArrayList<String> id = new ArrayList<>();
//        ArrayList<LocalDateTime> timestamp = new ArrayList<>();
//        ArrayList<String> location = new ArrayList<>(); // should this be an enum value?
//        ArrayList<Float> temperature = new ArrayList<>();
//        ArrayList<Float> humidity = new ArrayList<>();

        // ===== mocked =========
        // todo: change index method/strategy
        Map<String, Integer> yearStartIndex = new HashMap<>(); // map year start to array index
        yearStartIndex.put("2007", 94998);
        Map<String, Integer> yearEndIndex = new HashMap<>(); // map year start to array index
        yearEndIndex.put("2011", 219134);
        // ===== end mocked =====

        // ingest data from data/*.csv 
                HashMap<String, Integer> dataTypes = new HashMap<>();
        dataTypes.put("id", ColumnStoreParent.INTEGER_DATATYPE);
        dataTypes.put("Timestamp", ColumnStoreParent.TIME_DATATYPE);
        dataTypes.put("Station", ColumnStoreParent.STRING_DATATYPE);
        dataTypes.put("Temperature", ColumnStoreParent.FLOAT_DATATYPE);
        dataTypes.put("Humidity", ColumnStoreParent.FLOAT_DATATYPE);

        // todo: create a ColumnStoreBase class with common datatypes - datatypes above becomes redundant
        ColumnStoreParent csMM = new ColumnStoreMM(dataTypes);
        ColumnStoreParent csDisk = new ColumnStoreDisk(dataTypes);
        // ColumnStoreParent csDiskEnhanced = new ColumnStoreDiskEnhanced(dataTypes);
        // List<ColumnStoreParent> columnStores = Arrays.asList(csMM, csDisk); // csDiskEnhanced
        
        // scan data

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        String startYear = query.get("startYear");
        String startDate = "01/01/" + startYear;

        String endYear = query.get("endYear");
        String endDate = "31/12/" + endYear;

        Integer scanStartIndex = yearStartIndex.get(startYear);
        Integer scanEndIndex = yearEndIndex.get(endYear);

        class Result {
            LocalDateTime timestamp;
            String location;
            String category; // todo: use enum
            Float value;
            // todo: add read+write methods
        }

        ArrayList<Result> results = new ArrayList();

        for (int i = scanStartIndex; i <= scanEndIndex; i++) {
            for (int j = 0; j < 31; j++) { // monthly loop

                Result minTemp = new Result();
                Result maxTemp = new Result();
                Result minHumidity = new Result();
                Result maxHumidity = new Result();

                if (location.get(i).equals(query.get("location"))) {

                    if (minTemp > temperature.get(i)) {
                        Result tempResult = new Result();
                        tempResult.id = id.get(i);
                        tempResult.timestamp = timestamp.get(i);
                        tempResult.location = location.get(i);
                        tempResult.temperature = temperature.get(i);
                        tempResult.humidity = humidity.get(i);
                        minTemp = tempResult;
                    }

                }

                // todo: add to results

            }
        }

        // output result to file

    }
}
