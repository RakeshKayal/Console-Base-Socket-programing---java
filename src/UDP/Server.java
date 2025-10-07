package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("UDP Server running on port 6666...");
        DatagramSocket ds = new DatagramSocket(6666);

        byte[] buffer = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);

        ds.receive(dp); // waits for a packet

        String msg = new String(dp.getData(), 0, dp.getLength());
        System.out.println("Received: " + msg + " from " + dp.getAddress() + ":" + dp.getPort());

        ds.close();
    }
}
