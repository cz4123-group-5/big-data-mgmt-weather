package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a row in the resulting csv output file
 */
public class Output {
    public static final int MAX_HUMIDITY = 0;
    public static final int MAX_TEMP = 1;
    public static final int MIN_HUMIDITY = 2;
    public static final int MIN_TEMP = 3;

    LocalDateTime dateTime;
    String station;
    int type;
    float value;

    public Output(LocalDateTime dateTime, String station, int type, float value) {
        this.dateTime = dateTime;
        this.station = station;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        String typeString = "";
        switch (type) {
            case MAX_HUMIDITY: typeString = "Max Humidity";
            case MAX_TEMP: typeString = "Max Temperature";
            case MIN_HUMIDITY: typeString = "Min Humidity";
            case MIN_TEMP: typeString = "Min Temperature";
            default: typeString = "unknown";
        }

        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "," + station +
                "," + typeString +
                "," + value;
    }
}