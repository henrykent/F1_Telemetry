import java.io.*;
import java.net.*;
import java.math.*;


public class connectionHandler {

    public void createConnection(String hostname, int port) {

        try {

            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket(port);

            while (true) {

                byte[] buffer = new byte[2048];
                DatagramPacket received = new DatagramPacket(buffer, buffer.length, address, port);
                socket.receive(received);

                byte[] lengthTwo = new byte[] {buffer[1],buffer[0]};

                BigInteger one;
                one = new BigInteger(lengthTwo);
                String strResult = one.toString(2);
                System.out.println("ByteArray to Binary = "+strResult);

                //String data = new String(buffer, 0, received.getLength());
                //System.out.println(test.toString());

                //System.out.println(data);
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
