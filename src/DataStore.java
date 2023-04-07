package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

enum DataColumn {
    ID,
    TIMESTAMP,
    STATION,
    TEMPERATURE,
    HUMIDITY,
}

enum Station {
    CHANGI,
    PAYA_LEBAR,
}

public class DataStore {
    /*
    * This class is used to store the data in the column store.
    * This class also handles caching data to Main Memory, to reduce disk reads.
    */
    private static final Integer cacheSize = 1000;

    private Map<Integer, Record> cache = new HashMap<Integer, Record>(); // integer is line number of cached record

    private static final String dataPath = "storage/";

    public boolean wipeStorage() {
        // wipe files on app shutdown & startup (in the event it previously crashed)
        File idFile = new File(dataPath + "id");
        return idFile.delete();
        // todo, wipe other files
    }

    // todo: replace this with addToFileAtLine
    private void appendToFile(String fileName, String data) {
        try {
            new BufferedWriter(new FileWriter(fileName, true)).append(data).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertData(Record record) {
        // todo: figure out which line to insert the new data

        // for now im going to append the data to the end of each file
        appendToFile(dataPath + "id", record.getId());
        appendToFile(dataPath + "timestamp", String.valueOf(record.getTimestamp()));
        appendToFile(dataPath + "station", String.valueOf(record.getStation()));
        appendToFile(dataPath + "temp", String.valueOf(record.getTemperature()));
        appendToFile(dataPath + "humidity", String.valueOf(record.getHumidity()));
    }

    public void insertDataBulk(Record[] records) {
        for (Record record : records) {
            insertData(record);
        }
    }

    public Record readData(Integer lineNumber) {
        // todo: attempt to read from main memory first
        Record cachedRecord = cache.get(lineNumber);

        if (cachedRecord != null) {
            return cachedRecord;
        }

        // fallback to reading from disk
        cacheData(lineNumber);

        try (Stream<String> lines = Files.lines(Paths.get("file.txt"))) {
            String data = lines.skip(lineNumber).findFirst().get();
            String[] delimitedData = data.split("[,]", 0);

            return new Record(
                    delimitedData[0],
                    LocalDateTime.parse(delimitedData[1]),
                    Station.valueOf(delimitedData[2]),
                    Float.valueOf(delimitedData[3]),
                    Float.valueOf(delimitedData[4])
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cacheData(Integer lineNumber) {
        // when a read is triggered and cache missed, we can cache new data
        int endLineNumber = lineNumber + cacheSize;

        cache.clear();

        for (int i = lineNumber; i < endLineNumber; i++) {
            cache.put(i, readData(i));
        }
    }

}
