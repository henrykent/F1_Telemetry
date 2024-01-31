import javax.swing.*;
import java.math.*;

public class userInterface extends JFrame {
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
        //liveViewUpdateLoop();
    }

    private void liveViewUpdateLoop() {
        try {
            while (liveViewLoop == true) {
                Main test = new Main();
                BigInteger[] data = test.getLastestLiveViewData();
                //System.out.println(data[236]);
                //set GUI data.
                if (data != null) {
                    speedDataLabel.setText(String.valueOf(data[236]));
                }

                Thread.sleep(1000/60);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
