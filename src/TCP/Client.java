package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket s= new Socket("localhost",9999);

        String msg="hello";

        DataOutputStream is= new DataOutputStream(s.getOutputStream());
        is.writeUTF(msg);
        System.out.println("msg sent ");


    }
}
