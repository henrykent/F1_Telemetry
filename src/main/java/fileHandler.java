import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class fileHandler {
    public fileHandler() {
        String date = "2024-01-01 00:00:00";
        File file = new File(date + ".csv");
        try {
            FileWriter outputCSV = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputCSV);

            String[] header = { "time", "gForceLateral", "gForceLongitudinal", "gForceVertical", "wheelSlipRL", "wheelSlipRR", "wheelSlipFL", "wheelSlipFR", "weather", "trackTemperature", "airTemperature", "sessionType", "trackId", "formula", "currentLapTime", "sector1TimeInMS", "sector2TimeInMS", "carPosition", "currentLapNum", "sector", "speed", "throttle", "steer", "brake", "clutch", "gear", "engineRPM", "brakesTemperatureRL", "brakesTemperatureRR", "brakesTemperatureFL", "brakesTemperatureFR", "tyresSurfaceTemperatureRL", "tyresSurfaceTemperatureRR", "tyresSurfaceTemperatureFL", "tyresSurfaceTemperatureFR", "tyresInnerTemperatureRL", "tyresInnerTemperatureRR", "tyresInnerTemperatureFL", "tyresInnerTemperatureFR", "engineTemperature", "tyresPressureRL", "tyresPressureRR", "tyresPressureFL", "tyresPressureFR", "fuelMix", "fuelInTank", "fuelCapacity", "tyresWearRL", "tyresWearRR", "tyresWearFL", "tyresWearFR", "tyreVisualCompound", "ersHarvestedThisLapMGUK", "ersHarvestedThisLapMGUH"};
            writer.writeNext(header);

            // add data to csv
            String[] data1 = { "2024-01-01 00:00:00",""};
            writer.writeNext(data1);
            String[] data2 = { "2024-01-01 00:00:01", "10", "630" };
            writer.writeNext(data2);

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
