package AdminToAllConnectedClient;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 8090);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        out.writeObject("client"); // identify as client

        System.out.println("Connected to server. Waiting for messages...");

        while (true) {
            String msg = (String) in.readObject();
            System.out.println(msg);
        }
    }
}
