import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Output {
    public static void writeStats(String matricNum, Map<YearMonth, MonthlyStats> monthlyStats) throws IOException {

        String filePath = "ScanResult_" + matricNum + ".csv";
        File outputFile = new File(filePath);

        // delete file if it exists, else output will be added on to existing file resulting in duplicates
        if (outputFile.exists()) {
            outputFile.delete();
        }

        System.out.println(outputFile.getAbsolutePath());
        outputFile.createNewFile();

        if(!outputFile.setReadable(true) || !outputFile.setWritable(true)) {
            System.out.println("Could not set permissions for this file.");
            return;
        }

        FileOutputStream outputStream = new FileOutputStream(outputFile, true);
        outputStream.write("Date,Station,Category,Value\n".getBytes(StandardCharsets.UTF_8));

        for (Map.Entry<YearMonth, MonthlyStats> entry : monthlyStats.entrySet()) {
            MonthlyStats entryValue = entry.getValue();
            ArrayList<MonthlyStat> stats = new ArrayList<>();

            stats.addAll(entryValue.getMaxTemperatures().values());
            stats.addAll(entryValue.getMinTemperatures().values());
            stats.addAll(entryValue.getMaxHumidities().values());
            stats.addAll(entryValue.getMinHumidities().values());

            // sort stats by timestamp
            Collections.sort(stats);

//            stats.forEach(System.out::println);
            stats.forEach(stat -> {
                try {
                    outputStream.write((stat.toString() + "\n").getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

        System.out.println("Output written to: " + filePath);
    }
}
