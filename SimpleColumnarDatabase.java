import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;


/**
 * A simple columnar database implementation (in memory)
 */
public class SimpleColumnarDatabase {

    private final List<Integer> ids;
    private final List<LocalDateTime> timestamps;
    private final List<Station> stations;
    private final List<Double> temperatures;
    private final List<Double> humidities;
    private final CustomSkipList<Integer> indexList;

    public SimpleColumnarDatabase() {
        ids = new ArrayList<>();
        timestamps = new ArrayList<>();
        stations = new ArrayList<>();
        temperatures = new ArrayList<>();
        humidities = new ArrayList<>();
        indexList = new CustomSkipList<>();
    }

    public void insert(int id, LocalDateTime timestamp, Station station, Double temperature, Double humidity) {
        ids.add(id);
        timestamps.add(timestamp);
        stations.add(station);
        temperatures.add(temperature);
        humidities.add(humidity);

        int unsortedIndex = ids.size() - 1;

        // insert index in indexMap, sorting against timestamp
        if (indexList.isEmpty()) {
            indexList.add(0, unsortedIndex); //  first element
        } else {
            int targetIndex = 0;
            for (Integer unsorted_i : indexList) {
                if (timestamps.get(unsorted_i).isAfter(timestamp)) {
                    break;
                }
                targetIndex++;
            }
            indexList.add(targetIndex, unsortedIndex);
        }
//        System.out.println("Inserted record " + id + " at index " + unsortedIndex);
    }

    /**
     * Returns the maximum and minimum temperatures and humidities for each month within the specified years.
     *
     * @param startYear the start year of the time period (inclusive)
     * @param endYear   the end year of the time period (inclusive)
     * @param station   the station name to filter the records by
     * @return a map containing the year and month as a key (YearMonth), and a MonthlyStats object containing the maximum and minimum temperatures and humidities for that month as the value
     */
    public Map<YearMonth, MonthlyStats> getMonthlyStats(int startYear, int endYear, Station station) {
        System.out.println("Generating monthly stats...");
        Map<YearMonth, MonthlyStats> monthlyStatsMap = new HashMap<>();

        for (Integer unsortedIndex : indexList) {
//            int unsortedIndex = indexList.get(i);
            // print every 1000 records
            if (unsortedIndex % 10 == 0) {
                System.out.println("Getting " + unsortedIndex + " records");
            }

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
        System.out.println("Generated monthly stats.");

        return monthlyStatsMap;
    }


}