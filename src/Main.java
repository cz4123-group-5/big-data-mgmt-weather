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

        // ingest data from data/*.csv (data is pre-sorted)

        // scan data

        // output result to file
    }
}
