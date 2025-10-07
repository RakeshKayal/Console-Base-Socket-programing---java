package ClientToClientCommunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket s = new Socket("localhost", 8089);
        System.out.println("Client connected to server.");

        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        os.writeObject(name);
        os.flush();

        // Thread to continuously receive messages
        new Thread(() -> {
            try {
                while (true) {
                    String msg = (String) ois.readObject();
                    System.out.println(msg);
                }
            } catch (Exception e) {
                System.out.println("Connection closed.");
            }
        }).start();

        // Main thread: send messages
        while (true) {
            System.out.print("Enter receiver name: ");
            String receiverName = sc.nextLine();
            System.out.print("Enter message: ");
            String msg = sc.nextLine();

            os.writeObject(receiverName);
            os.writeObject(msg);
            os.flush();
        }
    }
}
