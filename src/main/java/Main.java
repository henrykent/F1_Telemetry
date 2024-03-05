import java.math.*;
public class Main {
    private static BigInteger[] lastestLiveViewData = new BigInteger[301];
    public static void main(String[] args) {
        fileHandler test = new fileHandler();

        userInterface userInterface = new userInterface();
        Thread thread = new Thread(userInterface);
        thread.start();

        connectionHandler connection = new connectionHandler();

        connection.createConnection("127.0.0.1",20777);



    }

    public static void updateData(BigInteger[] data) {
        lastestLiveViewData = data;
    }

    public BigInteger[] getLastestLiveViewData() {
        return lastestLiveViewData;
    }
}