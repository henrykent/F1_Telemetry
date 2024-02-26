import javax.swing.*;
import java.math.*;
import java.lang.*;
public class userInterface extends JFrame implements Runnable {
    private JButton liveViewButton;
    private JButton compareViewButton;
    private JButton settingsViewButton;
    private JButton helpViewButton;
    private JPanel cardView;
    private JPanel main;
    private JPanel topBar;
    private JPanel liveView;
    private JPanel right;
    private JPanel left;
    private JPanel recordingControls;
    private JPanel lapInfo;
    private JPanel throttleInfo;
    private JPanel speedInfo;
    private JPanel RPMInfo;
    private JPanel brakingInfo;
    private JPanel peakGInfo;
    private JPanel gearInfo;
    private JPanel lap;
    private JPanel pitLap;
    private JLabel lapTitleLabel;
    private JLabel lapDataLabel;
    private JLabel pitLapTitleLabel;
    private JLabel pitLapDataLabel;
    private JPanel speed;
    private JPanel maxSpeed;
    private JLabel speedTitleLabel;
    private JLabel speedDataLabel;
    private JLabel maxSpeedTitleLabel;
    private JLabel maxSpeedDataLabel;
    private JLabel RPMTitleLabel;
    private JLabel RPMDataLabel;
    private JLabel peakGTitleLabel;
    private JLabel peakGDataLabel;
    private JLabel gearTitleLabel;
    private JLabel gearDataLabel;
    private JLabel brakingTitleLabel;
    private JLabel brakingDataLabel;
    private JLabel throttleTitleLabel;
    private JLabel throttleDataLabel;
    private boolean liveViewLoop = true;
    private int maxSpeedValue = 0;
    private float maxGForceValue = 0;

    public userInterface() {
        setContentPane(main);
        setTitle("F1 Telemetry");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920,1080);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        try {
            while (liveViewLoop == true) {
                Main test = new Main();
                BigInteger[] data = test.getLastestLiveViewData();
                //set GUI data.
                if (data != null) {
                    if (data[236] != null) {
                        speedDataLabel.setText(String.valueOf(data[236]) + " km/h");
                        int intForm = Integer.parseInt(String.valueOf(data[236]));
                        if (maxSpeedValue < intForm) {
                            maxSpeedValue = intForm;
                            maxSpeedDataLabel.setText(maxSpeedValue + " km/h");
                        }
                    }
                    if (data[242] != null) {
                        RPMDataLabel.setText(String.valueOf(data[242]));
                    }
                    if (data[228] != null) {
                        lapDataLabel.setText(String.valueOf(data[228]));
                        if (data[229] != null) {
                            if (String.valueOf(data[229]) != "0") {
                                pitLapDataLabel.setText(String.valueOf(data[228]));
                            }
                        }

                    }
                    if (data[237] != null) {
                        String throttleString = data[237].toString(2);
                        float throttleFloat = Float.intBitsToFloat(Integer.parseUnsignedInt(throttleString, 2));
                        int throttleFormatted = (int) (throttleFloat * 100);
                        throttleDataLabel.setText(throttleFormatted + "%");
                    }
                    if (data[239] != null) {
                        String brakeString = data[239].toString(2);
                        float brakeFloat = Float.intBitsToFloat(Integer.parseUnsignedInt(brakeString, 2));
                        int brakeFormatted = (int) (brakeFloat * 100);
                        brakingDataLabel.setText(brakeFormatted + "%");
                    }
                    if (data[241] != null) {
                        gearDataLabel.setText(gearDecode(data[241]));
                    }
                    if (data[12] != null) {
                        peakGDataCheck(12, data);
                    }
                    if (data[13] != null) {
                        peakGDataCheck(13, data);
                    }
                    if (data[14] != null) {
                        peakGDataCheck(14, data);
                    }
                }

                Thread.sleep(1000/60);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void peakGDataCheck(int indexNumber, BigInteger[] data) {
        String binaryString = data[indexNumber].toString(2);
        if (binaryString.charAt(0) == '-') {
            binaryString = binaryString.substring(1);
            String new1 = binaryString.replace('0','O');
            String new2 = new1.replace('1','0');
            String new3 = new2.replace('O','1');
            binaryString = new3;
        }
        float GForceFloat = Float.intBitsToFloat(Integer.parseInt(binaryString, 2));
        int forceRounding = (int) (GForceFloat * 10);
        float finalValue = (float) forceRounding /10;
        if (maxGForceValue < finalValue) {
            maxGForceValue = finalValue;
            peakGDataLabel.setText(maxGForceValue + "g");
        }
    }

    private String gearDecode(BigInteger input) {
        if (String.valueOf(input) == "0") {
            return "N";
        } else if (String.valueOf(input) == "-1") {
            return "R";
        } else {
            return String.valueOf(input);
        }
    }
}
