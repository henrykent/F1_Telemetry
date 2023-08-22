import java.io.*;
import java.net.*;


public class connectionHandler {

    public void createConnection(String hostname, int port) {

        try {

            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket(port);

            while (true) {

                byte[] buffer = new byte[2048];
                DatagramPacket received = new DatagramPacket(buffer, buffer.length, address, port);
                socket.receive(received);

                //String data = new String(buffer, 0, received.getLength());

                StringBuffer test = unpack(buffer);
                System.out.println(test.toString());

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

    public StringBuffer unpack(byte[] array) {

        int len = array.length;
        int length = len << 1;

        StringBuffer buf = new StringBuffer(length);
        buf.setLength(length);

        for (int i = 0, j = 0; i < len; ++i) {

            byte by = array[i];

            byte hi = (byte) ((by & 0xF0) >> 4);
            byte lo = (byte) (by & 0x0F);

            buf.setCharAt(j++, (char) hexaNibble(hi));
            buf.setCharAt(j++, (char) hexaNibble(lo));
        }

        return buf;
    }

    public byte hexaNibble(byte by) {
        return (byte) ((by > 9) ? (by + 'a' - 10) : (by + '0'));
    }
}
