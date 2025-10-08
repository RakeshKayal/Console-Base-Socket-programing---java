package AdminToAllConnectedClient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static List<ObjectOutputStream> clientStreams = new ArrayList<>();
    private static ObjectInputStream adminInputStream;

    public static void main(String[] args) throws IOException {
        System.out.println("Server is running...");
        ServerSocket serverSocket = new ServerSocket(8090);


            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    // Thread to handle both admin and clients
    static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                name = (String) in.readObject();

                if (name.equalsIgnoreCase("admin")) {
                    System.out.println("Admin connected.");
                    adminInputStream = in;

                    // start a thread to continuously read admin messages and broadcast them
                    new Thread(() -> {
                        try {
                            while (true) {
                                String msg = (String) adminInputStream.readObject();
                                broadcast("Admin: " + msg);
                            }
                        } catch (Exception e) {
                            System.out.println("Admin disconnected.");
                        }
                    }).start();

                } else {
                    synchronized (clientStreams) {
                        clientStreams.add(out);
                        System.out.println(name + " connected.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String msg) {
            synchronized (clientStreams) {
                for (ObjectOutputStream o : clientStreams) {
                    try {
                        o.writeObject(msg);
                        o.flush();
                    } catch (IOException ignored) {}
                }
            }
        }
    }
}
