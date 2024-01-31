import java.math.*;
public class Main {
    private static BigInteger[] lastestLiveViewData = new BigInteger[301];
    public static void main(String[] args) {
        userInterface userInterface = new userInterface();

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