package ClientToClientCommunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    static HashMap<String, ObjectOutputStream> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Server is running ....");

        ServerSocket serverSocket = new ServerSocket(8089);
        System.out.println("Waiting for clients...");

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private String clientName;
        private ObjectOutputStream os;
        private ObjectInputStream ois;

        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            // Always create ObjectOutputStream first
            this.os = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            try {
                // Read client name
                clientName = (String) ois.readObject();

                synchronized (map) {
                    map.put(clientName, os);
                }

                System.out.println(clientName + " is connected.");

                // Continuously read receiver name and message
                while (true) {
                    String receiverName = (String) ois.readObject();
                    String msg = (String) ois.readObject();

                    ObjectOutputStream receiver;
                    synchronized (map) {
                        receiver = map.get(receiverName);
                    }

                    if (receiver != null) {
                        receiver.writeObject(clientName + " : " + msg);
                        receiver.flush();
                    } else {
                        ObjectOutputStream sender = map.get(clientName);
                        sender.writeObject("User '" + receiverName + "' not found.");
                        sender.flush();
                    }
                }

            } catch (Exception e) {
                System.out.println(clientName + " disconnected.");
                synchronized (map) {
                    map.remove(clientName);
                }
                try {
                    socket.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
