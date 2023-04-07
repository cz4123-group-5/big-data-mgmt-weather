package src;

import java.time.LocalDateTime;

public class Record {
    private String id;
    private LocalDateTime timestamp;
    private Station station;
    private Float temperature;
    private Float humidity;

    public Record(String id, LocalDateTime timestamp, Station station, Float temperature, Float humidity) {
        this.id = id;
        this.timestamp = timestamp;
        this.station = station;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Station getStation() {
        return station;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
}
