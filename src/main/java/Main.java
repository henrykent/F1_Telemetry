
public class Main {
    public static void main(String[] args) {
        connectionHandler connection = new connectionHandler();

        connection.createConnection("127.0.0.1",20777);

    }
}