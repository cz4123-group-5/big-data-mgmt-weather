import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// New class to hold the monthly statistics
class MonthlyStats {
    private final Map<LocalDate, MonthlyStat> maxTemperatures;
    private final Map<LocalDate, MonthlyStat> minTemperatures;
    private final Map<LocalDate, MonthlyStat> maxHumidities;
    private final Map<LocalDate, MonthlyStat> minHumidities;

    public Map<LocalDate, MonthlyStat> getMaxTemperatures() {
        return maxTemperatures;
    }

    public void clearMaxTemperature() {
        this.maxTemperatures.clear();
    }

    public Map<LocalDate, MonthlyStat> getMinTemperatures() {
        return minTemperatures;
    }

    public void clearMinTemperature() {
        this.minTemperatures.clear();
    }

    public Map<LocalDate, MonthlyStat> getMaxHumidities() {
        return maxHumidities;
    }

    public void clearMaxHumidity() {
        this.maxHumidities.clear();
    }

    public Map<LocalDate, MonthlyStat> getMinHumidities() {
        return minHumidities;
    }

    public void clearMinHumidity() {
        this.minHumidities.clear();
    }

    /**
     * Constructor for MonthlyStats
     * @param date the date of the first entry
     * @param station the station of the first entry
     * @param temperature the temperature of the first entry
     * @param humidity the humidity of the first entry
     */
    public MonthlyStats(
            LocalDate date, Station station, Double temperature, Double humidity
    ) {
        this.maxTemperatures = new HashMap<>();
        this.minTemperatures = new HashMap<>();
        this.maxHumidities = new HashMap<>();
        this.minHumidities = new HashMap<>();

        this.maxTemperatures.put(date, new MonthlyStat(date, station, temperature, StatCategory.MAX_TEMP));
        this.minTemperatures.put(date, new MonthlyStat(date, station, temperature, StatCategory.MIN_TEMP));

        this.maxHumidities.put(date, new MonthlyStat(date, station, humidity, StatCategory.MAX_HUMIDITY));
        this.minHumidities.put(date, new MonthlyStat(date, station, humidity, StatCategory.MIN_HUMIDITY));
    }
}