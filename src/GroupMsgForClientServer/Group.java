package GroupMsgForClientServer;

import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Group  {

    String groupName;
   public HashMap<String , ObjectOutputStream> groupMember= new HashMap<>();

   public Group(String groupName){
       this.groupName=groupName;
   }
   public  void  addMember(String  name,ObjectOutputStream outputStream){

       synchronized (groupMember){
           groupMember.put(name,outputStream);
       }

   }
   public  void broadcast(String sender,String  msg){

       synchronized (groupMember){
           for (String s : groupMember.keySet()) {
               try {
                   ObjectOutputStream os= groupMember.get(s);
                   os.writeObject(sender + ":"+ msg);
                   os.flush();

               }catch (Exception e){
                   System.out.println("Error sending to " + s);
               }

           }

       }

   }




}
