package GroupMsgForClientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {


        Socket socket= new Socket("localhost",8090);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter group name: ");
        String group = sc.nextLine();

        os.writeObject(name);
        os.writeObject(group);
        os.flush();
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

        while (true) {
            String msg = sc.nextLine();
            os.writeObject(msg);
            os.flush();
        }


    }
}
