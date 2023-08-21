import java.io.*;
import java.net.*;
//import com.google.gson.JsonParser;

public class connectionHandler {

    public void createConnection(String hostname, int port) {

        try {

            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket(port);

            while (true) {

                byte[] buffer = new byte[2048];
                DatagramPacket received = new DatagramPacket(buffer, buffer.length, address, port);
                socket.receive(received);

                String data = new String(buffer, 0, received.getLength());
                //JsonParser jsonParser = new JsonParser();

                //JsonArray jsonArrayOutput = (JsonArray)jsonParser.parse(data);
                //System.out.println("Output : " + jsonArrayOutput);

                System.out.println(data);
                System.out.println();

                Thread.sleep(1000/60);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("Timeout error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
