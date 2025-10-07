package broadCastFromServerToAll;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8090);
            System.out.println("Connected to server.");

            ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());

            while (true) {
                String msg = (String) inputStream.readObject();
                System.out.println("Server: " + msg);
            }

        } catch (Exception e) {
            System.out.println("Connection closed.");
        }
    }
}
