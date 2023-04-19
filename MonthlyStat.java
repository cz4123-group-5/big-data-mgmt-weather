import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MonthlyStat implements Comparable<MonthlyStat> {
    LocalDate date;
    Station station;
    Double value;
    StatCategory statCategory;

    /**
     * Constructor for MonthlyStat
     * @param timestamp the date of the record
     * @param station the station of the record
     * @param value the value of the record
     * @param statCategory the category of the record
     */
    public MonthlyStat(LocalDate timestamp, Station station, Double value, StatCategory statCategory) {
        this.date = timestamp;
        this.station = station;
        this.value = value;
        this.statCategory = statCategory;
    }

    @Override
    public int compareTo(MonthlyStat other) {
        return this.date.compareTo(other.date);
    }

    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s,%s,%s,%s", dateFormatter.format(date), station.getName(), statCategory.getName(), value);
    }
}
