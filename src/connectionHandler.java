import java.io.*;
import java.net.*;

public class connectionHandler {

    public void createConnection(String hostname, int port) {

        try {
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();

            while (true) {

                byte[] buffer = new byte[2048];
                DatagramPacket received = new DatagramPacket(buffer, buffer.length);
                socket.receive(received);

                String data = new String(buffer, 0, received.getLength());

                System.out.println(data);
                System.out.println();

                Thread.sleep(1000);
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
