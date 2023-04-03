package src;

import java.util.HashMap;
import java.util.Map;

public class Query {

    private int startYear;
    private int endYear;
    private String location;


    //  Inputting matriculation number A1234567B should scan the year 2007 and 2017 at Changi (note:  because the second last digit6 is even)
    static Map<String, Object> parseQueryInput(String input) {
        Map<String, Object> queryData = new HashMap<>();

        int yearLastDigit = Character.getNumericValue(input.charAt(7));

        int startYear = yearLastDigit > 1 ? 2000 + yearLastDigit : 2010 + yearLastDigit;
        int endYear = yearLastDigit > 1 ? 2010 + yearLastDigit : 2020 + yearLastDigit;
        String location = input.charAt(6) % 2 == 0 ? "Changi" : "Paya Lebar";

        queryData.put("startYear", startYear);
        queryData.put("endYear", endYear);
        queryData.put("location", location);

        return queryData;
    }



//    static toString() {
//
//    }

}
