import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fileHandler {
    private File file;
    private final int[] indexes = {12,13,14,34,35,36,37,48,49,50,53,54,55,210,211,212,227,228,230,236,237,238,239,240,241,242,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,268,271,272,279,280,281,282,284,299,300,301};
    public fileHandler() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date dateDate = new Date();
        String date = dateFormat.format(dateDate);

        file = new File(date + ".csv");

        try {
            FileWriter outputCSV = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputCSV);

            String[] header = { "time", "gForceLateral", "gForceLongitudinal", "gForceVertical", "wheelSlipRL", "wheelSlipRR", "wheelSlipFL", "wheelSlipFR", "weather", "trackTemperature", "airTemperature", "sessionType", "trackId", "formula", "currentLapTime", "sector1TimeInMS", "sector2TimeInMS", "carPosition", "currentLapNum", "sector", "speed", "throttle", "steer", "brake", "clutch", "gear", "engineRPM", "brakesTemperatureRL", "brakesTemperatureRR", "brakesTemperatureFL", "brakesTemperatureFR", "tyresSurfaceTemperatureRL", "tyresSurfaceTemperatureRR", "tyresSurfaceTemperatureFL", "tyresSurfaceTemperatureFR", "tyresInnerTemperatureRL", "tyresInnerTemperatureRR", "tyresInnerTemperatureFL", "tyresInnerTemperatureFR", "engineTemperature", "tyresPressureRL", "tyresPressureRR", "tyresPressureFL", "tyresPressureFR", "fuelMix", "fuelInTank", "fuelCapacity", "tyresWearRL", "tyresWearRR", "tyresWearFL", "tyresWearFR", "tyreVisualCompound", "ersHarvestedThisLapMGUK", "ersHarvestedThisLapMGUH"};
            writer.writeNext(header);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLineToFile(BigInteger[] data) {
        try {
            FileWriter outputCSV = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputCSV);

            String[] toWriteString = new String[55];
            toWriteString[0] = getTime();

            for (int i=1; i<55; i++) {
                BigInteger temp = data[indexes[i]];
                String temp2 = String.valueOf(temp);
                toWriteString[i] = temp2;
            }

            writer.writeNext(toWriteString);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTime() {
        long currentTimestamp = System.currentTimeMillis();
        return String.valueOf(currentTimestamp);
    }
}
