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

                unpack testUnpack = new unpack();
                BigInteger[] testInteger = testUnpack.unpack(buffer);

                if (testInteger != null) {
                    System.out.print("[");
                    for (int i = 0; i < testInteger.length; i++) {
                        System.out.print(testInteger[i].toString(2) + ", ");
                    }
                    System.out.println("]");
                } else {
                    System.out.println("Null");
                }


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
