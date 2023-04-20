import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * Main method to handle input and output.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleColumnarDatabase db = new SimpleColumnarDatabase();

        // parse data file
        BufferedReader reader;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("Parsing data file, please wait a moment...");
        try {
            reader = new BufferedReader(new FileReader("SingaporeWeather.csv"));
            String line = reader.readLine(); //  read and discard first line (csv headers)

            while (line != null) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                String[] parts = line.split(",");

                Double temperature;
                try {
                    temperature = Double.parseDouble(parts[3]);
                } catch (NumberFormatException e) {
                    temperature = null;
                }

                Double humidity;
                try {
                    humidity = Double.parseDouble(parts[4]);
                } catch (NumberFormatException e) {
                    humidity = null;
                }

                db.insert(Integer.parseInt(parts[0]),
                        LocalDateTime.parse(parts[1], dateFormatter),
                        Station.valueOf(parts[2].toUpperCase().replace(' ', '_')),
                        temperature,
                        humidity);
            }
            db.writeCachesToDisk();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // handle query
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your matriculation number (in caps): ");
        String matriculationNumber = scanner.nextLine();

        // check that matriculation number has valid format i.e. U2022923F or U1921880A
        if (!matriculationNumber.matches("[A-Z][0-9]{7}[A-Z]")) {
            System.out.println("Invalid matriculation number format.");
            return;
        }

        int lastDigit = Character.getNumericValue(matriculationNumber.charAt(matriculationNumber.length() - 2));
        int startYear = lastDigit > 1 ? 2000 + lastDigit : 2010 + lastDigit;
        int endYear = startYear + 10;

        // Get the second last digit to determine the station
        int secondLastDigit = Character.getNumericValue(matriculationNumber.charAt(matriculationNumber.length() - 3));
        Station station = secondLastDigit % 2 == 0 ? Station.CHANGI : Station.PAYA_LEBAR;

        System.out.println("========== Query ==========");
        System.out.println("Years to check: " + startYear + " & " + endYear);
        System.out.println("Station: " + station);
        System.out.println("===========================");

        // generate monthly stats from query parameters
        Map<YearMonth, MonthlyStats> monthlyStats = db.getMonthlyStats(startYear, endYear, station);

        try {
            Output.writeStats(matriculationNumber, monthlyStats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
