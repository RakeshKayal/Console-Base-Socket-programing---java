package AdminToAllConnectedClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Admin {
    public static void main(String[] args) throws IOException {
        System.out.println("Admin Panel");
        Socket socket = new Socket("localhost", 8090);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("admin"); // identify as admin

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter message: ");
            String msg = sc.nextLine();
            out.writeObject(msg);
        }
    }
}
