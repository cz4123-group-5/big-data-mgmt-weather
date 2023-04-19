import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;


/**
 * A simple columnar database implementation (in memory)
 */
public class SimpleColumnarDatabase {

    private final ArrayListOnDisk<Integer> ids;
    private final ArrayListOnDisk<LocalDateTime> timestamps;
    private final ArrayListOnDisk<Station> stations;
    private final ArrayListOnDisk<Double> temperatures;
    private final ArrayListOnDisk<Double> humidities;
    private final SkipList indexList;

    /**
     * Constructor for SimpleColumnarDatabase
     */
    public SimpleColumnarDatabase() {
        ids = new ArrayListOnDisk<>("ids.dat", Integer.class);
        timestamps = new ArrayListOnDisk<>("timestamps.dat", LocalDateTime.class);
        stations = new ArrayListOnDisk<>("stations.dat", Station.class);
        temperatures = new ArrayListOnDisk<>("temperatures.dat", Double.class);
        humidities = new ArrayListOnDisk<>("humidities.dat", Double.class);

        SkipList.TimestampComparator comparator = new SkipList.TimestampComparator(this.timestamps);
        indexList = new SkipList(comparator);
    }

    /**
     * Inserts a new record into the database
     *
     * @param id          the id of the record
     * @param timestamp   the timestamp of the record
     * @param station     the station of the record
     * @param temperature the temperature of the record
     * @param humidity    the humidity of the record
     */
    public void insert(int id, LocalDateTime timestamp, Station station, Double temperature, Double humidity) {
        // print the id
//        System.out.println("Inserting id: " + id);

        // append new data
        ids.add(id);
        timestamps.add(timestamp);
        stations.add(station);
        temperatures.add(temperature);
        humidities.add(humidity);

        int unsortedIndex = ids.size() - 1;
        indexList.add(unsortedIndex);
    }

    /**
     * Gets the monthly stats for a given station
     *
     * @param startYear the start year
     * @param endYear   the end year
     * @param station   the station
     * @return a map of YearMonth to MonthlyStats
     */
    public Map<YearMonth, MonthlyStats> getMonthlyStats(int startYear, int endYear, Station station) {
        System.out.println("Generating monthly stats...");
        Map<YearMonth, MonthlyStats> monthlyStatsMap = new HashMap<>();

        for (Integer unsortedIndex : indexList) {
            LocalDateTime timestamp = timestamps.get(unsortedIndex);
            LocalDate date = timestamp.toLocalDate();
            int year = date.getYear();

            if (year != startYear && year != endYear || !stations.get(unsortedIndex).equals(station)) {
                continue;
            }

            YearMonth yearMonth = YearMonth.from(date);
            Double temperature = temperatures.get(unsortedIndex);
            Double humidity = humidities.get(unsortedIndex);

            MonthlyStats currentStats = monthlyStatsMap.get(yearMonth);
            if (currentStats == null) {
                currentStats = new MonthlyStats(
                        timestamp.toLocalDate(), station, temperature, humidity
                );
                monthlyStatsMap.put(yearMonth, currentStats);
            } else {
                if (temperature != null) {
                    if (temperature > currentStats.getMaxTemperatures().entrySet().stream().findFirst().get().getValue().value) {
                        currentStats.clearMaxTemperature();
                        currentStats.getMaxTemperatures().put(date, new MonthlyStat(
                                date, station, temperature, StatCategory.MAX_TEMP
                        ));
                    } else if (temperature.equals(currentStats.getMaxTemperatures().entrySet().stream().findFirst().get().getValue().value)) {
                        currentStats.getMaxTemperatures().put(date, new MonthlyStat(
                                date, station, temperature, StatCategory.MAX_TEMP
                        ));
                    }
                    if (temperature < currentStats.getMinTemperatures().entrySet().stream().findFirst().get().getValue().value) {
                        currentStats.clearMinTemperature();
                        currentStats.getMinTemperatures().put(date, new MonthlyStat(
                                date, station, temperature, StatCategory.MIN_TEMP
                        ));
                    } else if (temperature.equals(currentStats.getMinTemperatures().entrySet().stream().findFirst().get().getValue().value)) {
                        currentStats.getMinTemperatures().put(date, new MonthlyStat(
                                date, station, temperature, StatCategory.MIN_TEMP
                        ));
                    }
                }
                if (humidity != null) {
                    if (humidity > currentStats.getMaxHumidities().entrySet().stream().findFirst().get().getValue().value) {
                        currentStats.clearMaxHumidity();
                        currentStats.getMaxHumidities().put(date, new MonthlyStat(
                                date, station, humidity, StatCategory.MAX_HUMIDITY
                        ));
                    } else if (humidity.equals(currentStats.getMaxHumidities().entrySet().stream().findFirst().get().getValue().value)) {
                        currentStats.getMaxHumidities().put(date, new MonthlyStat(
                                date, station, humidity, StatCategory.MAX_HUMIDITY
                        ));
                    }
                    if (humidity < currentStats.getMinHumidities().entrySet().stream().findFirst().get().getValue().value) {
                        currentStats.clearMinHumidity();
                        currentStats.getMinHumidities().put(date, new MonthlyStat(
                                date, station, humidity, StatCategory.MIN_HUMIDITY
                        ));
                    } else if (humidity.equals(currentStats.getMinHumidities().entrySet().stream().findFirst().get().getValue().value)) {
                        currentStats.getMinHumidities().put(date, new MonthlyStat(
                                date, station, humidity, StatCategory.MIN_HUMIDITY
                        ));
                    }
                }
            }
        }
        return monthlyStatsMap;
    }

    public void writeCachesToDisk() {
        System.out.println("Writing caches to disk...");
        ids.writeCacheToDisk();
        timestamps.writeCacheToDisk();
        stations.writeCacheToDisk();
        temperatures.writeCacheToDisk();
        humidities.writeCacheToDisk();
    }
}