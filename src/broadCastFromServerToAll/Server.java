package broadCastFromServerToAll;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    // Store all client output streams
    private static final List<ObjectOutputStream> clientStreams = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8090);
        System.out.println("Server started on port 8090...");

        // Thread to accept new clients
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                    synchronized (clientStreams) {
                        clientStreams.add(out);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Main thread handles broadcast messages
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter broadcast message: ");
            String msg = scanner.nextLine();

            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down server...");
                serverSocket.close();
                break;
            }

            broadcast(msg); // send to all clients
        }

        scanner.close();
    }

    private static void broadcast(String msg) {
        synchronized (clientStreams) {
            for (ObjectOutputStream out : clientStreams) {
                try {
                    out.writeObject(msg);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Removing disconnected client...");
                    clientStreams.remove(out);
                    break;
                }
            }
        }
    }
}
