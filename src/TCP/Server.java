package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        System.out.println("TCP is Running  .. ");
        ServerSocket socket= new ServerSocket(9999);
        System.out.println("connection is open try to connect ... ");
        Socket s= socket.accept(); //
        System.out.println("client connected : "+socket.getInetAddress());
        DataInputStream dis = new DataInputStream(s.getInputStream());// it create a tunnel for accept the data
        System.out.println(dis.readUTF());

        System.out.println("server is still oppening .. ");



    }
}
