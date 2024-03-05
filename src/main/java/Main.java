import java.math.*;
public class Main {
    private static BigInteger[] lastestLiveViewData = new BigInteger[301];
    private static boolean toRecord = false;
    private static fileHandler fileHandler;
    public static void main(String[] args) {

        userInterface userInterface = new userInterface();
        Thread thread = new Thread(userInterface);
        thread.start();

        connectionHandler connection = new connectionHandler();

        connection.createConnection("127.0.0.1",20777);

    }

    public static void updateData(BigInteger[] data) {
        lastestLiveViewData = data;
        if (toRecord == true) {
            fileHandler.writeLineToFile(data);
        }
    }

    public BigInteger[] getLastestLiveViewData() {
        return lastestLiveViewData;
    }

    public static void startRecord() {
        fileHandler = new fileHandler();
        toRecord = true;
    }

    public static void stopRecord() {
        toRecord = false;
        fileHandler = null;
    }
}