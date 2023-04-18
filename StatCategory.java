public enum StatCategory {
    MAX_TEMP("Max Temperature"),
    MIN_TEMP("Min Temperature"),
    MAX_HUMIDITY("Max Humidity"),
    MIN_HUMIDITY("Min Humidity");

    private final String name;

    StatCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
