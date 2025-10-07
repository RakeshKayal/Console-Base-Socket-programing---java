package GroupMsgForClientServer;

import TCP.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {


    public static HashMap<String, Group> groupMap = new HashMap<>();

    public static void main(String[] args) throws IOException {


        System.out.println("Server is running .... ");
        ServerSocket serverSocket= new ServerSocket(8090);

        while (true){
            Socket s= serverSocket.accept();

            new ClientHandeler(s).start();
        }
    }
    public  static  class ClientHandeler extends  Thread{

        public  Socket socket;
        public String  clientName;
        public ObjectInputStream objectInputStream;
        public ObjectOutputStream outputStream;
        public  String  groupName;

        ClientHandeler(Socket socket) throws IOException {
            this.socket=socket;
            this.outputStream= new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream= new ObjectInputStream(socket.getInputStream());

        }

        @Override
        public void run() {
            try {
                 clientName= (String) objectInputStream.readObject();
                 groupName= (String) objectInputStream.readObject();

                Group group;
                synchronized (groupMap){
                    group=groupMap.get(groupName);
                    if (group==null){
                        group= new Group(groupName);
                        groupMap.put(groupName,group);
                    }
                }
                group.addMember(clientName,outputStream);
                System.out.println(clientName + "join to the "+ groupName +"group");
                group.broadcast(clientName+"join", "in"+groupName);


                while (true){
                    String msg= (String) objectInputStream.readObject();
                    group.broadcast(clientName,msg);
                }

            } catch (IOException e) {
                System.out.println("sorry");
            } catch (ClassNotFoundException e) {
                System.out.println("sorry some problem occurs");
            }

        }
    }
}
