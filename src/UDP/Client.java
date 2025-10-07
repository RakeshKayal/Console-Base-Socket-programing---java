package UDP;

import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket();

        String msg = "Hello UDP";
        InetAddress ip = InetAddress.getByName("localhost");

        DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), ip, 6666);
        ds.send(dp);

        System.out.println("Message sent: " + msg);
        ds.close();

    }
}
