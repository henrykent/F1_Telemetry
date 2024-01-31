import java.io.*;
import java.net.*;
import java.math.*;


public class connectionHandler {

    public void createConnection(String hostname, int port) {
        BigInteger[] testIntegerBuffer = null;

        try {

            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket(port);

            while (true) {

                byte[] buffer = new byte[2048];
                DatagramPacket received = new DatagramPacket(buffer, buffer.length, address, port);
                socket.receive(received);

                unpack unpacked = new unpack();
                BigInteger[] toSend = unpacked.unpack(buffer, testIntegerBuffer);
                Main.updateData(toSend);
                System.out.println("toSend[236]");

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
