public enum Station {
    CHANGI("Changi"),
    PAYA_LEBAR("Paya Lebar");

    private final String name;

    Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
