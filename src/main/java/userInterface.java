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
                    speedDataLabel.setText(String.valueOf(data[236]) + " km/h");
                    RPMDataLabel.setText(String.valueOf(data[242]));
                    lapDataLabel.setText(String.valueOf(data[228]));
                    if (data[237] != null) {
                        throttleDataLabel.setText(data[237].floatValue() + "%");
                        //System.out.println(data[237].toString(2));
                    }
                    if (data[239] != null) {
                        System.out.println(data[239].toString(2));
                    }
                    brakingDataLabel.setText(String.valueOf(data[239]) + "%");
                    gearDataLabel.setText(gearDecode(data[241]));
                }

                Thread.sleep(1000/60);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
